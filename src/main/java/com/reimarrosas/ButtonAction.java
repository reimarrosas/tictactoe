package com.reimarrosas;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public interface ButtonAction {
    ActionListener wrapAction(JButton b, int x, int y);
}
