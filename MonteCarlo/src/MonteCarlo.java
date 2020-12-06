
import java.util.Random;
import java.util.ArrayList;

class Circle{
    double centerX;
    double centerY;
    double radius;
    public Circle(double x, double y, double r){
        this.centerX = x;
        this.centerY = y;
        this.radius = r;
    }
}
public class MonteCarlo{
    static Random rand = new Random();
    public static void main(String[] args){
        ArrayList<Circle> circs = new ArrayList<Circle>();
        circs.add(new Circle(1, 1, 1));
        circs.add(new Circle(1, 2, 1));
        System.out.println("Estimated area:");
        System.out.println(estimateArea(circs, 100));
    }
    private static double sample(double min, double max){
        //This method returns a random number between min and max
        return min + (max - min) * rand.nextDouble();
    }
    private static boolean isIn(double x, double y, Circle c){
        //This method returns true if the point (x, y) is within the circle c, and false otherwise
        double dist = Math.sqrt(Math.pow(x - c.centerX, 2) + Math.pow(y - c.centerY, 2));
        return dist <= c.radius;
    }
    public static double estimateArea(ArrayList<Circle> circles, int numSamples){
    //YOUR CODE HERE
		double c = 0;
		double x = 0;
		double t = 0;
		double n = t/(double)numSamples;
		double area = (x*c)/n;
    	for(int i = 0; i < numSamples; i ++) {
    		if(circles.get(i).centerX + circles.get(i).radius > c) {
    			c = circles.get(i).centerX + circles.get(i).radius;
    		}if(circles.get(i).centerY + circles.get(i).radius > x)
    			x = circles.get(i).centerY + circles.get(i).radius;
    		}
     for(int i = 0; i < numSamples; i ++) {
    		if(isIn(c,x,circles.get(i))) {
				t++;
    		}
    		}
    	return area;
    }
}