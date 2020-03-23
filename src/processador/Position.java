package processador;

public class Position {

	public String OPCode = null;
	String r1 = null, r2 = null;
	int num = 0;

	// STOP
    public Position(String OPC){
        this.OPCode = OPC;
    }
    
    public Position(Integer num){
        this.num = num;
    }
    
    public Position(String OPC, String r1, String r2){
        this.OPCode = OPC;
        this.r1 = r1;
        this.r2 = r2;
    }
    
    public Position(String OPC, String r1, Integer num){
        this.OPCode = OPC;
        this.r1 = r1;
        this.num = num;
    }
    
    public Position(String OPC, Integer num, String r1){
        this.OPCode = OPC;
        this.r1 = r1;
        this.num = num;
    }

}
