package ai

import com.badlogic.gdx.ai.steer.behaviors.{Arrive, Seek}
import com.badlogic.gdx.ai.steer.{SteeringAcceleration, SteeringBehavior, Steerable}
import com.badlogic.gdx.ai.utils.Location
import com.badlogic.gdx.math.{MathUtils, Vector2}

/**
  * Created by neild on 19/05/2016.
  */
class EnemySteering(startingPosition: Vector2, createTestSteeringBehaviourTarget: Boolean) extends Steerable[Vector2] {

    val steeringOutput: SteeringAcceleration[Vector2] = new SteeringAcceleration[Vector2](new Vector2())

    var orientation: Float = 0f
    var angularVelocity: Float = 0f

    //do we always need to face the direction we are moving?  (chassis does)
    var independentFacing: Boolean = false
    var maxLinearSpeed: Float = 3f
    var maxLinearAcceleration: Float = 0.1f
    var maxAngularSpeed: Float = 3f
    var maxAngularAcceleration: Float = 3f

    var position: Vector2 = startingPosition
    //think this should be something other than the position.. but this will get us started...
    var linearVelocity: Vector2 = new Vector2(startingPosition)

    var steeringBehaviour: SteeringBehavior[Vector2] = null

    if (createTestSteeringBehaviourTarget) {
        //steeringBehaviour = new Seek[Vector2](this, new EnemySteering(new Vector2(position.x - 300, position.y), false))
        steeringBehaviour = new Arrive[Vector2](this, new EnemySteering(new Vector2(position.x - 300, position.y), false))
        steeringBehaviour.asInstanceOf[Arrive[Vector2]].setDecelerationRadius(5f)
        steeringBehaviour.asInstanceOf[Arrive[Vector2]].setArrivalTolerance(10f)
    }

    var zeroLinearSpeedThreshold = 1f
    //TODO: We might need a reference to the Enemy class (or at least the sprite)


    override def getLinearVelocity: Vector2 = {
        linearVelocity
    }

    override def getPosition: Vector2 = {
        position
    }

    override def isTagged: Boolean = {
        false
    }

    override def getBoundingRadius: Float = {
        0f
    }

    override def setTagged(tagged: Boolean): Unit = {
        null
    }

    //implementation depends on co-ordinate system used...(we assume Y is up)
    override def vectorToAngle(vector: Vector2): Float = {
        Math.atan2(vector.x, vector.y).asInstanceOf[Float]
    }

    //implementation depends on co-ordinate system used...(we assume Y is up)
    override def angleToVector(outVector: Vector2, angle: Float): Vector2 = {
        outVector.x = (-Math.sin(angle)).asInstanceOf[Float]
        outVector.y = (-Math.cos(angle)).asInstanceOf[Float]
        outVector
    }

    override def getAngularVelocity: Float = {
        angularVelocity
    }

    override def getOrientation: Float = {
        0f
    }

    override def setMaxAngularAcceleration(maxAngularAcceleration: Float): Unit = {
        this.maxAngularAcceleration = maxLinearAcceleration
    }

    override def getMaxAngularAcceleration: Float = {
        maxAngularAcceleration
    }

    override def setMaxLinearAcceleration(maxLinearAcceleration: Float): Unit = {
        this.maxLinearAcceleration = maxLinearAcceleration
    }

    override def getMaxLinearSpeed: Float = {
        maxLinearSpeed
    }

    override def setMaxLinearSpeed(maxLinearSpeed: Float): Unit = {
        this.maxLinearSpeed = maxLinearSpeed
    }

    override def getMaxLinearAcceleration: Float = {
        maxLinearAcceleration
    }

    override def setMaxAngularSpeed(maxAngularSpeed: Float): Unit = {
        this.maxAngularSpeed = getMaxAngularSpeed
    }

    override def getMaxAngularSpeed: Float = {
        maxAngularSpeed
    }


    def calculateOrientationFromLinearVelocity(character: Steerable[Vector2]): Float = {
        var result: Float = 0f
        if (character.getLinearVelocity.isZero(MathUtils.FLOAT_ROUNDING_ERROR)) {
            result = character.getOrientation
        }
        else {
            result = character.vectorToAngle(character.getLinearVelocity)
        }
        result
    }

    def update(deltaTime: Float) = {
        if (steeringBehaviour != null) {
            steeringBehaviour.calculateSteering(steeringOutput)
            applySteering(steeringOutput, 1f)
        }
    }

    def applySteering(steering: SteeringAcceleration[Vector2], time: Float) = {

        Console.println("SteeringX: " + steering.linear.x + " SteeringY: " + steering.linear.y)

        //update position and linear velocity. Velocity is trimmed at the max speed
        this.position.mulAdd(linearVelocity, time)
        this.linearVelocity.mulAdd(steering.linear, time).limit(this.getMaxLinearSpeed)

        //update orientation and angular velocity
        if (independentFacing) {
            this.orientation += angularVelocity * time
            this.angularVelocity += steering.angular * time
        } else {
            // for non-independent facing we have to align orientation to linear velocity
            val newOrientation: Float = calculateOrientationFromLinearVelocity(this)
            if (newOrientation != this.orientation) {
                this.angularVelocity = (newOrientation - this.orientation) * time
                this.orientation = newOrientation
            }
        }

    }

    override def newLocation(): Location[Vector2] = {
        return null;
    }

    override def setOrientation(orientation: Float): Unit = {
        this.orientation = orientation
    }

    override def getZeroLinearSpeedThreshold: Float = {
        getZeroLinearSpeedThreshold
    }

    override def setZeroLinearSpeedThreshold(value: Float): Unit = {
        this.zeroLinearSpeedThreshold = value
    }
}
