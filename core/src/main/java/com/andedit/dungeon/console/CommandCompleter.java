package com.andedit.dungeon.console;

import java.util.Iterator;

import com.andedit.console.command.Command;
import com.andedit.console.util.CmdUtils;
import com.andedit.dungeon.util.Cycle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedSet;

public class CommandCompleter implements Iterable<String> {
	private final Console console;
	private final OrderedSet<String> commands;
	/** List of commands for arguments auto completion */
	final Array<Command> list;

	public CommandCompleter(Console console) {
		this.console = console;
		commands = new OrderedSet<>();
		list = new Array<>();
	}

	public void set(String text) {
		reset();
		if (console.getCommands() == null) return; 
		text = text.toLowerCase();
		boolean hasSpace = text.contains(" ");
		for (Command command : console.getCommands().getAllCommands()) {
			String name = command.name.toLowerCase();
			if (name.startsWith(text)) {
				commands.add(command.name);
			}
			if (hasSpace && command.params.length > 0 && CmdUtils.split(text).length != 0) {
				if (name.startsWith(CmdUtils.getCommandName(text))) {
					list.add(command);
				}
				
			}
		}
	}

	public void reset() {
		commands.clear();
		list.clear();
	}
	
	@Override
	public Iterator<String> iterator() {
		return commands.iterator();
	}
	
	public Cycle<String> cycler() {
		return new Cycle<>(commands.orderedItems());
	}
}
