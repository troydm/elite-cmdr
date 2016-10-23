package com.github.troydm.elite.cmdr.matching

import com.github.troydm.elite.cmdr.event.Event
import com.github.troydm.elite.cmdr.event.SpeechEvent

/**
 * Created by troydm on 6/12/2016.
 */
class SpeechEventPartialMatcher implements EventActionMatcher {

    Runnable action
    String command
    boolean ignoreCase = true

    @Override
    Runnable action() {
        return action
    }

    @Override
    boolean matches(Event event) {
        if(event instanceof SpeechEvent){
            SpeechEvent speech = (SpeechEvent)event;
            return ignoreCase ?  speech.command.toLowerCase().contains(command.toLowerCase()) : speech.command.contains(command)
        }
        return false
    }
}
