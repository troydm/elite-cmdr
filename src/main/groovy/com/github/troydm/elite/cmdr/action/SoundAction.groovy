package com.github.troydm.elite.cmdr.action

import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer

/**
 * Created by dgeurkov on 6/10/2016.
 */
class SoundAction implements Runnable {

    MediaPlayer mediaPlayer

    SoundAction(String filePath) {
        this(filePath,1.0)
    }

    SoundAction(String filePath, double volume){
        filePath = filePath.replaceAll(" ","%20")
        if(!(filePath.startsWith("file:") || filePath.startsWith("http:") || filePath.startsWith("https:"))){
            filePath = "file:/"+filePath
        }
        Media hit = new Media(filePath)
        mediaPlayer = new MediaPlayer(hit)
        mediaPlayer.setVolume(volume)
    }

    void run() {
        mediaPlayer.play()
    }
}
