package programs;

import processador.Position;;

public class Program {
	
	private static Position[] program1 = new Position[] {
			 new Position("LDI", "r1", 0),
             new Position("STD", 50, "r1"),
             new Position("LDI", "r2", 1),
             new Position("STD", 51, "r2"),
			 new Position("LDI", "r8", 52),
			 new Position("LDI", "r6", 6),
             new Position("LDI", "r7", 61),
             new Position("LDI", "r3", 0),
             new Position("ADD", "r3", "r4"),
             new Position("LDI", "r1", 0),
             new Position("ADD", "r1", "r2"),
             new Position("ADD", "r2", "r3"),
             new Position("STX", "r8", "r3"),
             new Position("ADD", "r8", 1),
			 new Position("SUB", "r7", "r8"),
			 new Position("JMPIE", "r6", "r7"),
			 new Position("STOP"),
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
