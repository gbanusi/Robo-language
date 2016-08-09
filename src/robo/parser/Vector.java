package robo.parser;

import java.util.Arrays;

/**
 * Razred predstavlja implementaciju vektora čije su komponente 
 * decimalni brojevi.
 * 
 * @author marcupic
 */
public class Vector {

	/**
	 * Komponente vektora.
	 */
	private double[] components;
	
	/**
	 * Konstruktor.
	 * @param components komponente vektora
	 */
	public Vector(double...components) {
		this.components = Arrays.copyOf(components, components.length);
	}

	/**
	 * Zbrajanje vektora. Metoda vraća novi vektor koji je
	 * jednak zbroju trenutnog vektora i predanog vektora.
	 * @param other vektor s kojim treba zbrojiti
	 * @return rezultat zbrajanja
	 */
	public Vector add(Vector other) {
		checkCompatibility(other);
		double[] res = new double[components.length];
		for(int i = 0; i < res.length; i++) {
			res[i] = components[i] + other.components[i];
		}
		return new Vector(res);
	}
	
	/**
	 * Oduzimanje vektora. Metoda vraća novi vektor koji je
	 * jednak vektoru koji se dobije kada se od trenutnog vektora
	 * oduzme predani vektor.
	 * @param other vektor koji treba oduzeti
	 * @return rezultat oduzimanja
	 */
	public Vector sub(Vector other) {
		checkCompatibility(other);
		double[] res = new double[components.length];
		for(int i = 0; i < res.length; i++) {
			res[i] = components[i] - other.components[i];
		}
		return new Vector(res);
	}
	
	/**
	 * Pomoćna metoda koja provjerava jesu li trenutni i predani
	 * vektor kompatibilni po dimenzijama.
	 * @param other vektor s kojim se treba usporediti
	 */
	private void checkCompatibility(Vector other) {
		if(components.length != other.components.length) {
			throw new RuntimeException("Incompatible vectors.");
		}
	}
	
	 @Override
	public String toString() {
		 StringBuilder sb = new StringBuilder();
		 sb.append('[');
		 for(int i = 0; i < components.length; i++) {
			 if(i>0) sb.append(", ");
			 sb.append(components[i]);
		 }
		 sb.append(']');
		return sb.toString();
	}
}
