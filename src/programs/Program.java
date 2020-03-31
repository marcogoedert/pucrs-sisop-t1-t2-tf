package programs;

import processador.Position;;

public class Program {
	
	private static Position[] program1 = new Position[] {
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
		new Position("STOP"),
	};

	private static Position[] program2 = new Position[] {
		new Position("LDI", "r4", 20), 		//0 - Insere valor fibonacci
		new Position("LDI", "r5", 23),		//1
		new Position("JMPIL", "r5", "r4"),	//2
		new Position("STD", 49, "r4"), 		//3
		new Position("LDI", "r0", 52), 		//4
		new Position("LDI", "r1", 0), 		//5
		new Position("STD", 50, "r1"), 		//6
		new Position("LDI", "r2", 1), 		//7
		new Position("STD", 51, "r2"), 		//8
		new Position("LDI", "r6", 8), 		//9
		new Position("LDD", "r7", 49),		//10 - R7 = memoria[49]
		new Position("ADDI", "r7", 52), 	//11
		new Position("LDI", "r3", 0), 		//12
		new Position("ADD", "r3", "r1"), 	//13
		new Position("LDI", "r1", 0), 		//14
		new Position("ADD", "r1", "r2"), 	//15
		new Position("ADD", "r2", "r3"), 	//16
		new Position("STX", "r0", "r2"), 	//17
		new Position("ADDI", "r0", 1), 		//18
		new Position("SUB", "r7", "r0"), 	//19
		new Position("JMPIG", "r6", "r7"), 	//20
		new Position("STOP"), 				//21
		new Position("LDI", "r4", -1),		//22
		new Position("STD", 50, "r4"),		//23
		new Position("STOP")				//24
	};
	
	private static Position[] program3 = new Position[] {
		new Position("LDI", "r0", -5), 		//0 - Insere valor fatorial 		
		new Position("LDI", "r5", 11), // load posição stop em r5
		new Position("JMPIL", "r5", "r0"), // if r0<0 	pc = r5
		new Position("LDI", "r6", 6),
		new Position("LDI", "r1", 0),
		new Position("ADD", "r1", "r0"), //r1 = r0
		new Position("SUBI", "r1", 1), //r1--
		new Position("MULT", "r0", "r1"), //r0 = r0*r1
		new Position("SUBI", "r1", 1), // r1--
		new Position("JMPIG", "r6", "r1"), //if r1>0 	pc= r6
		new Position("STD", 50, "r0"),
		new Position("STOP"),
		new Position("LDI", "r0", -1),
		new Position("STD", 50, "r0"),
		new Position("STOP")
	};

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
