/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KnightTour;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author An
 */
public class KnightGame extends JFrame {
    public KnightGame() {
        super("Knight's Tour Simple Game");
        Container contents = getContentPane();
        contents.setLayout(new GridLayout(8, 8));
        //Thêm menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        //Thêm menu option
        JMenu game = new JMenu("Game");
        menuBar.add(game);
        //Thêm lựa chọn new game
        JMenuItem restart = new JMenuItem("New Game");
        game.add(restart);
        restart.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new KnightGame();
            }
        }));
        
        Board chess = new Board();
        
        for (int row = 0; row < chess.BOARD_SIZE; row++) {
            for (int column = 0; column < chess.BOARD_SIZE; column++) {
                contents.add(chess.getTile(row, column));
            }
        }
        
        setSize(1000, 1000);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    public KnightGame(int size) {
        super("Knight's Tour Simple Game");
        Container contents = getContentPane();
        contents.setLayout(new GridLayout(size, size));
        //Thêm menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        //Thêm menu option
        JMenu game = new JMenu("Game");
        game.setFont(new Font("sans-serif", Font.BOLD, 14));
        menuBar.add(game);
        //Thêm lựa chọn new game
        JMenuItem restart = new JMenuItem("New Game");
        restart.setFont(new Font("sans-serif", Font.BOLD, 14));
        game.add(restart);
        restart.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new KnightGame();
            }
        }));
        
        Board chess = new Board(size);
        
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                contents.add(chess.getTile(row, column));
            }
        }
        
        setSize(1000, 1000);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new KnightGame();
    }
}
