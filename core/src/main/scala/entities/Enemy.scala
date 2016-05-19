package entities

import com.badlogic.gdx.graphics.g2d.{Sprite}
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer

/**
  * Created by neild on 18/05/2016.
  */
class Enemy(baseSprite: Sprite, turretSprite: Sprite, startingX: Float, startingY: Float, mapCollisionLayer:TiledMapTileLayer)
    extends Player(baseSprite, turretSprite, startingX, startingY, mapCollisionLayer) {

}
