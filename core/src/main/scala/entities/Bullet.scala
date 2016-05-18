package entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.{Intersector, MathUtils, Vector2}

/**
  * Created by neild on 18/05/2016.
  */
class Bullet(xPos: Float, yPos: Float, headingDegrees: Float) {

    val lifeTime: Float = 2f
    var lifeTimer: Float = 0f
    val speed: Float = 60 * 2
    var headingRadians: Double = headingDegrees * Math.PI / 180d
    var shouldRemove: Boolean = false;

    var shotSound: Sound = null
    var shotSoundPlaying: Boolean = false
    var shotSoundId: Long = 0l

    var bulletSprite = new Sprite(new Texture("/Users/neild/Dev/iabsm/core/src/main/resources/bullet2.png"))
    //bulletSprite.setOrigin(bulletSprite.getWidth() / 2, bulletSprite.getHeight / 2)
    bulletSprite.setOrigin(4f, 4f)

    shotSound = Gdx.audio.newSound(Gdx.files.internal("/Users/neild/Dev/iabsm/core/src/main/resources/tankShot.wav"))

    setX(xPos)
    setY(yPos)
    playShotSound()


    def setX(playerX: Float) = {
        bulletSprite.setX(playerX)
    }

    def setY(playerY: Float) = {
        bulletSprite.setY(playerY)
    }

    def updatePosition(deltaTime: Float) = {

        val dx = -MathUtils.sin(headingRadians.toFloat) * speed
        val dy = MathUtils.cos(headingRadians.toFloat) * speed

        setX((bulletSprite.getX) + dx * deltaTime)
        setY((bulletSprite.getY) + dy * deltaTime)

        lifeTimer += deltaTime
        if (lifeTimer > lifeTime) {
            shouldRemove = true;
        }
    }

    def playShotSound() = {
        shotSoundId = shotSound.play(0.5f)
        shotSound.setLooping(shotSoundId, false)
    }

    def draw(batch: Batch): Unit = {
        updatePosition(Gdx.graphics.getDeltaTime)
        bulletSprite.draw(batch)
    }

    def dispose(): Unit = {
        bulletSprite.getTexture.dispose
        shotSound.dispose
    }


}



