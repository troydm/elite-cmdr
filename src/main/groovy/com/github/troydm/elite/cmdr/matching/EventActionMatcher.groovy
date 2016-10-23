package com.github.troydm.elite.cmdr.matching

import com.github.troydm.elite.cmdr.event.Event

/**
 * Created by troydm on 6/12/2016.
 */
interface EventActionMatcher {
    Runnable action()
    boolean matches(Event event)
}
