package game

abstract class Weapon {
  var attackPwr : Int = 0
  var durability : Int = 0
  val name: String
  
  def getName: String = name
}