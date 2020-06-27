package processador;

public class TimeSlice extends Thread {

    private static int waitTime = 500; // tempo de cada escalonamento em ms

    public TimeSlice(){

    }

    public void run(){
        while(true) 
        {
            if(Scheduler.isTimerReleased())
            {
                System.out.println("TIMER: Começando a contar "+waitTime+" ms.\ttime: "+(System.currentTimeMillis()/1000)+" segundos de execução");
                try {
                    Thread.sleep(waitTime);
                } catch (Exception e) { System.err.println(e); }
                System.out.println("TIMER: Interrompendo CPU...\ttime: "+(System.currentTimeMillis()/1000)+" segundos de execução");
                CPU.setInterruption(true);
                CPU.setTimerOver(true);
            }
        }
    }
}