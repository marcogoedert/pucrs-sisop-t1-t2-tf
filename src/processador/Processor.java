package processador;

import java.util.LinkedList;
import java.util.Queue;

import memoria.MemoryManager;
import memoria.MemoryManager.*;
import programs.Program;

public class Processor{

	private MemoryManager MM = new MemoryManager();
	private Queue<Process> processes = new LinkedList<Process>();

	public class Process
	{
		// Process Control Block
		private int id, PC = 0;
		private int[] regs = new int[8];
		private boolean executing = true;
		private Partition partition = null;

		public Process(int id, Position p[])
		{
			this.id = id;
			this.partition = MM.alocar(p);
		}

		public int getId()
		{
            return this.id;
		}

		public String getRegs()
		{
			StringBuilder sb = new StringBuilder();

			sb.append("R0: ").append(regs[0]).
			 append("\tR1: ").append(regs[1]).
			 append("\tR2: ").append(regs[2]).
			 append("\tR3: ").append(regs[3]).
			 append("\tR4: ").append(regs[4]).
			 append("\tR5: ").append(regs[5]).
			 append("\tR6: ").append(regs[6]).
			 append("\tR7: ").append(regs[7]);

			return sb.toString();
		}

		public boolean isExecuting(){
			return this.executing;
		}

		public Partition getPartition(){
			return this.partition;
		}

		@Override
		public String toString(){
			StringBuilder sb = new StringBuilder("PCB Id: ").append(this.id).
										  append("\tExecuting: ").append(this.executing).
										  append("\n").append(this.partition.toString()).
										  append(this.getRegs()).append("\n");
			return sb.toString();
		}
	}

	public Processor()
	{
		
	}

	public void main(){
		//DEBUG START
		System.out.println("Entrei na main!\tProcessos na fila: " + processes.size());
		//DEBUG FINISH
		int timeSlice = 20;
		while(!processes.isEmpty()){
			Process p = processes.peek();
			//DEBUG START
			System.out.println("Executando processo: \n" + p.toString());
			//DEBUG FINISH
			for(int i = 0; i < timeSlice; i++)
			{
				//DEBUG START
				System.out.println("timeSlice counter = "+i);
				//DEBUG FINISH
				readInstruction(p);
				if (!p.executing)
					break;
			}

			if(p.executing)
				processes.add(processes.poll());
			else			
				processes.remove();
		}
		System.out.println(MM.printByPartition());
		MM.desalocar();
	}

	public void runFibonacci10()
	{
		int id = processes.size();
		Position program[] = new Program().fibonacci10();
		Process process = new Process(id, program);
		processes.add(process);
		//DEBUG START
		System.out.println("Entrei em runFibonacci10!\n"+process.toString()+"\nProcesses length: "+processes.size()+"\n");
		//DEBUG FINISH
	}

	public void runFibonacciN()
	{
		int id = processes.size();
		Position program[] = new Program().fibonaccin();
		Process process = new Process(id, program);
		processes.add(process);
		//DEBUG START
		System.out.println("Entrei em runFibonacciN!\n"+process.toString()+"\nProcesses length: "+processes.size()+"\n");
		//DEBUG FINISH
	}	
	
	public void runFatorial()
	{
		int id = processes.size();
		Position program[] = new Program().fatorial();
		Process process = new Process(id, program);
		processes.add(process);
		//DEBUG START
		System.out.println("Entrei em runFatorial!\n"+process.toString()+"\nProcesses length: "+processes.size()+"\n");
		//DEBUG FINISH
	}
	
	public void runBubbleSort()
	{
	}
	
	private void readInstruction(Process process)
	{		
		int posAbs = process.PC + process.getPartition().getPos0(); // PosAbs = PosRel + Part.Pos0 
		Position pos = MM.getPosition(posAbs); 

		//DEBUG START
		System.out.println("Entrei em readInstruction! Executando processo: "+process.getId());
		System.out.println("posAbsoluta: "+posAbs+" (PC: "+process.PC+"\tPos0: "+process.getPartition().getPos0()+")\nPosition["+posAbs+"] = " + pos.toString()+"\n");
		//DEBUG FINISH
		
		switch(pos.OPCode) 
		{			
		case "STOP":
			process.executing = false;
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
		process.PC++;
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

	public void JMP(int k){
        processes.peek().PC = k;
	}

    public void JMPI(String Rs){
        int aux = getRegistrador(Rs);
        processes.peek().PC = processes.peek().regs[aux];
    }

    public void JMPIG(String Rs, String Rc){
        int aux1 = getRegistrador(Rc);
        int aux2 = getRegistrador(Rs);

        if (processes.peek().regs[aux1] > 0){
            processes.peek().PC = processes.peek().regs[aux2];
        }
    }

    public void JMPIL(String Rs, String Rc){
        int aux1 = getRegistrador(Rc);
        int aux2 = getRegistrador(Rs);

        if (processes.peek().regs[aux1] < 0){
            processes.peek().PC = processes.peek().regs[aux2];
        }
	}
	
	public void JMPIE(String Rs, String Rc){
        int aux1 = getRegistrador(Rc);
        int aux2 = getRegistrador(Rs);

        if (processes.peek().regs[aux1] == 0){
            processes.peek().PC = processes.peek().regs[aux2];
        }
	}
	
	public void ADDI(String Rd, int k)
	{
		int aux = getRegistrador(Rd);
		processes.peek().regs[aux] += k;
	}

	public void SUBI(String Rd, int k)
	{
		int aux = getRegistrador(Rd);
		processes.peek().regs[aux] -= k;
	}

	public void LDI(String Rd, int k) 
	{
		int aux = getRegistrador(Rd);
		processes.peek().regs[aux] = k;
	}
	
	private void LDD(String Rd, int n)
	{
		processes.peek().regs[getRegistrador(Rd)] = MM.getPosition(n).num;
	}
	
	private void STD(int n, String Rs)
    {
		MM.setPosition(n, new Position(processes.peek().regs[getRegistrador(Rs)]));
    }

	public void ADD(String Rd, String Rs)
	{
		int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		processes.peek().regs[aux_d] += processes.peek().regs[aux_s];
	}

	public void SUB(String Rd, String Rs)
	{
		int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		processes.peek().regs[aux_d] -= processes.peek().regs[aux_s];
	}

	public void MULT(String Rd, String Rs)
	{
		int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		processes.peek().regs[aux_d] *= processes.peek().regs[aux_s];
	}
	
	private void LDX(String Rd, String Rs)
	{
		int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		processes.peek().regs[aux_d] = MM.getPosition(processes.peek().regs[aux_s]).num;
	}
	
	private void STX(String Rd, String Rs)
    {
        int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		MM.setPosition(processes.peek().regs[aux_d], new Position(processes.peek().regs[aux_s]));
    }
	
	private void SWAP(String Rd, String Rs)
	{
		// Rd7 <- Rd3, Rd6 <- Rd2,
		// Rd5 <- Rd1, Rd4 <- Rd0
	}

}
