package game
import org.newdawn.slick.Graphics


abstract class Sprite {
  val name : String
  def getName : String = name
  
  def render(g: Graphics)
}