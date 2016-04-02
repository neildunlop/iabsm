package com.neildunlop.iabsm

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}


object Main extends App {
    val cfg = new LwjglApplicationConfiguration()
    cfg.title = "Smile"
    cfg.height = 1280
    cfg.width = 720
    //cfg.useGL30 = true
    cfg.forceExit = false
    new LwjglApplication(new Iabsm(), cfg)
}
