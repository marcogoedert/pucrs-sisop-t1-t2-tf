package programs;

import processador.Position;;

public class Program {
	
	private static Position[] program1 = new Position[] {
			 new Position("LDD", "r1", 50),
             new Position("LDI", "r2", 11),
             new Position("JMPIL", "r2", "r1"),
             new Position("STD", 51, "r1"),
             new Position("LDD", "r3", 51),
             new Position("SUBI", "r3", 1),
             new Position("MULT", "r1", "r3"),
             new Position("LDI", "r4", 5),
             new Position("JMPIG", "r4", "r3"),
             new Position("STD", 52, "r1"),
             new Position("STOP"),
             new Position("LDI", "r5", -1),
             new Position("STD", 52, "r5"),
             new Position("STOP")
			};
	private static Position[] program2 = null;
	private static Position[] program3 = null;
	private static Position[] program4 = null;
	
	public Program() {
	
	}
	
	public Position[] fibonacci10() {
		return program1;
	}
	
	public Position[] fibonaccin() {
		return program2;
	}
	
	public Position[] fatorial() {
		return program3;
	}
	
	public Position[] bubblesort() {
		return program4;
	}
}
