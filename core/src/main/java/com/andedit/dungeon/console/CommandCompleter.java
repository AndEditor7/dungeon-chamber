package com.andedit.dungeon.console;

import java.util.Iterator;

import com.andedit.dungeon.util.Cycle;
import com.badlogic.gdx.utils.OrderedSet;
import com.badlogic.gdx.utils.reflect.Method;

public class CommandCompleter implements Iterable<String> {
	private final AndConsole console;
	private final OrderedSet<String> commands;

	public CommandCompleter(AndConsole console) {
		this.console = console;
		commands = new OrderedSet<>();
	}

	public void set(String text) {
		reset();
		if (console.getCommands() == null) return; 
		text = text.toLowerCase();
		for (Method method : console.getAllMethods()) {
			String name = method.getName();
			if (name.toLowerCase().startsWith(text)) {
				commands.add(name);
			}
		}
	}

	public void reset() {
		commands.clear();
	}
	
	@Override
	public Iterator<String> iterator() {
		return commands.iterator();
	}
	
	public Cycle<String> cycler() {
		return new Cycle<>(commands.orderedItems());
	}
}
