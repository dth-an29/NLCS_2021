/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NQueens;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

/**
 *
 * @author An
 */
public class Board {

    int BOARD_SIZE = 8;
    private JButton[][] tiles = new JButton[BOARD_SIZE][BOARD_SIZE];
    private int[] queens = new int[BOARD_SIZE];
    private int iRow = 1, iCol = 1, time;
    InputQ inputQ;

    public Board() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                tiles[row][col] = new JButton();
                if ((row + col) % 2 == 0) {
                    tiles[row][col].setBackground(Color.WHITE);
                } else {
                    tiles[row][col].setBackground(Color.PINK);
                }
                tiles[row][col].setOpaque(true);
                tiles[row][col].setBorder(new LineBorder(Color.BLACK));
            }
        }

        for (int q = 0; q < BOARD_SIZE; q++) {
            queens[q] = -BOARD_SIZE;
        }
    }

    public Board(int size, int time, int initRow, int initCol) {
        this.BOARD_SIZE = size;
        this.tiles = new JButton[size][size];
        this.queens = new int[size];
        this.iCol = initCol;
        this.iRow = initRow;
        this.time = time;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                tiles[row][col] = new JButton();
                if ((row + col) % 2 == 0) {
                    tiles[row][col].setBackground(Color.WHITE);
                } else {
                    tiles[row][col].setBackground(Color.PINK);
                }
                tiles[row][col].setOpaque(true);
                tiles[row][col].setBorder(new LineBorder(Color.BLACK));
            }
        }
    }
    
    public void resetBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                tiles[row][col] = new JButton();
                if ((row + col) % 2 == 0) {
                    tiles[row][col].setBackground(Color.WHITE);
                } else {
                    tiles[row][col].setBackground(Color.PINK);
                }
                tiles[row][col].setOpaque(true);
                tiles[row][col].setBorder(new LineBorder(Color.BLACK));
            }
        }
    }

    //    Lấy vị trí ô được chỉ định
    public JButton getTile(int row, int col) {
        if (isWithinBound(row, col)) {
            return tiles[row][col];
        } else {
            return null;
        }
    }

//    Kiểm tra xem ô có tồn tại trong bàn cờ hay không
    public boolean isWithinBound(int row, int col) {
        return (row >= 0 && col >= 0) && (row < BOARD_SIZE && col < BOARD_SIZE);
    }

    //    Kiểm tra xem ô này đặt quân hậu được không
    public boolean isValid(int row, int col) {
        int c1 = (col - row); //đường chéo trừ
        int c2 = (col + row); //đường chéo cộng

        for (int i = 0; i < BOARD_SIZE; i++) {
            if (queens[i] == col || queens[i] == c1 || queens[i] == c2) {
                return false;
            }
            c1++;
            c2--;
        }
        return true;
    }

    public boolean search(int q) {
        if (q >= BOARD_SIZE) {
            int out = JOptionPane.showConfirmDialog(null, "Tất cả các quân hậu đã được đặt thành công!\nBạn có muốn thử tiếp không?", "Susscess", JOptionPane.YES_NO_OPTION);
            if (out == JOptionPane.YES_OPTION) {
                inputQ = new InputQ();
                inputQ.setVisible(true);
            }
            return true;
        }
        if (queens[q] != -BOARD_SIZE) {
//            System.out.print(queens[q] + "\n");
            return search(q + 1);
        } else {
            for (int col = 0; col < BOARD_SIZE; col++) {
//                System.out.print(BOARD_SIZE);
                tiles[q][col].setBackground(Color.CYAN);
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isValid(q, col)) {
//                    System.out.print(col);
                    queens[q] = col;
                    tiles[q][col].setBackground(Color.GREEN);
                    setQueen(q, col);
                    if (search(q + 1)) {
                        return true;
                    } else {
                        queens[q] = -BOARD_SIZE;
                        tiles[q][col].setIcon(null);
                    }
                }
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ((q + col) % 2 == 0) {
                    tiles[q][col].setBackground(Color.WHITE);
                } else {
                    tiles[q][col].setBackground(Color.PINK);
                }
            }
        }
        return false;
    }

    public void setQueen(int row, int col) {
        queens[row] = (col);
        ImageIcon img = new ImageIcon(getClass().getResource("/icon/pic_queen.png"));
        int size = 1000 / BOARD_SIZE;
        Image scaled = scaleImage(img.getImage(), size, size);
        ImageIcon scaledIcon = new ImageIcon(scaled);
        tiles[row][col].setIcon(scaledIcon);
    }

    private Image scaleImage(Image image, int w, int h) {
        return image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
    }

    public void solveNQueens() {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int q = 0; q < BOARD_SIZE; q++) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queens[q] = -BOARD_SIZE;
        }
        setQueen(iRow, iCol);

        new Thread(() -> {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!search(0)) {
                int output = JOptionPane.showConfirmDialog(null, "Không tìm thấy vị trí thích hợp để đặt các quân hậu!\nBạn có muốn thử vị trí khác không?", "Information", JOptionPane.YES_NO_OPTION);
                if (output == JOptionPane.YES_OPTION) {
                    inputQ = new InputQ();
                    inputQ.setVisible(true);
                }
            } else {
                printChess();
            }
        }).start();
    }

    public void printChess() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (queens[row] == col) {
                    tiles[row][col].setBackground(Color.GREEN);
                    //    setQueen(row, col);
                    System.out.printf("Q\t");
                } else {
                    System.out.printf("*\t");
                }
            }
            System.out.printf("\n");
        }
    }
}
