package geometria;

import java.awt.geom.Point2D;

public class Circulos {
	
	static double eps = 1e-6;
	
	public static int puntoEnCirculo(Point2D.Double c, double r, Point2D.Double p){
		double r2 = r*r;
		double dist = c.distanceSq(p);
		if(Math.abs(dist - r2) < eps){
			return 0; // En el borde
		}else if(dist > r2){
			return 1; // Afuera
		}else{
			return 2; // Adentro
		}
	}
	
	public static void circuloPuntos(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3){
		double x1 = p1.getX(), y1 = p1.getY();
		double x2 = p2.getX(), y2 = p2.getY();
		double x3 = p3.getX(), y3 = p3.getY();
		
		double det = x1*(y2-y3) + x2*(y3-y1) + x3*(y1-y2);
		if(Math.abs(det) < eps){
			// Los puntos son colineales, no existe circulo que pase por los 3
		}else{			
			double k1 = -(x1*x1) - (y1*y1);
			double k2 = -(x2*x2) - (y2*x2);
			double k3 = -(x3*x3) - (y3*y3);
			
			double d1 = k1*(y2-y3) + k2*(y3-y1) + k3*(y1-y2);
			double d2 = x1*(k2-k3) + x2*(k3-k1) + x3*(k1-k2);
			double d3 = x1*(y2*k3 - y3*k2) + x2*(y3*k1 - y1*k3) + x3*(y1*k2 - y2*k1);
			
			double A = d1/det;
			double B = d2/det;
			double C = d3/det;
			
			// Los parametros del circulo
			double x0 = -A/2;
			double y0 = -B/2;
			double r = 0.5 * Math.sqrt(A*A + B*B - 4*C*C);
		}
	}

}
