package processador;

public class Processor{
	
	static final int TAM = 1024;
    Position memory[] = new Position[TAM];
    public static int[] regs = new int[8];
    int PC = 0;

    public Processor() {

    }
    
    private void readMemory() {
    	while(true){
            if (memory[PC].OPCode == "STOP" || PC > 1023 || PC < 0) break;
            
            PC++;
        }
    }
    
    public static int getRegistrador(String registrador) {
        switch(registrador){

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

    public void ADDI(String Rd, int k) {

        int aux = getRegistrador(Rd);
        regs[aux] += k;

    }

    public void SUBI(String Rd, int k) {

        int aux = getRegistrador(Rd);
        regs[aux] -= k;

    }

    public void LDI(String Rd, int k) {

        int aux = getRegistrador(Rd);
        regs[aux] = k;

    }

    public void ADD(String Rd, String Rs) {

        int aux_d = getRegistrador(Rd);
        int aux_s = getRegistrador(Rs);
        regs[aux_d] += regs[aux_s];

    }

    public void SUB(String Rd, String Rs) {

        int aux_d = getRegistrador(Rd);
        int aux_s = getRegistrador(Rs);
        regs[aux_d] -= regs[aux_s];

    }

    public void MULT(String Rd, String Rs) {

        int aux_d = getRegistrador(Rd);
        int aux_s = getRegistrador(Rs);
        regs[aux_d] *= regs[aux_s];

    }
    
    public void runProgram1() {
    	
    }
    
	public void runProgram2() {
	    	
    }
	
	public void runProgram3() {
		
	}
	
	public void runProgram4() {
		
	}
    
	
	
}
 