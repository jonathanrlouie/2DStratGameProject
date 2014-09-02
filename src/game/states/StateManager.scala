package game.states

import scala.collection.mutable.Stack

class StateManager {
  
	@scala.reflect.BeanProperty
	val stateStack = new Stack[BoardState]()
	private var currentState: BoardState = null
	
	def enterState(state: BoardState, sdb: StateDataBundle): Unit = {
		stateStack.push(state)
		state.init(sdb)
		currentState = stateStack.top
	}
	
	def exitState(): Unit = {
		stateStack.pop()
		currentState = stateStack.top
	}
	
	def updateCurrentState(sdb: StateDataBundle): Unit = {
		currentState.update(sdb,this)
	}
	
	def renderCurrentState(sdb: StateDataBundle): Unit = {
		currentState.render(sdb)
	}
}