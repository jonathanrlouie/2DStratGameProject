package game.states

import org.newdawn.slick.Input
import org.lwjgl.input.Keyboard
import game.Board
import game.Cursor
import game.AirUnit
import game.CharacterUnit
import game.BoardLocation

class MoveMenu extends BoardState{
	private var board: Board = null
	private var cursor: Cursor = null
	private var unitSel: CharacterUnit = null
  
	// 3d array
	// 1st element: x position of parent node
	// 2nd element: y position of parent node
	// 3rd element: number of movement points remaining when reaching this tile
	var parent: Array[Array[Array[Int]]] = null
	val vertexList: List[Int] = null
	
	def init(sdb: StateDataBundle): Unit = {
		board = sdb.getBoard()
		cursor = sdb.getCursor()
		unitSel = sdb.getUnitSel()
	
		parent = Array.fill[Int](board.getHeight,board.getWidth,3){-100}
		
		
		val boardWidth = board.getWidth
	    val boardHeight = board.getHeight
	    val move = unitSel.getMove;
	    val jump = unitSel.getJump;
	    parent(unitSel.getBoardY)(unitSel.getBoardX) = Array(unitSel.getBoardX,unitSel.getBoardY,move+1);
	    if (unitSel.isInstanceOf[AirUnit]){
           // generate the valid movement locations for an air unit given its movement and initial board location
	       genAirMoveTiles(move,board.getBoardLocation(unitSel.getBoardX,unitSel.getBoardY))
	    } else {
	       // generate the valid movement locations for a ground unit given its move, jump, and initial board location
           genGroundMoveTiles(move,jump,board.getBoardLocation(unitSel.getBoardX,unitSel.getBoardY))
	    }
	}
	
		/** generates the valid locations on the board that selected
   *  air unit can move to immediately
   *  and recurses until the unit is out of movement points
   */
  def genAirMoveTiles(move: Int, vsel: BoardLocation){
    val boardx = vsel.getBoardX
    val boardy = vsel.getBoardY
    
    if (move>0){
      calcValidAirSpaces(1,0,boardx,boardy,move)
      calcValidAirSpaces(0,1,boardx,boardy,move)
      calcValidAirSpaces(-1,0,boardx,boardy,move)
      calcValidAirSpaces(0,-1,boardx,boardy,move)
    }
    
  }
  
  // calculate valid moveable tiles for air units
  // xdir is 1 for tile on right (ydir = 0) 
  // xdir is -1 for tile on the left (ydir = 0)
  // ydir is 1 for tile below (xdir = 0)
  // ydir is -1 for tile above (xdir = 0)
  def calcValidAirSpaces(xdir: Int, ydir: Int, boardx: Int, boardy: Int, move: Int){
    val nextLoc = board.getBoardLocation(boardx+xdir,boardy+ydir)
    if (nextLoc.isPassable){
      if (parent(nextLoc.getBoardY)(nextLoc.getBoardX)(2) < move){
        parent(boardy+ydir)(boardx+xdir) = Array(boardx,boardy,move)
        genAirMoveTiles(move-1, nextLoc)
      }
    }
  }
  
  /** generates the valid locations on the board that selected
   *  ground unit can move to immediately
   *  and recurses until the unit is out of movement points
   */
  def genGroundMoveTiles(move : Int, jump : Int, vsel : BoardLocation) {
    val boardx = vsel.getBoardX
    val boardy = vsel.getBoardY
    
    if (move>0){
      // jumping down/walking code
      
      // boundary condition, reduce drop height if at edge of screen
	  val jumplimD = if (boardy+jump+1 >= board.getHeight){
	    board.getHeight-boardy-1;
	  } else {
	    jump+1
	  }
      calcLowerValidSpaces(-1, move, jump, boardx, boardy, jumplimD, 0)
      calcLowerValidSpaces(1, move, jump, boardx, boardy, jumplimD, 0)
	  
      // jumping upwards code
      
      // boundary condition for the top border
	  val jumplimU = if (boardy-jump < 0){
	    boardy
	  } else {
	    jump
	  }
      calcUpperValidSpaces(move, jump, boardx, boardy, jumplimU, 0)
    }
  }
  
  // calculate valid moveable tiles at unit's height or lower
  // dir is 1 when calculating tiles on the right 
  // dir is -1 for tiles on the left
  def calcLowerValidSpaces(dir: Int, move: Int, jump: Int, boardx: Int, boardy: Int, jumplimD: Int, i: Int) {
    if (i <= jumplimD){
      val nextLoc = board.getBoardLocation(boardx+dir,boardy+i)
      // if we encounter a block
      if (!nextLoc.isPassable){
        /** Make sure we don't say block above ground level is valid;
          * we don't know that unless we check the jumping up condition.
          * Also, check if we have found a shorter path to this vertex/
          * haven't explored this vertex already if it IS valid
          * check i != 0 to see if adjacent block; terminate if so
          */
        if (i != 0 &&
          parent(nextLoc.getBoardY-1)(nextLoc.getBoardX)(2) < move){
          parent(boardy+i-1)(boardx+dir) = Array(boardx,boardy,move)
          genGroundMoveTiles(move-1,jump,board.getBoardLocation(boardx+dir,boardy+i-1));
        }
      } else {
        calcLowerValidSpaces(dir, move, jump, boardx, boardy, jumplimD, i+1)
      }
    }
  }
  
  // calculate valid moveable tiles above unit's height
  def calcUpperValidSpaces(move: Int, jump: Int, boardx: Int, boardy: Int, jumplimU: Int, i: Int){
    if (i < jumplimU){
	  val nextLoc = board.getBoardLocation(boardx,boardy-i-1)
	  if (nextLoc.isPassable){
	    // check left side of nextLoc
	    if (board.getBoardLocation(boardx-1,boardy-i-1).isPassable
	    && !board.getBoardLocation(boardx-1,boardy-i).isPassable
	    && parent(boardy-i-1)(boardx-1)(2) < move){
	      parent(boardy-i-1)(boardx-1) = Array(boardx,boardy,move)
	      genGroundMoveTiles(move-1,jump,board.getBoardLocation(boardx-1,boardy-i-1))
	    }
	    // check right side of nextLoc
        if (board.getBoardLocation(boardx+1,boardy-i-1).isPassable
	    && !board.getBoardLocation(boardx+1,boardy-i).isPassable
        && parent(boardy-i-1)(boardx+1)(2) < move){
	      parent(boardy-i-1)(boardx+1) = Array(boardx,boardy,move)
          genGroundMoveTiles(move-1,jump,board.getBoardLocation(boardx+1,boardy-i-1))
	    }
	    calcUpperValidSpaces(move, jump, boardx, boardy, jumplimU, i+1)
	  }  
	}
  }
  
	def update(sdb: StateDataBundle, sm: StateManager): Unit = {
		val input = sdb.getInput()
		val delta = sdb.getDelta()
		val spriteSize = sdb.getSpriteSize()
		cursor.update(input, delta, board)
		
		if (input.isKeyPressed(Keyboard.KEY_Z)){
			
		} else if (input.isKeyPressed(Keyboard.KEY_X)){
			input.clearKeyPressedRecord()
			restoreCursor(spriteSize)
			sm.exitState()
		}
	}
	
	def restoreCursor(spriteSize: Int): Unit = {
	  	cursor.setBoardX(unitSel.getBoardX)
		cursor.setBoardY(unitSel.getBoardY)
		cursor.setX(cursor.getBoardX*spriteSize)
		cursor.setY(cursor.getBoardY*spriteSize)
	}

	
	def render(sdb: StateDataBundle): Unit = {
		val imgholder = sdb.getImgHolder()
	  	val camera = sdb.getCamera()
	  	val spriteSize = sdb.getSpriteSize()
		camera.centerOn(cursor.getX()+spriteSize/2, cursor.getY()+spriteSize/2)
		cursor.render(imgholder.getImage("cursor"),camera)
		
	}
}