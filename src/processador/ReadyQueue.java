package processador;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class ReadyQueue {
 
    private static Queue<PCB> filaProntos = new LinkedList<PCB>();
    
    private static Semaphore semaEscalonador = new Semaphore(0);           // semaforo do escalonador
    private static Semaphore mutex = new Semaphore(1);	    // para exclusao mutua 
    //private Semaphore semaCPU = new Semaphore(0);
    //private Semaphore mutex = new Semaphore(1); 

    public ReadyQueue () {}

    public void add(PCB p) { 
        try {
            mutex.acquire();
        }  catch (InterruptedException ie) {} 

        System.out.println("\tFP: Adicionando PCB "+p.id+" na FP...");
        filaProntos.add(p); 
        
        mutex.release();
    }

    public PCB peek() { return filaProntos.peek(); }

    public Boolean isEmpty() { return filaProntos.isEmpty(); }

    public Integer size() { return filaProntos.size(); }

    public void remove() { filaProntos.remove(); }

    public PCB poll() {
        // Verifica se escalonador está liberado, se o GP liberou entao
        // quer dizer que há ao menos 1 PCB na FP
        try {
            semaEscalonador.acquire();
            mutex.acquire();
        }  catch (InterruptedException ie) {} 

        // Tira o primeiro PCB da FP, libera a MUTEX e retorna PCB para ESC
        PCB nextPCB = filaProntos.poll();
        //DEBUG START
        System.out.println("\tFP: Fazendo poll do prox. PCB "+nextPCB.id+" para o ESC...");
        //DEBUG END
        mutex.release();
        return nextPCB;
    }

    public void releaseEscalonador(){ 
        System.out.println("\tFP: Liberando escalonador...");
        semaEscalonador.release(); //+1 crédito p/ escalonador após add PCB na FP
    }

}