package com.reimarrosas;

import java.awt.Dimension;

import javax.swing.JButton;

public class Game {

    private static final String TITLE = "Tic Tac Toe!";
    private static final int WIDTH = 480;
    private static final int HEIGHT = 480;

    private final Window _w;
    private final int _maxTurnCount;

    private String _turn = "X";
    private int _moveCount = 0;

    public Game() {
        _w = new Window(TITLE, new Dimension(WIDTH, HEIGHT));
        _maxTurnCount = _w.getGridSize() * _w.getGridSize();
        _start();
    }

    private void _start() {
        _w.addButtonListeners((b, x, y) -> e -> {
            _move(b, x, y);
        });
    }

    private void _move(JButton b, int x, int y) {
        if (!b.getText().equals("")) {
            return;
        }

        b.setText(_turn);
        _turn = switch (_turn) {
            case "X" -> "O";
            case "O" -> "X";
            default -> throw new IllegalStateException("Invalid Turn State");
        };

        ++_moveCount;
        var winner = _checkWinner(_w.getButtonState(), x, y);
        if (winner.equals("") && _moveCount != _maxTurnCount) {
            return;
        }

        String message = null;
        if (!winner.equals("")) {
            message = String.format("%s Wins!", winner);
        } else if (_moveCount == _maxTurnCount) {
            message = "Draw!";
        }

        if (message == null) {
            return;
        }

        String[] opts = new String[]{"Restart", "Quit"};
        int response = _w.createDialog(TITLE, message, opts, opts[0]);
        if (response == 0) {
            _w.reset();
            _turn = "X";
            _moveCount = 0;
            return;
        }

        _w.close();
    }

    private String _checkWinner(String[][] boardState, int x, int y) {
        var s = boardState[y][x];

        // Rows
        for (int i = 0; i < boardState.length; i++) {
            if (!boardState[i][x].equals(s)) {
                break;
            } else if (i == boardState.length - 1) {
                return s;
            }
        }

        // Cols
        for (int i = 0; i < boardState.length; i++) {
            if (!boardState[y][i].equals(s)) {
                break;
            } else if (i == boardState.length - 1) {
                return s;
            }
        }

        // Diag
        if (x == y) {
            for (int i = 0; i < boardState.length; i++) {
                if (!boardState[i][i].equals(s)) {
                    break;
                } else if (i == boardState.length - 1) {
                    return s;
                }
            }
        }

        // Anti-Diag
        if (x + y + 1 == boardState.length) {
            for (int i = 0; i < boardState.length; i++) {
                if (!boardState[i][boardState.length - 1 - i].equals(s)) {
                    break;
                } else if (i == boardState.length - 1) {
                    return s;
                }
            }
        }

        return "";
    }
}
