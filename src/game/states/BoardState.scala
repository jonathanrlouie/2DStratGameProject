package game.states

import org.newdawn.slick.Input

trait BoardState {
	def init(sdb: StateDataBundle): Unit
	def update(sdb: StateDataBundle, sm: StateManager): Unit
	def render(sdb: StateDataBundle): Unit
}