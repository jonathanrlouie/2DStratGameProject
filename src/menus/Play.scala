package menus

import java.io._
import org.newdawn.slick._
import org.newdawn.slick.state._
import game.Board
import game.BoardLocation
import game.CharacterUnit

class Play(state : Int) extends BasicGameState{
  var board : Board = new Board(new Array[CharacterUnit](0),4,4,"GGGG"+
  																"GSEG"+
  																"GSSG"+
  																"GGGG")
  
  def load(path : String) : Object = {
	var ois : ObjectInputStream = new ObjectInputStream(new FileInputStream(path))
	val result : Object = ois.readObject
	ois.close
	result
  }
  
  override def init(gc:GameContainer, sbg : StateBasedGame){
    board.createBoard
  }
  
  override def render(gc:GameContainer, sbg : StateBasedGame, g:Graphics){
    val backgroundimg : Image = new Image("res/Background.png")
    backgroundimg.draw(0,0)
    for (i <- 0 until board.getBoardWidth){
      for (j <- 0 until board.getBoardHeight){
        val bl : BoardLocation = board.getBoardLocation(i,j)
        if (bl.hasSprite){
          val img : Image = new Image("res/" + bl.getSprite.getName + ".png")
          img.draw(40*i,40*j)
        }
      }
    }
  }
  
  override def update(gc:GameContainer, sbg : StateBasedGame, delta : Int){
    
  }
  
  override def getID : Int = 2
}