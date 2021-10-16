/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KnightTour;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

/**
 *
 * @author An Data structure for managing interaction with a board
 */
public class Board {

    int BOARD_SIZE = 8;
    private int[][] nextTiles = new int[BOARD_SIZE][2];
    private JButton[][] tiles = new JButton[BOARD_SIZE][BOARD_SIZE];
    private int visitedTileCounter;
    private Knight knight;
    private int preCol=-1, preRow=-1;

    public Board() {
        TileHandler tileHandler = new TileHandler();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                tiles[row][col] = new JButton();
                if ((row + col) % 2 == 0) {
                    tiles[row][col].setBackground(Color.WHITE);
                } else {
                    tiles[row][col].setBackground(Color.BLACK);
                }
                tiles[row][col].setOpaque(true);
                tiles[row][col].setBorder(new LineBorder(Color.BLACK));
                tiles[row][col].addActionListener(tileHandler);
            }
        }

//      Initialize next tiles is unvisited state
        for (int moveNumber = 0; moveNumber < Knight.MAX_MOVE_NUM; moveNumber++) {
            nextTiles[moveNumber][0] = -1;
            nextTiles[moveNumber][1] = -1;
        }
    }

    public Board(int size, int time) {
        this.BOARD_SIZE = size;
        this.nextTiles = new int[size][2];
        this.tiles = new JButton[size][size];
        this.visitedTileCounter = 0;

        TileHandler tileHandler = new TileHandler();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                tiles[row][col] = new JButton();
                if ((row + col) % 2 == 0) {
                    tiles[row][col].setBackground(Color.WHITE);
                } else {
                    tiles[row][col].setBackground(Color.BLACK);
                }
                tiles[row][col].setOpaque(true);
                tiles[row][col].setBorder(new LineBorder(Color.BLACK));
                tiles[row][col].addActionListener(tileHandler);
            }
        }

//      Initialize next tiles is unvisited state
        for (int moveNumber = 0; moveNumber < Knight.MAX_MOVE_NUM; moveNumber++) {
            nextTiles[moveNumber][0] = -1;
            nextTiles[moveNumber][1] = -1;
        }
    }

//  Reset the board to an empty state
    public void resetBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                tiles[row][col].setText("");
                if ((row + col) % 2 == 0) {
                    tiles[row][col].setBackground(Color.WHITE);
                } else {
                    tiles[row][col].setBackground(Color.BLACK);
                }
            }
        }

        tiles[knight.getCurrentRow()][knight.getCurrentCol()].setIcon(null);

        visitedTileCounter = 0;

//      Initialize the next tiles is unvisited state
        for (int moveNumber = 0; moveNumber < Knight.MAX_MOVE_NUM; moveNumber++) {
            nextTiles[moveNumber][0] = -1;
            nextTiles[moveNumber][1] = -1;
        }
    }

//  Get the tile at a specified coordinate 
    public JButton getTile(int row, int col) {
        if (isWithinBound(row, col)) {
            return tiles[row][col];
        } else {
            return null;
        }
    }

//  Check if a tile is inside a board
    public boolean isWithinBound(int row, int col) {
        return (row >= 0 && col >= 0) && (row < BOARD_SIZE && col < BOARD_SIZE);
    }

//  Check if this tile is visited or not
    private void markAsVisited(int row, int col) {
        if (isWithinBound(row, col) && isNotVisited(row, col)) {
            SwingUtilities.invokeLater(() -> {
                visitedTileCounter++;
                tiles[row][col].setIcon(knight.getIcon());
                tiles[row][col].setBackground(Color.YELLOW);
                if (preRow != -1 && preCol != -1) {
                    tiles[preRow][preCol].setText("" + (visitedTileCounter - 1));
                }
                preRow = row;
                preCol = col;
            });
        }
    }

//  Check if a tile has been visited
    public boolean isNotVisited(int row, int col) {
        return !(tiles[row][col].getBackground() == Color.YELLOW);
    }

//  Paint reachable tiles to suggest the next moves for the knight
    private void displayMoveSuggestion() {
        int validMoveCounter = 0;

        nextTiles = knight.nextDestination();
        for (int moveNumber = 0; moveNumber < Knight.MAX_MOVE_NUM; moveNumber++) {
            int nextRow = nextTiles[moveNumber][0];
            int nextCol = nextTiles[moveNumber][1];
            if (isWithinBound(nextRow, nextCol) && isNotVisited(nextRow, nextCol)) {
                tiles[nextRow][nextCol].setBackground(Color.GREEN);
                validMoveCounter++;
            }
        }

        if (validMoveCounter == 0) {
            JOptionPane.showMessageDialog(null, "Out of valid moves!", "Tour Ended", JOptionPane.PLAIN_MESSAGE);
        }
    }

//  Repaint the tiles that were painted for move suggestion
    private void clearMoveSuggestion() {
        for (int moveNumber = 0; moveNumber < Knight.MAX_MOVE_NUM; moveNumber++) {
            int nextRow = nextTiles[moveNumber][0];
            int nextCol = nextTiles[moveNumber][1];
            if (isWithinBound(nextRow, nextCol) && (tiles[nextRow][nextCol].getBackground() == Color.YELLOW)) {
                if ((nextRow + nextCol) % 2 == 0) {
                    tiles[nextRow][nextCol].setBackground(Color.WHITE);
                } else {
                    tiles[nextRow][nextCol].setBackground(Color.BLACK);
                }
            }
        }
    }

//  Calculate the accessbility number of a tile
    private int getAccessibilityScore(int row, int col) {
        int accessibilityScore = 0;

        for (int moveNumber = 0; moveNumber < Knight.MAX_MOVE_NUM; moveNumber++) {
            int neighborRow = row + Knight.xMove[moveNumber];
            int neighborCol = col + Knight.yMove[moveNumber];

            if (isWithinBound(neighborRow, neighborCol) && isNotVisited(neighborRow, neighborCol)) {
                accessibilityScore++;
            }
        }

        return accessibilityScore;
    }

//  Get the minimum accessibility score
    private int getMinAccessibilityScore(int[][] nextTiles) {
        int minScore = Knight.MAX_MOVE_NUM;

        for (int moveNumber = 0; moveNumber < Knight.MAX_MOVE_NUM; moveNumber++) {
            int row = nextTiles[moveNumber][0];
            int col = nextTiles[moveNumber][1];

            if (isWithinBound(row, col) && isNotVisited(row, col)) {
                minScore = Math.min(minScore, getAccessibilityScore(row, col));
            }
        }

        return minScore;
    }

//  Get the optimal move number from the suggested moves
//  Optimized false is a basic heuristic
    private int getOptimalMoveNumber(int[][] nextTiles, boolean optimizedTiedSquares) {
        int minScore = Knight.MAX_MOVE_NUM;
        int optimalMoveNumber = -1;

        for (int moveNumber = 0; moveNumber < Knight.MAX_MOVE_NUM; moveNumber++) {
            int nextRow = nextTiles[moveNumber][0];
            int nextCol = nextTiles[moveNumber][1];

            if (isWithinBound(nextRow, nextCol) && isNotVisited(nextRow, nextCol)) {
                int score = getAccessibilityScore(nextRow, nextCol);
                if (score < minScore) {
                    minScore = score;
                    optimalMoveNumber = moveNumber;
                } else if (optimizedTiedSquares && score == minScore) {
                    int[][] currOptimalNextTiles = knight.nexDestination(nextTiles[optimalMoveNumber][0], nextTiles[optimalMoveNumber][1]);
                    int[][] tiedSquareNextTiles = knight.nexDestination(nextTiles[moveNumber][0], nextTiles[moveNumber][1]);

                    if (getMinAccessibilityScore(tiedSquareNextTiles) < getMinAccessibilityScore(currOptimalNextTiles)) {
                        optimalMoveNumber = moveNumber;
                    }
                }
            }
        }

        return optimalMoveNumber;
    }

//  Move the knight based on the heuristic approach
    private boolean moveKnight(boolean optimizedTiedSquares) {
        nextTiles = knight.nextDestination();

        int optimalMoveNumber = getOptimalMoveNumber(nextTiles, optimizedTiedSquares);

        if (optimalMoveNumber <= -1) {
            JOptionPane.showMessageDialog(null, "Out of valid moves!", "Tour Ended", JOptionPane.PLAIN_MESSAGE);
        } else {
            tiles[knight.getCurrentRow()][knight.getCurrentCol()].setIcon(null);
            if (knight.move(optimalMoveNumber)) {
                markAsVisited(nextTiles[optimalMoveNumber][0], nextTiles[optimalMoveNumber][1]);
                return true;
            }
        }

        return false;
    }

//  Make a tour with a knight using heuristic approach
    public void runTour(int intialRow, int intialCol, boolean optimized, int time) {
        knight = new Knight("/KnightTour/knight.png", intialRow, intialCol);
        markAsVisited(intialRow, intialCol);
        
        new Thread(() -> {
            while (visitedTileCounter < (BOARD_SIZE * BOARD_SIZE) && moveKnight(optimized)) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (visitedTileCounter >= BOARD_SIZE * BOARD_SIZE) {
                JOptionPane.showMessageDialog(null, "All tiles have been visited!", "Full Tour", JOptionPane.PLAIN_MESSAGE);
            }
        }).start();

    }

//  A class for handling the user's interaction with the tiles on a board.
    private class TileHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            clearMoveSuggestion();

            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
//                  Move the knight to the clicked and unvisited tile
                    if (source == tiles[row][col] && isNotVisited(row, col)) {
                        if (visitedTileCounter == 0) {
                            knight = new Knight("knight.png", row, col);
                            markAsVisited(row, col);
                        } else {
                            tiles[knight.getCurrentRow()][knight.getCurrentCol()].setIcon(null);
                            if (knight.move(row, col)) {
                                markAsVisited(row, col);
                            } else {
                                tiles[knight.getCurrentRow()][knight.getCurrentCol()].setIcon(knight.getIcon());
                            }
                        }
                    }
                }
            }

            if (visitedTileCounter >= BOARD_SIZE * BOARD_SIZE) {
                JOptionPane.showMessageDialog(null, "All tiles have been visited", "Full Tour", JOptionPane.PLAIN_MESSAGE);
            } else {
                displayMoveSuggestion();
            }
        }
    }
}
