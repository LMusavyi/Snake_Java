/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.util.Random;

import javax.swing.JPanel;

public class GameWindow extends JPanel implements ActionListener{

	
	
	static final int Height = 850;
	static final int Width = 1000;
	static final int objectArea = 20;
	static final int NUMBER_OF_UNITS = (Width * Height) / (objectArea * objectArea);

	// int arrays to hold values for plane coordinates for each body part of the snake
	final int xPos[] = new int[NUMBER_OF_UNITS];
	final int yPos[] = new int[NUMBER_OF_UNITS];
	
	// initial length of the snake
	int length = 1;
	int foodEaten;
        //food position variables
	int foodX;
	int foodY;
	char direction = 'R';
	boolean inMotion = false;
	Random random;
	Timer timer;
	
	GameWindow() {
		random = new Random();
                //SIZE OF GAME WINDOW
		this.setPreferredSize(new Dimension(Width, Height));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		play();
	}	
	
	public void play() {
		addFood();
		inMotion = true;
		
                //Framerate
		timer = new Timer(100, this);
		timer.start();	
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		draw(graphics);
	}
	
	
	public void draw(Graphics graphics) {
		
		if (inMotion) {
                    //head of snake 
			graphics.setColor(Color.RED);
			graphics.fillRect(xPos[0], yPos[0], objectArea, objectArea);
                    //snake body
			for (int i = 1; i < length; i++) {
				graphics.setColor(Color.CYAN);
				graphics.fillRect(xPos[i], yPos[i], objectArea, objectArea);
			}
                        
                        //food
                        graphics.setColor(Color.GREEN);
			graphics.fillOval(foodX, foodY, objectArea,objectArea);
			
                        //score
			graphics.setColor(Color.WHITE);
			graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
			FontMetrics metrics = getFontMetrics(graphics.getFont());
			graphics.drawString("Score: " + foodEaten, (Width - metrics.stringWidth("Score: " + foodEaten)) / 50, graphics.getFont().getSize());
		
		} 
	}
        
        public void GameOver(){
            //JOptionPane that has default yes and no values - 1= no 2 = yes
            int answer = JOptionPane.showConfirmDialog(null, "Game Over! Would you like to play again?","Confirmation",JOptionPane.YES_NO_OPTION);
            if (answer==1){
               
                System.exit(0);
            }if (answer==0){
                GameWindow gameWindow = new GameWindow();
                play();
            }
        }
	
	public void addFood() {
		foodX = random.nextInt((int)(Width / objectArea))*objectArea;
		foodY = random.nextInt((int)(Height / objectArea))*objectArea;
	}
	
	public void processCollisions() {
		// Head to body collision check
		for (int i = length; i > 0; i--) {
			if (xPos[0] == xPos[i] && yPos[0] == yPos[i]) {
				inMotion = false;
                                GameOver();
			}
		}
		
		// Check to see if snake is out of bounds
		if (xPos[0] < 0 || xPos[0] > Width || yPos[0] < 0 || yPos[0] > Height) {
			inMotion = false;
                        GameOver();
		}
		
		if(!inMotion) {
			timer.stop();
		}
                
                if(xPos[0] == foodX && yPos[0] == foodY) {
			length++;
			foodEaten++;
			addFood();
		}
	
	}
        
	public void move() {
		for (int i = length; i > 0; i--) {
                        
                        //handles movement of body parts by shifting in the desired direction by one unit
			xPos[i] = xPos[i-1];
			yPos[i] = yPos[i-1];
		}
		
            switch (direction) {
                //directs movement of the head
                default:
                    xPos[0] = xPos[0] + objectArea;
                    break;
                case 'L':
                    xPos[0] = xPos[0] - objectArea;
                    break;
                case 'U':
                    yPos[0] = yPos[0] - objectArea;	
                    break;
                case 'D':
                    yPos[0] = yPos[0] + objectArea;
                    break;
            }
	}
        
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (inMotion) {
			move();
			
			processCollisions();
		}
		repaint();
	}
        
        
	
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
                            //Additional cases to disqualify movements that has snake move backwards into itself
				case KeyEvent.VK_LEFT:
					if (direction != 'R') {
						direction = 'L';
					}
					break;
					
				case KeyEvent.VK_RIGHT:
					if (direction != 'L') {
						direction = 'R';
					}
					break;
					
				case KeyEvent.VK_UP:
					if (direction != 'D') {
						direction = 'U';
					}
					break;
					
				case KeyEvent.VK_DOWN:
					if (direction != 'U') {
						direction = 'D';
					}
					break;		
			}
		}
	}
}
