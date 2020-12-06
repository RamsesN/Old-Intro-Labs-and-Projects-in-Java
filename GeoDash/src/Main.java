import javax.swing.JPanel;
	
	
	import javax.swing.JFrame;
	import javax.swing.JOptionPane;

	import java.awt.Color;
	import java.awt.Graphics;
	import java.awt.Graphics2D;
	import java.awt.Rectangle;
	import java.awt.Dimension;
	import java.awt.Font;
	import java.awt.event.KeyListener;
	import java.awt.event.KeyEvent;
	import java.io.File;
	import java.io.IOException;
	import java.io.*;
	
	public class Main extends JPanel implements KeyListener{
		//game dimensions
	    public static final int WIDTH = 1024;
	    public static final int HEIGHT = 768;
	    public static final int FPS = 60;
	    public static final int RADIUS = 50;
	    //number of generating levels
	    static int numLevels = 100;
	    //String[][] spots = new String[numLevels][];
	    //instances
	    Player p = new Player();   
	    Obstacle[][] Array = new Obstacle[15][numLevels*30]; 
	    Obstacle[][] Array2 = new Obstacle[15][numLevels*30];
	    Obstacle[][] Array3 = new Obstacle[15][numLevels*30];
	    boolean jumping;
	    boolean slowing;
	    int score;
	    String highScore = "";
	    //game state booleans	    
	    boolean gamerunning = false;
	    boolean gameover = false;
	    //user defined DS for combination of LevelData
	    MyDS ds = new MyDS();

	    class Runner implements Runnable{
	        public Runner() {
	        	//call method to get random levels
	        	random(ds);
	        	for (int i = 0; i < numLevels; i++) {
	        		for (int j = 0; j < 15; j++) {
	        			for (int k = 30*i; k < (30*i)+30; k++) {
	        				switch (ds.peek()[j].charAt(k-(i*30))) {
		        			case '0':
		        				Array[j][k] = new Obstacle(0, 0);
		        				Array2[j][k] = new Obstacle(0,0);
		        				Array3[j][k] = new Obstacle(0,0);
		        				break;
		        			case '1':
		        				Array[j][k] = new Obstacle(k, j);
		        				Array2[j][k] = new Obstacle(0,0);
		        				Array3[j][k] = new Obstacle(0,0);
		        				break;
		        			case '2':
		        				Array[j][k] = new Obstacle(0, 0);
		        				Array2[j][k] = new Obstacle(k, j);
		        				Array3[j][k] = new Obstacle(0,0);
		        				break;
		        			case '3':
		        				Array[j][k] = new Obstacle(0, 0);
		    					Array2[j][k] = new Obstacle(0, 0);
		    					Array3[j][k] = new Obstacle(k, j);
		    					break;
	        				}
	        			}
	        		}
	        	}	
	        }

	        public void run()
	        {
	        	//keep time
	        	long timeElapsed = 0;
	        	long startTime = System.nanoTime();
	        	//while player is alive
	            while(p.blueSquareCollision(Array) && p.redSquareCollision(Array2) && p.positionY + p.side < HEIGHT){
	            	p.move(); 	
	            	p.blueSquareCollision(Array);
	            	p.redSquareCollision(Array2);
	            	p.yellowSquareCollision(Array3);
	            	if (jumping) {
	            		p.jump();
	            		jumping = false;
	            	}
	            	if(slowing) {
	            		p.slow();
	            		slowing = false;
	            	}
	            	for (int i = 0; i < 15; i++) {
	            		for (int j = 0; j < 30*numLevels; j++) {
	            		Array[i][j].move();
	            		Array2[i][j].move();
	            		Array3[i][j].move();
	            		}
	            	}

	                repaint();
	                try{
	                    Thread.sleep(p.speed/FPS);
	                    long endTime = System.nanoTime();
	                    timeElapsed = endTime - startTime;
	            	    long l = timeElapsed/189782;
	            	    score = (int) l;
	                }
	                catch(InterruptedException e){}
	            }
	            //game ends
	            gameover = true;
	            gamerunning = false;
	            //checks if high score
	            checkScore();
	            repaint();
	        	}    
	    }
	    
	    public void keyPressed(KeyEvent e) {
	    	char c=e.getKeyChar();	
	    	if (c == 'w') 
	    		jumping = true;	    	
	    	else if (c == 'a') 
	    		slowing = true;
	    	else if (c == ' ') {
	    		if (!gamerunning) {
	    		gameover = false;
	    		gamerunning = true;		
	    		p.speed = 1000;
	            Thread Mainthread = new Thread(new Runner());
	            Mainthread.start();
	    		}
	    	}
	    	else if(c == 'q') 
	    		System.exit(1);
	    }
	    public void keyReleased(KeyEvent e) {
	       }
	    public void keyTyped(KeyEvent e) {
	    }
	    public void addNotify() {
	        super.addNotify();
	        requestFocus();
	    }
	    public Main(){
	        addKeyListener(this);
	        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	        if (gamerunning) {
	        Thread mainThread = new Thread(new Runner());
	        mainThread.start();
	        gameover = false;
			gamerunning = true;
	        }
	    }
	    public static void main(String[] args){
	        JFrame frame = new JFrame("GeoDash");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        Main world = new Main();
	        frame.setContentPane(world);
	        frame.pack();
	        frame.setVisible(true);
	        }
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);         
	        //initialize high score
	        if (highScore.equals("")) {
	        	highScore = this.getHighScore();
	        }
	        //start screen
	        if (!gameover && !gamerunning) {	
	        	g.setColor(Color.BLACK);
	            g.fillRect(0, 0, WIDTH, HEIGHT);
	
	            String string = "GEO DASH!";
	            Font stringFont = new Font( "SansSerif", Font.PLAIN, 100 );
	            g.setFont( stringFont );
	            g.setColor(Color.CYAN);
	            g.drawString(string, 240, 300);
	            
	            String thing = "Press Space to Play!";
	            Font font = new Font( "Times", Font.PLAIN, 25 );
	            g.setFont( font );
	            g.setColor(Color.white);
	            g.drawString(thing, 700, 100);
	            g.drawString("W = JUMP", 240,500);
	            g.drawString("A = SLOW", 240,530);
	            g.drawString("BLUE SQUARE = SAFE", 240,560);
	            g.drawString("RED SQUARE = DANGER", 240,590);
	            g.drawString("YELLOW SQUARE = SLOW POWERUP", 240,620);
	        }
	        //game screen
	        else if (!gameover && gamerunning) {
	        	g.setColor(Color.BLACK);
	        	g.fillRect(0, 0, WIDTH, HEIGHT);
	        	//player
	        	g.setColor(Color.WHITE);
	        	g.fillRect((int)p.positionX, (int)p.positionY,  (int)p.side,  (int)p.side);
	        	//obstacle  
	        	g.setColor(Color.cyan);
	        	for (int i = 0; i < 15; i++) {
	        		for (int j = 0; j < 30*numLevels; j++) {
	        			g.fillRect((int)Array[i][j].ObsX,(int)Array[i][j].ObsY, (int)Array[i][j].side, (int)Array[i][j].side);
	        		}
	        	}
	        	//red
	        	g.setColor(Color.RED);
	        	for (int i = 0; i < 15; i++) {
	        		for (int j = 0; j < 30*numLevels; j++) {
	        			g.fillRect((int)Array2[i][j].ObsX,(int)Array2[i][j].ObsY, (int)Array2[i][j].side, (int)Array2[i][j].side);
	        		}
	        	}
	        	//yellow
	        	for (int i = 0; i < 15; i++) {
	        		for (int j = 0; j < 30*numLevels; j++) {
	        			if (Array3[i][j].alive) {
	        				g.setColor(Color.YELLOW);
	        				g.fillRect((int)Array3[i][j].ObsX,(int)Array3[i][j].ObsY, (int)Array3[i][j].side, (int)Array3[i][j].side);
	        			}
	        		}
	        	}
	        	//title
	        	Font stringFont = new Font( "SansSerif", Font.PLAIN, 30 );
	            g.setFont( stringFont );
	        	String title = "GEODASH";
	        	g.setColor(Color.WHITE);
	        	g.drawString(title, 400, 200);
	        	//quit button
	        	Font other = new Font( "SansSerif", Font.PLAIN, 13 );
	            g.setFont(other);
	        	Rectangle quitButton = new Rectangle(10, 50, 100, 37);
	        	g.drawString("QUIT = Q", quitButton.x + 19, quitButton.y + 29);
	        	Graphics2D g2d = (Graphics2D) g;
	        	g2d.draw(quitButton);
	        	//score
	        	g.drawString("Your score: " + this.score, 900, 66);
	        	//slow
	        	if (p.canslow)
	        		g.drawString("POWERUP", 600,66);
	        	}
	       //game over screen 
	       else {
	        	g.setColor(Color.BLACK);
	        	g.fillRect(0, 0, WIDTH, HEIGHT);
	
	            String string = "GAME OVER!";
	            Font stringFont = new Font( "SansSerif", Font.PLAIN, 100 );
	            g.setFont( stringFont );
	            g.setColor(Color.RED);
	            g.drawString(string, 190, 300);
	            
	            String thing = "Press Space to Restart";
	            Font font = new Font( "Times", Font.PLAIN, 25 );
	            g.setFont( font );
	            g.setColor(Color.white);
	            g.drawString(thing, 700, 100);
	             
	            String thing2 = "Your Final Score is: "+ score;
	            g.setFont( font );
	            g.setColor(Color.white);
	            g.drawString(thing2, 380, 360);
	            g.drawString("HighScore: " + highScore, 70, 100);
		        } 
	     }    
	    public String getHighScore() {
	    	//format:   Danny: 100000
	    	FileReader readFile = null;
	    	BufferedReader reader = null;
	    	try {
	    		readFile = new FileReader("highscore.txt");
	    		reader = new BufferedReader(readFile);
	    		return reader.readLine();
	    	}
	    	catch (Exception e) {
	    		return "Nobody:0";
	    	}
	    	finally {
	    		try {
	    			if (reader != null)
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    }
	    public void checkScore() {
	    	//if player sets new high score, ask for name and input into a new txt file
	    	if (highScore.equals(""))
	    			return;
	    	if (score > Integer.parseInt((highScore).split(":")[1])) {
	    		String name = JOptionPane.showInputDialog("New Record! Please enter your name:");
	    		highScore = name + ":" + score;
	    		
	    		File scoreFile = new File("highScore.txt");
	    		if (!scoreFile.exists()) {
	    			try {
	    				scoreFile.createNewFile();
	    			}
	    			catch (IOException e){
	    				e.printStackTrace();
	    			}
	    		}
	    		FileWriter writeFile = null;
	    		BufferedWriter writer = null;
	    		try {
	    			writeFile = new FileWriter(scoreFile);
	    			writer = new BufferedWriter(writeFile);
	    			writer.write(this.highScore);
	    		}
	    		catch (Exception e) {
	    			return;
	    		}
	    		finally {
	    			try {
	    				if (writer != null)
	    					writer.close();
	    			}
	    			catch (IOException e){
	    				e.printStackTrace();
	    			}
	    		}
	    	}
	    }
	    public void random(MyDS ds2) {
	    	//assign random levels to each of spots[numLevels][]
	    	for (int i = 0; i < numLevels; i++) {
	    		int spotsNum = (int)(Math.random() * 3 + 1); 
	    		switch (spotsNum) {
	    		case 1: ds2.append(LevelData.LEVEL1);
	    		break;
	    		case 2: ds2.append(LevelData.LEVEL2);
	    		break;
	    		case 3: ds2.append(LevelData.LEVEL3);
	    		break;
	    		}
	    	}
	    }
	}

	class Player {
	    double velocityY = 0;
	    double gravity = 0.4;
	    double side = 30;
	    double ground = 70;
	    double positionX = 100;
	    double positionY = HEIGHT - side - ground;
	    boolean canjump = false;
	    boolean canslow = false;
	    int speed = 1000;
	  
	    public static final int WIDTH = 1024;
	    public static final int HEIGHT = 768;
		
		public void move() {
	         positionY += velocityY; 
	         velocityY += gravity;
		}
		public boolean blueOverlaps (Obstacle input) { 
			return positionX <= input.ObsX + input.side && positionX + side >= input.ObsX && positionY <= input.ObsY + input.side && positionY + side > input.ObsY;
		}   
		public boolean Overlaps (Obstacle input) { 
			return positionX <= input.ObsX + input.side && positionX + side >= input.ObsX && positionY <= input.ObsY + input.side && positionY + side >= input.ObsY;
		}   
		public boolean blueSquareCollision(Obstacle input[][]) {
        	for (int i = 0; i < 15; i++) {
        		for (int j = 0; j < 30*Main.numLevels; j++) {
        			if(blueOverlaps(input[i][j])) {									
	        			if (positionY + side > input[i][j].ObsY) { 
	        				//if base of Player lower than top of obstacle
	        				velocityY = 0;	
	        				positionY  = input[i][j].ObsY - side;  
	        				canjump = true;
	        			} 
	        		
	        			return false;
        			}
	    		}
	        }
	     return true;  
		}		
		public boolean redSquareCollision(Obstacle input[][]) {
        	for (int i = 0; i < 15; i++) {
        		for (int j = 0; j < 30*Main.numLevels; j++) {
        			if(Overlaps(input[i][j])) {									
	        			if (positionY + side > input[i][j].ObsY) {      
	        				// if base of Player lower than top of obstacle
	        				velocityY = 0;	
	        				positionY  = input[i][j].ObsY - side; 
	        			} 
	        		return false;
        			}
	    		}
	        }
	     return true;  
		}	
		public void yellowSquareCollision(Obstacle input[][]) {
        	for (int i = 0; i < 15; i++) {
        		for (int j = 0; j < 30*Main.numLevels; j++) {
        			if(Overlaps(input[i][j])) {									
        				input[i][j].alive = false;
        				canslow = true;
        			}
	    		}
	        } 
		}	
		public void slow() {
			if (canslow) {
				speed += 100;
				canslow = false;
			}
		}
		public void jump() {
			if (canjump) {
				velocityY += -10;
				//every jump, increase player speed
				speed -= 50;
				canjump = false;
				//if speed is too fast, cap it
				if (speed <= 100)
					speed = 100;
				} 
	    	}         
		}
	
	
	class Obstacle{
		double velocityX = 2; 
		double velocityY = 0; 
		double side = 50;
		double ObsX = 0;
		double ObsY = 0;
		boolean alive = true;
		
	    public static final int WIDTH = 1024;
	    public static final int HEIGHT = 768;
			
		public Obstacle(double input1, double input2) { 
			ObsX = input1 * side;
			ObsY = input2 * side;
		}		
	    public void move() {
		    this.ObsX -= velocityX; 
	    }
	}
	
