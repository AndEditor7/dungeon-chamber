package com.andedit.dungeon.console.command;

import com.andedit.console.command.Commands;
import com.andedit.dungeon.console.CommandInfo;
import com.andedit.dungeon.console.Console;

public class ConsoleCmds extends Commands {
	@CommandInfo(description = "Gets a list of all commands.")
	public void help() {
		
	}
	
	@CommandInfo(description = "Gets information about the command.", parameter = "command")
	public void help(String string) {
		Console.help(this, string).forEach(context::log);
	}
}
