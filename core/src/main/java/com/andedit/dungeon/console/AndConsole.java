package com.andedit.dungeon.console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.andedit.dungeon.Assets;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.reflect.Annotation;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Method;
import com.strongjoshua.console.AbstractConsole;
import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.ConsoleUtils;
import com.strongjoshua.console.LogLevel;
import com.strongjoshua.console.annotation.ConsoleDoc;

public final class AndConsole extends AbstractConsole {
	public final ConsoleField field;
	
	private final ObjectSet<String> cmds;
	private final Thread thread;
	private volatile boolean running = true;
	
	public AndConsole() {
		field = new ConsoleField(this, Assets.SKIN);
		cmds = new ObjectSet<>();
		logToSystem = true;
		executeHiddenCommands = false;
		setMaxEntries(1000);
		
		thread = new Thread(null, this::console, "Console", 0);
		thread.setDaemon(true);
		thread.start();
	}
	
	private void console() {
		final Scanner scanner = new Scanner(System.in);
		while (running) {
			final String input = scanner.nextLine();
			Gdx.app.postRunnable(() -> execCommand(input));
		}
		scanner.close();
	}
	
	@Override
	public void setCommandExecutor(CommandExecutor commands) {
		if (commands == null) {
			return;
		}
		cmds.clear();
		super.setCommandExecutor(commands);
		getAllMethods().forEach(m -> cmds.add(m.getName()));
	}
	
	public CommandExecutor getCommands() {
		return exec;
	}
	
	@Override
	public void execCommand(String command) {
		try {
			if (command.endsWith("?") && command.split(" ").length == 1) {
				log(command, LogLevel.COMMAND);
				printHelp(command.substring(0, command.length()-1));
				return;
			}
			super.execCommand(command);
		} catch (Exception e) {
			log("No such method found.", LogLevel.ERROR);
		}
	}
	
	@Override
	public void log(String msg, LogLevel level) {
		if (Util.isBlank(msg)) {
			return;
		}
		super.log(msg, level);
		field.log(msg, level);
	}
	
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
	}
	
	public List<Method> getAllMethods() {
		ArrayList<Method> methods = new ArrayList<Method>();
		Class<?> type = exec.getClass();
		while (type != Object.class) {
			Collections.addAll(methods, ClassReflection.getDeclaredMethods(type));
			type = type.getSuperclass();
		}
		return methods.stream().filter(this::filterMethod).collect(Collectors.toList());
	}
	
	boolean filterMethod(Method method) {
		return method.isPublic() && ConsoleUtils.canDisplayCommand(this, method);
	}
	
	@Override
	public void setVisible(boolean visible) {
		field.setFocus(visible);
	}
	
	@Override
	public boolean isVisible() {
		return field.isFocus;
	}
	
	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		field.setDisabled(disabled);
	}
	
	@Override
	public int getDisplayKeyID() {
		return Keys.GRAVE;
	}
	
	@Override
	public boolean hitsConsole(float screenX, float screenY) {
		return field.hit(screenX, screenY, true) != null;
	}
	
	@Override
	public void setMaxEntries(int numEntries) {
		log.setMaxEntries(numEntries);
	}
	
	@Override
	public void dispose() {
		running = false;
	}
}
