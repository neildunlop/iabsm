package entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Batch, TextureRegion, Animation}

/**
  * Created by neild on 18/05/2016.
  */
class Explosion {

    val frameCols = 4
    val frameRows = 4

    var explosionAnimation: Animation = null
    var explosionSheet: Texture = null
    var explosionFrames: com.badlogic.gdx.utils.Array[TextureRegion] = null
    var currentFrame: TextureRegion = null
    var stateTime: Float = 0f

    var explosionSound: Sound = null
    var explosionSoundPlaying: Boolean = false
    var explosionSoundId: Long = 0l

    create()

    def create() = {

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("/Users/neild/Dev/iabsm/core/src/main/resources/explosion.wav"))

        explosionSheet = new Texture(Gdx.files.internal("explode_4.png"))
        val tmp: Array[Array[TextureRegion]] = TextureRegion.split(explosionSheet, explosionSheet.getWidth / frameCols, explosionSheet.getHeight / frameRows)
        explosionFrames = new com.badlogic.gdx.utils.Array[TextureRegion]
        var index = 0;

        for (i <- 0 until frameRows) {
            for (j <- 0 until frameCols) {
                explosionFrames.add(tmp(i)(j))
                index += 1
            }
        }

        playExplosionSound()
        explosionAnimation = new Animation(0.025f, explosionFrames)
        stateTime = 0f;
    }

    def update(deltaTime: Float) = {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = explosionAnimation.getKeyFrame(stateTime, false);
    }

    def draw(batch: Batch, xPos: Float, yPos: Float): Unit = {
        update(Gdx.graphics.getDeltaTime)
        batch.draw(currentFrame, xPos, yPos);
    }

    def dispose() = {
        explosionSheet.dispose()
        explosionSound.dispose()
    }

    def isAnimationFinished(): Boolean = {
        explosionAnimation.isAnimationFinished(stateTime)
    }

    def playExplosionSound() = {
        explosionSoundId = explosionSound.play(0.4f)
        explosionSound.setLooping(explosionSoundId, false)
    }
}
