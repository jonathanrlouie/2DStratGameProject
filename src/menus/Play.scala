package menus

import java.io._
import org.lwjgl.input.Keyboard
import org.newdawn.slick._
import org.newdawn.slick.state._
import game._
import scala.Array
import scala.collection.mutable.Stack

class Play(state : Int) extends BasicGameState{
  // number of weapons a unit can hold
  val wepNum = 3
  val spriteSize = 40
  // number of kills a team needs to win the game
  val scoreLimit = 20
  val teams = Array[Team](new Team, new Team)
  
  //placeholder
  val units = Array[CharacterUnit](new HeliPilot(Array[Weapon](new Knife,null,null),teams(0)))
  
  var board : Board = new Board(units,12,9,"GEEGEEEEEEEG"+
  								   		   "GEGEEEEEEEEG"+
  										   "SEEEEEEEESES"+
  										   "SESEEEEEEEES"+
  										   "SEEEEEaEEEES"+
  										   "SEEEEEEEEEES"+
  						 			       "SESSEEEEESES"+
  										   "SESESEESSEES"+
  										   "SSSSSSSSSSSS")
  // the team who's turn it currently is
  var currentTeam = 0
  
  var camera = new Array[Int](2)
  var relativeCamera = new Array[Int](2)
  
  // restore camera position
  var restoreCamera = new Array[Int](2)
  var restoreRelCamera = new Array[Int](2)
  
  val cameraWidth = 8
  val cameraHeight = 8
  
  // whether unit's options menu is open (move, use item, turn around)
  var unitOptionsOpen = false
  var unitOption = 0
  // whether unit's weapon menu is open
  var weaponMenuOpen = false
  // whether movement tiles for a unit exist on the board or not
  var moveSelectOn = false
  
  var unitSel: CharacterUnit = units(0)
  // 3d array
  // 1st element: x position of parent node
  // 2nd element: y position of parent node
  // 3rd element: number of movement points remaining when reaching this tile
  var parent = Array.fill[Int](board.getBoardHeight,board.getBoardWidth,3){-100}
  
  
  def load(path : String) : Object = {
	var ois : ObjectInputStream = new ObjectInputStream(new FileInputStream(path))
	val result : Object = ois.readObject
	ois.close
	result
  }
  
  override def init(gc:GameContainer, sbg : StateBasedGame){
    board.createBoard
    // temporary; just need to initialize the board variable for all units
    units(0).setBoard(board)
    // start camera at top left of board
    camera(0) = 0
    camera(1) = 0
    relativeCamera(0) = 0
    relativeCamera(1) = 0
  }
  
  override def render(gc:GameContainer, sbg : StateBasedGame, g:Graphics){
    renderBoard(g)
      
    if (unitOptionsOpen){
      g.setColor(Color.black)
      g.drawRect(220,0,196,3*42+1)
      new Image("res/MovePanel.png").draw(221,1)
      new Image("res/ItemPanel.png").draw(221,43)
      new Image("res/TurnPanel.png").draw(221,85)
      g.setColor(Color.yellow)
      g.drawRect(221,unitOption*42,194,42)
    }
    
	// temporary way to render tiles unit can move to
	if (moveSelectOn){
	  g.setColor(Color.red)
	  for(i <- camera(0) to camera(0)+cameraWidth; j <- camera(1) to camera(1)+cameraHeight){
	    val bl : BoardLocation = board.getBoardLocation(i,j)
	    if (parent(bl.getBoardY)(bl.getBoardX)(0) != -100) {         
	      g.drawRect((i-camera(0))*spriteSize,(j-camera(1))*spriteSize,spriteSize-1,spriteSize-1)
	    }
	  }
	}
	  
	if (weaponMenuOpen){
	  val xcoord = unitSel.getX
	  val ycoord = unitSel.getY
        
	  // TODO; placeholder
	  g.setColor(Color.black)
	  g.drawRect(220,0,40*5,40)
	  val weps = unitSel.getWeapons
	  g.setColor(Color.green)
	  g.drawRect(40*unitSel.getSelectedWep+220,0,40,40)
	  for (i <- 0 until wepNum){
	    if (weps(i) != null){
	      new Image("res/" + weps(i).getName + ".png").draw(spriteSize*i+221,1)
	    }	  
	  }
    }
	
    drawCursor
  }
  
  def drawCursor: Unit = {
    new Image("res/Cursor.png").draw(relativeCamera(0)*spriteSize,relativeCamera(1)*spriteSize)
  }
  
  override def update(gc:GameContainer, sbg : StateBasedGame, delta : Int){
    val input = gc.getInput
    val cursorX = board.getCursor(0)
    val cursorY = board.getCursor(1)
    
    if (input.isKeyPressed(Keyboard.KEY_LEFT)){
      if (!unitOptionsOpen){
        if (weaponMenuOpen){
          if (unitSel.getSelectedWep > 0){
            unitSel.setSelectedWep(unitSel.getSelectedWep-1)
          }
        } else {
          if (cursorX > 0){
            board.setCursor(cursorX-1,cursorY)
            if (relativeCamera(0) > 0){
              relativeCamera(0) -= 1
            } else {
              camera(0) -= 1
            }
          }
        }
      }
    }
    else if (input.isKeyPressed(Keyboard.KEY_RIGHT)){
      if (!unitOptionsOpen){
        if (weaponMenuOpen){
          if (unitSel.getSelectedWep < wepNum-1){
            unitSel.setSelectedWep(unitSel.getSelectedWep+1)
          }
        } else {
          if (cursorX < board.getBoardWidth-1){
            board.setCursor(cursorX+1,cursorY)
            if (relativeCamera(0) < cameraWidth){
              relativeCamera(0) += 1
            } else {
              camera(0) += 1
            }
          }
        }
      }
    }
    else if (input.isKeyPressed(Keyboard.KEY_DOWN)){
      if (!weaponMenuOpen){
        if (unitOptionsOpen){
          if (unitOption < 2){
            unitOption += 1
          }
        } else {
	      if (cursorY < board.getBoardHeight-1){
	        board.setCursor(cursorX,cursorY+1)
	        if (relativeCamera(1) < cameraHeight){
	          relativeCamera(1) += 1
	        } else {
	          camera(1) += 1
	        }
	      }  
        }
      }
    }
    else if (input.isKeyPressed(Keyboard.KEY_UP)){
      if (!weaponMenuOpen){
        if (unitOptionsOpen){
          if (unitOption > 0){
            unitOption -= 1
          }
        }
        else { 
          if (cursorY > 0){
            board.setCursor(cursorX,cursorY-1)
            if (relativeCamera(1) > 0){
              relativeCamera(1) -= 1
            } else {
              camera(1) -= 1
            }
          }
        }
      }
    }
    // press X to exit a menu
    else if (input.isKeyPressed(Keyboard.KEY_X)){
      if (unitOptionsOpen){
        unitOption = 0
        unitOptionsOpen = false
      } else if (weaponMenuOpen){
        weaponMenuOpen = false
        unitOptionsOpen = true
      } else if (moveSelectOn){
        moveSelectOn = false
        unitOptionsOpen = true
        restorePosition
      }
    }
    // press Z to select unit and perform actions
    else if (input.isKeyPressed(Keyboard.KEY_Z)){
      val boardLoc = board.getBoardLocation(cursorX,cursorY)
      val boardLocSpr = boardLoc.getSprite
      if (!unitOptionsOpen && !weaponMenuOpen && !moveSelectOn){
        if (boardLocSpr.isInstanceOf[CharacterUnit] && boardLocSpr.asInstanceOf[CharacterUnit].getTeamNum == currentTeam){
          unitOptionsOpen = true
          unitSel = boardLocSpr.asInstanceOf[CharacterUnit]
          restoreCamera = camera.clone
          restoreRelCamera = relativeCamera.clone
        }
      } else {
        if (unitOptionsOpen){
          unitOptionsOpen = false
          if (unitOption == 0){
            val boardWidth = board.getBoardWidth
	        val boardHeight = board.getBoardHeight
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
	        moveSelectOn = true
          } else if (unitOption == 1){
            weaponMenuOpen = true
          } else {
            if (unitSel.isFacingRight){
              unitSel.faceRight(false)
            } else {
              unitSel.faceRight(true)
            }
          }
          unitOption = 0
        }
        else if (moveSelectOn){
          // temporary until I fix groundMoveTiles' algorithm
          // TODO
          if (parent(cursorY)(cursorX)(0) != -100){
            val stck = new Stack[Array[Int]]
            stck.push(Array(cursorX,cursorY))
            var pathCoords = Array(parent(cursorY)(cursorX)(0),
                parent(cursorY)(cursorX)(1))
            // push parent coords on to stack one by one
            while (pathCoords(0) != parent(pathCoords(1))(pathCoords(0))(0)
                || pathCoords(1) != parent(pathCoords(1))(pathCoords(0))(1)){
              stck.push(Array(pathCoords(0),pathCoords(1)))
              pathCoords = Array(parent(pathCoords(1))(pathCoords(0))(0), 
                  parent(pathCoords(1))(pathCoords(0))(1))
            }
            // testing stack behaviour
            while (!stck.isEmpty){
              val popped = stck.pop()
              println(popped(0) + ", " + popped(1))
            }
          }
	    }
        else if (weaponMenuOpen){
          if (unitSel.getWeapons(unitSel.getSelectedWep) != null){
            unitSel.useItem(unitSel.getSelectedWep)
            if (unitSel.getTeam.getScore == scoreLimit){
              gameWin(unitSel.getTeamNum)
            }
          }
        }
      }
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
	  val jumplimD = if (boardy+jump+1 >= board.getBoardHeight){
	    board.getBoardHeight-boardy-1;
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
  
  // restore the position of the camera
  def restorePosition{
    camera = restoreCamera.clone
    relativeCamera = restoreRelCamera.clone
    board.setCursor(unitSel.getBoardX,unitSel.getBoardY)
  }
  
  def gameWin(teamNum: Int){
    //TODO
  }
  
  def renderBoard(g: Graphics){
    val backgroundimg : Image = new Image("res/Background.png")
    backgroundimg.draw(0,0)
    for (i <- camera(0) to camera(0)+cameraWidth; j <- camera(1) to camera(1)+cameraHeight){
      val bl : BoardLocation = board.getBoardLocation(i,j)
      //might need to change this later, not sure
      if (bl.hasSprite){
        val blspr = bl.getSprite
        if (blspr.isInstanceOf[Block]){
          val img : Image = new Image("res/" + blspr.getName + ".png")
          img.draw(spriteSize*(i-camera(0)),spriteSize*(j-camera(1)))
        } 
        else if (blspr.isInstanceOf[CharacterUnit]){
          //obvious placeholder
          g.setColor(Color.green)
          g.drawRect(spriteSize*(i-camera(0)),spriteSize*(j-camera(1)),39,39)
        }
      }
    }
  }
  
  override def getID : Int = 2
}