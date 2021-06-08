import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

class MyJTextArea extends JTextArea implements UndoableEditListener, FocusListener, KeyListener

{

    private static final long serialVersionUID = 1L;
    private UndoManager undoManager;

    public MyJTextArea() {
        this(new String());
        undoManager = new UndoManager();
        undoManager.setLimit(20);
    }

    public MyJTextArea(String text) {
        super(text);
        getDocument().addUndoableEditListener(this);
        this.addKeyListener(this);
        this.addFocusListener(this);
    }

    private void createUndoMananger() {
        undoManager = new UndoManager();
        undoManager.setLimit(50);
    }

    private void removeUndoMananger() {
        undoManager.end();
    }

    public void focusGained(FocusEvent fe) {
        createUndoMananger();
    }

    public void focusLost(FocusEvent fe) {
        removeUndoMananger();
    }

    public void undoableEditHappened(UndoableEditEvent undoableEditEvent) {
        undoManager.addEdit(undoableEditEvent.getEdit());
    }

    public void keyPressed(KeyEvent keyEvent) {
        if ((keyEvent.getKeyCode() == KeyEvent.VK_Z) && (keyEvent.isControlDown())) {
            try {
                undoManager.undo();
            } catch (CannotUndoException cue) {
                Toolkit.getDefaultToolkit().beep();
            }
        }

        if ((keyEvent.getKeyCode() == KeyEvent.VK_Y) && (keyEvent.isControlDown())) {
            try {
                undoManager.redo();
            } catch (CannotRedoException cue) {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    public void keyReleased(KeyEvent e) { }

    public void keyTyped(KeyEvent e) { }

    public void undo()
    {
        try {
            undoManager.undo();
        } catch (CannotUndoException cue) {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    public void redo()
    {
        try {
            undoManager.redo();
        } catch (CannotRedoException cue) {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}