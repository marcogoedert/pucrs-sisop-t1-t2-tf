package programs;

import processador.Position;;

public class Programs {
		
	private static Position[] fibonacci10 = new Position[] {
		new Position("LDI", "r1", 0),
		new Position("STD", 50, "r1"),
		new Position("LDI", "r2", 1),
		new Position("STD", 51, "r2"),
		new Position("LDI", "r0", 52),
		new Position("LDI", "r6", 5),
		new Position("LDI", "r7", 61),
		new Position("LDI", "r3", 0),
		new Position("ADD", "r3", "r1"),
		new Position("LDI", "r1", 0),
		new Position("ADD", "r1", "r2"),
		new Position("ADD", "r2", "r3"),
		new Position("STX", "r0", "r2"),
		new Position("ADDI", "r0", 1),
		new Position("SUB", "r7", "r0"),
		new Position("JMPIG", "r6", "r7"),
		new Position("STOP")
	};

	private static Position[] fibonaccin = new Position[] {
		new Position("LDI", "r4", 20), 		
		new Position("LDI", "r5", 23),		
		new Position("JMPIL", "r5", "r4"),	
		new Position("STD", 49, "r4"), 		
		new Position("LDI", "r0", 52), 		
		new Position("LDI", "r1", 0), 		
		new Position("STD", 50, "r1"), 		
		new Position("LDI", "r2", 1), 		
		new Position("STD", 51, "r2"), 		
		new Position("LDI", "r6", 8), 		
		new Position("LDD", "r7", 49),		
		new Position("ADDI", "r7", 52), 	
		new Position("LDI", "r3", 0), 		
		new Position("ADD", "r3", "r1"), 	
		new Position("LDI", "r1", 0), 		
		new Position("ADD", "r1", "r2"), 	
		new Position("ADD", "r2", "r3"), 	
		new Position("STX", "r0", "r2"), 	
		new Position("ADDI", "r0", 1), 		
		new Position("SUB", "r7", "r0"), 	
		new Position("JMPIG", "r6", "r7"), 	
		new Position("STOP"), 				
		new Position("LDI", "r4", -1),		
		new Position("STD", 50, "r4"),		
		new Position("STOP")				
	};
	
	private static Position[] fatorial = new Position[] {
		new Position("LDI", "r0", 5), 			
		new Position("LDI", "r5", 11), 
		new Position("JMPIL", "r5", "r0"), 
		new Position("LDI", "r6", 6),
		new Position("LDI", "r1", 0),
		new Position("ADD", "r1", "r0"), 
		new Position("SUBI", "r1", 1), 
		new Position("MULT", "r0", "r1"), 
		new Position("SUBI", "r1", 1), 
		new Position("JMPIG", "r6", "r1"), 
		new Position("STD", 50, "r0"),
		new Position("STOP"),
		new Position("LDI", "r0", -1),
		new Position("STD", 50, "r0"),
		new Position("STOP")
	};
	
	private static Position[] program4;
	
	public Programs() {
	
	}
	
	public static Position[] getFibonacci10() {
		return fibonacci10;
	}
	
	public static Position[] getFibonacciN() {
		return fibonaccin;
	}
	
	public static Position[] getFatorial() {
		return fatorial;
	}
	
	public static Position[] getBubblesort() {
		return program4;
	}
}
