package entities

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.{OrthographicCamera, Texture}
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.{Gdx, Input}

/**
  * We may want to split the logic for the player from the Sprite which is what we show on screen.
  */
class Player(baseSprite:Sprite, turretSprite:Sprite, startingX:Float, startingY:Float, mapCollisionLayer:TiledMapTileLayer) {

    var playerSpeed: Float = 60.0f
    // pixels per second.
    var playerX: Float = 500f
    var playerY: Float = 500f
    var chassisRotation: Float = 0f
    var turretRotation: Float = 0f
    var rotationSpeed: Float = 180f
    // degrees per second

    var engineSound: Sound = null
    var engineSoundPlaying: Boolean = false
    var engineSoundId: Long = 0l

    var turretSound: Sound = null
    var turretSoundPlaying: Boolean = false
    var turretSoundId: Long = 0l

    var tankBaseSprite: Sprite = baseSprite
    var tankTurretSprite: Sprite = turretSprite

    var collisionLayer:TiledMapTileLayer = null


    tankBaseSprite.setOrigin(tankBaseSprite.getWidth() / 2, tankBaseSprite.getHeight / 2)
    tankTurretSprite.setOrigin(tankTurretSprite.getWidth() / 2, tankTurretSprite.getHeight / 2)

    engineSound = Gdx.audio.newSound(Gdx.files.internal("/Users/neild/Dev/iabsm/core/src/main/resources/tankEngine.wav"))
    turretSound = Gdx.audio.newSound(Gdx.files.internal("/Users/neild/Dev/iabsm/core/src/main/resources/turretSound.wav"))

    tankBaseSprite.setX(startingX)
    tankBaseSprite.setY(startingY)

    tankTurretSprite.setX(startingX)
    tankTurretSprite.setY(startingY)

    collisionLayer = mapCollisionLayer

    val tileWidth = collisionLayer.getTileWidth
    val tileHeight = collisionLayer.getTileHeight


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

    def moveLeft(camera: OrthographicCamera) = {
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
            val moveDelta = Gdx.graphics.getDeltaTime() * playerSpeed
            playerX -= moveDelta
            camera.translate(-moveDelta,0)
            setX(playerX)
            if(collision()) {
                //undo the move
                playerX += moveDelta
                camera.translate(moveDelta,0)
                setX(playerX)
            }
        }
    }

    def moveRight(camera: OrthographicCamera) = {
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
            val moveDelta = Gdx.graphics.getDeltaTime() * playerSpeed
            playerX += moveDelta
            camera.translate(moveDelta,0)
            setX(playerX)
            if(collision()) {
                //undo the move
                playerX -= moveDelta
                camera.translate(-moveDelta,0)
                setX(playerX)
            }
        }
    }

    def moveUp(camera: OrthographicCamera) = {
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
            val moveDelta = Gdx.graphics.getDeltaTime() * playerSpeed
            playerY += moveDelta
            camera.translate(0,moveDelta)
            setY(playerY)
            if(collision()) {
                //undo the move
                playerY -= moveDelta
                camera.translate(0,-moveDelta)
                setY(playerY)
            }
        }
    }

    def moveDown(camera: OrthographicCamera) = {
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
            val moveDelta = Gdx.graphics.getDeltaTime() * playerSpeed
            playerY -= moveDelta
            camera.translate(0,-moveDelta)
            setY(playerY)
            if(collision()) {
                //undo the move
                playerY = moveDelta
                camera.translate(0,moveDelta)
                setY(playerY)
            }
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



    def collision():Boolean = {
        collisionLayer.getCell((tankBaseSprite.getX/tileWidth).toInt, (tankBaseSprite.getY/tileHeight).toInt)
            .getTile.getProperties
            .containsKey("blocked")
    }

    def update(deltaTime: Float) = {




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
        tankBaseSprite.setRotation(-playerRotation)
    }

    def rotateTurret(turretRotation: Float) = {
        tankTurretSprite.setRotation(-turretRotation)
    }
}
