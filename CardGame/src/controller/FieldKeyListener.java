package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FieldKeyListener implements KeyListener {

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		// consumes char other than digit, backspace or delete option
		if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE
					|| c == KeyEvent.VK_DELETE)) {
			e.consume();
		}

	}

}
