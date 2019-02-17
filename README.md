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

# project description