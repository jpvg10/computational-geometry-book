package geometria;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Comparator;

public class Poligonos {

	public static double eps = 1e-6;

	public static double perimetro(Point2D.Double[] poligono) {
		int n = poligono.length;
		double p = 0.0;
		for(int i=0; i<n; i++) {
			int j = (i+1)%n;
			p += poligono[i].distance(poligono[j]);
		}
		return p;
	}

	public static double area(Point2D.Double[] poligono) {
		int n = poligono.length;
		double a = 0.0;
		for(int i=0; i<n; i++) {
			int j = (i+1)%n;
			a += poligono[i].x*poligono[j].y - poligono[j].x*poligono[i].y;
		}
		return a/2.0;
	}

	public static Point2D.Double centroide(Point2D.Double[] poligono){
		int n = poligono.length;
		double a = area(poligono);
		double cx = 0.0, cy = 0.0;
		for(int i=0; i<n; i++) {
			int j = (i+1)%n;
			cx += (poligono[i].x+poligono[j].x)*(poligono[i].x*poligono[j].y - poligono[j].x*poligono[i].y);
		}
		for(int i=0; i<n; i++) {
			int j = (i+1)%n;
			cy += (poligono[i].y+poligono[j].y)*(poligono[i].x*poligono[j].y - poligono[j].x*poligono[i].y);
		}
		cx = cx / (6*a);
		cy = cy / (6*a);
		return new Point2D.Double(cx, cy);
	}

	// Retorna positivo si p1,p2,p3 hacen un giro a la izquierda
	// Retorna negativo si p1,p2,p3 hacen un giro a la derecha
	// Retorna 0 si p1,p2,p3 son colineales
	public static int giro(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3){
		double det = p1.x*(p2.y-p3.y) + p2.x*(p3.y-p1.y) + p3.x*(p1.y-p2.y);
		if(Math.abs(det) < eps){
			return 0;
		}else if(det > 0){
			return 1;
		}else {
			return -1;
		}
	}

	public static boolean esConvexo(Point2D.Double[] poligono){
		int n = poligono.length;
		boolean izq = giro(poligono[0], poligono[1], poligono[2]) >= 0;
		for(int i=1; i<n; i++){
			int j = (i+1) % n;
			int k = (i+2) % n;
			int z = giro(poligono[i], poligono[j], poligono[k]);
			if((z >= 0) != izq) return false;
		}
		return true;
	}

	public static double angulo(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {
		double[] v1 = {p1.x-p2.x, p1.y-p2.y};
		double[] v2 = {p3.x-p2.x, p3.y-p2.y};
		double m1 = Math.sqrt(v1[0]*v1[0] + v1[1]*v1[1]);
		double m2 = Math.sqrt(v2[0]*v2[0] + v2[1]*v2[1]);
		double theta = (v1[0]*v2[0] + v1[1]*v2[1]) / (m1*m2);
		return Math.acos(theta);
	}

	// Usa tambien el metodo 'giro' definido anteriormente
	public static boolean puntoEnPoligono(Point2D.Double[] poligono, Point2D.Double a) {
		int n = poligono.length;
		double anguloTotal = 0.0;
		for(int i=0; i<n; i++) {
			int j = (i+1) % n;
			if(giro(poligono[i], a, poligono[j]) == 1) {
				anguloTotal += angulo(poligono[i], a, poligono[j]);
			}else {
				anguloTotal -= angulo(poligono[i], a, poligono[j]);
			}
		}
		if(Math.abs(Math.abs(anguloTotal) - 2*Math.PI) < eps) {
			return true;
		}else {
			return false;
		}
	}

	// Usa el metodo 'giro' definido anteriormente
	public static Point2D.Double[] convexHull(Point2D.Double[] puntos) {
		int n = puntos.length;
		int k = 0;
		Point2D.Double[] hull = new Point2D.Double[2 * n];

		Arrays.sort(puntos, new Comparator<Point2D.Double>() {
			@Override
			public int compare(Point2D.Double p1, Point2D.Double p2) {
				if (Math.abs(p1.x - p2.x) < eps) {
					if(Math.abs(p1.y - p2.y) < eps) return 0;
					else if(p1.y < p2.y) return -1;
					else return 1;
				} else {
					if(p1.x < p2.x)	return -1;
					else return 1;
				}
			}
		});

		// Lower hull
		for (int i=0; i<n; i++) {
			while (k >= 2 && giro(hull[k-2], hull[k-1], puntos[i]) <= 0) {
				k--;
			}
			hull[k] = puntos[i];
			k++;
		}

		// Upper hull
		int t = k+1;
		for (int i=n-2; i>=0; i--) {
			while (k >= t && giro(hull[k-2], hull[k-1], puntos[i]) <= 0) {
				k--;
			}
			hull[k] = puntos[i];
			k++;
		}
		if (k > 1) {
			hull = Arrays.copyOfRange(hull, 0, k-1); // Remove non-hull vertices after k
		}

		return hull;
	}

}
