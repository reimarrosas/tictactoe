package com.reimarrosas;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Window {

    private static final int GRID_SIZE = 3;

    private final JFrame f;
    private final JPanel _panel;
    private final JButton[][] _buttons;

    public Window(String title, Dimension d) {
        f = new JFrame(title) {
            @Override
            public Dimension getPreferredSize() {
                return d;
            }
        };
        _panel = new JPanel();
        _buttons = new JButton[GRID_SIZE][GRID_SIZE];

        setPanel();
        setButtons();
        setFrame(title, d);
    }

    private void setFrame(String title, Dimension d) {
        f.add(_panel);
        f.pack();
        f.setTitle(title);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setVisible(true);
    }

    private void setPanel() {
        _panel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
    }

    private void setButtons() {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                JButton b = new JButton();
                _buttons[y][x] = b;
                _panel.add(b);
            }
        }
    }

    public void addButtonListeners(ButtonAction ba) {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                JButton b = _buttons[y][x];
                b.addActionListener(ba.wrapAction(b, x, y));
            }
        }
    }

    public String[][] getButtonState() {
        String[][] ret = new String[GRID_SIZE][GRID_SIZE];

        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                ret[y][x] = _buttons[y][x].getText();
            }
        }

        return ret;
    }

    public int getGridSize() {
        return GRID_SIZE;
    }

    public int createDialog(
            String title,
            String message,
            String[] options,
            String initialValue) {
        return JOptionPane.showOptionDialog(
                f,
                message,
                title,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                initialValue);
    }

    public void reset() {
        _clearButtons();
        f.revalidate();
        f.repaint();
    }

    public void close() {
        f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
    }

    private void _clearButtons() {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                _buttons[y][x].setText("");
            }
        }
    }
}
