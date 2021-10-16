/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KnightTour;

import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author An
 */
public class Heuristic extends JFrame {
    public Heuristic() {
        super("Knight's Tour");
        Container contents = getContentPane();
        Board chessBoard = new Board();

        contents.setLayout(new GridLayout(chessBoard.BOARD_SIZE, chessBoard.BOARD_SIZE));
                
        for (int row = 0; row < chessBoard.BOARD_SIZE; row++) {
            for (int col = 0; col < chessBoard.BOARD_SIZE; col++) {
                contents.add(chessBoard.getTile(row, col));
            }
        }
        
        setSize(1000, 1000);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
//        for(int row = 0; row < chessBoard.BOARD_SIZE; row++) {
//            for (int col = 0; col < chessBoard.BOARD_SIZE; col++) {
//                chessBoard.runTour(row, col, false);
//                chessBoard.resetBoard();
//            }
//        }
        chessBoard.runTour(7, 5, true, 300);
    }
    
    public Heuristic(int size, int time, int initRow, int initCol) {
        super("Knight's Tour");
        Container contents = getContentPane();
        Board chessBoard = new Board(size, time);

        contents.setLayout(new GridLayout(size, size));
                
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                contents.add(chessBoard.getTile(row, col));
            }
        }
        
        setSize(1000, 1000);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
//        for(int row = 0; row < chessBoard.BOARD_SIZE; row++) {
//            for (int col = 0; col < chessBoard.BOARD_SIZE; col++) {
//                chessBoard.runTour(row, col, false);
//                chessBoard.resetBoard();
//            }
//        }
        SwingUtilities.invokeLater(()->{
            chessBoard.runTour(initRow, initCol, false, time);

        });
    }
    
    public static void main(String[] args) {
        new Heuristic();
    }
}
