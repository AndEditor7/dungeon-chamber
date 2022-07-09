package com.andedit.dungeon.console;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.andedit.dungeon.util.Platform;

/** */
@Target(METHOD)
@Retention(RUNTIME)
public @interface CommandInfo {
	/** A description about of the command. */
	String description();
	/** Names for arguments in ordered. */
	String[] parameter() default {};
	
	Platform platform() default Platform.BOTH;
}