package cs2.game

import scalafx.scene.canvas.GraphicsContext
import cs2.util.Vec2
import scalafx.scene.paint.Color

/** contains the control and logic to present a coordinated set of Enemy objects.
 *  For now, this class generates a "grid" of enemy objects centered near the
 *  top of the screen.
 *
 *  @param nRows - number of rows of enemy objects
 *  @param nCols - number of columns of enemy objects
 */

class EnemySwarm(private val nRows:Int, private val nCols:Int) {
  
  protected var total = 0

  var score = 0

  var enemyArray = Array.ofDim[Enemy](nRows, nCols)
  val width = 50 + ((nCols-1)*60)

  //top left
  var coords = Vec2(400 - (width/2), 50)
  //top right
  var rCoords = Vec2(400-(width/2) + (60*nCols), 50)
  var vector = Vec2(3, 0)

  for(i <- 0 until nCols){
    for (j <- 0 until nRows){
      enemyArray(j)(i) = new Enemy(SpaceGameApp.starImg, Vec2(coords.x + (60*i), coords.y + (60*j)), SpaceGameApp.bulletImg)
    }
  }

  def getCoords():Vec2 = {
    coords
  }

  def setCoords(newC:Vec2):Unit = {
    coords = newC
  }

  var r = false //reset!!

  /** method to display all Enemy objects contained within this EnemySwarm
   *
   *  @param g - the GraphicsContext to draw into
   *  @return none/Unit
   */

  def update(g:GraphicsContext):Unit = {
    
  }
  
  def display(g:GraphicsContext):Unit = {
    if(rCoords.x >= 800) {
      vector = Vec2(-3, 0)
    }
    if(coords.x <= 0) {
      vector = Vec2(3, 0)
    }
    //g.setFill(Color.White)
    for(i <- 0 until nCols){
      for (j <- 0 until nRows){
        if(!(enemyArray(j)(i).isDead)) {
          if(r) {
            enemyArray(j)(i).moveTo(Vec2(400-(width/2) + (60*i), 50 + (60*j)))
          }
          else {
            enemyArray(j)(i).move(vector)
          }
          enemyArray(j)(i).display(g)
        }
      }
    }
    if(!r) {
      rCoords += vector
      coords += vector
    }
    else {
      coords = Vec2(400 - (width/2), 50)
      rCoords = Vec2(400-(width/2) + (60*nCols), 50)
      r = false
    }
  }
  
  def canBeReset():Boolean = {
     if(total == nCols*nRows){ true }
     else { false }
  }

  def reset():Unit = {
    for(i <- 0 until nCols){
      for (j <- 0 until nRows){
        enemyArray(j)(i).isDead = false
      }
    }
    r = true
    total = 0
  }

  /** overridden method of ShootsBullets. Creates a single, new bullet instance
   *  originating from a random enemy in the swarm. (Not a bullet from every
   *  object, just a single from a random enemy)
   *
   *  @return Bullet - the newly created Bullet object fired from the swarm
   */
  def shoot():Bullet = {
    val rand = new scala.util.Random
    val row = rand.nextInt(nRows)
    val col = rand.nextInt(nCols)
    if((!(enemyArray(row)(col)).isDead)) {
      (enemyArray(row)(col)).shoot()
    }
    else {
      this.shoot()
    }
  }

  def intersects(other:Bullet):Boolean = {
    var result = false
    for(i <- 0 until nCols){
      for (j <- 0 until nRows){
        if(enemyArray(j)(i).intersects(other) && !(enemyArray(j)(i).isDead)){
          enemyArray(j)(i).isDead = true
          result = true
          total += 1
          score += 10
        }
      }
    }
    result
  }

  def intersects(other:Player):Boolean = {
    var result = false
    for(i <- 0 until nCols){
      for (j <- 0 until nRows){
        if(enemyArray(j)(i).intersects(other) && !(enemyArray(j)(i).isDead)){ result = true }
      }
    }
    result
  }

  override def clone():EnemySwarm = {
    val c = new EnemySwarm(this.nRows, this.nCols)
    for(i <- 0 until nCols){
      for (j <- 0 until nRows){
        c.enemyArray(j)(i) = this.enemyArray(j)(i).clone()
        c.enemyArray(j)(i).isDead = this.enemyArray(j)(i).isDead
      }
    }
    c.total = this.total
    c.coords = this.coords.clone()
    c.rCoords = this.rCoords.clone()
    c.vector = this.vector.clone()
    c.score = this.score
    c
  }
}