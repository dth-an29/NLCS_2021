/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NQueens;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

/**
 *
 * @author An
 */
public class NQueens extends JFrame {
    public NQueens() {
        super("N-Queens");
        Container ctn = getContentPane();
        Board chessBoard = new Board();
        
        ctn.setLayout(new GridLayout(chessBoard.BOARD_SIZE, chessBoard.BOARD_SIZE));
        
        for (int row = 0; row < chessBoard.BOARD_SIZE; row++) {
            for (int col = 0; col < chessBoard.BOARD_SIZE; col++) {
                ctn.add(chessBoard.getTile(row, col));
            }
        }
        
        setSize(1000, 1000);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        
        SwingUtilities.invokeLater(()->{
            chessBoard.solveNQueens();
        });
    }
    
    public NQueens(int size,int time, int iRow, int iCol) {
        super("N-Queens");
        Container ctn = getContentPane();
        Board chessBoard = new Board(size, time, iRow, iCol);
        
        ctn.setLayout(new GridLayout(size, size));
        
        //Thêm menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        //Thêm menu option
        JMenu board = new JMenu("Board");
        board.setFont(new Font("sans-serif", Font.BOLD, 14));
        menuBar.add(board);
        //Thêm lựa chọn new game
        JMenuItem restart = new JMenuItem("New Board");
        restart.setFont(new Font("sans-serif", Font.BOLD, 14));
        board.add(restart);
        restart.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
//                new InputK();
            }
        }));
        
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                ctn.add(chessBoard.getTile(row, col));
            }
        }
        
        setSize(1000, 1000);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        
        SwingUtilities.invokeLater(()->{
            chessBoard.solveNQueens();
        });
    }
    
    public static void main(String args[]) {
        new NQueens();
    }
}
