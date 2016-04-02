package com.neildunlop.iabsm

import com.badlogic.gdx._
import screens.PlayScreen

class Iabsm extends Game {

    override def create() = {
        val playScreen: PlayScreen = new PlayScreen()
        setScreen(playScreen)
        Gdx.input.setInputProcessor(playScreen);

    }


    override def dispose(): Unit = super.dispose()

    override def render(): Unit = super.render()

    override def resize(width: Int, height: Int): Unit = super.resize(width, height)

    override def pause(): Unit = super.pause()

    override def resume(): Unit = super.resume()
}
