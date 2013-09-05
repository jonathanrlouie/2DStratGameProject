package menus

import java.io._
import scala.collection.mutable.HashMap
import game._
import org.lwjgl.input
import org.newdawn.slick._
import org.newdawn.slick.state._

class PrepMenu(state : Int) extends BasicGameState{
  
  val classes = Array[String]("Fighter","Magician","Ranger","Stealth","Flier","Support","Builder")
  val fighters = Array[String]("Knight", "Paladin")
  
  var store = new HashMap[String, Array[String]]
  var units = new Array[CharacterUnit](6)
  var unitselected : Int = 1
  var clselected : Int = 0
  var input : Input = _
  var mousex : Int = _
  var mousey : Int = _
  
  def load(path : String) : Object = {
	var ois : ObjectInputStream = new ObjectInputStream(new FileInputStream(path))
	val result : Object = ois.readObject
	ois.close
	result
  }
  
  def save(obj : Object,path : String){
	var oos : ObjectOutputStream = new ObjectOutputStream(new FileOutputStream(path));
	oos.writeObject(obj)
	oos.flush
	oos.close
  }
  
  override def init(gc:GameContainer, sbg : StateBasedGame){
    try{
      type Unithashmap = HashMap[String,Array[String]]
	  if (load("unitPrefs.bin").isInstanceOf[Unithashmap]){
	    println("hi")
	    store = load("unitPrefs.bin").asInstanceOf[Unithashmap]
	  }
    } catch {
      case e:FileNotFoundException => println("File was not found")
      case e:Exception => e.printStackTrace
    }
  }
  
  override def render(gc:GameContainer, sbg : StateBasedGame, g:Graphics){
    g.setColor(Color.red)
    g.drawRect(500,400,100,60)
    g.drawString("START", 525, 425)
    
    g.drawRect(500,330,100,60)
    g.drawString("Save\nPreferences", 500, 335)
    
    g.drawRect(500,260,100,60)
    g.drawString("Load\nPreferences", 500, 265)
    
    // TODO: use images from res folder
    g.setColor(Color.yellow)
    for (i <- 1 to 6){
      g.drawRect(50+33*(i-1),50,32,32)
    }
    
    g.setColor(Color.cyan)
    for (i <- 1 to 6){
      if (unitselected == i){
        g.fillRect(51+33*(i-1),51,31,31)
      }
    }
    
    g.setColor(Color.green)
    for (i <- 0 to 6){
      g.drawRect(50+81*i,100,80,32)
      g.drawString(classes(i),53+81*i,107)
    }
    
    clselected match{
      case 1 => for (i <- 0 until fighters.length){
        g.drawString(fighters(i),53,140+33*i)
        g.drawRect(50,133+33*i,80,32)
      }
      case _ =>
    }
    
  }
  
  override def update(gc:GameContainer, sbg : StateBasedGame, delta : Int){
    input = gc.getInput
    mousex = input.getAbsoluteMouseX
    mousey = input.getAbsoluteMouseY
    
    if (mouseInArea(500,400,100,60) && input.isMouseButtonDown(0)){
      sbg.enterState(2)
    }
    
    for (i <- 1 to 6){
      if (mouseInArea(50+33*(i-1),50,32,32) && input.isMouseButtonDown(0)){
        unitselected = i
        clselected = 0
      }
    }
    
    for (i <- 1 to 7){
      if (mouseInArea(50+81*(i-1),100,80,32) && input.isMouseButtonDown(0)){
        clselected = i
      }
    }
    
    clselected match{
      case 1 =>  if (mouseInArea(50,133,80,33) && input.isMouseButtonDown(0)){
        units(unitselected) = new Knight
      }
      case _ =>
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
  
  override def getID : Int = 1

}