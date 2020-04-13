package shell;

import java.util.Scanner;
import memoria.MemoryManager;
import processador.Processor;

public class Shell {
	public static void main(String args[]) {
		
		Scanner s = new Scanner(System.in);
		Processor cpu = new Processor();
		MemoryManager MM = new MemoryManager();
		boolean closed = false;
		
		System.out.println("╔════════════════════════════════╗");
		System.out.println("║         Virtual Machine        ║");
		System.out.println("║                                ║");
		System.out.println("║ Programs:                      ║");
		System.out.println("║                                ║");
		System.out.println("║  1 => fibonacci-10             ║");
		System.out.println("║  2 => fibonacci-n              ║");
		System.out.println("║  3 => fatorial                 ║");
		System.out.println("║  4 => bubblesort (not working) ║");
		System.out.println("║  5 => print-memory             ║");
		System.out.println("║  6 => close                    ║");
		System.out.println("║                                ║");
		System.out.println("╚════════════════════════════════╝");
		System.out.println("Tip: Write 1 2 3 to execute multiple programs at once."); 

		while(!closed) 
		{
			System.out.print("> ");
			String line = s.nextLine();
			String arguments[] = line.split(" ");

			for(int i = 0; i < arguments.length; i++)
			{
				switch(arguments[i]) 
				{
				case "1":	cpu.runFibonacci10();	break;
				case "2": 	cpu.runFibonacciN(); 	break;  
				case "3":	cpu.runFatorial(); 		break;
				case "4":	cpu.runBubbleSort(); 	break;
				case "5": 	System.out.println(MM.printByPartition()); 	break;
				case "6":
					closed = true;
					System.out.println("Good bye.");
					break;
				default: 
					System.err.print("Choose an existing program!\n");
				}
			}
			//s.nextLine();
			cpu.main();
		}		
		s.close();
	}
}
