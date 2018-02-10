package geometria;

import java.awt.geom.Point2D;

public class Puntos {
	
	static double eps = 1e-6;

	public static void main(String[] args) {
		Point2D.Double o = new Point2D.Double(); // El punto (0, 0)
		Point2D.Double p = new Point2D.Double(0.5, 2.75);
		
		double d = o.distance(p);
		double dSquare = o.distanceSq(p);
	}
	
	public static Point2D.Double rotar(Point2D.Double p, double theta){
		double x = p.getX() * Math.cos(theta) - p.getY() * Math.sin(theta);
		double y = p.getX() * Math.sin(theta) + p.getY() * Math.cos(theta);
		return new Point2D.Double(x, y);
	}

}
