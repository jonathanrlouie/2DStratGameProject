package game

trait Weapon {
  val attackPwr : Int
  var durability : Int = 0
  val name: String
  
  def attack(unit: CharacterUnit,board:Board)
  def getName: String = name
}