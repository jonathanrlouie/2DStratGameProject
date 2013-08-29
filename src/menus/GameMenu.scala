package menus

import org.lwjgl.input.Mouse
import org.newdawn.slick._
import org.newdawn.slick.state._

class GameMenu(state : Int) extends BasicGameState{
  
  var mousex : Int = _
  var mousey : Int = _
  def init(gc:GameContainer, sbg : StateBasedGame){
    
  }
  
  def render(gc:GameContainer, sbg : StateBasedGame, g:Graphics){
    g.setColor(Color.green)
    g.drawRect(240, 200, 130, 80)
  }
  
  def update(gc:GameContainer, sbg : StateBasedGame, delta : Int){
    var input : Input = gc.getInput
    mousex = input.getAbsoluteMouseX
    mousey = input.getAbsoluteMouseY
    if (mouseInArea(240, 200, 130, 80) && input.isMouseButtonDown(0)){
      sbg.enterState(1)
    }
  }
  
  /** checks if the mouse is in an area given the coordinates of the top
   *  left corner of the area and its width and height
   *  
   * EFFECTS: returns true if mouse is in a specified area
   */
  def mouseInArea(x:Int, y:Int, width:Int, height:Int):Boolean ={
    (mousex>=x && mousex <= x+width) && (mousey >= y && mousey <= y+height)
  }
  
  def getID : Int = 0

}