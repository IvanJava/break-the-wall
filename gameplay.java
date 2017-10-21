package ru.java2e;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class gameplay extends JPanel implements KeyListener, ActionListener {

	private boolean play = false; // it's for game don't start immediately when we start a game.
	private int score = 0;	// when we start game we have 0 scores.
	
	private int totalbricks = 21; // total amount of bricks
	private Timer Timer;	// time of ball, how fast it can move 
	private int delay = 6;
	
	private int PlayerX = 310; // starting position of the slider
	private int ballposX = 210; //staring position of ball
	private int ballposY = 350; 
	private int ballXdir = -1;
	private int ballYdir = -2;
	private  mapGenerator map;
	
	
	public gameplay () {
		map = new mapGenerator(3, 7); 
		addKeyListener(this); // to work with keylistener we have to add it
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		Timer = new Timer(delay, this); // create object for the timer and his speed
		Timer.start();
		
		
		
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.black);
		g.fillRect(1,1, 992, 892);
		
		// drawing map
		
		map.draw((Graphics2D)g);
		
		//borders
		g.setColor(Color.orange);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(681, 0, 3, 592);
		
		// scores!
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("" +score, 590, 30);
		
		// the paddle 
		g.setColor(Color.green);
		g.fillRect(PlayerX, 550, 100, 8);
		
		// the ball 
		g.setColor(Color.red);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		if (totalbricks <= 0)
		{
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You won", 290, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 350);	
		}
		
		if(ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game over, Scores: ", 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 350);
		}
		
		g.dispose();
	}
	
	@Override// for start ball's movement 
	public void actionPerformed(ActionEvent e) {
		Timer.start();
		
		// intersection with slider!!!
		if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(PlayerX, 550, 100, 8))) {
			ballYdir = -ballYdir;
		}
		// intersection with bricks!!!!!
			A: for(int i=0; i<map.map.length; i++) {
			for(int j=0; j < map.map[0].length;j++) {
				if(map.map[i][j] > 0) {
					int brickX = j*map.brickWidth + 88;
					int brickY = i*map.brickHeigth + 50;
					int brickWidth = map.brickWidth;
					int brickHeigth = map.brickHeigth;
					
					Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeigth);
					Rectangle ballRect = new Rectangle (ballposX, ballposY, 20, 20);
					Rectangle brickRect = rect;
					
					if(ballRect.intersects(brickRect)) {
						map.setBrickValue(0, i, j);
						totalbricks--;
						score += 5;
						
						if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
							ballXdir = - ballXdir;
						}else {
							ballYdir = - ballYdir;
						}
						break A;
					}
					
				}
			}
		}
		
		if(play) {
			ballposX += ballXdir;		// it's the movement itself
			ballposY += ballYdir;
			
			if (ballposX < 0) {			// it's for ball's attach of left border
				ballXdir= -ballXdir;
			}
			
			if (ballposY < 0) {			// it's for ball's attach of top border
				ballYdir= -ballYdir;
			}
			
			if (ballposX > 670) {		// it's for ball's attach of right border
				ballXdir= -ballXdir;
			}
		}
		
		repaint();
		
	}

	@Override // this method bellow is about moving the slider
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
			if (PlayerX >= 600) {
				PlayerX = 600;
			}else {
				moveRight();
			}
		}
		
	if(e.getKeyCode()== KeyEvent.VK_LEFT) {
		if (PlayerX < 10) {
			PlayerX = 10;
		}else {
			moveLeft();
		}
	}
	if(e.getKeyCode() == KeyEvent.VK_ENTER) {
		if (!play) {
			play = true;
			ballposX = 120;
			ballposY = 350;
			ballXdir = -1;
			ballYdir = -2;
			PlayerX = 310;
			totalbricks = 21;
			map = new mapGenerator (3,7);
			
			repaint();
		}
	}
	
}
	
	public void moveRight() {
		play = true;
		PlayerX+=20;
	}
	public void moveLeft() {
		play=true;
		PlayerX-=20;
	}
	
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

}
}