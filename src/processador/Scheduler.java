package processador;

import java.util.concurrent.Semaphore;

public class Scheduler extends Thread
{    
    // Responsabilidades do escalonador
    private static PCB nextPCB = null;                 //responsavel por enviar proximo PCB p/ CPU
    private static ReadyQueue FP = new ReadyQueue();   //responsavel por remover os PCB da FP
    private static Semaphore semaCPU = new Semaphore(0);   //responsavel por liberar CPU para rodar instruc.
    private static Semaphore semaTimer = new Semaphore(0); //responsavel por liberar Timer para escalonar instruc.
    public static Boolean isExecuting = false;  // flag que GP usa para ativar o escalonador
    
    // Threads dependentes do escalonador
    private static CPU cpu = new CPU();                //responsavel por liberar CPU
    private static TimeSlice timer = new TimeSlice();  //responsavel por liberar Timer

    public Scheduler() {
        //devem começar bloqueados por padrao
        cpu.start();
        timer.start();  
    }

    public void run(){
        while(true)
        {
            // primeiro ato deve bloquear a thread
            nextPCB = FP.poll(); //tenta remover 1 PCB da fila de prontos; bloqueia se nao houver PCBs na fila
            
            // DEBUG START
            System.out.println("\t\tESCALONADOR: Adicionando PCB "+nextPCB.getId()+" a CPU...");
            //DEBUG END

            cpu.setPCB(nextPCB);

            // DEBUG START
            System.out.println("\t\tESCALONADOR: Liberando CPU e Timer...");
            //DEBUG END

            isExecuting = false;    //hiberna até a proxima chamada do GP
            semaCPU.release();      //libera +1 credito p/ cpu
            semaTimer.release();    //libera +1 credito p/ timer

            System.out.println("\t\tESCALONADOR: Hibernando...\tisExecuting: "+isExecuting);
        }
    }

    public static boolean isCpuReleased()
    {
        try {
            semaCPU.acquire();
        } catch (InterruptedException ie) {} 
        return true;
    }

    public static boolean isTimerReleased()
    {
        try {
            semaTimer.acquire();
        } catch (InterruptedException ie) {} 
        return true;
    }

}