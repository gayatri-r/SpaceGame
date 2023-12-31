package cs2.game

import scalafx.scene.image.Image
import cs2.util.Vec2
import scalafx.scene.canvas.GraphicsContext

/** A graphical sprite object used for gaming or other visual displays
 *  
 *  @constructor create a new sprite based on the given image and initial location
 *  @param img the image used to display this sprite
 *  @param pos the initial position of the '''center''' of the sprite in 2D space
 */
abstract class Sprite(protected val img:Image, protected var pos:Vec2) {

  /** moves the sprite a relative amount based on a specified vector
   *  
   *  @param direction - an offset that the position of the sprite should be moved by
   *  @return none/Unit
   */
  def move(direction:Vec2):Unit = {
    pos+=direction
  }
  
  /** moves the sprite to a specific location specified by a vector (not a relative movement)
   *  
   *  @param location - the new location for the sprite's position
   *  @return none/Unit
   */
  def moveTo(location:Vec2):Unit = {
    pos=location
  }
  
  /** Method to display the sprite at its current location in the specified Graphics2D context
   *  
   *  @param g - a GraphicsContext object capable of drawing the sprite
   *  @return none/Unit
   */
  def display(g:GraphicsContext):Unit = {
    g.drawImage(img, pos.x, pos.y)
  }

  def intersects(other:Bullet):Boolean = {
    if((this.pos.x + 50) >= (other.pos.x) &&
       (this.pos.x) <= (other.pos.x + 50) &&
       (this.pos.y - 59) <= (other.pos.y) &&
       (this.pos.y) >= (other.pos.y - 100)) {
        true
       }
    else false
  }

  def intersects(other:Player):Boolean = {
    if((this.pos.x + 50) >= (other.pos.x) &&
       (this.pos.x) <= (other.pos.x + 50) &&
       (this.pos.y - 50) <= (other.pos.y) &&
       (this.pos.y) >= (other.pos.y - 50)) {
        true
       }
    else false
  }

}
