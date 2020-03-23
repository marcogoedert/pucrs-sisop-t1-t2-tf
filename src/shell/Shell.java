package shell;
import java.util.Scanner;

import processador.Processor;

public class Shell {
	public static void main(String args[]) {
		
		Scanner s = new Scanner(System.in);
		Processor cpu = new Processor();
		
		System.out.println("╔══════════════════════════════════════╗");
		System.out.println("║            Virtual Machine           ║");
		System.out.println("║                                      ║");
		System.out.println("║ Programs:                            ║");
		System.out.println("║                                      ║");
		System.out.println("║  - fibonacci_10                      ║");
		System.out.println("║  - fibonnaci_n                       ║");
		System.out.println("║  - fatorial                          ║");
		System.out.println("║  - bubblesort                        ║");
		System.out.println("║  - close                             ║");
		System.out.println("║                                      ║");
		System.out.println("╚══════════════════════════════════════╝");
		System.out.print("Tip: write the name of the program to run it.");
		
		while(true) {
			System.out.print("> ");
			String p = s.next();
			switch(p) {
			case "fibonacci_10": cpu.runProgram1();
			case "fibonnaci_n": cpu.runProgram2();
			case "fatorial": cpu.runProgram3();
			case "bubblesort": cpu.runProgram4();
			case "close": break;
			default: System.err.println("Choose an existing program!");
			}
			s.close();
		}
		
	}
}
