package cs2.game

import scalafx.scene.image.Image
import cs2.util.Vec2

/** The player representation for a simple game based on sprites. Handles all
 *  information regarding the player's positions, movements, and abilities.
 *
 *  @param avatar the image representing the player
 *  @param initPos the initial position of the '''center''' of the player
 *  @param bulletPic the image of the bullets fired by this player
 */
class Player(avatar:Image, initPos:Vec2, private val bulletPic:Image) extends Sprite(avatar, initPos) {

  /** moves the player sprite one "step" to the left.  The amount of this
   *  movement will likely need to be tweaked in order for the movement to feel
   *  natural.
   *
   *  @return none/Unit
   */
  def moveLeft():Unit = {
    if(pos.x > 0) { move(Vec2(-5, 0)) }
  }

  /** moves the player sprite one "step" to the right (see note above)
   *
   *  @return none/Unit
   */
  def moveRight():Unit = {
    if(pos.x < 750) { this.move(Vec2(5, 0)) }
  }

  def moveUp():Unit = {
    if(pos.y > 0) { this.move(Vec2(0, -5)) }
  }

  def moveDown():Unit = {
    if(pos.y < 741) { this.move(Vec2(0, 5)) }
  }

  def reset():Unit = {
    //this.moveTo(Vec2(375, 700))
    this.moveTo(Vec2(375, 700))
  }

  /** creates a new Bullet instance beginning from the player, with an
   *  appropriate velocity
   *
   *  @return Bullet - the newly created Bullet object that was fired
   */
  def shoot():Bullet = {
    new Bullet(SpaceGameApp.bulletImg, Vec2((pos.x), (pos.y)-55), Vec2(0, -10))
  }

  def location():Vec2 = {
    pos
  }

  override def clone():Player = {
    new Player(this.avatar, pos.clone(), this.bulletPic)
  }

}
