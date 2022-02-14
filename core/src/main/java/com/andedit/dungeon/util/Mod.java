package com.andedit.dungeon.util;

import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;

/** Indicate that object can be modified or will be modified. */
@Documented
@Target(PARAMETER)
public @interface Mod {

}
