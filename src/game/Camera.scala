package game

import org.newdawn.slick.GameContainer

class Camera(gc: GameContainer, board: Board) {
	private var cameraX: Float = 0.0f
	private var cameraY: Float = 0.0f
	
	private val boardWidth: Int = board.getWidth
	private val boardHeight: Int = board.getHeight
	
	private val this.gc = gc
	private val this.board = board
	
	def centerOn(x: Float, y: Float) {
		cameraX = x - gc.getWidth / 2
		cameraY = y - gc.getHeight / 2
      
		if (cameraX < 0) {
			cameraX = 0;
    	}
		if (cameraX + gc.getWidth() > board.getWidth) {
			cameraX = board.getWidth - gc.getWidth();
		}
		
		if (cameraY < 0) {
            cameraY = 0;
        }
        if (cameraY + gc.getHeight() > board.getHeight) {
            cameraY = board.getHeight - gc.getHeight();
        }
	}
        
    def centerOn(x: Float, y: Float, height: Float, width: Float) {
        this.centerOn(x + width / 2, y + height / 2)
    }
    
    
    
	
}