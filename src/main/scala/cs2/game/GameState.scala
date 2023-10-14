package cs2.game

import cs2.util.Vec2
import scala.collection.mutable.Buffer
import scalafx.scene.canvas.GraphicsContext

class GameState(var playerSprite:Player, var enemy:EnemySwarm, var playerBulletList:Buffer[Bullet], var enemyBulletList:Buffer[Bullet], var lives:Int) {
  var counter = 0
  var pLocation = playerSprite.location()

  override def clone():GameState = {
    var clonedPBL = Buffer[Bullet]()
    for(p <- this.playerBulletList){
      clonedPBL += p.clone()
    }
    var clonedEBL = Buffer[Bullet]()
    for(e <- this.enemyBulletList){
      clonedEBL += e.clone()
    }
    var cloned = new GameState(this.playerSprite.clone(), this.enemy.clone(), clonedPBL, clonedEBL, this.lives)
    //lives is an int and NOT a reference type so it's okay to just copy it over
    cloned.counter = this.counter
    cloned
  }
}