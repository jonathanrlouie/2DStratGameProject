package ui

import menus.GameMenu
import menus.Play
import menus.PrepMenu
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

class Game extends StateBasedGame("Strategy Game"){
  val menu = 0
  val prep = 1
  val play = 2
  addState(new GameMenu(menu))
  addState(new PrepMenu(prep))
  addState(new Play(play))
  def initStatesList(gc : GameContainer){
     getState(menu).init(gc,this)
     getState(prep).init(gc,this)
     getState(play).init(gc,this)
     enterState(menu)
  }

}