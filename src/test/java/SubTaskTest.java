import org.junit.Test;

import java.util.LinkedList;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by mtumilowicz on 2019-02-17.
 */
public class SubTaskTest {

    @Test
    public void simulation() throws InterruptedException {
        Runnable barrierAction = () -> System.out.println("all subtasks arrive");
        CyclicBarrier barrier = new CyclicBarrier(5, barrierAction);
        
        var threads = new LinkedList<Thread>();
        
        for (int i = 0; i < 5; i++) {
            SubTask t = new SubTask(barrier, i);
            t.start();
            threads.add(t);
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }
}