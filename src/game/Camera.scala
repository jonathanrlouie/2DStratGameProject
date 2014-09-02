package game

import org.newdawn.slick.GameContainer

class Camera(board: Board, gc: GameContainer) {
	val spriteSize = 40
	
	@scala.reflect.BeanProperty
	var cameraX: Float = 0.0f
	@scala.reflect.BeanProperty
	var cameraY: Float = 0.0f
	
	private val boardWidth: Int = board.getWidth
	private val boardHeight: Int = board.getHeight
	
	private val this.gc: GameContainer = gc
	private val this.board = board
	
	def centerOn(x: Float, y: Float) {
		cameraX = x - gc.getWidth / 2
		cameraY = y - gc.getHeight / 2
      
		if (cameraX < 0) {
			cameraX = 0;
    	}
		if (cameraX + gc.getWidth() > board.getWidth*spriteSize) {
			cameraX = board.getWidth*spriteSize - gc.getWidth();
		}
		
		if (cameraY < 0) {
            cameraY = 0;
        }
		
        if (cameraY + gc.getHeight() > board.getHeight*spriteSize) {
            cameraY = board.getHeight*spriteSize - gc.getHeight();
        }
	}
        
    def centerOn(x: Float, y: Float, height: Float, width: Float) {
        this.centerOn(x + width / 2, y + height / 2)
    }
    
    
	
}