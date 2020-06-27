package processador;

import memoria.MemoryManager;
import memoria.MemoryManager.Partition;
import programs.Programs;
import shell.Shell;

public class ProcessManager {
    private static Integer nextId = 0;
	private static MemoryManager MM = new MemoryManager(); // gerente de memoria (aloca e desaloca particoes)
    private static ReadyQueue FP = new ReadyQueue();       // fila de prontos (adiciona e remove PCBs)
    private static Scheduler ESC = new Scheduler();        // escalonador

    //CPU cpu = new CPU();        // substituir pela thread CPU

    public ProcessManager() {
        ESC.start(); // Inicializa escalonador que se mantem bloqueado por padrao
    }

    public static void endProcess(PCB process)
    {
        Partition p = process.partition;
        
        //DEBUG START
        System.out.println("GP: Resultado PCB "+process.id+":\n"+p.getPartitionData());
        //DEBUG END

        p.format(); // formata particao da memoria
    } 

    public static void addProcess(int n)
	{
        // se houver espaço na memoria
        if (MM.getOcupiedPartitions() < 8)
        {
            int id = nextId++;
            Position program[] = null;
            switch(n)
            {
                case 1: program = Programs.getFibonacci10(); break;
                case 2: program = Programs.getFibonacciN(); break;
                case 3: program = Programs.getFatorial(); break;
                case 4: program = Programs.getBubblesort(); break;
            }
            
            PCB pcb = new PCB(id, program); // cria PCB solicitado pelo usuario
            
            // DEBUG START
            System.out.println("GP: Criei o PCB "+id+". Adicionando na FP...");
            //DEBUG END
            
            FP.add(pcb);    // e adiciona na FP
            
            tryReleaseScheduler();
		} else System.err.println("Desaloque posições da memória para inserir novos processos!");		
	}

    public static void tryReleaseScheduler()
    {
        // se houver PCB na FP e o ESC estiver parado
        if (FP.size() > 0 && !Scheduler.isExecuting)
        {
            // DEBUG START
            System.out.println("GP: Pedindo release do escalonador a FP...");
            //DEBUG END

            // Libera semaforo e flag do ESC para passar PCBs pra CPU 
            Scheduler.isExecuting = true;
            FP.releaseEscalonador();
        } else {
            System.err.println("GP: ERRO ao tentar liberar escalonador ->FP size: "+FP.size()+
                                "\tScheduler is executing: "+ Scheduler.isExecuting+"\n");
        }
    }

    public void request(String r) {
        switch(r) 
        {
        case "0":   Shell.printMenu(); break;
        case "1":	addProcess(1);	break;
        case "2": 	addProcess(2);	break;  
        case "3":	addProcess(3);	break;
        case "4":	addProcess(4);	break;
        case "-1":
            Shell.closed = true;
            System.out.println("Good bye.");
            System.exit(0);
        default: 
            System.err.print("Escolha um programa existente!\n");
        }
        //CPU.main();
    }
    
}

