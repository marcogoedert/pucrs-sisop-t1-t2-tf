package processador;

import memoria.MemoryManager;

public class Processor{

	private MemoryManager MM = new MemoryManager(); // gerenciador de memória
	private ReadyQueue FP = new ReadyQueue(); 		// fila de prontos
	PCB pcb = null;
		
	public Processor(){}

	public void main(){
		
		int timeSlice = 20;
		if (!FP.isEmpty()){
			System.out.println("Processos na fila: " + FP.size());
			while(!FP.isEmpty()){
				// Pega processo da fila de prontos
				PCB p = FP.peek();
				// Executa processo durante seu time slice
				for(int i = 0; i < timeSlice; i++)
				{
					readInstruction(p);
					if (!p.executing)
						break;
				}
				
				if (p.executing)
					FP.add(FP.poll());
				else
				{			
					FP.remove(); // QUEM DEVE REMOVER EH O GP
					// DEBUG START
					System.out.println(p.partition.getPartitionData());
					// DEBUG END
					p.partition.format();
				}
			}
		} else System.out.println("Nenhum novo processo a ser executado.");
	}

	private void readInstruction(PCB process)
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
		PCB p = FP.peek();
		int num = p.getPartition().compensate(k);
        FP.peek().PC = num;
	}

    public void JMPI(String Rs){
		PCB p = FP.peek();
		int rs = getRegistrador(Rs);
		rs = p.getPartition().compensate(p.regs[rs]);
        p.PC = p.regs[rs];
    }

    public void JMPIG(String Rs, String Rc){
		// if Rc > 0 then PC <- Rs
		// else PC <- PC + 1
		PCB p = FP.peek();
		int rc = getRegistrador(Rc);
		int rs = getRegistrador(Rs);
		
		rs = p.getPartition().compensate(p.regs[rs]);
		
        if (p.regs[rc] > 0){
            p.PC = rs;
        }
    }

    public void JMPIL(String Rs, String Rc){
		PCB p = FP.peek();
		int rc = getRegistrador(Rc);
		int rs = getRegistrador(Rs);
		rs = p.getPartition().compensate(p.regs[rs]);

        if (p.regs[rc] < 0){
            p.PC = rs;
        }
	}
	
	public void JMPIE(String Rs, String Rc){
		PCB p = FP.peek();
		int rc = getRegistrador(Rc);
		int rs = getRegistrador(Rs);
		rs = p.getPartition().compensate(p.regs[rs]);

        if (p.regs[rc] == 0){
            p.PC = rs;
        }
	}
	
	public void ADDI(String Rd, int k)
	{
		PCB p = FP.peek();
		int aux = getRegistrador(Rd);
		p.regs[aux] += k;
	}

	public void SUBI(String Rd, int k)
	{
		PCB p = FP.peek();
		int aux = getRegistrador(Rd);
		p.regs[aux] -= k;
	}

	public void LDI(String Rd, int k) 
	{
		PCB p = FP.peek();
		int aux = getRegistrador(Rd);
		p.regs[aux] = k;
	}
	
	private void LDD(String Rd, int n)
	{
		PCB p = FP.peek();
		n = p.getPartition().compensate(n);
		p.regs[getRegistrador(Rd)] = MM.getPosition(n).num;
	}
	
	private void STD(int n, String Rs)
    {
		PCB p = FP.peek();
		n = p.getPartition().compensate(n);		
		MM.setPosition(n, new Position(p.regs[getRegistrador(Rs)]));
    }

	public void ADD(String Rd, String Rs)
	{
		PCB p = FP.peek();
		int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		p.regs[aux_d] += p.regs[aux_s];
	}

	public void SUB(String Rd, String Rs)
	{
		PCB p = FP.peek();
		int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		p.regs[aux_d] -= p.regs[aux_s];
	}

	public void MULT(String Rd, String Rs)
	{
		PCB p = FP.peek();
		int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		p.regs[aux_d] *= p.regs[aux_s];
	}
	
	private void LDX(String Rd, String Rs)
	{
		PCB p = FP.peek();
		int rd = getRegistrador(Rd);
		int rs = getRegistrador(Rs);

		rs = p.getPartition().compensate(rs);

		p.regs[rd] = MM.getPosition(rs).num;
	}
	
	private void STX(String Rd, String Rs)
    {	
		PCB p = FP.peek();
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
