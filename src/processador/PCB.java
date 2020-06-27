package processador;

import memoria.MemoryManager;
import memoria.MemoryManager.*;

public class PCB
{
    // Process Control Block
    int id, PC = 0;
    int[] regs = new int[8];
    boolean executing = true;
    Partition partition = null;
    MemoryManager MM = new MemoryManager();

    public PCB(int id, Position process[])
    {
        this.id = id;
        this.partition = MM.alocar(process);
        this.PC = this.partition.getPos0();
    }
    
    public int getId() { return this.id; }

    public int getPC() { return this.PC; }

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

    public boolean isExecuting(){ return this.executing; }

    public Partition getPartition(){ return this.partition; }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("PCB Id: ").append(this.id).
                                        append("\tExecuting: ").append(this.executing).
                                        append("\n").append(this.partition.toString()).
                                        append(this.getRegs()).append("\n");
        return sb.toString();
    }
}