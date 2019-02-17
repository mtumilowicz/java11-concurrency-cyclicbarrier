# java11-concurrency-cyclicbarrier

# preface
* a thread arriving at the barrier waits for other threads to arrive
* all threads waiting at the barrier point are released when the last
arrives
* use-case: split task to subtasks and combine the results when all
are ready
* situation where the required number of threads wait at the barrier, 
is called **tripping the barrier**

# java
* barrier in java is represented by `CyclicBarrier` class
* can be reused (`reset()`)
* could execute barrier action - `Runnable` task executed
just before release
* constructors
    * `CyclicBarrier​(int parties)` -  trip when the given number of 
    parties (threads) are waiting upon it
    * `CyclicBarrier​(int parties, Runnable barrierAction)`
        * `barrierAction` - the command to execute when the barrier 
        is tripped
* methods
    * `int	await()`
        * waits until all parties have invoked await on this 
        barrier
        * returns the arrival index of the current thread (countdown)
        * `InterruptedException` if the current thread was 
        interrupted while waiting
        * `BrokenBarrierException`
             *  if another thread was interrupted or timed out 
             while the current thread was waiting
             * the barrier was reset
             * the barrier was broken when `await` was called
             * the barrier action (if present) failed due to 
             an exception
* `int	await​(long timeout, TimeUnit unit)`
* `int	getNumberWaiting()`
* `int	getParties()`	
* `boolean	isBroken()`
* `void	reset()`
    * if any parties are currently waiting at the barrier, 
    they will return with a `BrokenBarrierException`
    
# project description
1. sub tasks
    ```
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
    ```
1. simulation
    ```
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
    ```
    can produce output
    ```
    SubTask 1 is performing work
    SubTask 3 is performing work
    SubTask 0 is performing work
    SubTask 2 is performing work
    SubTask 4 is performing work
    
    SubTask 1 ended the work
    SubTask 0 ended the work
    SubTask 4 ended the work
    SubTask 3 ended the work
    SubTask 2 ended the work
    
    all subtasks arrive
    
    SubTask 0 passed the barrier
    SubTask 4 passed the barrier
    SubTask 3 passed the barrier
    SubTask 1 passed the barrier
    SubTask 2 passed the barrier
    ```