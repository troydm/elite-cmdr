package com.github.troydm.elite.cmdr.action

/**
 * Created by dgeurkov on 6/9/2016.
 */
class SequentialAction implements Runnable {
    List<Runnable> actions

    SequentialAction(Collection<Runnable> actions){
        this.actions = new ArrayList<>(actions)
    }

    void run() {
        for(Runnable a : actions)
            a.run()
    }
}
