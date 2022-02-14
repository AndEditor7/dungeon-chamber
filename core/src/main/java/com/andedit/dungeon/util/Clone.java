package com.andedit.dungeon.util;

import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;

/** Indicate that you must clone the object or self independent. */
@Documented
@Target(PARAMETER)
public @interface Clone {

}
