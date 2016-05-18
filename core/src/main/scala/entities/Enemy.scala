package entities

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}

/**
  * Created by neild on 18/05/2016.
  */
class Enemy(baseSprite: Sprite, turretSprite: Sprite, startingX: Float, startingY: Float)
    extends Player(baseSprite, turretSprite, startingX, startingY) {

}
