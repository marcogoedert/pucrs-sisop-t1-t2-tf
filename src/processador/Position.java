package processador;

public class Position {

	public String OPCode = null, r1 = null, r2 = null, label = null;
	public int num = 0;
	
	// Constructor #1 - Ex.: STOP
    public Position(String OPC){
        this.OPCode = OPC;
        this.label = OPC;
    }
    
    // Constructor #2 - Value
    public Position(Integer num){
        this.num = num;
        this.label = Integer.toString(num);
    }
    
    // Constructor #3 - Ex.: MULT r1,r3
    public Position(String OPC, String r1, String r2){
    	this.OPCode = OPC;
        this.r1 = r1;
        this.r2 = r2;
        
        StringBuilder sb = new StringBuilder(OPCode).append(" ").append(this.r1).append(",").append(this.r2);
        this.label = sb.toString();        
    }
    
    // Constructor #4 - Ex.: LDI r2,11
    public Position(String OPC, String r1, Integer num){
        this.OPCode = OPC;
        this.r1 = r1;
        this.num = num;
        
        StringBuilder sb = new StringBuilder(OPCode).append(" ").append(this.r1).append(",").append(this.num);
        this.label = sb.toString();
    }
    
    // Constructor #5 - Ex.: STD 52,r1
    public Position(String OPC, Integer num, String r2){
        this.OPCode = OPC;
        this.r2 = r2;
        this.num = num;
        
        StringBuilder sb = new StringBuilder(OPCode).append(" ").append(this.num).append(",").append(this.r2);
        this.label = sb.toString();
    }
    
    public String toString() {
    	return label;
    }

}
