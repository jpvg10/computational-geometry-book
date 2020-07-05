import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Lineas {

	static double eps = 1e-6;

	public static void main(String[] args) {
		Point2D.Double p1 = new Point2D.Double(1, 0);
		Point2D.Double p2 = new Point2D.Double(3, 5);
		Line2D.Double linea = new Line2D.Double(p1, p2);

		Point2D.Double p3 = new Point2D.Double(-2, -1);
		double dLinea = linea.ptLineDist(p3);

		// Podemos representar un segmento con la misma clase Line2D.Double
		Line2D.Double segmento = new Line2D.Double(p1, p2);
		Point2D.Double p4 = new Point2D.Double(-2, -1);
		double dSegmento = segmento.ptSegDist(p4);
	}

	public static double distanciaSegmentos(Line2D.Double s1, Line2D.Double s2) {
		double[] d = new double[4];
		d[0] = s2.ptSegDist(s1.getP1());
		d[1] = s2.ptSegDist(s1.getP2());
		d[2] = s1.ptSegDist(s2.getP1());
		d[3] = s1.ptSegDist(s2.getP2());
		double min = Double.MAX_VALUE;
		for (int i = 0; i < 4; i++) {
			if (d[i] < min) {
				min = d[i];
			}
		}
		return min;
	}

	public static int vectoresParalelosPerpend(double[] a, double[] b) {
		if (Math.abs(a[1] * b[0] - a[0] * b[1]) < eps) {
			return 1; // Son paralelos
		} else if (Math.abs(a[0] * b[0] + a[1] * b[1]) < eps) {
			return 2; // Son perpendiculares
		} else {
			return 0; // Ni paralelos ni perpendiculares
		}
	}

	public static int lineasParalelasPerpend(Line2D.Double l1, Line2D.Double l2) {
		double[] v1 = { l1.getX2() - l1.getX1(), l1.getY2() - l1.getY1() };
		double[] v2 = { l2.getX2() - l2.getX1(), l2.getY2() - l2.getY1() };
		return vectoresParalelosPerpend(v1, v2);
	}

	public static double[] ecuacionGeneral(Line2D.Double l) {
		double[] coef = new double[3];
		Point2D p1 = l.getP1();
		Point2D p2 = l.getP2();
		if (Math.abs(p1.getX() - p2.getX()) < eps) {
			coef[0] = 1.0;
			coef[1] = 0.0;
			coef[2] = -p1.getX();
		} else {
			coef[0] = -(p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
			coef[1] = 1.0;
			coef[2] = -coef[0] * p1.getX() - p1.getY();
		}
		return coef;
	}

	public static double[] interseccion(Line2D.Double l1, Line2D.Double l2) {
		double[] e1 = ecuacionGeneral(l1);
		double[] e2 = ecuacionGeneral(l2);
		if (Math.abs(e1[0] - e2[0]) < eps && Math.abs(e1[1] - e2[1]) < eps) {
			if (Math.abs(e1[2] - e2[2]) < eps) {
				return null; // Las lineas son la misma
			}
			double[] p = { Double.NaN, Double.NaN }; // Las lineas son paralelas
			return p;
		} else {
			// Las lineas se intersectan
			double x = (e2[1] * e1[2] - e1[1] * e2[2]) / (e2[0] * e1[1] - e1[0] * e2[1]);
			double y = Math.abs(e1[1]) > eps ? -(e1[0] * x + e1[2]) : (e2[0] * x + e2[2]);
			double[] p = { x, y };
			return p;
		}
	}

	public static boolean colineales(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {
		double det = p1.x * (p2.y - p3.y) + p2.x * (p3.y - p1.y) + p3.x * (p1.y - p2.y);

		if (Math.abs(det) < eps) {
			return true;
		} else {
			return false;
		}
	}

}
