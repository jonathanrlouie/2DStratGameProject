package game

class Board(c:Array[CharacterUnit],w:Int,h:Int,map:String) {
  val DEFAULT_BOARD : String = "GGGG" +
		  					   "GEEG" +
		  					   "GEEG" +
		  					   "GGGG"
  var boardwidth : Int = w
  var boardheight : Int = h
  var cursorPos = new Array[Int](2)
  var boardmap : String = map
  var board : Array[Array[BoardLocation]] = _
  var characters : Array[CharacterUnit] = c
  
  
  /** generates a new board and places all proper sprites in the proper
   *  BoardLocations on a map
   * 
   * REQUIRES: boardwidth and boardheight correspond to boardmap's
   * dimension
   * MODIFIES: board
   * EFFECTS: creates a board
   */
  def createBoard{
    
    setCursor(0,0)
    board = new Array[Array[BoardLocation]](boardheight)
    for (i <- 0 until boardheight){
      board(i) = new Array[BoardLocation](boardwidth)
    }
    
    for (i <- 0 until boardmap.length){
      var charOnBoard : Char = boardmap.charAt(i)
      
      var x = i % boardwidth
      var y = i / boardwidth
      
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
    if (i >= 0){
      (board(y))(x) = new BoardLocation(tile, characters(i), x, y)
    } else {
      (board(y))(x) = new BoardLocation(tile, null, x, y)
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
    (board(y))(x)
  }
  
  def getCursor: Array[Int] = cursorPos
  
  def setCursor(x: Int, y:Int) {
    cursorPos(0) = x
    cursorPos(1) = y
  }
  
  def getBoard : Array[Array[BoardLocation]] = board
  def getBoardWidth : Int = boardwidth
  def getBoardHeight : Int = boardheight
  
  def setSize(w : Int, h : Int){
    boardwidth = w
    boardheight = h
  }
  
  def setBoard(bd:Array[Array[BoardLocation]]){
    board = bd;
  }
  
  def setCharacters(chrs:Array[CharacterUnit]){
    characters = chrs
  }

}