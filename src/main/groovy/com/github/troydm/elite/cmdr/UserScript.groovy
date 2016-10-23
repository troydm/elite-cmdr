package com.github.troydm.elite.cmdr

import com.github.troydm.elite.cmdr.action.KeyPressAction
import com.github.troydm.elite.cmdr.event.Event
import com.github.troydm.elite.cmdr.event.SpeechEvent
import com.github.troydm.elite.cmdr.event.SpeechContainsEvent
import com.github.troydm.elite.cmdr.event.generator.SpeechEventGenerator
import com.github.troydm.elite.cmdr.matching.SpeechEventEqualsMatcher
import com.github.troydm.elite.cmdr.matching.SpeechEventPartialMatcher

/**
 * UserScript - Elite CMDR Groovy user script used for DSL-like language
 * Created by troydm on 6/23/2016.
 */
abstract class UserScript extends Script {

    SpeechEventGenerator speechEventGenerator

    def recognize(String grammar){
        speechEventGenerator = new SpeechEventGenerator(grammar)
    }

    def speech(String command){
        SpeechEvent speech = new SpeechEvent()
        speech.command = command
        return speech
    }

    def speechContains(String command){
        SpeechContainsEvent speech = new SpeechContainsEvent()
        speech.command = command
        return speech
    }

    def press(String key, long delay=0){
        return new KeyPressAction(key)
    }

    def on(Event event, Runnable action){
        if(event instanceof SpeechContainsEvent){
            SpeechEventEqualsMatcher m = new SpeechEventEqualsMatcher()
            m.command = ((SpeechEvent)event).command
            m.action = action
            EventMediator.instance.submit(m)
        }else if(event instanceof SpeechEvent){
            SpeechEventPartialMatcher m = new SpeechEventPartialMatcher()
            m.command = ((SpeechEvent)event).command
            m.action = action
            EventMediator.instance.submit(m)
        }
    }

    void start(){
        if(speechEventGenerator != null)
            speechEventGenerator.start()
        EventMediator.instance.start()
    }

    void stop(){
        if(speechEventGenerator != null)
            speechEventGenerator.stop()
        speechEventGenerator = null
        EventMediator.instance.clearAll()
        EventMediator.instance.stop()
    }

}
