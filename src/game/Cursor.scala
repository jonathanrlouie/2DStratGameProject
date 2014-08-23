package game

import org.newdawn.slick.Input
import org.newdawn.slick.Image
import org.newdawn.slick.Graphics
import org.lwjgl.input.Keyboard
import org.newdawn.slick.GameContainer

class Cursor {
	@scala.reflect.BeanProperty
	var x: Float = 0
	@scala.reflect.BeanProperty
	var y: Float = 0
	@scala.reflect.BeanProperty
	var boardx: Int = 0
	@scala.reflect.BeanProperty
	var boardy: Int = 0
	
	def update(gc: GameContainer, delta: Int): Unit = {
		val input = gc.getInput
		if (input.isKeyDown(Input.KEY_UP)) {
			y -= delta * 0.1f;
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			y += delta * 0.1f;
		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			x -= delta * 0.1f;
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			x += delta * 0.1f;
		}
	}
	
	def render(img: Image){
		img.draw(x,y)
	}
}