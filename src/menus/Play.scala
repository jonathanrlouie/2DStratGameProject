package menus

import java.io._
import org.lwjgl.input.Keyboard
import org.newdawn.slick._
import org.newdawn.slick.state._
import game._
import scala.Array

class Play(state : Int) extends BasicGameState{
  val spriteSize = 40
  // number of kills a team needs to win the game
  val scoreLimit = 20
  val teams = Array[Team](new Team, new Team)
  
  //placeholder
  val units = Array[CharacterUnit](new Knight(Array[Weapon](new Knife),teams(0)))
  
  var board : Board = new Board(units,12,9,"GEEGEEEEEEEG"+
  								   		   "GEGEEEESEEEG"+
  										   "SEEESEEEESES"+
  										   "SESEEEEEEEES"+
  										   "SEEEEEEEEEES"+
  										   "SEEEEEEEEEES"+
  						 			       "SEEEEESEEEES"+
  										   "SEEEEaSEEEES"+
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
	    if (((parent(bl.getBoardY))(bl.getBoardX))(0) != -100) {         
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
	  for (i <- 0 until 5){
	    //if (weps(i) != null){
	      new Image("res/" + /*weps(i).getName*/ "Grass" + ".png").draw(spriteSize*i+221,1)
	    //}	  
	  }
    }
	
    drawCursor(g);
  }
  
  def drawCursor(g : Graphics): Unit = {
    g.setColor(Color.yellow)
    g.drawRect(relativeCamera(0)*spriteSize,relativeCamera(1)*spriteSize,spriteSize-1,spriteSize-1)
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
          if (unitSel.getSelectedWep < 4){
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
	        if (unitSel.isInstanceOf[AirUnit]){
              // TODO
	        } else {
	          // generate the valid movement locations for a unit given its move, jump, and initial board location
	          (parent(unitSel.getBoardY))(unitSel.getBoardX) = Array(unitSel.getBoardX,unitSel.getBoardY,move+1);
              groundMoveTiles(move,jump,board.getBoardLocation(unitSel.getBoardX,unitSel.getBoardY))
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
          if (((parent(cursorY))(cursorX))(0) != -100){
            board.getBoardLocation(unitSel.getBoardX,unitSel.getBoardY).removeSprite
	        board.getBoardLocation(cursorX,cursorY).addSprite(unitSel)
          }
	    }
        else if (weaponMenuOpen){
          unitSel.useItem(unitSel.getSelectedWep)
          if (unitSel.getTeam.getScore == scoreLimit){
            gameWin(unitSel.getTeamNum)
          }
        }
      }
    }
  }
  
  /** finds the valid locations on the board that selected
   *  unit can move to immediately
   *  and recurses until the unit is out of movement points
   */
  def groundMoveTiles(move : Int, jump : Int, vsel : BoardLocation) {
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
      var blockFoundL = false
      var blockFoundR = false
      // check each block to the sides of the unit and
      // below the unit on those same sides
      for (i <- 0 to jumplimD){
        if (!blockFoundL){
          val nextlocL = board.getBoardLocation(boardx-1,boardy+i)
          // if we encounter a block
          if (!nextlocL.isPassable){
            blockFoundL = true
            /** Make sure we don't say block above ground level is valid;
             * we don't know that unless we check the jumping up condition.
             * Also, check if we have found a shorter path to this vertex/
             * haven't explored this vertex already if it IS valid
             */
            if (i != 0 &&
               ((parent(nextlocL.getBoardY-1))(nextlocL.getBoardX))(2) < move){
		       (parent(boardy+i-1))(boardx-1) = Array(boardx,boardy,move)
               groundMoveTiles(move-1,jump,board.getBoardLocation(boardx-1,boardy+i-1));
            }
          }
        }
        // same thing on the right hand side; might make this into a method later
        if (!blockFoundR){
          val nextlocR = board.getBoardLocation(boardx+1,boardy+i)
          if (!nextlocR.isPassable){
            blockFoundR = true
            if (i != 0 &&
               ((parent(nextlocR.getBoardY-1))(nextlocR.getBoardX))(2) < move){
		       (parent(boardy+i-1))(boardx+1) = Array(boardx,boardy,move)
               groundMoveTiles(move-1,jump,board.getBoardLocation(boardx+1,boardy+i-1));
            }
          }
        }
      }
      
	  // jumping upwards code
      
      // boundary condition for the top border
	  val jumplimU = if (boardy-jump < 0){
	    boardy
	  } else {
	    jump
	  }
	  for(j <- 0 until jumplimU){
	    // start with left side
	    // here, nextlocL is the actual square that we want to move to
	    val nextlocL = board.getBoardLocation(boardx-1,boardy-j-1)
	    // check each space above space to left of selected space
	    // if both empty and there's a block under
	    if (nextlocL.isPassable && !board.getBoardLocation(boardx-1,boardy-j).isPassable){
	      var jumpable:Boolean = true;
	      // check that the column above unit is empty up to jumpable position
	      for (k <- 1 to nextlocL.getBoardY){
	        if(!board.getBoardLocation(boardx,boardy-k).isPassable){
	          jumpable = false;
	        }
	      }
	      if (jumpable && ((parent(nextlocL.getBoardY))(nextlocL.getBoardX))(2) < move){
	        (parent(boardy-j-1))(boardx-1) = Array(boardx,boardy,move)
	        groundMoveTiles(move-1,jump,nextlocL);
	      }
	    }
	    // right side
	    // here, nextlocR is the actual square that we want to move to
	    val nextlocR = board.getBoardLocation(boardx+1,boardy-j-1)
	    if (nextlocR.isPassable && !board.getBoardLocation(boardx+1,boardy-j).isPassable){
	      var jumpable:Boolean = true;
	      for (k <- 1 to nextlocR.getBoardY){
	        if(!board.getBoardLocation(boardx,boardy-k).isPassable){
	          jumpable = false;
	        }
	      }
	      if (jumpable && ((parent(nextlocR.getBoardY))(nextlocR.getBoardX))(2) < move){
	        (parent(boardy-j-1))(boardx+1) = Array(boardx,boardy,move)
	        groundMoveTiles(move-1,jump,nextlocR);
	      }
	    }
	  }
    }
  }
  
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