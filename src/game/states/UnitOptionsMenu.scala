package game.states

import org.newdawn.slick.Input
import org.lwjgl.input.Keyboard
import game.CharacterUnit

class UnitOptionsMenu extends BoardState{
	private val numOptions = 2
	private var menuItem: Int = 0
	val stateMap: Map[String, BoardState] = 
		Map("weaponMenu" -> new WeaponMenu(),
		    "moveMenu" -> new MoveMenu())
  
		    
	def init(sdb: StateDataBundle): Unit = {
	  
	}
	
	def update(sdb: StateDataBundle, sm: StateManager){
		val input = sdb.getInput()
		val unitSel = sdb.getUnitSel()
		if (input.isKeyPressed(Keyboard.KEY_UP)){
			if (menuItem == 0){
				menuItem = numOptions
			} else {
				menuItem-=1
			}
		} else if (input.isKeyPressed(Keyboard.KEY_DOWN)){
			if (menuItem == numOptions){
				menuItem = 0
			} else {
				menuItem+=1
			}
		}

		
		if (input.isKeyPressed(Keyboard.KEY_Z)){
			input.clearKeyPressedRecord()
			if (menuItem == 0){
				sm.enterState(stateMap("moveMenu"), sdb)
			} else if (menuItem == 1){
				sm.enterState(stateMap("weaponMenu"), sdb)
			} else if (menuItem == 2){
				unitSel.turn()
			}
		} else if (input.isKeyPressed(Keyboard.KEY_X)){
			input.clearKeyPressedRecord()
			sm.exitState()
		}
	}
	
	def render(sdb: StateDataBundle): Unit = {
		val imgholder = sdb.getImgHolder()
	  	val camera = sdb.getCamera()
	  	val cursor = sdb.getCursor()
	  	val spriteSize = sdb.getSpriteSize()
	  	val gc = sdb.getGc()
	  	val g = sdb.getGraphics()
	  	camera.centerOn(cursor.getX()+spriteSize/2, cursor.getY()+spriteSize/2)
		cursor.render(imgholder.getImage("cursor"),camera)
		val moveImg = imgholder.getImage("movePanel")
		val itmImg = imgholder.getImage("itemPanel")
		val turnImg = imgholder.getImage("turnPanel")
		moveImg.draw(gc.getWidth()/2,0)
		itmImg.draw(gc.getWidth()/2,42)
		turnImg.draw(gc.getWidth()/2,84)
		g.drawRect(gc.getWidth()/2,41*menuItem,194,41)
	}
}