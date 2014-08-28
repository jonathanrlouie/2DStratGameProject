package game

import org.newdawn.slick.Input
import org.newdawn.slick.Image
import org.newdawn.slick.Graphics
import org.lwjgl.input.Keyboard
import org.newdawn.slick.GameContainer

class Cursor {
	val spriteSize = 40
    private var moving = false
	@scala.reflect.BeanProperty
	var x: Float = 0
	@scala.reflect.BeanProperty
	var y: Float = 0
	@scala.reflect.BeanProperty
	var boardX: Int = 0
	@scala.reflect.BeanProperty
	var boardY: Int = 0
	
	var xspeed = 0.0f
    var yspeed = 0.0f
	
	def update(input: Input, delta: Int): Unit = {
		if (!moving){
	    	if (input.isKeyDown(Input.KEY_UP)) {
				yspeed = -0.1f
				moving = true
			} else if (input.isKeyDown(Input.KEY_DOWN)) {
				yspeed = 0.1f
				moving = true
			} else if (input.isKeyDown(Input.KEY_LEFT)) {
				xspeed = -0.1f
				moving = true
			} else if (input.isKeyDown(Input.KEY_RIGHT)) {
				xspeed = 0.1f
				moving = true
			}
		} else {
			if (x < (boardX+1)*spriteSize && x > (boardX-1)*spriteSize){
				println("tunak")
				x += xspeed * delta
			} else if (y < (boardY+1)*spriteSize && y > (boardY-1)*spriteSize){
				println("hey")
				y += yspeed * delta
			} else {
				println("hello")
				if (xspeed > 0){
					boardX += 1
				} else if (xspeed < 0){
					boardX -= 1
				} else if (yspeed > 0){
					boardY += 1
				} else if (yspeed < 0){
					boardY -= 1
				}
				xspeed = 0.0f
				yspeed = 0.0f
				moving = false
			}
		}
		
	}
	
	def render(img: Image, camera: Camera){
		img.draw(x-camera.getCameraX(),y-camera.getCameraY())
	}
}