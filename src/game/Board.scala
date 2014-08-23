package game

import scala.Array
import org.newdawn.slick.Graphics

class Board(c:Array[CharacterUnit],w:Int,h:Int,map:String) {
  // (0,0) is at the top left here
  val DEFAULT_BOARD : String = "GGGG" +
		  					   "GEEG" +
		  					   "GEEG" +
		  					   "GGGG"
		  					   
  @scala.reflect.BeanProperty
  var width : Int = w
  @scala.reflect.BeanProperty
  var height : Int = h
  var boardmap : String = map
  
  var board = Array.ofDim[BoardLocation](height,width)
  var characters : Array[CharacterUnit] = c
  
  
  /** generates a new board and places all proper sprites in the proper
   *  BoardLocations on a map
   * 
   * REQUIRES: boardwidth and boardheight correspond to boardmap's
   * dimension
   * MODIFIES: board
   * EFFECTS: creates a board
   */
  def init(): Unit = {
    
    for (i <- 0 until boardmap.length){
      var charOnBoard : Char = boardmap.charAt(i)
      
      var x = i % width
      var y = i / width
      
      charOnBoard match{
        case 'a' => addBoardLocation(charOnBoard, 0 ,x, y)
        case 'b' => addBoardLocation(charOnBoard, 1 ,x, y)
        case 'c' => addBoardLocation(charOnBoard, 2 ,x, y)
        case _ => addBoardLocation(charOnBoard, -1 ,x, y)
      }
    }
  }
  
  /** helper method for createBoard to help create new BoardLocations
   * 
   * MODIFIES: board
   * EFFECTS: add a new board location
   */
  
  def addBoardLocation(tile : Char, i : Int, x : Int , y : Int){
    // i determines if a character should be added or not
    if (i >= 0){
      board(y)(x) = new BoardLocation(tile, characters(i), x, y)
    } else {
      board(y)(x) = new BoardLocation(tile, null, x, y)
    }
  }
  
  /** returns all members of a certain team given the team's number
   * 
   * EFFECTS: produces list of team members of a given number
   */
  def getTeam(num : Int) : Array[CharacterUnit] = {
    var team = characters.filter((c : CharacterUnit) => c.getTeamNum.equals(num))
    team
  }
  
  /** returns a BoardLocation given x and y values
   * 
   * REQUIRES: x and y are within the board's width and height
   * respectively; x and y are not negative
   * EFFECTS: gets a BoardLocation
   */
  def getBoardLocation(x:Int, y:Int) : BoardLocation = {
    board(y)(x)
  }
  
  def getBoard : Array[Array[BoardLocation]] = board
  
  def setSize(w : Int, h : Int){
    width = w
    height = h
  }
  
  def setBoard(bd:Array[Array[BoardLocation]]){
    board = bd;
  }
  
  def setCharacters(chrs:Array[CharacterUnit]){
    characters = chrs
  }
  
  def render(g: Graphics){
    
  }

}