package processador;

public class Position {

	 public String OPCode;
	    public static int[] regs = new int[8];

	    public Position(String OPC){
	        this.OPCode = OPC;
	    }

	    public static int getRegistrador(String registrador){
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

	    public static void LDI(String r, int value){
	        int aux = getRegistrador(r);
	        regs[aux] = value;
	    }

}
