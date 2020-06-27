package processador;

public class TimeSlice extends Thread {

    private static int waitTime = 100; // tempo de cada escalonamento em ms

    public TimeSlice(){

    }

    public void run(){
        while(true) 
        {
            if(Scheduler.isTimerReleased())
            {
                long timestamp1 = System.currentTimeMillis();
                long timestamp2 = System.currentTimeMillis();
                System.out.println("\t\t\tTIMER: Começando a contar "+waitTime+" ms.\ttime: "+(0)+" segundos de execução");

                // entra em loop durante 'waitTime' ms
                do {
                    // se CPU é interrompida antes de exceder 'waitTime', reinicia timer
                    if (CPU.isInterrupted)
                    {
                        //DEBUG START
                        System.out.println("\t\t\tTIMER: CPU pre-interrompida, reiniciando timer...\ttime: "+(System.currentTimeMillis()-timestamp1)/1000+" segundos de execução");
                        //DEBUG END
                        break;
                    }
                    // senao, testa 'waitTime'
                    timestamp2 = System.currentTimeMillis();
                } while(timestamp2-timestamp1 < waitTime);

                // se excedeu 'waitTime', atualiza as flags equivalentes
                if (timestamp2-timestamp1 >= waitTime )
                {
                    //DEBUG START
                    System.out.println("\t\t\tTIMER: Interrompendo CPU...\ttime: "+(System.currentTimeMillis()-timestamp1)/1000+" segundos de execução");
                    //DEBUG END
                    CPU.setInterruption(true);
                    CPU.setTimerOver(true);
                }               
            }
        }
    }
}