package screens

import com.badlogic.gdx.{Input, Gdx}
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}

/**
  * We may want to split the logic for the player from the Sprite which is what we show on screen.
  */
class Player() {

    var playerSpeed: Float = 60.0f
    // pixels per second.
    var playerX: Float = 500f
    var playerY: Float = 500f
    var chassisRotation: Float = 0f
    var turretRotation: Float = 0f
    var rotationSpeed: Float = 180f
    // degress per second

    var engineSound: Sound = null
    var engineSoundPlaying: Boolean = false
    var engineSoundId: Long = 0l

    var turretSound: Sound = null
    var turretSoundPlaying: Boolean = false
    var turretSoundId: Long = 0l

    // the movement velocity
    //var velocity: Vector2 = new Vector2()
    //val speed: Float = 60*2
    //val gravity: Float = 60 *1.8f

    var tankBaseSprite: Sprite = null
    var tankTurretSprite: Sprite = null

    tankBaseSprite = new Sprite(new Texture("/Users/neild/Dev/iabsm/core/src/main/resources/tankBase.png"))
    tankTurretSprite = new Sprite(new Texture("/Users/neild/Dev/iabsm/core/src/main/resources/tankTurret.png"))
    tankBaseSprite.setOrigin(tankBaseSprite.getWidth() / 2, tankBaseSprite.getHeight / 2)
    tankTurretSprite.setOrigin(tankTurretSprite.getWidth() / 2, tankTurretSprite.getHeight / 2)

    engineSound = Gdx.audio.newSound(Gdx.files.internal("/Users/neild/Dev/iabsm/core/src/main/resources/tankEngine.wav"));
    turretSound = Gdx.audio.newSound(Gdx.files.internal("/Users/neild/Dev/iabsm/core/src/main/resources/turretSound.wav"));

    tankBaseSprite.setX(500)
    tankBaseSprite.setY(500)

    tankTurretSprite.setX(500)
    tankTurretSprite.setY(500)

    def playEngineSound() = {
        if (!engineSoundPlaying) {
            engineSoundPlaying = true
            engineSoundId = engineSound.play(0.1f)
            engineSound.setLooping(engineSoundId, true)
        }
    }

    def stopEngineSound() = {
        if (engineSoundPlaying) {
            engineSoundPlaying = false
            engineSound.stop(engineSoundId)
            engineSound.setLooping(engineSoundId, false)
        }
    }

    def playTurretSound() = {
        if (!turretSoundPlaying) {
            turretSoundPlaying = true
            turretSoundId = turretSound.play(0.4f)
            turretSound.setLooping(turretSoundId, true)
        }
    }

    def stopTurretSound() = {
        if (turretSoundPlaying) {
            turretSoundPlaying = false
            turretSound.stop(turretSoundId)
            turretSound.setLooping(turretSoundId, false)
        }
    }

    def stopSounds(keycode:Int) = keycode match {

        case Input.Keys.W | Input.Keys.A | Input.Keys.S | Input.Keys.D => stopEngineSound()
        case Input.Keys.K | Input.Keys.L => stopTurretSound()
        case _ =>
    }

    def moveLeft() = {
        playEngineSound()
        if (chassisRotation > 271f && chassisRotation < 360f || chassisRotation == 0f) {
            //top left quadrant = turn left
            chassisRotation -= Gdx.graphics.getDeltaTime() * rotationSpeed
        }
        else if (chassisRotation >= 180f && chassisRotation < 269f) {
            //bottom left quadrant = turn right
            chassisRotation += Gdx.graphics.getDeltaTime() * rotationSpeed
        }
        else if (chassisRotation >= 90f && chassisRotation < 180f) {
            //bottom right quadrant = turn right
            chassisRotation += Gdx.graphics.getDeltaTime() * rotationSpeed
        }
        else if (chassisRotation < 90f && chassisRotation > 0f) {
            //top right quadrant = turn left
            chassisRotation -= Gdx.graphics.getDeltaTime() * rotationSpeed
        }
        if (chassisRotation < 0f) {
            chassisRotation = 360f + chassisRotation
        }
        if (chassisRotation > 360f) {
            chassisRotation = chassisRotation - 360f
        }
        rotateChassis(chassisRotation)
        if (chassisRotation > 269f && chassisRotation < 271f) {
            rotateChassis(270f)
            playerX -= Gdx.graphics.getDeltaTime() * playerSpeed
            setX(playerX)
        }
    }

    def moveRight() = {
        playEngineSound()
        if (chassisRotation > 270f && chassisRotation < 360f || chassisRotation == 0f) {
            //top left quadrant = turn right
            chassisRotation += Gdx.graphics.getDeltaTime() * rotationSpeed
        }
        else if (chassisRotation >= 180f && chassisRotation < 270f) {
            //bottom left quadrant = turn left
            chassisRotation -= Gdx.graphics.getDeltaTime() * rotationSpeed
        }
        else if (chassisRotation > 90f && chassisRotation < 180f) {
            //bottom right quadrant = turn left
            chassisRotation -= Gdx.graphics.getDeltaTime() * rotationSpeed
        }
        else if (chassisRotation < 90f && chassisRotation > 0f) {
            //top right quadrant = turn right
            chassisRotation += Gdx.graphics.getDeltaTime() * rotationSpeed
        }
        if (chassisRotation < 0f) {
            chassisRotation = 360f + chassisRotation
        }
        if (chassisRotation > 360f) {
            chassisRotation = chassisRotation - 360f
        }
        rotateChassis(chassisRotation)
        if (chassisRotation > 89f && chassisRotation < 91f) {
            chassisRotation = 90f
            rotateChassis(chassisRotation)
            playerX += Gdx.graphics.getDeltaTime() * playerSpeed
            setX(playerX)
        }
    }

    def moveUp() = {
        playEngineSound()
        if (chassisRotation > 270f && chassisRotation < 359f) {
            //top left quadrant = turn right
            chassisRotation += Gdx.graphics.getDeltaTime() * rotationSpeed
        }
        else if (chassisRotation >= 180f && chassisRotation < 270f) {
            //bottom left quadrant = turn right
            chassisRotation += Gdx.graphics.getDeltaTime() * rotationSpeed
        }
        else if (chassisRotation >= 90f && chassisRotation < 180f) {
            //bottom right quadrant = turn left
            chassisRotation -= Gdx.graphics.getDeltaTime() * rotationSpeed
        }
        else if (chassisRotation < 90f && chassisRotation > 1f) {
            //top right quadrant = turn left
            chassisRotation -= Gdx.graphics.getDeltaTime() * rotationSpeed
        }
        if (chassisRotation < 0f) {
            chassisRotation = 360f + chassisRotation
        }
        if (chassisRotation > 360f) {
            chassisRotation = chassisRotation - 360f
        }
        rotateChassis(chassisRotation)

        if (chassisRotation > 359f && chassisRotation < 360f || chassisRotation >= 0f && chassisRotation < 1f) {
            rotateChassis(0f)
            playerY += Gdx.graphics.getDeltaTime() * playerSpeed
            setY(playerY)
        }
    }

    def moveDown() = {
        playEngineSound()
        if (chassisRotation > 270f && chassisRotation <= 360f) {
            //top left quadrant = turn left
            chassisRotation -= Gdx.graphics.getDeltaTime() * rotationSpeed
        }
        else if (chassisRotation >= 181f && chassisRotation < 270f) {
            //bottom left quadrant = turn left
            chassisRotation -= Gdx.graphics.getDeltaTime() * rotationSpeed
        }
        else if (chassisRotation >= 90f && chassisRotation < 180f) {
            //bottom right quadrant = turn right
            chassisRotation += Gdx.graphics.getDeltaTime() * rotationSpeed
        }
        else if (chassisRotation < 90f && chassisRotation >= 0f) {
            //top right quadrant = turn right
            chassisRotation += Gdx.graphics.getDeltaTime() * rotationSpeed
        }
        if (chassisRotation < 0f) {
            chassisRotation = 360f + chassisRotation
        }
        if (chassisRotation > 360f) {
            chassisRotation = chassisRotation - 360f
        }
        rotateChassis(chassisRotation)

        if (chassisRotation > 179f && chassisRotation < 181f) {
            chassisRotation = 180f
            rotateChassis(chassisRotation)
            playerY -= Gdx.graphics.getDeltaTime() * playerSpeed
            setY(playerY)
        }
    }

    def turretClockwise() = {
        playTurretSound()
        turretRotation += Gdx.graphics.getDeltaTime() * rotationSpeed
        if (turretRotation > 360f) {
            turretRotation = turretRotation - 360f
        }
        rotateTurret(turretRotation)
    }

    def turretAntiClockwise() = {
        playTurretSound()
        turretRotation -= Gdx.graphics.getDeltaTime() * rotationSpeed
        if (turretRotation > 360f) {
            turretRotation = turretRotation - 360f
        }
        rotateTurret(turretRotation)
    }

    def update(deltaTime: Float) = {
        //calcualte the new position of the player according to speed and gravity
        //        velocity.y -= gravity*deltaTime  //I dont care about gravity! :)
        //
        //        //clamp velocity
        //        if(velocity.y>speed) {
        //            velocity.y = speed
        //        }
        //        else if(velocity.y<speed) {
        //            velocity.y = -speed
        //        }
        //        setX(getX+velocity.x*deltaTime)
        //        setY(getY+velocity.y*deltaTime)
        ////        setX(500)
        ////        setY(500)
    }


    def draw(batch: Batch): Unit = {
        update(Gdx.graphics.getDeltaTime)
        tankBaseSprite.draw(batch)
        tankTurretSprite.draw(batch)
    }

    def dispose(): Unit = {
        tankBaseSprite.getTexture.dispose
        tankTurretSprite.getTexture.dispose
        engineSound.dispose
        turretSound.dispose
    }

    def setX(playerX: Float) = {
        tankBaseSprite.setX(playerX)
        tankTurretSprite.setX(playerX)
    }

    def setY(playerY: Float) = {
        tankBaseSprite.setY(playerY)
        tankTurretSprite.setY(playerY)
    }

    def rotateBoth(playerRotation: Float) = {
        tankBaseSprite.setRotation(-playerRotation)
        tankTurretSprite.setRotation(-playerRotation)
    }

    def rotateChassis(playerRotation: Float) = {
        Console.println("Rotation" + playerRotation)
        tankBaseSprite.setRotation(-playerRotation)
    }

    def rotateTurret(turretRotation: Float) = {
        tankTurretSprite.setRotation(-turretRotation)
    }
}
