package com.github.troydm.elite.cmdr

import com.github.troydm.elite.cmdr.matching.EventActionMatcher

import java.util.concurrent.ArrayBlockingQueue
import com.github.troydm.elite.cmdr.event.Event

import groovy.util.logging.Log

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * EventMediator class - handles Event's and executes them against Action's using EventActionMatcher'ers
 * Created by troydm on 6/12/2016.
 */
@Singleton
@Log
class EventMediator implements Runnable {
    Thread thread
    ExecutorService threadPool = Executors.newSingleThreadExecutor()
    ArrayBlockingQueue<Event> eventQueue = new ArrayBlockingQueue<>(5)
    Collection<EventActionMatcher> matchers = Collections.synchronizedList(new ArrayList<>())

    def clearAll(){
        eventQueue.clear()
        matchers.clear()
    }

    def submit(Event event){
        if(running)
            eventQueue.put(event)
    }

    def submit(EventActionMatcher matcher){
        matchers.add(matcher)
    }

    synchronized void start(){
        thread = new Thread(this,"EventMediator")
        thread.setDaemon(true)
        thread.start()
    }

    volatile boolean running = false

    void run(){
        running = true
        while(running){
            Event event = eventQueue.poll(100,TimeUnit.MILLISECONDS)
            if(event != null){
                log.info ("Event received: "+event)
                boolean actionFound = false
                for(EventActionMatcher matcher : matchers) {
                    if (matcher.matches(event)) {
                        if(running) threadPool.submit(matcher.action())
                        actionFound = true
                        break
                    }
                }
                if(!actionFound)
                    log.warning("No action found for event: "+event)
            }
        }
        eventQueue.clear()
        matchers.clear()
        threadPool.shutdownNow()
        synchronized(this){
            thread = null
        }
    }

    synchronized void stop(){
        running = false
    }

}
