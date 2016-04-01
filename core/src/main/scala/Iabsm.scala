package com.neildunlop.iabsm

import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.{Screen, Gdx, Game}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType

class Iabsm extends Game {

    class DemoScreen extends Screen {
        val camera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
        camera.position.set(Gdx.graphics.getWidth/2, Gdx.graphics.getHeight/2, 0)
        camera.update()
        val mainBatch = new SpriteBatch
        val shapeRenderer = new ShapeRenderer
        shapeRenderer.setProjectionMatrix(camera.combined)

        def render(delta: Float) = {
            Gdx.gl.glClearColor(1, 1, 0, 1)
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
            Gdx.gl.glLineWidth(20)

            mainBatch.begin()

            shapeRenderer.begin(ShapeType.Line)
            shapeRenderer.setColor(0, 0, 0, 1)
            shapeRenderer.circle(160, 284, 100)
            shapeRenderer.circle(120, 314, 18)
            shapeRenderer.circle(200, 314, 18)
            shapeRenderer.polyline(Array(100, 260, 160, 220, 220, 260.0f))
            shapeRenderer.end()

            mainBatch.end()
        }

        def resize(width: Int, height: Int) = {}
        def show() = {}
        def hide() = {}
        def pause() = {}
        def resume() = {}
        def dispose() = {}
    }

    override def create() {
        this.setScreen(new DemoScreen)
    }
}
