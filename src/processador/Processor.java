package processador;

public class Processor{
	
	static final int TAM = 1024;
    Position memory[] = new Position[TAM];
    int PC = 0;

    public Processor() {

    }
    
    private void readMemory() {
    	while(true){
            if (memory[PC].OPCode == "STOP") break;
            
            PC++;
        }
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
 