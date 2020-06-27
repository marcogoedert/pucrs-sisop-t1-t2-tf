package processador;

import memoria.MemoryManager;

public class CPU extends Thread {

    static PCB pcb = null;         
    private static  MemoryManager MM = new MemoryManager(); // gerenciador de memória
    private static boolean timeOver = false;  // flag que Timer usa para controlar quantum do escalonador
    public static boolean isInterrupted = false;  // flag que Timer usa para ativar o escalonador
    
    public CPU(){
        
    }

    public void setPCB(PCB otherPcb) 
    { 
        //DEBUG START
        System.out.println("\t\t\tCPU: Sincronizando com PCB "+otherPcb.id+"...");
        //DEBUG END
        pcb = otherPcb; 
    }

	public boolean isInterrupted() { return isInterrupted; }

    public static void setInterruption(Boolean i) { isInterrupted = i; }
    
    public static void setTimerOver(Boolean i) { timeOver = i; }

    public void run(){
        while (true)
        {
			isInterrupted = timeOver = false; // reseta flags
			// primeiro ato deve bloquear a thread
            if (Scheduler.isCpuReleased()) //tenta verificar permissao do ESC; bloqueia se nao houver PCB no escalonador
            {
                // DEBUG START
                System.out.println("\t\t\tCPU: Executando PCB "+pcb.id+"...\tisInterrupted? "+isInterrupted);
                //DEBUG END

                // executa instruções até ser interrompido pelo timer ou fim do processo
                while (!isInterrupted)
                {
                    readInstruction(pcb);

                    if (!pcb.executing) {
                        System.out.println("\t\t\tCPU: Fim do PCB "+pcb.id+"; interrompendo...");
                        isInterrupted = true;
                    }
                }
                checkInterruption();
            }
        }
    }

    //verifica origem da interrupção e chama a rotina de tratamento adequeada 
    private void checkInterruption()
    {
        ReadyQueue FP = new ReadyQueue();

        if (!pcb.executing) { // equivalente ao "ROT TRAT ***""
            System.out.println("\t\t\tCPU: Fim do PCB. Pedindo ao GP para desalocar da memoria...");
            ProcessManager.endProcess(pcb); //desaloca PCB
        }
        else if (timeOver) {// equivalente ao "ROT TRAT TIMER"
            System.out.println("\t\t\tCPU: Time Over. Adicionando PCB ao fim da FP...");
            FP.add(pcb);    //PCB retorna pro final da FP
        }
        else 
        {
            System.err.println("\t\t\tCPU: INTERRUPCAO DESCONHECIDA.");
            System.exit(1);
        }

        // reseta flags da cpu e tenta proximo escalonamento
        timeOver = false;
        isInterrupted = false;
        System.out.println("\t\t\tCPU: Flags resetadas!\ttimeOver = "+timeOver+"\tisInterrupted = "+isInterrupted+
                            "\tPedindo proximo ESC ao GP...");

        ProcessManager.tryReleaseScheduler();
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
		int num = pcb.getPartition().compensate(k);
        pcb.PC = num;
	}

    public void JMPI(String Rs){
		int rs = getRegistrador(Rs);
		rs = pcb.getPartition().compensate(pcb.regs[rs]);
        pcb.PC = pcb.regs[rs];
    }

    public void JMPIG(String Rs, String Rc){
		// if Rc > 0 then PC <- Rs
		// else PC <- PC + 1
		
		int rc = getRegistrador(Rc);
		int rs = getRegistrador(Rs);
		
		rs = pcb.getPartition().compensate(pcb.regs[rs]);
		
        if (pcb.regs[rc] > 0){
            pcb.PC = rs;
        }
    }

    public void JMPIL(String Rs, String Rc){
		
		int rc = getRegistrador(Rc);
		int rs = getRegistrador(Rs);
		rs = pcb.getPartition().compensate(pcb.regs[rs]);

        if (pcb.regs[rc] < 0){
            pcb.PC = rs;
        }
	}
	
	public void JMPIE(String Rs, String Rc){
		
		int rc = getRegistrador(Rc);
		int rs = getRegistrador(Rs);
		rs = pcb.getPartition().compensate(pcb.regs[rs]);

        if (pcb.regs[rc] == 0){
            pcb.PC = rs;
        }
	}
	
	public void ADDI(String Rd, int k)
	{
		
		int aux = getRegistrador(Rd);
		pcb.regs[aux] += k;
	}

	public void SUBI(String Rd, int k)
	{
		
		int aux = getRegistrador(Rd);
		pcb.regs[aux] -= k;
	}

	public void LDI(String Rd, int k) 
	{
		
		int aux = getRegistrador(Rd);
		pcb.regs[aux] = k;
	}
	
	private void LDD(String Rd, int n)
	{
		
		n = pcb.getPartition().compensate(n);
		pcb.regs[getRegistrador(Rd)] = MM.getPosition(n).num;
	}
	
	private void STD(int n, String Rs)
    {
		
		n = pcb.getPartition().compensate(n);		
		MM.setPosition(n, new Position(pcb.regs[getRegistrador(Rs)]));
    }

	public void ADD(String Rd, String Rs)
	{
		
		int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		pcb.regs[aux_d] += pcb.regs[aux_s];
	}

	public void SUB(String Rd, String Rs)
	{
		
		int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		pcb.regs[aux_d] -= pcb.regs[aux_s];
	}

	public void MULT(String Rd, String Rs)
	{
		
		int aux_d = getRegistrador(Rd);
		int aux_s = getRegistrador(Rs);
		pcb.regs[aux_d] *= pcb.regs[aux_s];
	}
	
	private void LDX(String Rd, String Rs)
	{
		
		int rd = getRegistrador(Rd);
		int rs = getRegistrador(Rs);

		rs = pcb.getPartition().compensate(rs);

		pcb.regs[rd] = MM.getPosition(rs).num;
	}
	
	private void STX(String Rd, String Rs)
    {	
		
        int rd = getRegistrador(Rd);
		int rs = getRegistrador(Rs);

		rd = pcb.getPartition().compensate(pcb.regs[rd]);		

		MM.setPosition(rd, new Position(pcb.regs[rs]));
    }
	
	private void SWAP(String Rd, String Rs)
	{
		// Rd7 <- Rd3, Rd6 <- Rd2,
		// Rd5 <- Rd1, Rd4 <- Rd0
	}
}