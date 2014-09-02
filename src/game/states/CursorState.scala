package game.states

import org.lwjgl.input.Keyboard
import org.newdawn.slick.Input
import game._

class CursorState extends BoardState{
	val stateMap: Map[String, BoardState] = 
		Map("unitOptionsMenu" -> new UnitOptionsMenu())
			
	def init(sdb: StateDataBundle): Unit = {
	  
	}
		
	def update(sdb: StateDataBundle, sm: StateManager): Unit = {
		val cursor = sdb.getCursor()
		val input = sdb.getInput()
		val board = sdb.getBoard()
		val delta = sdb.getDelta()
		val spriteSize = sdb.getSpriteSize()
		cursor.update(input, delta, board)
		
		val cursorboardx = cursor.getBoardX()
		val cursorboardy = cursor.getBoardY()
		val sprite = board.getBoardSprite(cursorboardx, cursorboardy)
		if (input.isKeyPressed(Keyboard.KEY_Z) && cursorOnSprite(sprite,spriteSize,cursor)){
			sdb.setUnitSel(sprite.asInstanceOf[CharacterUnit])
			input.clearKeyPressedRecord()
			sm.enterState(stateMap("unitOptionsMenu"), sdb)
		}
	}
	
	def cursorOnSprite(sprite: Sprite,spriteSize: Int, cursor: Cursor): Boolean = {
		sprite.isInstanceOf[CharacterUnit] && cursor.getX() % spriteSize == 0 && cursor.getY() % spriteSize == 0
	}
	
	def render(sdb: StateDataBundle): Unit = {
		val imgholder = sdb.getImgHolder()
	  	val camera = sdb.getCamera()
	  	val cursor = sdb.getCursor()
	  	val spriteSize = sdb.getSpriteSize()
		camera.centerOn(cursor.getX()+spriteSize/2, cursor.getY()+spriteSize/2)
		cursor.render(imgholder.getImage("cursor"),camera)
	}
}