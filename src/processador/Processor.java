package processador;

public class Processor{
	
	static final int TAM = 1024;
    Position memory[] = new Position[TAM];
    int PC = 0;

    public Processor() {

    }
    
    public void readMemory() {
    	while(true){
            if (memory[PC].OPCode == "STOP") break;
            
            PC++;
        }
    }
    
}
 