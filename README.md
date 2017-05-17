elite-cmdr
==========

Elite Dangerous Voice Commander Programmable in Groovy

Building:

    gradle shadowJar

Running: 

    Go into build\libs and double click on elite-cmdr jar file


Sample Configuration:

    recognize ''
    public <jump> = jump;
    public <landing-gear> = landing gear;
    public <system-map> = system map;
    public <galaxy-map> = galaxy map;
    ''
    on speech("jump"), press("j")
    on speech("landing gear"), press("\\")
    on speech("system map"), press("F1")
    on speech("galaxy map"), press("F2")

How it works
------------

Recognize method takes JSGF grammar and generates speech events which
can be mapped using speech or speechContains on actions such as play, press, seq and par.
seq and par can be used to execute multiple actions simultaneously.
an action is basicly Runnable so you can have any kind of custom code as your action, even Groovy closure.

Some more examples:

    on speech("jump"), seq(press("j"),play("jumping.mp3"))
    on speech("jump"), par(press("j",1),play("jumping.mp3"))
    on speech("landing"), seq(press("\\"),play("landing.mp3"))
    on speech("gear"), seq(press("\\"),play("landing.mp3"))

LICENSE
-------

MIT

