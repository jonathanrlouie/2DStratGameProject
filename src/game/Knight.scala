package game

class Knight(weps: Array[Weapon], teamObj: Team) extends Fighter(weps, teamObj){
  override val name = "Knight"
  override val hp_max = 100
  movement = 3
  jumpheight = 3

}