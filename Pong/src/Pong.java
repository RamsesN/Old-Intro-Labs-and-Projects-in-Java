import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.io.Console;

class Pair{
    double x;
    double y;
    
    public Pair(double arg1, double arg2){
	this.x = arg1;
	this.y = arg2;

    }public Pair times(double arg1){
	Pair newnumbe = new Pair((x*arg1),(y*arg1));
	return newnumbe;
    }public Pair add(Pair arg1){
	Pair newnumb = new Pair((x+arg1.x),(y+arg1.y));
	return newnumb;
    }public Pair divide(double arg1){
	Pair newnum = new Pair((x/arg1),(y/arg1));
	return newnum;
    }public void flipX(){
	x = -x;
    }public void flipY(){
	y = -y;
    }
}

class Sphere{
    Pair position;
    Pair velocity;
    Pair acceleration;
    double radius;
    double dampening;
    Color color;
    Random rand;
    Graphics g2;
    boolean point;
    public Sphere()
    {
        Random rand = new Random(); 
        position = new Pair(500.0, 400.0);
        velocity = new Pair(150.0,rand.nextDouble()*150);
        acceleration = new Pair(0, 0);
        radius = 25;
        dampening = 1.1;
        color = Color.white;
        Graphics g2;
        point = null != null;

    }

    public void update(World w, double time){
        position = position.add(velocity.times(time));
        velocity = velocity.add(acceleration.times(time));
        bounce(w, point);
    }    

    public void setPosition(Pair p){
        position = p;
    }
    public void setVelocity(Pair v){
        velocity = v;
    }

    public void setAcceleration(Pair a){
        acceleration = a;
    } 

    public void draw(Graphics g){
        Color c = g.getColor();
        
        g.setColor(color);
        g.drawOval((int)(position.x - radius), (int)(position.y - radius), (int)(2*radius), (int)(2*radius));
        g.setColor(c);
    }

    private void bounce(World w, boolean point){
    	
        if (position.x - radius < 0){
            velocity.flipX();
            position.x = radius;
	    w.resetWorld(true);
	    w.tPaused = 180;
	    
        }
        else if (position.x + radius > w.width ){
            velocity.flipX();
            position.x = w.width - radius;
	    w.resetWorld(false);
	    w.tPaused = 180;
        }
	else if( position.x - radius < w.Left.position.x + w.Left.border.x && position.y - radius <= w.Left.position.y + w.Left.border.y && position.y + radius >= w.Left.position.y ){
            velocity.flipX();
	    double rel = position.y-w.Left.position.y;
	    double degree = -(rel - 75);
	    setVelocity(new Pair(500*Math.cos(Math.toRadians(degree)),-500*Math.sin(Math.toRadians(degree))));
	}
	else if( position.x + radius > w.Right.position.x && position.y - radius <= w.Right.position.y + w.Right.border.y && position.y + radius >= w.Right.position.y  ){
            velocity.flipX();
	    double rel2 = position.y-w.Right.position.y;
	    System.out.println(rel2);
	    double degree2 = -(rel2 - 75);
	    setVelocity(new Pair(-500*Math.cos(Math.toRadians(degree2)),-500*Math.sin(Math.toRadians(degree2))));	    // position.x = w.width - radius;
	}
        if (position.y - radius < 0){
            velocity.flipY();
            position.y = radius;
        }
        else if(position.y + radius >  w.height){
            velocity.flipY();
            position.y = w.height - radius;
        }
    } 
}
class Paddle {
    Pair position;
    Pair velocity;
    Pair border;
    Color color;
    public Paddle(Pair initPosition){    
        position = initPosition;
	velocity = new Pair(0,0);
	border = new Pair(20,150);
	color = Color.white;
    }    

    public void draw(Graphics g){
        g.setColor(color);
        g.fillRect((int)(position.x), (int)(position.y), (int)(border.x), (int)(border.y));
    }
    public void update(World w, double time){
	
	Pair prev  = new Pair(position.x,position.y);	
	position = position.add(velocity.times(time));
	if(position.y<=0 || position.y+ border.y >= w.height){
	    position  = prev; 
	    
	}else{position = position.add(velocity.times(time));}
    }    
    public void setPosition(Pair p){
        position = p;
    }
    public void setVelocity(Pair v){
        velocity = v;
    }

}

class World{
    int height;
    int width;
    int tPaused;
    Sphere sphere = new Sphere();
    Pair pl = new Pair(10,300);
    Pair pr = new Pair(1000,300);
    Paddle Left = new Paddle(pl);
    Paddle Right = new Paddle(pr);
    public int p2s;
    public int p1s;
    public World(int initWidth, int initHeight){
	width = initWidth;
	height = initHeight;
	tPaused = 180;
	this.p2s = 0;
	this.p1s = 0;
    }
    public void drawSpheres(Graphics g){
	    sphere.draw(g);
    }  
    public void drawPaddles(Graphics g){  
	Left.draw(g);
	Right.draw(g);
    }
    public void updateSpheres(double time){
	sphere.update(this, time);
    }
    public void updatePaddles(double time){
	Left.update(this,time);
	Right.update(this,time);
    }
    public void resetWorld(boolean dir){
	//ADD CODE TO SET PADDLE VELOCITY BACK TO ZERO(MAYBE USE A NEW PAIR IN THE DATA MEMBERS)
	// ADD CODE TO ADD A DELAY BEFORE START
    Pair toplay = new Pair(0,0);
	System.out.println("@@@@@@@@");
	Left.setPosition(pl);
	Left.setVelocity(toplay);
	Right.setPosition(pr);
	Right.setVelocity(toplay);
	sphere.setPosition(new Pair(520.0, 400.0));
	sphere.setVelocity(new Pair(0,0));
	Random ahelp = new Random();
	double angle;	

	if(ahelp.nextDouble() > 0.5) {  
		angle = ahelp.nextDouble() * 300.0;
	}else {
		angle = ahelp.nextDouble() * -300.0;
	}
        if (dir){
		sphere.setVelocity(new Pair(150,angle));
		this.p2s ++;
        }else {	
	    sphere.setVelocity(new Pair(-150,angle));
	    this.p1s ++;
	}
	}
    }


public class Pong extends JPanel implements KeyListener{
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int FPS = 60;
    World world;
    
    class Runner implements Runnable{
	public void run(){
	    while(true){
	    	if(world.tPaused > 0){
	    		world.tPaused -= 1;
	    		}else {
	    	world.updateSpheres(1.0 / (double)FPS);
		    world.updatePaddles(1.0 / (double)FPS);
		    repaint();
	    		}
		    try{
			Thread.sleep(1000/FPS);
		}
		catch(InterruptedException e){}
	    }
	    
	}
    }
    public void keyPressed(KeyEvent e) {
	char c=e.getKeyChar();
	System.out.println("You pressed down: " + c);
	Pair r = new Pair(0,-200);
	Pair v = new Pair(0,200);
	Pair stop = new Pair(0,0); 

	    if (c == 'r'){
		    world.Left.setVelocity(r);
		}
	    if (c == 'v'){
		    world.Left.setVelocity(v);
		}
	    if (c == 'j'){
		    world.Right.setVelocity(r);
		}
	    if (c == 'm'){
		    world.Right.setVelocity(v);
		}
	    if(c == 'f'){
		world.Left.setVelocity(stop);
	    }
	    if(c == 'i'){
		world.Right.setVelocity(stop);
	    }
    }
    public void keyReleased(KeyEvent e) {
	char c=e.getKeyChar();
	System.out.println("\tYou let go of: " + c);
    }
    public void keyTyped(KeyEvent e) {
	char c = e.getKeyChar();
	System.out.println("You typed: " + c);
    }
    public void addNotify() {
	super.addNotify();
	requestFocus();
    }    
    
    public Pong(){
	world = new World(WIDTH, HEIGHT);
	addKeyListener(this);
	this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	Thread mainThread = new Thread(new Runner());
	mainThread.start();
    }
    
    public static void main(String[] args){
	JFrame frame = new JFrame("Pong!!!");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Pong newGame = new Pong();
	frame.setContentPane(newGame);
	frame.pack();
	frame.setVisible(true);
    }
    
    public void paintComponent(Graphics g) {
	super.paintComponent(g);       
	if(world.p2s < 10 && world.p1s<10 ) {
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, WIDTH, HEIGHT);
	
	String score = "Player 2 Score:" + world.p2s;
    Font stringFont = new Font( "SansSerif", Font.PLAIN, 25 );
	g.setFont( stringFont );
    g.setColor(Color.WHITE);
    g.drawString(score, 770, 80);
    
	String score1 = "Player 1 Score:" + world.p1s;
	g.setFont( stringFont );
    g.setColor(Color.WHITE);
    g.drawString(score1, 80, 80);
    repaint();
    
	world.drawSpheres(g);
	world.drawPaddles(g);
	}
	else if( world.p1s >= 10) {
		g.setColor(Color.BLACK);
    	g.fillRect(0, 0, WIDTH, HEIGHT);

        String win1 = "Tiye Sucks!... ;-)";
        Font stringFont = new Font( "SansSerif", Font.PLAIN, 100 );
        g.setFont( stringFont );
        g.setColor(Color.WHITE);
        g.drawString(win1, 150, 300);
        
	}
	else if ( world.p2s >= 10) {
		g.setColor(Color.BLACK);
    	g.fillRect(0, 0, WIDTH, HEIGHT);

        String win2 = "PLAYER 2 WINS!";
        Font stringFont = new Font( "SansSerif", Font.PLAIN, 100 );
        g.setFont( stringFont );
        g.setColor(Color.WHITE);
        g.drawString(win2, 150, 300);
	}
    }
}
