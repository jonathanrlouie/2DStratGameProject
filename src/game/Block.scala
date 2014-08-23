package game

import org.newdawn.slick.Graphics

abstract class Block extends Sprite{
  val durability : Int
  val movPenalty : Int
  
  def getDurability : Int = durability
  def getMovePenalty : Int = movPenalty
  
  def render(g: Graphics) {
    // TODO
  }
}