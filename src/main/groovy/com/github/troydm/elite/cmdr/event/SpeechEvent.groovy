package com.github.troydm.elite.cmdr.event

/**
 * Speech event driven by CMU Sphinx 4
 * Created by dgeurkov on 6/9/2016.
 */
class SpeechEvent implements Event {
    String command

    String toString(){
        return "speech(\""+command+"\")"
    }
}
