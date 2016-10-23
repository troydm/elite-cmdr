package com.github.troydm.elite.cmdr.event.generator

import com.github.troydm.elite.cmdr.EventMediator
import com.github.troydm.elite.cmdr.TempFileManager
import edu.cmu.sphinx.api.Configuration
import edu.cmu.sphinx.api.Microphone;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

import java.util.concurrent.TimeUnit;
import groovy.util.logging.Log;
import com.github.troydm.elite.cmdr.event.SpeechEvent


/**
 * SpeechEventGenerator - event generator driven by CMU Sphinx 4
 * Created by dgeurkov on 6/9/2016.
 */
@Log
class SpeechEventGenerator implements EventGenerator, Runnable {

    File grammar
    Thread thread
    Configuration configuration
    StreamSpeechRecognizer recognizer
    volatile boolean running


    static Microphone mic = new Microphone(16000, 16, true, false)

    SpeechEventGenerator(String jsgfText){
        configuration = new Configuration()
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us")
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict")
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin")
        grammar = TempFileManager.instance.newTempFile("grammar.gram")
        grammar.withWriter { out ->
            out.println("#JSGF V1.0")
            out.println("grammar elite-cmdr;")
            out.println(jsgfText)
        }
        configuration.setGrammarName("grammar")
        configuration.setGrammarPath(TempFileManager.instance.getTempDir().toURI().toString())
        configuration.setUseGrammar(true)
        recognizer = new StreamSpeechRecognizer(configuration)
    }

    @Override
    public void run(){
        mic.startRecording()
        try {
            SpeechResult result
            running = true
            recognizer.startRecognition(mic.getStream())
            log.info("Speech recognition event generator started")
            while (running) {
                if ((result = recognizer.getResult()) != null) {
                    if(!running)
                        break;
                    log.info("Speech event recognized, hypothesis: "+result.getHypothesis())
                    EventMediator.instance.submit(new SpeechEvent(command: result.getHypothesis()))
                } else {
                    TimeUnit.MILLISECONDS.sleep(100)
                }
            }
            recognizer.stopRecognition()
            log.info("Speech recognition event generator stopped")
        }catch(InterruptedException ex){
            recognizer.stopRecognition()
            log.info("Speech recognition event generator stopped")
        }catch(Exception ex){
            log.error(ex)
        }
        mic.stopRecording()
        synchronized(this){
            running = false
            thread = null
        }
    }

    @Override
    synchronized void start() {
        thread = new Thread(this,"SpeechEventGenerator")
        thread.setDaemon(true)
        thread.setPriority(Thread.MAX_PRIORITY)
        thread.start()
    }

    @Override
    synchronized void stop(){
        running = false
        thread.interrupt()
    }
}
