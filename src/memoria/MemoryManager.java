package memoria;
import processador.Position;

public class MemoryManager {
    
    private static final int TAM = 1024;
	private static Position memory[] = new Position[TAM]; // 1024 posicoes de memoria
    private Partition[] partitions = new Partition[] {
        new Partition(0, 0, 127),
        new Partition(1, 128, 255),
        new Partition(2, 256, 383),
        new Partition(3, 384, 511),
        new Partition(4, 512, 639),
        new Partition(5, 640, 767),
        new Partition(6, 768, 895),
        new Partition(7, 896, 1023)
    };
    
    public class Partition{

        private int id, pos0, pos1;
        private boolean using = false;

        public Partition(int id, int pos0, int pos1){
            this.id = id;
            this.pos0 = pos0;
            this.pos1 = pos1;
        }

        public int getId()
        {
            return this.id;
        }

        public int getPos0()
        {
            return this.pos0;
        }

        public int getPos1()
        {
            return this.pos1;
        }

        public boolean isUsing()
        {
            return this.using;
        }

        public boolean isUsed()
        {
            return !(memory[this.pos0] == null);
        }
       
        public String getPartitionData(){
            StringBuilder sb = new StringBuilder();
            boolean prevIsNull = false;
            for(int i = pos0; i < pos1; i++) {
                if (memory[i] != null){
                    sb.append(i).append(".\t").append(memory[i].label).append("\n");
                    prevIsNull = false;
                } else {
                    if (!prevIsNull) {
                        sb.append("...null...\n");
                        prevIsNull = true;
                    }				
                }
            }
            return sb.toString();
        }

        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder("Partition Id: ").append(this.id).
                                          append("\tpos0: ").append(this.pos0).
                                          append("\tpos1: ").append(this.pos1).
                                          append("\tusing: ").append(this.using).
                                          append("\tused: ").append(this.isUsed()).append("\n");
            return sb.toString();
        }

    }
    
    public Position getPosition(int pos){
        return memory[pos];
    }

    public void setPosition(int n, Position pos){
        memory[n] = pos;        
    }

    // Procura uma partição livre da memória, marca sua flag 'using' como true e a retorna.
    private Partition getPartition(){
        for(int i = 0; i < partitions.length; i++){
            Partition p = partitions[i];
            if (!p.using)
            {
                p.using = true;
                return p;
            }
        }
        return null;
    }

    // Pega uma particao e partindo de 'pos0' adiciona um programa
    public Partition alocar(Position[] program)
    {
        Partition partition = getPartition(); // aloca uma partição da memória

        // escreve programa no começo da partição
        for(int i = 0; i < program.length; i++) 		
            memory[partition.pos0+i] = program[i];
            
        return partition;
    }

    public void desalocar(){
        // Reseta memória
        memory = new Position[TAM];

        // Reseta status de todas partições
        for(int i=0; i<partitions.length; i++)
            partitions[i].using = false;
    }

	public String printByPartition(){
        StringBuilder sb = new StringBuilder("Memory by partitions:\n");

		for(int i = 0; i < partitions.length; i++){
            Partition p = partitions[i];
            sb.append(p.toString());
            if (p.isUsed())
            	sb.append(p.getPartitionData());
        }
        return sb.toString();
    }

    @Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\n");
		boolean prevIsNull = false;
        for(int i = 0; i < memory.length; i++) {
            if (memory[i] != null){
				sb.append(i).append(".\t").append(memory[i].label).append("\n");
				prevIsNull = false;
			} else {
				if (!prevIsNull) {
					sb.append("...null...\n");
					prevIsNull = true;
				}				
			}
        }
        return sb.toString();
    }
}