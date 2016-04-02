package screens

import com.badlogic.gdx.graphics.{OrthographicCamera, GL20}
import com.badlogic.gdx.{Input, InputProcessor, Gdx, Screen}
import com.badlogic.gdx.maps.tiled.{TmxMapLoader, TiledMap}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer

/**
  * Created by neild on 01/04/2016.
  */
class PlayScreen extends Screen with InputProcessor {

    var map: TiledMap = null
    var renderer: OrthogonalTiledMapRenderer = null
    var camera: OrthographicCamera = null

    override def hide(): Unit = {
        dispose()
    }

    override def resize(width: Int, height: Int): Unit = {
        camera.viewportWidth = width
        camera.viewportHeight = height
        camera.setToOrtho(false,width,height);
        camera.update()
    }

    override def dispose(): Unit = {
        map.dispose()
        renderer.dispose()
    }

    override def pause(): Unit = {

    }

    override def render(delta: Float): Unit = {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();
    }

    override def show(): Unit = {

        val w = Gdx.graphics.getWidth();
        val h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        map = new TmxMapLoader().load("/Users/neild/Dev/iabsm/core/src/main/resources/desert.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        Gdx.input.setInputProcessor(this);
    }

    override def resume(): Unit = {
    }


    override def mouseMoved(screenX: Int, screenY: Int): Boolean = {
        false
    }

    override def keyTyped(character: Char): Boolean = {
        false
    }

    override def keyDown(keycode: Int): Boolean = {
        false
    }

    override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
        false
    }

    override def keyUp(keycode: Int): Boolean = {

        if(keycode == Input.Keys.A)
            camera.translate(-32,0);
        if(keycode == Input.Keys.D)
            camera.translate(32,0);
        if(keycode == Input.Keys.W)
            camera.translate(0,-32);
        if(keycode == Input.Keys.S)
            camera.translate(0,32);
        if(keycode == Input.Keys.NUM_1)
            map.getLayers().get(0).setVisible(!map.getLayers().get(0).isVisible());
//        if(keycode == Input.Keys.NUM_2)
//            map.getLayers().get(1).setVisible(!map.getLayers().get(1).isVisible());
        return false;
    }

    override def scrolled(amount: Int): Boolean = {
        false
    }

    override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
        false
    }

    override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
        false
    }
}
