package screens

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.{Texture, OrthographicCamera, GL20}
import com.badlogic.gdx.math.{Rectangle, Intersector}
import com.badlogic.gdx.{Input, InputProcessor, Gdx, Screen}
import com.badlogic.gdx.maps.tiled.{TiledMapTileLayer, TmxMapLoader, TiledMap}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import entities.{Explosion, Enemy, Bullet, Player}

import scala.collection.mutable

/**
  * Created by neild on 01/04/2016.
  */
class PlayScreen extends Screen with InputProcessor {

    var map: TiledMap = null
    var renderer: OrthogonalTiledMapRenderer = null
    var camera: OrthographicCamera = null

    var player: Player = null
    var enemy1: Enemy = null
    var bullets: mutable.ListBuffer[Bullet] = mutable.ListBuffer()
    var explosion: Explosion = null


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
        player.dispose()
        if(enemy1!=null) {
            enemy1.dispose()
        }
        bullets.foreach(_.dispose())
    }

    override def pause(): Unit = {

    }

    def fireBullet() = {
        val muzzlePosX = player.tankTurretSprite.getX+player.tankTurretSprite.getWidth/2
        val muzzlePosY = player.tankTurretSprite.getY+(player.tankTurretSprite.getHeight/2)
        val bullet = new Bullet(muzzlePosX, muzzlePosY, 360-player.turretRotation)
        bullets += bullet
    }

    override def render(delta: Float): Unit = {

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveLeft(camera)
            //camera.translate(-32,0)
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight(camera)
            //camera.translate(32,0)
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.moveDown(camera)
            //camera.translate(0,32)
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.moveUp(camera)
            //camera.translate(0,-32)
        }

        if(Gdx.input.isKeyPressed(Input.Keys.L)) {
            player.turretClockwise()
        }
        if(Gdx.input.isKeyPressed(Input.Keys.K)) {
            player.turretAntiClockwise()
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            fireBullet()
        }

        Gdx.gl.glClearColor(1, 0, 0, 1);
        //Gdx.graphics.getGL20().glClearColor( 1, 1, 1, 1 );
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();

        renderer.getBatch.begin()

        //order that we call draw determines Z-Order
        bullets.foreach(_.draw(renderer.getBatch))
        player.draw(renderer.getBatch)
        if(enemy1!=null) {
            enemy1.draw(renderer.getBatch)
        }

        for(bullet <- bullets) {
            if(bullet.shouldRemove) {
                bullet.dispose()
                bullets -= bullet
            }

            if(enemy1!=null) {
                if (Intersector.intersectRectangles(bullet.bulletSprite.getBoundingRectangle, enemy1.tankBaseSprite.getBoundingRectangle, new Rectangle())) {
                    explosion = new Explosion()
                    bullet.dispose()
                    bullets -= bullet
                }
            }
        }
        //we only create explosions when a bullet hits but we need to keep drawing the explosion until the animation finishes
        if(explosion!=null) {
            explosion.draw(renderer.getBatch, enemy1.tankBaseSprite.getX, enemy1.tankBaseSprite.getY)
            if(explosion.isAnimationFinished()) {
                explosion = null
                enemy1 = null
            }
        }

        renderer.getBatch.end()
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

        val playerBaseSprite = new Sprite(new Texture("/Users/neild/Dev/iabsm/core/src/main/resources/tankBase.png"))
        val playerTurretSprite = new Sprite(new Texture("/Users/neild/Dev/iabsm/core/src/main/resources/tankTurret.png"))

        val enemyBaseSprite = new Sprite(new Texture("/Users/neild/Dev/iabsm/core/src/main/resources/tankBaseGrey.png"))
        val enemyTurretSprite = new Sprite(new Texture("/Users/neild/Dev/iabsm/core/src/main/resources/tankTurretGrey.png"))


        player = new Player(playerBaseSprite, playerTurretSprite, 500, 50, map.getLayers.get(0).asInstanceOf[TiledMapTileLayer])
        enemy1 = new Enemy(enemyBaseSprite, enemyTurretSprite, 400, 400, map.getLayers.get(0).asInstanceOf[TiledMapTileLayer])
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

        player.stopSounds(keycode)
        //this code moves the map
//        if(keycode == Input.Keys.A)
//            playerX -= Gdx.graphics.getDeltaTime() * playerSpeed
//            player.setX(playerX)
//            //camera.translate(-32,0);
//        if(keycode == Input.Keys.D)
//            playerX += Gdx.graphics.getDeltaTime() * playerSpeed
//            player.setX(playerX)
//            //camera.translate(32,0);
//        if(keycode == Input.Keys.W)
//            playerY -= Gdx.graphics.getDeltaTime() * playerSpeed
//            player.setY(playerY)
//            //camera.translate(0,-32);
//        if(keycode == Input.Keys.S)
//            playerY += Gdx.graphics.getDeltaTime() * playerSpeed
//            player.setY(playerY)
//        //camera.translate(0,32);
//        if(keycode == Input.Keys.NUM_1)
//            map.getLayers().get(0).setVisible(!map.getLayers().get(0).isVisible());
////        if(keycode == Input.Keys.NUM_2)
////            map.getLayers().get(1).setVisible(!map.getLayers().get(1).isVisible());
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
