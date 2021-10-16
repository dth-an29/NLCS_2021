/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KnightTour;

import javax.swing.ImageIcon;

/**
 *
 * @author An
 * Data structure to control a knight's movement
 */
public class Knight {
    static final int MAX_MOVE_NUM = 8;
    static final int[] xMove = new int[]{2, 1, -1, -2, -2, -1, 1, 2};
    static final int[] yMove = new int[]{-1, -2, -2, -1, 1, 2, 2, 1};
    private ImageIcon icon;
    private int currentRow;
    private int currentCol;
    
    public Knight(String imageFile, int initialRow, int initialCol) {
        setIcon(imageFile);
        this.currentRow = initialRow;
        this.currentCol = initialCol;
    }
    
    public ImageIcon getIcon() {
        return icon;
    }
    
    public void setIcon(String imageFile) {
        this.icon = new ImageIcon(getClass().getResource(imageFile));
    }
    
//  Kiểm tra nước đi có hợp lệ hay không
    public boolean isValid(int nextRow, int nextCol) {
        int xDiff = Math.abs(nextRow - currentRow);
        int yDiff = Math.abs(nextCol - currentCol);
        
        return (xDiff == 1 && yDiff == 2) || (xDiff == 2 && yDiff == 1);
    }
    
//  Mảng các nước đi có thể đi được từ hàng và cột hiện tại
    public int[][] nextDestination() {
        int[][] nextTiles = new int[MAX_MOVE_NUM][2];
        
        for (int moveNumber = 0; moveNumber < MAX_MOVE_NUM; moveNumber++) {
            nextTiles[moveNumber][0] = currentRow + xMove[moveNumber]; //hàng
            nextTiles[moveNumber][1] = currentCol + yMove[moveNumber]; //cột
        }
        
        return nextTiles;
    }
    
//  Mảng các nước đi có thể đi được từ hàng và cột được chỉ định
    public int[][] nextDestination(int row, int col) {
        int[][] nextTiles = new int[MAX_MOVE_NUM][2];
        
        for (int moveNumber = 0; moveNumber < MAX_MOVE_NUM; moveNumber++) {
            nextTiles[moveNumber][0] = row + xMove[moveNumber];
            nextTiles[moveNumber][1] = col + yMove[moveNumber];
        }
        
        return nextTiles;
    }
    
//  Di chuyển quân mã đến ô tiếp theo dựa trên thứ tự các bước đi
    public  boolean move(int moveNumber) {
        if(moveNumber < MAX_MOVE_NUM) {
            currentRow += xMove[moveNumber];
            currentCol += yMove[moveNumber];
            return true;
        }
        return false;
    }
    
//  Di chuyển quân mã đến ô được chỉ định
    public boolean move(int nextRow, int nextCol) {
        if (isValid(nextRow, nextCol)) {
            currentRow = nextRow;
            currentCol = nextCol; 
            return true;
        }
        return false;
    }
    
    public int getCurrentRow() {
        return this.currentRow;
    }
    
    public int getCurrentCol() {
        return this.currentCol;
    }
}

