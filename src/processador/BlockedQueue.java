package processador;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class BlockedQueue {
 
    public static Queue<PCB> filaBloqueados = new LinkedList<PCB>();

    private static Semaphore mutex = new Semaphore(1);  // exclusao mutua 

    public BlockedQueue () {}

    public void add(PCB p) { 
        try {
            mutex.acquire();
        }  catch (InterruptedException ie) {} 

        System.out.println("\tFB: Adicionando PCB "+p.id+" na FB...");
        filaBloqueados.add(p); 
        mutex.release();
        ReadyQueue FP = new ReadyQueue();
        FP.add(filaBloqueados.poll());
    }

    public PCB peek() { return filaBloqueados.peek(); }

    public Boolean isEmpty() { return filaBloqueados.isEmpty(); }

    public Integer size() { return filaBloqueados.size(); }

    public void remove() { filaBloqueados.remove(); }

    public PCB poll() {
        // Verifica se escalonador está liberado, se o GP liberou entao
        // quer dizer que há ao menos 1 PCB na FB
        try {
            mutex.acquire();
        }  catch (InterruptedException ie) {} 

        // Tira o primeiro PCB da FB, libera a MUTEX e retorna PCB para ESC
        PCB nextPCB = filaBloqueados.poll();
        //DEBUG START
        System.out.println("\tFB: Fazendo poll do prox. PCB "+nextPCB.id+" para o ESC...");
        //DEBUG END
        mutex.release();
        return nextPCB;
    }
}