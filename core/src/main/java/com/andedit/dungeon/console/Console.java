package com.andedit.dungeon.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.andedit.console.CmdContext;
import com.andedit.console.ConsoleAbstract;
import com.andedit.console.command.Command;
import com.andedit.console.command.CommandList;
import com.andedit.console.command.Commands;
import com.andedit.console.util.CmdUtils;
import com.andedit.dungeon.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;

public final class Console extends ConsoleAbstract implements Disposable {
	public final ConsoleField field;
	
	//private final ObjectSet<String> cmds;
	private final Thread thread;
	private volatile boolean running = true;
	private Commands commands;
	
	public Console() {
		field = new ConsoleField(this, Assets.SKIN);
		//cmds = new ObjectSet<>();
		
		thread = new Thread(null, this::console, "Console", 0);
		thread.setDaemon(true);
		thread.start();
	}
	
	private void console() {
		final Scanner scanner = new Scanner(System.in);
		while (running) {
			final String input = scanner.nextLine();
			Gdx.app.postRunnable(() -> execute(input));
		}
		scanner.close();
	}
	
	public void setCommands(Commands commands) {
		if (commands == null) {
			return;
		}
		//cmds.clear();
		this.commands = commands;
	}
	
	public Commands getCommands() {
		return commands;
	}
	
	@Override
	public CmdContext execute(String command) {
		CmdContext context;
		
		String name = CmdUtils.getCommandName(command);
		if (name.endsWith("?")) {
			context = new CmdContext();
			logInput(context, command);
			help(getCommands(), name.substring(0, name.length()-1)).forEach(context::log);
		} else {
			context = super.execute(command);
		}
		
		field.log(context);
		return context;
	}
	
	/*
	@Override
	public void printHelp (String command) {
		boolean found = false;
		for (Method method : getAllMethods()) {
			String name = method.getName();
			if (name.equalsIgnoreCase(command)) {
				found = true;
				Annotation annotation = method.getDeclaredAnnotation(ConsoleDoc.class);
				
				StringBuilder exam = new StringBuilder();
				StringBuilder info = new StringBuilder();
				
				info.append(name).append(": ");
				
				if (annotation != null) {
					info.append(annotation.getAnnotation(ConsoleDoc.class).description());
				}

				log(info.toString());
				log(getExample(method));
			}
		}

		if (!found)
			log("Command does not exist.");
	}
	
	public List<String> getExample(String cmd) {
		ArrayList<String> array = new ArrayList<>();
		for (Method method : getAllMethods()) {
			if (method.getName().equalsIgnoreCase(cmd)) {
				array.add(getExample(method));
			}
		}
		return array;
	}
	
	String getExample(Method method) {
		Annotation annotation = method.getDeclaredAnnotation(ConsoleDoc.class);
		StringBuilder sb = new StringBuilder();
		sb.append(method.getName());
		
		String[] params;
		if (annotation != null && (params = annotation.getAnnotation(ConsoleDoc.class).paramDescriptions()).length != 0) {
			for (String param : params) {
				sb.append(" <").append(param).append('>');
			}
		} else {
			Class<?>[] types = method.getParameterTypes();
			if (types.length != 0) {
				for (Class<?> type : types) {
					sb.append(" <").append(type.getSimpleName()).append('>');
				}
			}
		}
		
		return sb.toString();
	} */
	
	@Override
	public void dispose() {
		running = false;
	}

	public void setFocus(boolean visible) {
		field.setFocus(visible);
	}

	public boolean isFocus() {
		return field.isFocus;
	}
	
	public static List<String> help(Commands commands, String string) {
		CommandList list = commands.getCommands(string);
		List<String> array = new ArrayList<>(list.commands.size());
		for (Command command : list) {
			array.add(getRequire(command));
		}
		return array;
	}
	
	public static String getRequire(Command command) {
		String[] params = null;
		CommandInfo doc = command.getAnnotation(CommandInfo.class);
		if (doc != null) {
			params = doc.parameter();
		}
		return command.getRequire(params);
	}
}
