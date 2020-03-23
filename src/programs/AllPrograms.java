package programs;

import processador.Position;;

public class AllPrograms {
	
	public AllPrograms() {
	
	}
	
	public Position[] fibonacci10() {
		return new Position[] {
				 new Position("LDD", "r1", 50),
                 new Position("LDI", "r2", 11),
                 new Position("JMPL", "r2", "r1"),
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
	}
	
	public Position[] fibonaccin() {
		return null;
	}
	
	public Position[] fatorial() {
		return null;
	}
	
	public Position[] bubblesort() {
		return null;
	}
}
