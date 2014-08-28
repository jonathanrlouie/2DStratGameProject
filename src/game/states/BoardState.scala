package game.states

import org.newdawn.slick.Input

trait BoardState {  
	def update(db: StateDataBundle, sm: StateManager): Unit
}