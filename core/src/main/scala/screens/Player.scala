package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.Vector2

/**
  * We may want to split the logic for the player from the Sprite which is what we show on screen.
  */
class Player(sprite: Sprite) extends Sprite(sprite) {

    // the movement velocity
    var velocity: Vector2 = new Vector2()
    val speed: Float = 60*2
    val gravity: Float = 60 *1.8f
    setX(500)
    setY(500)

    def update(deltaTime: Float) = {
        //calcualte the new position of the player according to speed and gravity
        velocity.y -= gravity*deltaTime  //I dont care about gravity! :)

        //clamp velocity
        if(velocity.y>speed) {
            velocity.y = speed
        }
        else if(velocity.y<speed) {
            velocity.y = -speed
        }
        setX(getX+velocity.x*deltaTime)
        setY(getY+velocity.y*deltaTime)
//        setX(500)
//        setY(500)
    }


    override def draw(batch: Batch): Unit = {
        update(Gdx.graphics.getDeltaTime)
        super.draw(batch)
    }
}
