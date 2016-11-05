package tests;

import parser.execution.values.RoboValue;
import parser.execution.values.RoboVector3D;

public class Tester {

	public static void main(String[] args) {
		Double d = new Double(5);
		Integer i = new Integer(5);
		Object k = new Integer(10);

		RoboValue rv = new RoboVector3D(12, 12, 12);
		System.out.println(rv.getClass());
	}

}
