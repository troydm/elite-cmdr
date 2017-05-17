elite-cmdr
==========

Elite Dangerous Voice Commander Programmable in Groovy

Building:

    gradle build


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
can be mapped using speech or speechContains on actions such as play, press, seq and par

Some more examples:

    on speech("jump"), seq(press("j"),play("jumping.mp3"))
    on speech("jump"), par(press("j",1),play("jumping.mp3"))

LICENSE
-------

MIT

