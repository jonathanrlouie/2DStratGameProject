package game

abstract class Block extends Sprite{
  val durability : Int
  val movPenalty : Int
  
  def getDurability : Int = durability
  def getMovePenalty : Int = movPenalty
}