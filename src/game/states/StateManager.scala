package game.states

import scala.collection.mutable.Stack

class StateManager {
  
	@scala.reflect.BeanProperty
	val stateStack = new Stack[BoardState]()
	private var currentState: BoardState = null
	
	def enterState(state: BoardState): Unit = {
		stateStack.push(state)
		currentState = stateStack.top
	}
	
	def leaveState(): Unit = {
		stateStack.pop()
	}
	
	def updateCurrentState(sdb: StateDataBundle): Unit = {
		currentState.update(sdb,this)
	}
}