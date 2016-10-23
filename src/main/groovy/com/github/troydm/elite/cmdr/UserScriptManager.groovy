package com.github.troydm.elite.cmdr

import org.codehaus.groovy.control.CompilerConfiguration
import groovy.util.logging.Log
import java.util.logging.Level



/**
 * Created by troydm on 6/23/2016.
 */
@Singleton
@Log
class UserScriptManager implements Runnable {

    Thread thread
    File file = new File(System.getProperty("user.home") + File.separator + ".elite-cmdr")
    public String text
    GroovyShell shell
    UserScript userScript

    synchronized void load(){
        if(file.exists())
            text = file.getText("UTF-8")
        else
            text = ""
    }

    synchronized void save(String newText){
        text = newText
        file.setText(text,"UTF-8")
    }

    synchronized void start(){
        if(userScript != null)
            stop()
        thread = new Thread(this,"UserScriptManager")
        thread.setDaemon(true)
        thread.start()
    }

    synchronized void stop(){
        if(thread != null && thread.isAlive())
            thread.interrupt()
        if(userScript != null) {
            userScript.stop()
            userScript = null
        }
    }

    @Override
    void run(){
        try {
            CompilerConfiguration config = new CompilerConfiguration()
            config.scriptBaseClass = "com.github.troydm.elite.cmdr.UserScript"
            Binding binding = new Binding()
            binding.setProperty("out", System.out)
            shell = new GroovyShell(binding, config)
            userScript = (UserScript) shell.evaluate(text + "\n\nreturn this")
            userScript.start()
        }catch(Exception ex){
            log.log(Level.SEVERE, ex.getMessage())
        }
    }

}
