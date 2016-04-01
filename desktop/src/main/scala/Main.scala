package com.neildunlop.iabsm

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}


object Main extends App {
    val cfg = new LwjglApplicationConfiguration()
    cfg.title = "Smile"
    cfg.height = 568
    cfg.width = 320
    //cfg.useGL30 = true
    cfg.forceExit = false
    new LwjglApplication(new Iabsm(), cfg)
}
