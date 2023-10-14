package cs2.game

import scalafx.application.JFXApp
import scalafx.Includes._
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.animation.AnimationTimer
import scalafx.scene.paint.Color
import scalafx.scene.input.MouseEvent
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import scalafx.scene.image.Image
import cs2.util.Vec2
import scala.collection.mutable.Buffer
import scala.collection.mutable.Set
import scalafx.scene.text.Font
import cs2.adt.LinkedStack

/** main object that initiates the execution of the game, including construction
 *  of the window.
 *  Will create the stage, scene, and canvas to draw upon. Will likely contain or
 *  refer to an AnimationTimer to control the flow of the game.
 */
object SpaceGameApp extends JFXApp {

  var showStartScreen = true

  val path1 = getClass().getResource("/images/star.png")
  val starImg = new Image(path1.toString)
  //50x59

  val path3 = getClass().getResource("/images/dead_enemy.jpg")
  val dEnemy = new Image(path3.toString)
  //50x59

  val path2 = getClass().getResource("/images/bullet.png")
  val bulletImg = new Image(path2.toString)
  //50x100

  stage = new JFXApp.PrimaryStage {
    title = "Galaga Clone"
    scene = new Scene(800,800) {

      val canvas = new Canvas(800,800)
      content = canvas
      val g = canvas.graphicsContext2D

      val playerSprite = new Player(starImg, Vec2(375, 700), starImg)
      var enemyBulletList = Buffer[Bullet]()
      var eToRemove = Buffer[Bullet]()
      var playerBulletList = Buffer[Bullet]()
      var pToRemove = Buffer[Bullet]()
      var keySet = Set[KeyCode]()
      val enemy = new EnemySwarm(3, 4)
      val rand = new scala.util.Random
      var enemyCooldown = 0
      var playerCooldown = 0
      var lives = 3
      var totalFrames = 0

      var gameState = new GameState(playerSprite, enemy, playerBulletList, enemyBulletList, lives)

      val history = new LinkedStack[GameState]()

      canvas.requestFocus()

      canvas.onKeyPressed = (e:KeyEvent) => {
        if(e.code == KeyCode.Up || e.code == KeyCode.W) { keySet += KeyCode.Up }
        if(e.code == KeyCode.Left || e.code == KeyCode.A) { keySet += KeyCode.Left }
        if(e.code == KeyCode.Down || e.code == KeyCode.S) { keySet += KeyCode.Down }
        if(e.code == KeyCode.Right || e.code == KeyCode.D) { keySet += KeyCode.Right }
        if(e.code == KeyCode.Space) { keySet += KeyCode.Space }
        if(e.code == KeyCode.X) { keySet += KeyCode.X }
        if(e.code == KeyCode.R) { keySet += KeyCode.R }
      }

      canvas.onKeyReleased = (e:KeyEvent) => {
        if(e.code == KeyCode.Up || e.code == KeyCode.W) { keySet -= KeyCode.Up }
        if(e.code == KeyCode.Left || e.code == KeyCode.A) { keySet -= KeyCode.Left }
        if(e.code == KeyCode.Down || e.code == KeyCode.S) { keySet -= KeyCode.Down }
        if(e.code == KeyCode.Right || e.code == KeyCode.D) { keySet -= KeyCode.Right }
        if(e.code == KeyCode.Space) { keySet -= KeyCode.Space }
        if(e.code == KeyCode.X) { keySet -= KeyCode.X }
        if(e.code == KeyCode.R) { keySet -= KeyCode.R }
      }

      var oldT = 0L

      val timer = AnimationTimer(t => {
        if(t-oldT > 1e9/60) {
          oldT = t
          g.setFill(Color.White)
          g.fillRect(0,0, width.value, height.value)

          if(showStartScreen){
            if(keySet(KeyCode.X)) { showStartScreen = false }
            g.setFill(Color.Black)
            g.fillRect(0, 0, 800, 800)
            g.setFill(Color.Yellow)
            g.setFont(Font.font("Arial", 40))
            g.fillText("GALAGA", 310, 400)
            g.setFont(Font.font("Arial", 20))
            g.fillText("Press X to start", 325, 430)
          }
          else if(gameState.lives <= 0){
            if(keySet(KeyCode.X)) {
              gameState.playerBulletList.clear()
              gameState.enemyBulletList.clear()
              gameState.enemy.reset()
              gameState.lives = 3
              totalFrames = 0
              history.clear()
            }
            g.setFill(Color.Black)
            g.fillRect(0, 0, 800, 800)
            g.setFill(Color.Yellow)
            g.setFont(Font.font("Arial", 40))
            g.fillText("GAME OVER", 285, 400)
            g.fillText(gameState.enemy.score.toString(), 370, 450)
            g.setFont(Font.font("Arial", 20))
            g.fillText("Press X to reset", 325, 480)
          }
          else if(totalFrames > 0 && keySet(KeyCode.R)){
            gameState = history.pop()
            g.setFill(Color.Black)
            g.setFont(Font.font("Arial", 20))
            g.fillText(gameState.lives.toString(), 780, 760)
            g.fillText(gameState.enemy.score.toString(), 20, 750)
            
            gameState.enemy.display(g)
            gameState.playerSprite.display(g)
            for(sys <- gameState.enemyBulletList){
              sys.timeStep()
              sys.display(g)
            }
            for(sys <- gameState.playerBulletList){
              sys.timeStep()
              sys.display(g)
            }

            g.strokeRect(20, 765, 100, 20)
            g.setFill(Color.Black)
            g.fillRect(20, 765, totalFrames, 20)

            totalFrames -= 1
          }
          else {
            g.setFill(Color.Black)
            g.setFont(Font.font("Arial", 20))
            g.fillText(gameState.lives.toString(), 780, 760)
            g.fillText(gameState.enemy.score.toString(), 20, 750)
            g.strokeRect(20, 765, 100, 20)
            if(totalFrames <= 100) { g.fillRect(20, 765, totalFrames, 20) }
            else { g.fillRect(20, 765, 100, 20) }
            
            gameState.enemy.display(g)

            //player bullet intersection w/ enemy swarm
            for(b <- gameState.playerBulletList){
              gameState.enemy.intersects(b)
            }

            gameState.playerSprite.display(g)

            if(keySet(KeyCode.Left)) { gameState.playerSprite.moveLeft() }
            if(keySet(KeyCode.Right)) { gameState.playerSprite.moveRight() }
            if(keySet(KeyCode.Up)) { gameState.playerSprite.moveUp() }
            if(keySet(KeyCode.Down)) { gameState.playerSprite.moveDown() }
            if(keySet(KeyCode.Space) && (playerCooldown == 0)) {
              gameState.playerBulletList += gameState.playerSprite.shoot()
              playerCooldown = 20
            }

            if(enemyCooldown == 0) {
              gameState.enemyBulletList += gameState.enemy.shoot()
              enemyCooldown = 30
            }

            for(del <- gameState.enemyBulletList){
              if(del.isDead()){
                eToRemove += del
              }
            }

            for(sys <- gameState.enemyBulletList){
              sys.timeStep()
              sys.display(g)
            }

            for(del <- gameState.playerBulletList){
              if(del.isDead()){
                pToRemove += del
              }
            }

            for(sys <- gameState.playerBulletList){
              sys.timeStep()
              sys.display(g)
            }
            
            for(b <- gameState.enemyBulletList){
              if(gameState.playerSprite.intersects(b)){
                eToRemove += b
                gameState.playerSprite.reset()
                gameState.lives -= 1
              }
            }

            for(b <- gameState.enemyBulletList){
              for(p <- gameState.playerBulletList){
                if(b.intersects(p)){
                  eToRemove += b
                  pToRemove += p
                }
              }
            }

            if(gameState.enemy.intersects(gameState.playerSprite)) {
              gameState.lives -= 1
              gameState.playerSprite.reset()
            }

            gameState.enemyBulletList --= eToRemove
            gameState.playerBulletList --= pToRemove
            if(enemyCooldown > 0) { enemyCooldown-=1 }
            if(playerCooldown > 0) { playerCooldown-=1 }
            if (totalFrames < 100) { totalFrames += 1 }

            if(gameState.enemy.canBeReset){
              /** Certain rows of enemies disappear because they intersect with
              *   player bullets still on screen so I cleared the player bullets
              *   to stop this from happening (the line bellow this comment).
              *   
              *   If that code is commented out, the game will play as intended by
              *   the prompt in the homework, however at least one row of enemies
              *   will be missing because of the size of the
              *   bullets.
              */
              gameState.playerBulletList.clear()
              gameState.enemy.reset()
            }

            gameState.counter += 1
            history.push(gameState.clone())
          }
        }
      })
      timer.start()

    }
  }

}