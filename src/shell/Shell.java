package shell;

import java.util.Scanner;
import memoria.MemoryManager;
import processador.Processor;

public class Shell {
	static Scanner s = new Scanner(System.in);
	static Processor cpu = new Processor();
	static MemoryManager MM = new MemoryManager();
	static boolean closed = false;

	public static void tryProcess(int n)
	{
		if (MM.getOcupiedPartitions() < 8) {
			switch(n){
				case 1: cpu.runFibonacci10(); break;
				case 2: cpu.runFibonacciN(); break;
				case 3: cpu.runFatorial(); break;
				case 4:	cpu.runBubbleSort(); break;
				
			}			
		} else System.err.println("Desaloque posições da memória para inserir novos processos!");		
	}

	public static void printMenu(){
		System.out.println("╔════════════════════════════════╗");
		System.out.println("║         Virtual Machine        ║");
		System.out.println("║                                ║");
		System.out.println("║ Programs:                      ║");
		System.out.println("║                                ║");
		System.out.println("║  0 => print-menu               ║");
		System.out.println("║  1 => fibonacci-10             ║");
		System.out.println("║  2 => fibonacci-n              ║");
		System.out.println("║  3 => fatorial                 ║");
		System.out.println("║  4 => bubblesort (not working) ║");
		System.out.println("║  5 => print-memory             ║");
		System.out.println("║  6 => desalocar                ║");
		System.out.println("║  7 => close                    ║");
		System.out.println("║                                ║");
		System.out.println("╚════════════════════════════════╝");
	}
	public static void main(String args[]) {
		printMenu();
		System.out.println("Tip: 	 Write 1 2 3 to execute multiple programs at once."); 
		System.out.println("Warning: Only try to execute eight processes at a time\n");
		while(!closed) 
		{
			System.out.print("> ");
			String line = s.nextLine();
			String arguments[] = line.split(" ");

			for(int i = 0; i < arguments.length; i++)
			{
				
					switch(arguments[i]) 
					{
					case "0":   printMenu(); break;
					case "1":	tryProcess(1);	break;
					case "2": 	tryProcess(2);	break;  
					case "3":	tryProcess(3);	break;
					case "4":	tryProcess(4);	break;
					case "5": 	System.out.println(MM.printByPartition()); 	break;
					case "6":	
						MM.desalocar(); 
						System.out.println("Partições desalocadas.");
						break;					
					case "7":
						closed = true;
						System.out.println("Good bye.");
						break;
					default: 
						System.err.print("Choose an existing program!\n");
					}			
			}
			//s.nextLine();
			cpu.main();
			System.out.println("Particoes alocadas: "+MM.getOcupiedPartitions()+"/8.");
		}		
		s.close();
	}
}
