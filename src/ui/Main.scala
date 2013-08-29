package ui

import org.newdawn.slick.AppGameContainer;

object Main {
  def main(args: Array[String]): Unit = {
     var app : AppGameContainer = new AppGameContainer(new Game);
         app.start;
         app.setDisplayMode(640,480,false)
  }
}