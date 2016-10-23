package com.github.troydm.elite.cmdr.action

import java.awt.Robot
import java.awt.event.KeyEvent
import java.util.concurrent.TimeUnit
import groovy.util.logging.Log;


/**
 * KeyPressAction - key press action
 * Created by dgeurkov on 6/9/2016.
 */
@Log
class KeyPressAction implements Runnable {

    static Robot robot = new Robot()
    private String keyText
    private int ke
    private long holdInterval = 0
    private boolean holdAlt
    private boolean holdCtrl
    private boolean holdShift

    public KeyPressAction(String key) {
        this(key,0)
    }

    public KeyPressAction(String key, long holdIntervalMillis){
        keyText = key
        holdInterval = holdIntervalMillis
        while(true) {
            if (key.toUpperCase().startsWith("ALT-")) {
                holdAlt = true
                key = key.substring(4)
                continue;
            }
            if (key.toUpperCase().startsWith("SHIFT-")) {
                holdShift = true
                key = key.substring(6)
                continue;
            }
            if (key.toUpperCase().startsWith("CTRL-")) {
                holdCtrl = true
                key = key.substring(5)
                continue;
            }
            break;
        }
        ke = toKeyEvent(key)
    }

    private int toKeyEvent(String key){
        switch(key){
            case "a": return KeyEvent.VK_A
            case "A": holdShift = true; return KeyEvent.VK_A
            case "b": return KeyEvent.VK_B
            case "B": holdShift = true; return KeyEvent.VK_B
            case "c": return KeyEvent.VK_C
            case "C": holdShift = true; return KeyEvent.VK_C
            case "d": return KeyEvent.VK_D
            case "D": holdShift = true; return KeyEvent.VK_D
            case "e": return KeyEvent.VK_E
            case "E": holdShift = true; return KeyEvent.VK_E
            case "f": return KeyEvent.VK_F
            case "F": holdShift = true; return KeyEvent.VK_F
            case "g": return KeyEvent.VK_G
            case "G": holdShift = true; return KeyEvent.VK_G
            case "h": return KeyEvent.VK_H
            case "H": holdShift = true; return KeyEvent.VK_H
            case "i": return KeyEvent.VK_I
            case "I": holdShift = true; return KeyEvent.VK_I
            case "j": return KeyEvent.VK_J
            case "J": holdShift = true; return KeyEvent.VK_J
            case "k": return KeyEvent.VK_K
            case "K": holdShift = true; return KeyEvent.VK_K
            case "l": return KeyEvent.VK_L
            case "L": holdShift = true; return KeyEvent.VK_L
            case "m": return KeyEvent.VK_M
            case "M": holdShift = true; return KeyEvent.VK_M
            case "n": return KeyEvent.VK_N
            case "N": holdShift = true; return KeyEvent.VK_N
            case "o": return KeyEvent.VK_O
            case "O": holdShift = true; return KeyEvent.VK_O
            case "p": return KeyEvent.VK_P
            case "P": holdShift = true; return KeyEvent.VK_P
            case "q": return KeyEvent.VK_Q
            case "Q": holdShift = true; return KeyEvent.VK_Q
            case "r": return KeyEvent.VK_R
            case "R": holdShift = true; return KeyEvent.VK_R
            case "s": return KeyEvent.VK_S
            case "S": holdShift = true; return KeyEvent.VK_S
            case "t": return KeyEvent.VK_T
            case "T": holdShift = true; return KeyEvent.VK_T
            case "u": return KeyEvent.VK_U
            case "U": holdShift = true; return KeyEvent.VK_U
            case "v": return KeyEvent.VK_V
            case "V": holdShift = true; return KeyEvent.VK_V
            case "w": return KeyEvent.VK_W
            case "W": holdShift = true; return KeyEvent.VK_W
            case "x": return KeyEvent.VK_X
            case "X": holdShift = true; return KeyEvent.VK_X
            case "y": return KeyEvent.VK_Y
            case "Y": holdShift = true; return KeyEvent.VK_Y
            case "z": return KeyEvent.VK_Z
            case "Z": holdShift = true; return KeyEvent.VK_Z
            case "tab": return KeyEvent.VK_TAB
            case "TAB": return KeyEvent.VK_TAB
            case "Tab": return KeyEvent.VK_TAB
            case "Win": return KeyEvent.VK_WINDOWS
            case "WIN": return KeyEvent.VK_WINDOWS
            case "WIN": return KeyEvent.VK_WINDOWS
            case "esc": return KeyEvent.VK_ESCAPE
            case "Esc": return KeyEvent.VK_ESCAPE
            case "ESC": return KeyEvent.VK_ESCAPE
            case "f1": return KeyEvent.VK_F1
            case "F1": return KeyEvent.VK_F1
            case "f2": return KeyEvent.VK_F2
            case "F2": return KeyEvent.VK_F2
            case "f3": return KeyEvent.VK_F3
            case "F3": return KeyEvent.VK_F3
            case "f4": return KeyEvent.VK_F4
            case "F4": return KeyEvent.VK_F4
            case "f5": return KeyEvent.VK_F5
            case "F5": return KeyEvent.VK_F5
            case "f6": return KeyEvent.VK_F6
            case "F6": return KeyEvent.VK_F6
            case "f7": return KeyEvent.VK_F7
            case "F7": return KeyEvent.VK_F7
            case "f8": return KeyEvent.VK_F8
            case "F8": return KeyEvent.VK_F8
            case "f9": return KeyEvent.VK_F9
            case "F9": return KeyEvent.VK_F9
            case "f10": return KeyEvent.VK_F10
            case "F10": return KeyEvent.VK_F10
            case "f11": return KeyEvent.VK_F11
            case "F11": return KeyEvent.VK_F11
            case "f12": return KeyEvent.VK_F12
            case "F12": return KeyEvent.VK_F12
            case "pause": return KeyEvent.VK_PAUSE
            case "Pause": return KeyEvent.VK_PAUSE
            case "PAUSE": return KeyEvent.VK_PAUSE
            case "space": return KeyEvent.VK_SPACE
            case "Space": return KeyEvent.VK_SPACE
            case "SPACE": return KeyEvent.VK_SPACE
            case "enter": return KeyEvent.VK_ENTER
            case "Enter": return KeyEvent.VK_ENTER
            case "ENTER": return KeyEvent.VK_ENTER
            case "printscr": return KeyEvent.VK_PRINTSCREEN
            case "Printscr": return KeyEvent.VK_PRINTSCREEN
            case "PRINTSCR": return KeyEvent.VK_PRINTSCREEN
            case "0": return KeyEvent.VK_0
            case "1": return KeyEvent.VK_1
            case "2": return KeyEvent.VK_2
            case "3": return KeyEvent.VK_3
            case "4": return KeyEvent.VK_4
            case "5": return KeyEvent.VK_5
            case "6": return KeyEvent.VK_6
            case "7": return KeyEvent.VK_7
            case "8": return KeyEvent.VK_8
            case "9": return KeyEvent.VK_9
            case "`": return KeyEvent.VK_BACK_QUOTE
            case "~": holdShift = true; return KeyEvent.VK_BACK_QUOTE
            case "!": holdShift = true; return KeyEvent.VK_1
            case "@": holdShift = true; return KeyEvent.VK_2
            case "#": holdShift = true; return KeyEvent.VK_3
            case "\$": holdShift = true; return KeyEvent.VK_4
            case "%": holdShift = true; return KeyEvent.VK_5
            case "^": holdShift = true; return KeyEvent.VK_6
            case "&": holdShift = true; return KeyEvent.VK_7
            case "*": holdShift = true; return KeyEvent.VK_8
            case "(": holdShift = true; return KeyEvent.VK_9
            case ")": holdShift = true; return KeyEvent.VK_0
            case "-": return KeyEvent.VK_MINUS
            case "_": holdShift = true; return KeyEvent.VK_MINUS
            case "=": return KeyEvent.VK_EQUALS
            case "+": holdShift = true; return KeyEvent.VK_EQUALS
            case "backspace": return KeyEvent.VK_BACK_SPACE
            case "Backspace": return KeyEvent.VK_BACK_SPACE
            case "BACKSPACE": return KeyEvent.VK_BACK_SPACE
            case "[": return KeyEvent.VK_OPEN_BRACKET
            case "]": return KeyEvent.VK_CLOSE_BRACKET
            case "{": holdShift = true; return KeyEvent.VK_OPEN_BRACKET
            case "}": holdShift = true; return KeyEvent.VK_CLOSE_BRACKET
            case "\\": return KeyEvent.VK_BACK_SLASH
            case "|": holdShift = true; return KeyEvent.VK_BACK_SLASH
            case ";": return KeyEvent.VK_SEMICOLON
            case ":": holdShift = true; return KeyEvent.VK_SEMICOLON
            case "'": return KeyEvent.VK_QUOTE
            case "\"": holdShift = true; return KeyEvent.VK_QUOTE
            case ",": return KeyEvent.VK_COMMA
            case "<": holdShift = true; return KeyEvent.VK_COMMA
            case ".": return KeyEvent.VK_PERIOD
            case ">": holdShift = true; return KeyEvent.VK_PERIOD
            case "/": return KeyEvent.VK_SLASH
            case "?": holdShift = true; return KeyEvent.VK_SLASH
            case "insert": return KeyEvent.VK_INSERT
            case "Insert": return KeyEvent.VK_INSERT
            case "INSERT": return KeyEvent.VK_INSERT
            case "home": return KeyEvent.VK_HOME
            case "Home": return KeyEvent.VK_HOME
            case "HOME": return KeyEvent.VK_HOME
            case "end": return KeyEvent.VK_END
            case "End": return KeyEvent.VK_END
            case "END": return KeyEvent.VK_END
            case "delete": return KeyEvent.VK_DELETE
            case "Delete": return KeyEvent.VK_DELETE
            case "DELETE": return KeyEvent.VK_DELETE
            case "pgup": return KeyEvent.VK_PAGE_UP
            case "Pgup": return KeyEvent.VK_PAGE_UP
            case "PGUP": return KeyEvent.VK_PAGE_UP
            case "pgdown": return KeyEvent.VK_PAGE_DOWN
            case "Pgdown": return KeyEvent.VK_PAGE_DOWN
            case "PGDOWN": return KeyEvent.VK_PAGE_DOWN
            case "left": return KeyEvent.VK_LEFT
            case "Left": return KeyEvent.VK_LEFT
            case "LEFT": return KeyEvent.VK_LEFT
            case "right": return KeyEvent.VK_RIGHT
            case "Right": return KeyEvent.VK_RIGHT
            case "RIGHT": return KeyEvent.VK_RIGHT
            case "up": return KeyEvent.VK_UP
            case "Up": return KeyEvent.VK_UP
            case "UP": return KeyEvent.VK_UP
            case "down": return KeyEvent.VK_DOWN
            case "Down": return KeyEvent.VK_DOWN
            case "DOWN": return KeyEvent.VK_DOWN
            default: throw new IllegalArgumentException("Couldn't recognise key: "+key)
        }
    }

    void run() {
        if(holdAlt)
            robot.keyPress(KeyEvent.VK_ALT)
        if(holdShift)
            robot.keyPress(KeyEvent.VK_SHIFT)
        if(holdCtrl)
            robot.keyPress(KeyEvent.VK_CONTROL)
        robot.keyPress(ke)
        if(holdInterval > 0) {
            robot.waitForIdle()
            TimeUnit.MILLISECONDS.sleep(holdInterval)
        }
        robot.keyRelease(ke)
        if(holdAlt)
            robot.keyRelease(KeyEvent.VK_ALT)
        if(holdShift)
            robot.keyRelease(KeyEvent.VK_SHIFT)
        if(holdCtrl)
            robot.keyRelease(KeyEvent.VK_CONTROL)
        robot.waitForIdle()
        log.info("Key pressed: "+keyText)
    }
}
