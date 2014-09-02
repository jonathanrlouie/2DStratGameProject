package game

import org.newdawn.slick.Input
import org.newdawn.slick.Image
import org.newdawn.slick.Graphics
import org.lwjgl.input.Keyboard
import org.newdawn.slick.GameContainer

class Cursor(board: Board) {
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
	
	var speed = 0.2f
	private var facing: Char = ' '
    
	def update(input: Input, delta: Int, board: Board): Unit = {
		if (moving){
			checkStop(delta)
		} else {
			handleInput(input)
		}
	}
	
	def checkStop(delta: Int): Unit = {
		if (facing.equals('U') && y > boardY*spriteSize){
			y -= speed * delta
			if (y < boardY*spriteSize){
				y = boardY*spriteSize
			}
		} else if (facing.equals('D') && y < boardY*spriteSize){
			y += speed * delta
			if (y > boardY*spriteSize){
				y = boardY*spriteSize
			}
		} else if (facing.equals('L') && x > boardX*spriteSize){
			x -= speed * delta
			if (x < boardX*spriteSize){
				x = boardX*spriteSize
			}
		} else if (facing.equals('R') && x < boardX*spriteSize){
			x += speed * delta
			if (x > boardX*spriteSize){
				x = boardX*spriteSize
			}
		} else {
			moving = false
		}
	}
	
	def handleInput(input: Input): Unit = {
		changeFacing(input)
		moving = true
	}
	
	def changeFacing(input: Input): Unit = {
		if (input.isKeyDown(Input.KEY_UP) && boardY-1 >= 0) {
			facing = 'U'
			boardY-=1
		} else if (input.isKeyDown(Input.KEY_DOWN) && boardY+1 < board.getHeight()) {
			facing = 'D'
			boardY+=1
		} else if (input.isKeyDown(Input.KEY_LEFT) && boardX-1 >= 0) {
			facing = 'L'
			boardX-=1
		} else if (input.isKeyDown(Input.KEY_RIGHT) && boardX+1 < board.getWidth()) {
			facing = 'R'
			boardX+=1
		} else {
		    facing = ' '
		}
	}
	
	def render(img: Image, camera: Camera){
		img.draw(x-camera.getCameraX(),y-camera.getCameraY())
	}
}