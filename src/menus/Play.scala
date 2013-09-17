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
  
  var board : Board = new Board(units,12,7,"GEEEEEEEEEEG"+
  								   		   "GEEEEGEEEEEG"+
  										   "SEEEEEESEEES"+
  										   "SEEEEEEEEEES"+
  						 			       "SEEEEEESEEES"+
  										   "SEaEEEESEEES"+
  										   "SSSSSSSSSSSS")
  
  var unitSel: CharacterUnit = units(0)
  
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
    
    if (unitSel.getSelected == 2){
      val xcoord = unitSel.getX
      val ycoord = unitSel.getY
          
      //placeholder
      g.setColor(Color.black)
      g.drawRect(220,0,40*5,40)
      val weps = unitSel.getWeapons
      g.setColor(Color.green)
      g.drawRect(40*unitSel.getSelectedWep+220,0,40,40)
      for (i <- 0 until 5){
        //if (weps(i) != null){
          new Image("res/" + /*weps(i).getName*/ "Grass" + ".png").draw(spriteSize*i+221,1)
        //}
      }
    }
    
    g.setColor(Color.yellow)
    g.drawRect(board.getCursor(0)*spriteSize,board.getCursor(1)*spriteSize,spriteSize-1,spriteSize-1)
    
  }
  
  override def update(gc:GameContainer, sbg : StateBasedGame, delta : Int){
    val input = gc.getInput
    val cursorX = board.getCursor(0)
    val cursorY = board.getCursor(1)
    
    if (unitSel.getSelected == 0){
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
      else if (input.isKeyPressed(Keyboard.KEY_X)){
        val boardloc = board.getBoardLocation(cursorX,cursorY)
        if (boardloc.hasSprite){
          if (boardloc.getSprite.isInstanceOf[CharacterUnit]){
            unitSel = boardloc.getSprite.asInstanceOf[CharacterUnit]
            boardloc.getSprite.asInstanceOf[CharacterUnit].setSelected(2)
          }
        }
      }
    }
    else if (unitSel.getSelected == 2){
      if (input.isKeyPressed(Keyboard.KEY_RIGHT)){
        if (unitSel.getSelectedWep < 4){
          unitSel.setSelectedWep(unitSel.getSelectedWep+1)
        }
      }
      else if (input.isKeyPressed(Keyboard.KEY_LEFT)){
        if (unitSel.getSelectedWep > 0){
          unitSel.setSelectedWep(unitSel.getSelectedWep-1)
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
            val img : Image = new Image("res/" + blspr.getName + ".png")
            img.draw(spriteSize*i,spriteSize*j)
          } 
          else if (blspr.isInstanceOf[CharacterUnit]){
            //obvious placeholder
            g.setColor(Color.green)
            g.drawRect(blspr.asInstanceOf[CharacterUnit].getX,blspr.asInstanceOf[CharacterUnit].getY,39,39)
          }
        }
      }
    }
  }
  
  override def getID : Int = 2
}