package game

class Knight(weps: Array[Weapon]) extends Fighter{
  override val name = "Knight"
  weapons = weps
  movement = 3
  jumpheight = 3

}