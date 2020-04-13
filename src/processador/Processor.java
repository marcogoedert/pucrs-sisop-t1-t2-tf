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
			this.PC = this.partition.getPos0();
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
		
		int timeSlice = 20;
		if (!processes.isEmpty()){
			System.out.println("Processos na fila: " + processes.size());
			while(!processes.isEmpty()){
				Process p = processes.peek();
				for(int i = 0; i < timeSlice; i++)
				{
					readInstruction(p);
					if (!p.executing)
						break;
				}
	
				if(p.executing)
					processes.add(processes.poll());
				else			
					processes.remove();
			}
		} else System.out.println("Nenhum novo processo a ser executado.");
	}

	public void runFibonacci10()
	{
		int id = processes.size();
		Position program[] = new Program().fibonacci10();
		Process process = new Process(id, program);
		processes.add(process);
		//DEBUG START
		System.out.println("Iniciei o processo "+id+": runFibonacci10.");
		//DEBUG FINISH
	}

	public void runFibonacciN()
	{
		int id = processes.size();
		Position program[] = new Program().fibonaccin();
		Process process = new Process(id, program);
		processes.add(process);
		//DEBUG START
		System.out.println("Iniciei o processo "+id+": runFibonacciN.");
		//DEBUG FINISH
	}	
	
	public void runFatorial()
	{
		int id = processes.size();
		Position program[] = new Program().fatorial();
		Process process = new Process(id, program);
		processes.add(process);
		//DEBUG START
		System.out.println("Iniciei o processo "+id+": runFatorial.");
		//DEBUG FINISH
	}
	
	public void runBubbleSort()
	{
	}
	
	private void readInstruction(Process process)
	{		 
		Position pos = MM.getPosition(process.PC); 
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
		Process p = processes.peek();
		int num = p.getPartition().compensate(k);
        processes.peek().PC = num;
	}

    public void JMPI(String Rs){
		Process p = processes.peek();
		int rs = getRegistrador(Rs);
		rs = p.getPartition().compensate(p.regs[rs]);
        p.PC = p.regs[rs];
    }

    public void JMPIG(String Rs, String Rc){
		// if Rc > 0 then PC <- Rs
		// else PC <- PC + 1
		Process p = processes.peek();
		int rc = getRegistrador(Rc);
		int rs = getRegistrador(Rs);
		
		rs = p.getPartition().compensate(p.regs[rs]);
		
        if (p.regs[rc] > 0){
            p.PC = rs;
        }
    }

    public void JMPIL(String Rs, String Rc){
		Process p = processes.peek();
		int rc = getRegistrador(Rc);
		int rs = getRegistrador(Rs);
		rs = p.getPartition().compensate(p.regs[rs]);

        if (p.regs[rc] < 0){
            p.PC = rs;
        }
	}
	
	public void JMPIE(String Rs, String Rc){
		Process p = processes.peek();
		int rc = getRegistrador(Rc);
		int rs = getRegistrador(Rs);
		rs = p.getPartition().compensate(p.regs[rs]);

        if (p.regs[rc] == 0){
            p.PC = rs;
        }
	}
	
	public void ADDI(String Rd, int k)
	{
		Process p = processes.peek();
		int aux = getRegistrador(Rd);
		p.regs[aux] += k;
	}

	public void SUBI(String Rd, int k)
	{
		Process p = processes.peek();
		int aux = getRegistrador(Rd);
		p.regs[aux] -= k;
	}

	public void LDI(String Rd, int k) 
	{
		Process p = processes.peek();
		int aux = getRegistrador(Rd);
		p.regs[aux] = k;
	}
	
	private void LDD(String Rd, int n)
	{
		Process p = processes.peek();
		n = p.getPartition().compensate(n);
		p.regs[getRegistrador(Rd)] = MM.getPosition(n).num;
	}
	
	private void STD(int n, String Rs)
    {
		Process p = processes.peek();
		n = p.getPartition().compensate(n);		
		MM.setPosition(n, new Position(p.regs[getRegistrador(Rs)]));
    }

	public void ADD(String Rd, String Rs)
	{
		Process p = processes.peek();
		int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		p.regs[aux_d] += p.regs[aux_s];
	}

	public void SUB(String Rd, String Rs)
	{
		Process p = processes.peek();
		int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		p.regs[aux_d] -= p.regs[aux_s];
	}

	public void MULT(String Rd, String Rs)
	{
		Process p = processes.peek();
		int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		p.regs[aux_d] *= p.regs[aux_s];
	}
	
	private void LDX(String Rd, String Rs)
	{
		Process p = processes.peek();
		int rd = getRegistrador(Rd);
		int rs = getRegistrador(Rs);

		rs = p.getPartition().compensate(rs);

		p.regs[rd] = MM.getPosition(rs).num;
	}
	
	private void STX(String Rd, String Rs)
    {	
		Process p = processes.peek();
        int rd = getRegistrador(Rd);
		int rs = getRegistrador(Rs);

		rd = p.getPartition().compensate(p.regs[rd]);		

		MM.setPosition(rd, new Position(p.regs[rs]));
    }
	
	private void SWAP(String Rd, String Rs)
	{
		// Rd7 <- Rd3, Rd6 <- Rd2,
		// Rd5 <- Rd1, Rd4 <- Rd0
	}
}
