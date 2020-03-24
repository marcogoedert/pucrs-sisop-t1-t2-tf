package processador;
import java.util.Arrays;
import programs.Program;

public class Processor{

	private static final int TAM = 1024;
	private static Position memory[] = new Position[TAM]; // 1024 posicoes de memoria
	private static int[] regs = new int[8];	// registradores
	private static int PC = 0;		// program counter
	//private Map<String, Runnable> operations = new HashMap<>();  nao vai rolar
	
	public Processor()
	{
		
	}

	public void runFibonacci10()
	{
		// to-do...
		Position p[] = new Program().fibonacci10();
		loadMemory(p);
		
		// print assembly code
		Arrays.stream(p).forEach(line -> System.out.println(line.toString()));

		//print registers
		for(int i=0; i<regs.length; i++)
			System.out.println("r" + i + ": " + regs[i]);
	}

	public void runFibonacciN()
	{
		// to-do...
	}	

	public void runFatorial()
	{
		// to-do...
	}

	public void runBubbleSort()
	{
		// to-do...
	}
	
	private void readMemory()
	{		
		boolean stopped = false;
		
		while(!stopped)
		{	
			if (PC >= memory.length) break;
			
			Position pos = memory[PC];
			switch(memory[PC].OPCode) 
			{			
			case "STOP":
				stopped = true;
				break;
			case "JMP":
				JMP(pos.num);
				break;
			case "JMPI":
				JMPI(pos.r1);
				break;
			case "JMPIG":
				JMPIG(pos.r1, pos.r2);
				break;
			case "JMPIL":
				JMPIL(pos.r1, pos.r2);
				break;
			case "JMPIE":
				JMPIE(pos.r1, pos.r2);
				break;
			case "ADDI":
				ADDI(pos.r1, pos.num);
				break;
			case "SUBI":
				SUBI(pos.r1, pos.num);
				break;
			case "LDI":
				LDI(pos.r1, pos.num);
				break;
			case "LDD":
				LDD(pos.r1, pos.num);
				break;
			case "STD":
				STD(pos.num, pos.r2);
				break;
			case "ADD":
				ADD(pos.r1, pos.r2);
				break;
			case "SUB":
				SUB(pos.r1, pos.r2);
				break;
			case "MULT":
				MULT(pos.r1, pos.r2);
				break;
			case "LDX":
				LDX(pos.r1, pos.r2);
				break;
			case "STX":
				STX(pos.r1, pos.r2);
				break;
			case "SWAP":
				SWAP(pos.r1, pos.r2);
				break;
			default:
				System.err.println("Invalid Operation: " + pos.label);
				System.exit(1);
			}
			PC++;
		}
	}

	private void loadMemory(Position[] p)
	{
		PC = 0;									// Reset PC and memory
		memory = new Position[TAM];
		for(int i = 0; i < p.length; i++) 		// Load program into memory
			memory[i] = p[i];
		readMemory();							// Read loaded program
	}
	
	private static int getRegistrador(String registrador)
	{
		
		switch(registrador)
		{
		case "r0": return 0;
		case "r1": return 1;
		case "r2": return 2;
		case "r3": return 3;
		case "r4": return 4;
		case "r5": return 5;
		case "r6": return 6;
		case "r7": return 7;
		default: throw new IllegalArgumentException("Registrador Invalido");
		}
	}

	private void JMP(int n)
	{
		// to-do...
	}
	
	private void JMPI(String Rs)
	{
		// to-do...
	}
	
	private void JMPIG(String Rs, String Rc)
	{
		// to-do...
	}
	
	private void JMPIL(String Rs, String Rc)
	{
		// to-do...
	}
	
	private void JMPIE(String Rs, String Rc)
	{
		// to-do...
	}
	
	public void ADDI(String Rd, int k)
	{
		int aux = getRegistrador(Rd);
		regs[aux] += k;
	}

	public void SUBI(String Rd, int k)
	{
		int aux = getRegistrador(Rd);
		regs[aux] -= k;
	}

	public void LDI(String Rd, int k) 
	{
		int aux = getRegistrador(Rd);
		regs[aux] = k;
	}
	
	private void LDD(String Rd, int n)
	{
		// to-do...
	}
	
	private void STD(int n, String Rs)
	{
		// to-do...
	}

	public void ADD(String Rd, String Rs)
	{
		int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		regs[aux_d] += regs[aux_s];
	}

	public void SUB(String Rd, String Rs)
	{
		int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		regs[aux_d] -= regs[aux_s];
	}

	public void MULT(String Rd, String Rs)
	{
		int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		regs[aux_d] *= regs[aux_s];
	}
	
	private void LDX(String Rd, String Rs)
	{
		// to-do...
	}
	
	private void STX(String Rd, String Rs)
	{
		// to-do...
	}
	
	private void SWAP(String Rd, String Rs)
	{
		// to-do...
	}

}
