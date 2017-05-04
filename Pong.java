package pong;

import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("serial")
public class Pong extends Applet implements KeyListener, Runnable {
	private boolean player1UP = false, player1DOWN = false, player2UP = false, player2DOWN = false, turn, gameRunning = false;
	private int paddleWidth = 10, paddleHeight = 80, paddleSpeed = 7, ballSize = 10, ballDX = 4, ballDY = 5, appletWidth, appletHeight, fps = 70, timeout = 30;
	
	private Player player1;
	private Player player2;
	private Ball ball;
	
	//koda, ki prepreči utripanje zaslona
	private Image offScreenImage;
	private Dimension offScreenSize;
	private Graphics offScreenGraphics;

	@Override
	public final synchronized void update(Graphics g) {
		@SuppressWarnings("deprecation")
		Dimension d = size();
		if ((offScreenImage == null) || (d.width != offScreenSize.width) || (d.height != offScreenSize.height)) {
			offScreenImage = createImage(d.width, d.height);
			offScreenSize = d;
			offScreenGraphics = offScreenImage.getGraphics();
		}
		offScreenGraphics.clearRect(0, 0, d.width, d.height);
		paint(offScreenGraphics);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	//konec kode za preprečevanje utripanja zaslona
	
	public void init(){
		setBackground(Color.decode("#121212"));
		addKeyListener(this);
		setFocusable(true);
		System.out.println("init");
	}
	
	public void start(){
		Dimension appletSize = this.getSize();
		appletHeight = appletSize.height;
		appletWidth = appletSize.width;
		player1 = new Player(10, 0, paddleWidth, paddleHeight);
		player2 = new Player(appletWidth - paddleWidth - 10, 0, paddleWidth, paddleHeight);
		ball = new Ball(appletWidth / 2, appletHeight / 2, ballSize);
		(new Thread(this)).start();
		turn = ((int)(Math.random()* 2 + 1) == 1) ? true : false;
		
		System.out.println("start");
	}
	
	public void stop(){
		System.out.println("stop");
	}
	
	public void destroy(){
		System.out.println("destroy");
	}
	
	public void paint(Graphics g){
		loadBoard(g);
		movePlayers();
		moveBall();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(true){
				repaint();
				Thread.sleep(1000 / fps);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadBoard(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(new Font("LucidaConsolas", Font.BOLD, 23));
		g.drawString(""+player1.getScore(), appletWidth / 4, 20);
		g.drawString(""+player2.getScore(), appletWidth * 3 / 4, 20);
		drawDashedLine(g, appletWidth / 2, 0, appletWidth / 2, appletHeight);
		player1.drawShape(g);
		player2.drawShape(g);
		ball.drawShape(g);
	}
	
	public void movePlayers(){
		if(player1UP && (player1.getY() > 0))
			player1.setY(player1.getY() - paddleSpeed);
		if(player1DOWN && (player1.getY() + paddleHeight < appletHeight))
			player1.setY(player1.getY() + paddleSpeed);
		if(player2UP && (player2.getY() > 0))
			player2.setY(player2.getY() - paddleSpeed);
		if(player2DOWN && (player2.getY() + paddleHeight < appletHeight))
			player2.setY(player2.getY() + paddleSpeed);
	}
	
	public void moveBall(){
		if(!gameRunning)
			startBall();
		else{
			//odboji
			if(ball.getY() - ballSize <= 0)
				ballDY = -ballDY;
			if(ball.getY() + ballSize >= appletHeight)
				ballDY = -ballDY;
			if(ball.getX() <= player1.getX() + paddleWidth){
				if(ball.getY() - ballSize / 2 > player1.getY() && ball.getY() + ballSize / 2 < player1.getY() + paddleHeight){
					ballDX = -ballDX;
					turn = !turn;
				}
				else{
					player2.setScore(player2.getScore() + 1);
					resetBall();
				}
			}
			if(ball.getX() >= player2.getX()){
				if(ball.getY() - ballSize / 2 > player2.getY() && ball.getY() + ballSize / 2 < player2.getY() + paddleHeight){
					ballDX = -ballDX;
					turn = !turn;
				}
				else{
					player1.setScore(player1.getScore() + 1);
					resetBall();
				}
			}
			ball.setX(ball.getX() + ballDX);
			ball.setY(ball.getY() + ballDY);
		}
	}
	
	public void startBall(){
		if(timeout == 0){
			ballDX = turn ? (int)(Math.random() * 3 + 3) : -(int)(Math.random() * 3 + 3);
			ballDY = (int)(Math.random() * 2 + 1) == 1 ? (int)(Math.random() * 3 + 3) : -(int)(Math.random() * 3 + 3);
			gameRunning = true;
		}
		else
			timeout--;
	}
	
	public void resetBall(){
		gameRunning = false;
		turn = !turn;
		ball.setX(appletWidth / 2);
		ball.setY(appletHeight / 2);
		timeout = 30;
	}
	
	public void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2){
        Graphics2D g2d = (Graphics2D) g.create();
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(x1, y1, x2, y2);
        g2d.dispose();
	}
	
	public void changeSpeed(boolean belowZero){
		if(belowZero){
			
		}
		else{
			
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()){
		case 87:
			player1UP = true;
			break;
		case 83: 
			player1DOWN = true;
			break;
		case 40: 
			player2DOWN = true;
			break;
		case 38: 
			player2UP = true;
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()){
		case 87:
			player1UP = false;
			break;
		case 83: 
			player1DOWN = false;
			break;
		case 40: 
			player2DOWN = false;
			break;
		case 38: 
			player2UP = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
