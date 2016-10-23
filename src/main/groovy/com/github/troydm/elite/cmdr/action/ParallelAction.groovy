package com.github.troydm.elite.cmdr.action

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * ParallelAction - parallel action execution class
 * Created by dgeurkov on 6/9/2016.
 */
class ParallelAction implements Runnable {
    static ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*3)
    List<Runnable> actions

    ParallelAction(Collection<Runnable> actions){
        this.actions = new ArrayList<>(actions)
    }

    @Override
    void run() {
        for(Runnable a : actions)
            exec.execute(a)
        exec.awaitTermination(Long.MAX_VALUE,TimeUnit.MILLISECONDS)
    }
}
