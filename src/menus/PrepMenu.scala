package menus

import scala.collection.mutable.HashMap
import game.CharacterUnit
import org.lwjgl.input._
import org.newdawn.slick._
import org.newdawn.slick.state._

class PrepMenu(state : Int) extends BasicGameState{
  var kvs = new HashMap[String, Array[String]]
  var units = new Array[CharacterUnit](6)
  var selected : Int = 0
  var input : Input = _
  var mousex : Int = _
  var mousey : Int = _
  def init(gc:GameContainer, sbg : StateBasedGame){
    
  }
  
  def render(gc:GameContainer, sbg : StateBasedGame, g:Graphics){
    g.setColor(Color.red)
    g.drawRect(500,400,100,60)
    g.drawString("START", 525, 425)
    
    g.setColor(Color.yellow)
    for (i <- 1 to 6){
      g.drawRect(50+33*i,50,32,32)
    }
    
    g.setColor(Color.cyan)
    for (i <- 1 to 6){
      if (selected == i){
        g.fillRect(51+33*i,51,31,31)
      }
    }
    g.setColor(Color.green)
    g.drawString(mousex.toString,200,100)
    g.drawString(mousey.toString,200,150)
    g.drawString(selected.toString, 300,100)
  }
  
  def update(gc:GameContainer, sbg : StateBasedGame, delta : Int){
    input = gc.getInput
    mousex = input.getAbsoluteMouseX
    mousey = input.getAbsoluteMouseY
    for (i <- 1 to 6){
      if (mouseInArea(50+33*i,50,32,32) && input.isMouseButtonDown(0)){
        selected = i
      }
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
  
  def getID : Int = 1

}