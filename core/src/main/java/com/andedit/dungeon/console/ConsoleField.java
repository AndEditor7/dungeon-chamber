package com.andedit.dungeon.console;

import static com.andedit.dungeon.Main.main;

import com.andedit.console.CmdContext;
import com.andedit.console.command.Command;
import com.andedit.console.log.LogEntry;
import com.andedit.console.log.LogStatus;
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

public class ConsoleField extends TextField {	
	public boolean isFocus;
	
	private final Console console;
	private final CommandCompleter completer;
	private final Queue<ChatEntry> list = new Queue<>();
	
	private Cycle<String> cycle;
	
	ConsoleField(Console console, Skin skin) {
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
		for (ChatEntry entry : list) {
			entry.update(delta);
			if (entry.isOut()) {
				list.removeValue(entry, true);
			}
		}
	}
	
	public void log(CmdContext context) {
		for (LogEntry entry : context) {
			list.addFirst(new ChatEntry(entry));
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
		offset = isFocus ? getHeight() : 0;
		BitmapFont font = getStyle().font;
		if (isFocus) {
			super.draw(batch, parentAlpha);
			for (Command cmd : completer.list) {
				drawLine(batch, Console.getRequire(cmd), 0.5f);
			}
			for (String cmd : completer) {
				drawLine(batch, cmd + (getText().equalsIgnoreCase(cmd) ? " <" : ""), 0.5f);
			}
		} else {
			for (ChatEntry entry : list) {
				font.setColor(entry.getColor());
				drawLine(batch, entry.toString(), entry.getTrans() * 0.5f);
			}
		}
		batch.setColor(Color.WHITE);
		font.setColor(Color.WHITE);
	}
	
	protected float offset;
	protected void drawLine(Batch batch, String text, float trans) {
		batch.setColor(0, 0, 0, trans);
		batch.draw(Assets.BLANK, getX(), getY() - offset, getWidth(), 12f);
		getStyle().font.draw(batch, text.toString(), getX(), getY() - offset + 10f);
		offset += 12f;
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
			console.execute(getText());
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
