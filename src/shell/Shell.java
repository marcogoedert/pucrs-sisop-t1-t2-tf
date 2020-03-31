package shell;
import java.util.Scanner;

import processador.Processor;

public class Shell {
	public static void main(String args[]) {
		
		Scanner s = new Scanner(System.in);
		Processor cpu = new Processor();
		boolean closed = false;
		
		System.out.println("╔════════════════════════════════╗");
		System.out.println("║         Virtual Machine        ║");
		System.out.println("║                                ║");
		System.out.println("║ Programs:                      ║");
		System.out.println("║                                ║");
		System.out.println("║  - fibonacci-10                ║");
		System.out.println("║  - fibonacci-n                 ║");
		System.out.println("║  - fatorial                    ║");
		System.out.println("║  - bubblesort (not working)    ║");
		System.out.println("║  - print-memory                ║");
		System.out.println("║  - close                       ║");
		System.out.println("║                                ║");
		System.out.println("╚════════════════════════════════╝");
		
		while(!closed) 
		{
			System.out.print("> ");
			String p = s.next();
			switch(p) {
			case "fibonacci-10":	cpu.runFibonacci10();	break;
			case "fibonacci-n": 	cpu.runFibonacciN(); 	break;  
			case "fatorial": 		cpu.runFatorial(); 		break;
			case "bubblesort": 		cpu.runBubbleSort(); 	break;
			case "print-memory": 	cpu.memoryToString(); 	break;
			case "close":
				closed = true;
				System.out.println("Good bye.");
				break;
			default: 
				System.err.print("Choose an existing program!\n");
			}
			s.nextLine();
		}		
		s.close();
	}
}
