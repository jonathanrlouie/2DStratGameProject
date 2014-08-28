package game.states

import scala.collection.mutable.LinkedList
import game._
import org.newdawn.slick.Input

class StateDataBundle {
	@scala.reflect.BeanProperty
	var board: Board = null
	@scala.reflect.BeanProperty
	var input: Input = null
	@scala.reflect.BeanProperty
	var cursor: Cursor = null
	@scala.reflect.BeanProperty
	var units: Array[CharacterUnit] = null
	@scala.reflect.BeanProperty
	var unitSel: CharacterUnit = null
	@scala.reflect.BeanProperty
	var delta: Int = 0
	
	
}