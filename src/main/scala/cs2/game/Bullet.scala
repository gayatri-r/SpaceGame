package cs2.game

import scalafx.scene.image.Image
import cs2.util.Vec2

/** Representation of a bullet/projectile for a simple game based on sprites.
 *  Handles all information regarding a bullet's position, movements, and 
 *  abilities.
 *  
 *  @param pic the image representing the bullet
 *  @param initPos the initial position of the '''center''' of the bullet
 *  @param vel the initial velocity of the bullet
 */
class Bullet(pic:Image, initPos:Vec2, private var vel:Vec2) extends Sprite(pic, initPos) {

  /** advances the position of the Bullet over a single time step
   * 
   *  @return none/Unit
   */
  def timeStep():Unit = {
    pos += vel
  }

  def isDead():Boolean = {
    if(pos.y < 0 || pos.y > 800) true
    else false
  }
  
  override def intersects(other:Bullet):Boolean = {
    if((this.pos.x + 50) >= (other.pos.x) &&
       (this.pos.x) <= (other.pos.x + 50) &&
       (this.pos.y - 100) <= (other.pos.y) &&
       (this.pos.y) >= (other.pos.y - 100)) {
        true
       }
    else false
  }

  override def clone():Bullet = {
    new Bullet(this.pic, pos.clone(), vel.clone())
  }

}
