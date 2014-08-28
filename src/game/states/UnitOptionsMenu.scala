package game.states

import org.newdawn.slick.Input

class UnitOptionsMenu extends BoardState{
	val stateMap: Map[String, BoardState] = 
		Map("weaponMenu" -> new WeaponMenu(),
		    "moveMenu" -> new MoveMenu())
  
	def update(sdb: StateDataBundle, sm: StateManager){
	  	
	}
}