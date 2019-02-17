import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * Created by mtumilowicz on 2019-02-17.
 */
class SubTask extends Thread {
    
    private final CyclicBarrier barrier;
    private final int id;

    SubTask(CyclicBarrier barrier, int id) {
        this.barrier = barrier;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            System.out.println("SubTask " + id + " is performing work");
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(50) + 3);
            System.out.println("SubTask " + id + " ended the work");
            this.barrier.await();
            System.out.println("SubTask " + id + " passed the barrier");
        } catch (InterruptedException | BrokenBarrierException e) {
            // not used
        }
    }
}