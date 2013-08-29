package menus

import org.newdawn.slick._
import org.newdawn.slick.state._
import game.Board
import game.CharacterUnit

class Play(state : Int) extends BasicGameState{
  var board : Board = new Board(new Array[CharacterUnit](0),4,4,"GGGG"+
  																"GEEG"+
  																"GEEG"+
  																"GGGG")
  def init(gc:GameContainer, sbg : StateBasedGame){
    
  }
  
  def render(gc:GameContainer, sbg : StateBasedGame, g:Graphics){
    
  }
  
  def update(gc:GameContainer, sbg : StateBasedGame, delta : Int){
    
  }
  
  def getID : Int = 2
}