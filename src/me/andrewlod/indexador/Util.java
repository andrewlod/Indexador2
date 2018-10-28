package me.andrewlod.indexador;

public class Util {
	public static double log(double x, double base) {
		return (Math.log(x) / Math.log(base));
	}
	public static double sum(double[] v) {
		double s = 0;
		for(double i : v) {
			s+=i;
		}
		return s;
	}
	public static double[] multiEsc(double[] u, double[] v) {
		double[] resultado = new double[u.length];
		
		for(int i = 0; i < u.length; i++) {
			resultado[i] = u[i] * v[i];
		}
		
		return resultado;
	}
}
