package com.andedit.dungeon.console;

import com.strongjoshua.console.Console;
import com.strongjoshua.console.annotation.HiddenCommand;

@FunctionalInterface
public interface ConsoleGetter {
	@HiddenCommand
	Console getConsole();
}
