package com.andedit.dungeon.lwjgl3;

import java.util.Scanner;

import com.andedit.dungeon.Main;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration.GLEmulation;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
	public static void main(String[] args) {
		Main.api = new DesktopAPI();
		createApplication();
	}

	private static Lwjgl3Application createApplication() {
		return new Lwjgl3Application(Main.main, getDefaultConfiguration());
	}

	@SuppressWarnings("unused")
	private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Dungeon Chamber");
		config.setWindowSizeLimits(Main.WIDTH, Main.HEIGHT, -1, -1);
		
//		Scanner input = new Scanner(System.in);
//		System.out.println("Use ANGLE renderer? yes or no");
//		while (true) {
//			String line = input.nextLine();
//			if (line.equalsIgnoreCase("yes")) {
				config.setOpenGLEmulation(GLEmulation.ANGLE_GLES20, 3, 1);
//				break;
//			} else if (line.equalsIgnoreCase("no")) {
//				config.setOpenGLEmulation(GLEmulation.GL20, 3, 1);
//				break;
//			}
//			System.out.println("Try again; yes or no");
//		}
//		input.close();
		
		DisplayMode mode = Lwjgl3ApplicationConfiguration.getDisplayMode();
		config.setForegroundFPS(mode.refreshRate == 60 ? 100 : 60);

		// Fullscreen
		if (false) {
			config.setFullscreenMode(mode);
		}

		config.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
		return config;
	}
}