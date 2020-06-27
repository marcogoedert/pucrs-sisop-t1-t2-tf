package shell;

import java.util.Scanner;

import processador.ProcessManager;

public class Shell {
	private static Scanner s = new Scanner(System.in);
	
	public static boolean closed = false;

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
		System.out.println("║  -1 => close                   ║");
		System.out.println("║                                ║");
		System.out.println("╚════════════════════════════════╝");
	}
	
	public static void main(String args[]) {
		// Escreve tela inicial
		printMenu();
		System.out.println("Tip: 	 Write multiple numbers to execute multiple programs at once."); 
		System.out.println("Warning: Only try to execute eight processes at a time\n");
		ProcessManager GP = new ProcessManager();

		while(!closed) 
		{
			// Bash do usuário
			System.out.print("> ");
			String line = s.nextLine();
			String arguments[] = line.split(" ");

			for(int i = 0; i < arguments.length; i++)
			{
				GP.request(arguments[i]);
			}
			//System.out.println("Particoes alocadas: "+MM.getOcupiedPartitions()+"/8.");
		}		
		s.close();
	}
}
