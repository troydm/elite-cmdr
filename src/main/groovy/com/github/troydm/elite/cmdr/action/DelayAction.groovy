package com.github.troydm.elite.cmdr.action

import java.util.concurrent.TimeUnit

/**
 * Delay action
 * Created by dgeurkov on 6/9/2016.
 */
class DelayAction implements Runnable {

    private long delay;

    DelayAction(long delay){
        this.delay = delay
    }


    void run() {
        TimeUnit.MILLISECONDS.sleep(delay)
    }
}
