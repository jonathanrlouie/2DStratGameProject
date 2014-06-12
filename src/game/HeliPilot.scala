package game

class HeliPilot(weps: Array[Weapon], teamObj: Team) extends AirUnit(weps, teamObj){
  override val name = "HeliPilot"
  override val hp_max = 100
  movement = 3
}