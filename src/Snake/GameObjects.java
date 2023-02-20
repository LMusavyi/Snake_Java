package Snake;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;

public class GameObjects extends JFrame {
    
    
    

    public GameObjects() {
        
        this.add(new GameWindow());
        this.setTitle("2D Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.pack();

    }

}

