package ui

import menus.GameMenu
import menus.Play
import menus.PrepMenu
import org.newdawn.slick.state._
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
  
  // allows code to compile, since setup is done for the user already
  override def initStatesList(gc : GameContainer) {
    enterState(menu)
  }

}