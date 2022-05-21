package com.andedit.dungeon.console;

import static com.andedit.dungeon.Main.main;

import com.andedit.dungeon.Assets;
import com.andedit.dungeon.Main;
import com.andedit.dungeon.util.Cycle;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Queue;
import com.strongjoshua.console.LogLevel;

public class ConsoleField extends TextField {	
	public boolean isFocus;
	
	private final AndConsole console;
	private final CommandCompleter completer;
	private final Queue<LogEntry> list = new Queue<>();
	
	private Cycle<String> cycle;
	
	ConsoleField(AndConsole console, Skin skin) {
		super(null, skin);
		this.console = console;
		completer = new CommandCompleter(console);
		setSize(Main.WIDTH, 12);
		setPosition(0, Main.HEIGHT, Align.topLeft);
		setTouchable(Touchable.disabled);
		setTextFieldListener(this::keyTyped);
	}
	
	@Override
	public void act(float delta) {
		for (LogEntry entry : list) {
			entry.update(delta);
			if (entry.isOut()) {
				list.removeValue(entry, true);
			}
		}
	}
	
	public void log(String msg, LogLevel level) {
		String[] lines = msg.split("\n");
		for (String line : lines) {
			list.addFirst(new LogEntry("> " + line, level));
		}
	}
	
	public void setFocus(boolean isFocus) {
		setText(null);
		this.isFocus = isFocus;
		Stage stage = getStage();
		if (stage != null) {
			if (isFocus) 
				stage.setKeyboardFocus(this);
			else stage.unfocus(this);
		}
		updateCompletion();
	}
	
	public void updateCompletion() {
		cycle = null;
		completer.set(getText());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (!Float.isNaN(parentAlpha)) {
			return;
		}
		parentAlpha = 1;
		float offset = isFocus ? getHeight() : 0;
		BitmapFont font = getStyle().font;
		if (isFocus) {
			super.draw(batch, parentAlpha);
			for (String cmd : completer) {
				batch.setColor(0, 0, 0, 0.5f);
				batch.draw(Assets.BLANK, getX(), getY() - offset, getWidth(), 12f);
				font.draw(batch, cmd + (getText().equalsIgnoreCase(cmd) ? " <" : ""), getX(), getY() - offset + 10f);
				offset += 12f;
			}
		} else {
			for (LogEntry entry : list) {
				font.setColor(entry.getColor());
				batch.setColor(0, 0, 0, entry.getTrans() * 0.5f);
				batch.draw(Assets.BLANK, getX(), getY() - offset, getWidth(), 12f);
				font.draw(batch, entry.log, getX(), getY() - offset + 10f);
				offset += 12f;
			}
		}
		batch.setColor(Color.WHITE);
		font.setColor(Color.WHITE);
	}
	
	@Override
	protected InputListener createInputListener () {
		return new TextFieldClickListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if (isDisabled() || !hasKeyboardFocus()) {
					return false;
				}
				if (keycode == Keys.UP || keycode == Keys.DOWN) {
					//cycle(keycode == Keys.DOWN);
				}
				return super.keyDown(event, keycode);
			}
		};
	}
	
	@Override
	public void drawDebug(ShapeRenderer shapes) {
		
	}
	
	private void keyTyped(TextField field, char key) {
		if (key == '\n') {
			console.execCommand(getText());
			main.showConsole(false);
			return;
		}
		if (key == '\t') {
			cycle(true);
		} else {
			updateCompletion();
		}
	}
	
	private void cycle(boolean goNext) {
		if (cycle == null) {
			cycle = completer.cycler();
		}
		if (cycle.notEmpty()) {
			String cmd = goNext ? cycle.next() : cycle.previous();
			setText(cmd);
			setCursorPosition(cmd.length());
		}
	}
}
