package menus

import java.io._
import org.lwjgl.input.Keyboard
import org.newdawn.slick._
import org.newdawn.slick.state._
import game._
class Play(state : Int) extends BasicGameState{
  val spriteSize = 40
  //placeholder
  val units = Array[CharacterUnit](new Knight(Array[Weapon](new Knife)))
  var selectingWep = false
  
  var board : Board = new Board(units,12,7,"GEEEEEEEEEEG"+
  								   		   "GEEEEGEEEEEG"+
  										   "SEEEEEESEEES"+
  										   "SEEEEEEEEEES"+
  						 			       "SEEEEEESEEES"+
  										   "SEaEEEESEEES"+
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
    renderBoard(g)
    
    if (selectingWep){
      for (i <- 0 until units.length){
        if (units(i).isSelected){
          val xcoord = units(i).getX
          val ycoord = units(i).getY
          
          //placeholder
          g.setColor(Color.black)
          g.drawRect(xcoord-100,ycoord-5,32*5,32)
        }
      }
    }
    
    g.setColor(Color.yellow)
    g.drawRect(board.getCursor(0)*spriteSize,board.getCursor(1)*spriteSize,spriteSize-1,spriteSize-1)
    
  }
  
  override def update(gc:GameContainer, sbg : StateBasedGame, delta : Int){
    val input = gc.getInput
    val cursorX = board.getCursor(0)
    val cursorY = board.getCursor(1)
    
    if (!selectingWep){
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
        val boardloc = board.getBoardLocation(cursorX,cursorY)
        if (boardloc.hasSprite){
          if (boardloc.getSprite.isInstanceOf[CharacterUnit]){
            selectingWep = true
            boardloc.getSprite.asInstanceOf[CharacterUnit].setSelected(true)
          }
        }
      }
    }
  }
  
  def renderBoard(g: Graphics){
    val backgroundimg : Image = new Image("res/Background.png")
    backgroundimg.draw(0,0)
    for (i <- 0 until board.getBoardWidth){
      for (j <- 0 until board.getBoardHeight){
        val bl : BoardLocation = board.getBoardLocation(i,j)
        //might need to change this later, not sure
        if (bl.hasSprite){
          val blspr = bl.getSprite
          if (blspr.isInstanceOf[Block]){
            val img : Image = new Image("res/" + bl.getSprite.getName + ".png")
            img.draw(spriteSize*i,spriteSize*j)
          } 
          else if (blspr.isInstanceOf[CharacterUnit]){
            //obvious placeholder
            g.setColor(Color.green)
            g.drawRect(blspr.asInstanceOf[CharacterUnit].getX,blspr.asInstanceOf[CharacterUnit].getY,40,40)
          }
        }
      }
    }
  }
  
  override def getID : Int = 2
}