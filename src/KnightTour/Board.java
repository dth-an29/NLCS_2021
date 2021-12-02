/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KnightTour;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

/**
 *
 * @author An Data structure for managing interaction with a board
 */
public class Board {

    int BOARD_SIZE = 8;
    private int[][] nextTiles = new int[8][2];
    private JButton[][] tiles = new JButton[BOARD_SIZE][BOARD_SIZE];
    private int visitedTileCounter;
    private Knight knight;
    private int preCol = -1, preRow = -1;
    InputK inputK;
    boolean status;

    public Board() {
        TileHandler tileHandler = new TileHandler();

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
                tiles[row][col].addActionListener(tileHandler);
            }
        }

//        Khởi tạo trạng thái các ô chưa được ghé thăm
        for (int moveNumber = 0; moveNumber < nextTiles.length; moveNumber++) {
            nextTiles[moveNumber][0] = -1;
            nextTiles[moveNumber][1] = -1;
        }
    }

    public Board(int size) {
        this.BOARD_SIZE = size;
//        this.nextTiles = new int[size][2];
        this.tiles = new JButton[size][size];
        this.visitedTileCounter = 0;

        TileHandler tileHandler = new TileHandler();

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
                tiles[row][col].addActionListener(tileHandler);
            }
        }

        //        Khởi tạo trạng thái các ô chưa được ghé thăm
        for (int moveNumber = 0; moveNumber < nextTiles.length; moveNumber++) {
            nextTiles[moveNumber][0] = -1;
            nextTiles[moveNumber][1] = -1;
        }
    }

//    Khởi tạo bàn cờ rỗng
    public void resetBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                tiles[row][col].setText("");
                if ((row + col) % 2 == 0) {
                    tiles[row][col].setBackground(Color.WHITE);
                } else {
                    tiles[row][col].setBackground(Color.PINK);
                }
            }
        }

        tiles[knight.getCurrentRow()][knight.getCurrentCol()].setIcon(null);

        visitedTileCounter = 0;

//        Khởi tạo trạng thái các ô chưa được ghé thăm
        for (int moveNumber = 0; moveNumber < Knight.MAX_MOVE_NUM; moveNumber++) {
            nextTiles[moveNumber][0] = -1;
            nextTiles[moveNumber][1] = -1;
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

//    Đánh dấu ô đã được ghé thăm
    private void markAsVisited(int row, int col) {
        if (isWithinBound(row, col) && isNotVisited(row, col)) {
            //SwingUtilities.invokeLater(() -> {
            visitedTileCounter++;
            tiles[row][col].setIcon(knight.getIcon());
            tiles[row][col].setBackground(Color.YELLOW);
//                Đặt số vị trí vào ô đã được ghé thăm
            if (preRow != -1 && preCol != -1) {
                tiles[preRow][preCol].setText("" + (visitedTileCounter - 1));
            }
            preRow = row;
            preCol = col;
            //});
        }
    }

//    Kiểm tra xem ô đã được ghé thăm hay chưa
    public boolean isNotVisited(int row, int col) {
        return !(tiles[row][col].getBackground() == Color.YELLOW);
    }

//    Hiển thị các nước đi tiếp theo mà quân mã có thể đi
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
            JOptionPane.showMessageDialog(null, "Không tồn tại nước đi tiếp theo!\nThử lại ở bàn cờ mới bạn nhé!", "Tour Ended", JOptionPane.PLAIN_MESSAGE);
//            int out = JOptionPane.showConfirmDialog(null, "Không tồn tại nước đi tiếp theo!\nBạn có muốn thử lại không?", "Tour Ended", JOptionPane.YES_NO_OPTION);
//            if (out == JOptionPane.YES_OPTION) {
//                inputK = new InputK();
//                inputK.setVisible(true);
//            }
        }
    }

//    Tô màu lại các ô vừa mới hiển thị các nước đi có thể đi
    private void clearMoveSuggestion() {
        for (int moveNumber = 0; moveNumber < Knight.MAX_MOVE_NUM; moveNumber++) {
            int nextRow = nextTiles[moveNumber][0];
            int nextCol = nextTiles[moveNumber][1];
            if (isWithinBound(nextRow, nextCol) && (tiles[nextRow][nextCol].getBackground() == Color.GREEN)) {
                if ((nextRow + nextCol) % 2 == 0) {
                    tiles[nextRow][nextCol].setBackground(Color.WHITE);
                } else {
                    tiles[nextRow][nextCol].setBackground(Color.PINK);
                }
            }
        }
    }

//    Tính các khả năng tiếp cận của 1 ô
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
//    Lấy số khả năng tiếp cận thấp nhất trong các ô có thể đi tiếp theo
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

//    Lấy nước đi tối ưu nhất trong các nước đi đã tìm được
//    Nếu chế độ tối ưu được bật tức là biến optimizedTiedSquares true thì sẽ 
//    duyệt qua các ô tiếp theo lấy ô tối ưu nhất hay có khả năng tiếp cận là thấp nhất
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
//                    Mảng các ô tiếp theo tối ưu ở hiện tại
                    int[][] currOptimalNextTiles = knight.nextDestination(nextTiles[optimalMoveNumber][0], nextTiles[optimalMoveNumber][1]);
//                    Mảng các ô gắn liền với ô tiếp theo
                    int[][] tiedSquareNextTiles = knight.nextDestination(nextTiles[moveNumber][0], nextTiles[moveNumber][1]);

                    if (getMinAccessibilityScore(tiedSquareNextTiles) < getMinAccessibilityScore(currOptimalNextTiles)) {
                        optimalMoveNumber = moveNumber;
                    }
                }
            }
        }

        return optimalMoveNumber;
    }

//    Di chuyển quân mã dựa trên heuristic
    private boolean moveKnight(boolean optimizedTiedSquares) {
        nextTiles = knight.nextDestination();

        int optimalMoveNumber = getOptimalMoveNumber(nextTiles, optimizedTiedSquares);

        if (optimalMoveNumber <= -1) {
            JOptionPane.showMessageDialog(null, "Không tồn tại nước đi tiếp theo!\nVui lòng thử tại vị trí khác bạn nhé!", "Tour Ended", JOptionPane.PLAIN_MESSAGE);
//            int out = JOptionPane.showConfirmDialog(null, "Không tồn tại nước đi tiếp theo!\nBạn có muốn thử lại không?", "Tour Ended", JOptionPane.YES_NO_OPTION);
//            if (out == JOptionPane.YES_OPTION) {
//                inputK = new InputK();
//                inputK.setVisible(true);
//            }
        } else {
            tiles[knight.getCurrentRow()][knight.getCurrentCol()].setIcon(null);
            if (knight.move(optimalMoveNumber)) {
                markAsVisited(nextTiles[optimalMoveNumber][0], nextTiles[optimalMoveNumber][1]);
                return true;
            }
        }

        return false;
    }

    private Image scaleImage(Image image, int w, int h) {
        return image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
    }

//    start
    public void stopTour() {
        status = false;
    }

//    Chuyến đi của quân mã dựa trên heuristic và thời gian nghỉ
    public void runTour(int intialRow, int intialCol, boolean optimized, int time) {
        status = true;
        ImageIcon img = new ImageIcon(getClass().getResource("/icon/knight1.png"));
        int size = 900 / BOARD_SIZE;
        Image scaled = scaleImage(img.getImage(), size, size);
        ImageIcon scaledIcon = new ImageIcon(scaled);
        knight = new Knight(scaledIcon, intialRow, intialCol);
        try {
            Thread.sleep(500);
            resetBoard();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        markAsVisited(intialRow, intialCol);

        new Thread(() -> {
            while (visitedTileCounter < (BOARD_SIZE * BOARD_SIZE) && moveKnight(optimized)) {
                //tạo biến cờ
                if (!status) {
                    break;
                }
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (visitedTileCounter >= BOARD_SIZE * BOARD_SIZE) {
                JOptionPane.showMessageDialog(null, "Tất cả các ô đã được ghé thăm!\nHãy tiếp tục thử tại vị trí khác bạn nhé!", "Full Tour", JOptionPane.PLAIN_MESSAGE);
//                int out = JOptionPane.showConfirmDialog(null, "Tất cả các ô đã được ghé thăm!\nBạn có muốn thử tiếp không?", "Full Tour", JOptionPane.YES_NO_OPTION);
//                if (out == JOptionPane.YES_OPTION) {
//                    inputK = new InputK();
//                    inputK.setVisible(true);
//                }
            }
        }).start();
    }

//    Lớp cho người chơi tương tác với các ô trên bàn cờ
    private class TileHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            clearMoveSuggestion();
            ImageIcon img = new ImageIcon(getClass().getResource("/icon/knight1.png"));
            int size = 900 / BOARD_SIZE;
            Image scaled = scaleImage(img.getImage(), size, size);
            ImageIcon scaledIcon = new ImageIcon(scaled);

            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
//                  Di chuyển quân mã đến ô được click và nó vẫn chưa được ghé thăm
                    if (source == tiles[row][col] && isNotVisited(row, col)) {
                        if (visitedTileCounter == 0) {
                            knight = new Knight(scaledIcon, row, col);
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
                JOptionPane.showMessageDialog(null, "Tất cả các ô đã được ghé thăm!\nHãy tiếp tục thử tại vị trí khác bạn nhé!", "Full Tour", JOptionPane.INFORMATION_MESSAGE);
//                int out = JOptionPane.showConfirmDialog(null, "Tất cả các ô đã được ghé thăm!\nBạn có muốn thử tiếp không?", "Full Tour", JOptionPane.YES_NO_OPTION);
//                if (out == JOptionPane.YES_OPTION) {
//                    inputK = new InputK();
//                    inputK.setVisible(true);
//                }
            } else {
                displayMoveSuggestion();
            }
        }
    }
}
