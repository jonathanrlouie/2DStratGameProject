package game.states

import org.lwjgl.input.Keyboard
import org.newdawn.slick.Input
import game._

class CursorState extends BoardState{
	val stateMap: Map[String, BoardState] = 
		Map("unitOptionsMenu" -> new UnitOptionsMenu())
			
	def update(sdb: StateDataBundle, sm: StateManager){
		val cursor = sdb.getCursor()
		val input = sdb.getInput()
		val board = sdb.getBoard()
		val delta = sdb.getDelta()
		cursor.update(input, delta)
		
		val cursorboardx = cursor.getBoardX()
		val cursorboardy = cursor.getBoardY()
		val sprite = board.getBoardSprite(cursorboardx, cursorboardy)
		if (input.isKeyPressed(Keyboard.KEY_Z) && sprite.isInstanceOf[CharacterUnit]){
			sdb.setUnitSel(sprite.asInstanceOf[CharacterUnit])
			sm.enterState(stateMap("unitOptionsMenu"))
		}
		
	}
}