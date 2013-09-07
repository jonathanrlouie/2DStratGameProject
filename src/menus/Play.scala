package menus

import java.io._
import org.lwjgl.input.Keyboard
import org.newdawn.slick._
import org.newdawn.slick.state._
import game.Board
import game.BoardLocation
import game.CharacterUnit

class Play(state : Int) extends BasicGameState{
  var board : Board = new Board(new Array[CharacterUnit](0),12,7,"GEEEEEEEEEEG"+
  																 "GEEEEGEEEEEG"+
  																 "SEEEEEESEEES"+
  																 "SEEEEEEEEEES"+
  																 "SEEEEEESEEES"+
  																 "SEEEEEESEEES"+
  																 "SSSSSSSSSSSS")
  
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
    
    g.setColor(Color.yellow)
    g.drawRect(board.getCursor(0)*40,board.getCursor(1)*40,39,39)
    
  }
  
  override def update(gc:GameContainer, sbg : StateBasedGame, delta : Int){
    val input = gc.getInput
    val cursorX = board.getCursor(0)
    val cursorY = board.getCursor(1)
    
    if (input.isKeyPressed(Keyboard.KEY_LEFT)){
      if (cursorX > 0){
        board.setCursor(cursorX-1,cursorY)
      }
    }
    else if (input.isKeyPressed(Keyboard.KEY_RIGHT)){
      if (cursorX < board.getBoardWidth-1){
        board.setCursor(cursorX+1,cursorY)
      }
    }
    else if (input.isKeyPressed(Keyboard.KEY_DOWN)){
      if (cursorY < board.getBoardHeight-1){
        board.setCursor(cursorX,cursorY+1)
      }
    }
    else if (input.isKeyPressed(Keyboard.KEY_UP)){
      if (cursorY > 0){
        board.setCursor(cursorX,cursorY-1)
      }
    }
    else if (input.isKeyPressed(Keyboard.KEY_Z)){
      if (board.getBoardLocation(cursorX,cursorY).hasSprite){
        
      }
    }
  }
  
  override def getID : Int = 2
}