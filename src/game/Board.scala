package game

class Board(c:Array[Character],w:Int,h:Int) {
  val DEFAULT_BOARD : String = "GGGG" +
		  					   "GEEG" +
		  					   "GEEG" +
		  					   "GGGG"
  var boardwidth : Int = w
  var boardheight : Int = h
  var boardmap : String = DEFAULT_BOARD
  var board : Array[Array[BoardLocation]] = _
  var characters : Array[Character] = c
  
  def createBoard{
    
    
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
      (board(y))(x) = new BoardLocation(tile, characters(i))
    } else {
      (board(y))(x) = new BoardLocation(tile, null)
    }
  }
  
  def setSize(w : Int, h : Int){
    boardwidth = w
    boardheight = h
  }
  
  /** returns all members of a certain team given the team's colour
   * 
   * EFFECTS: produces list of team members of a given colour
   */
  def getTeam(col : String) : Array[Character] = {
    var team = characters.filter((c : Character) => c.getTeamCol.equals(col))
    team
  }
  
  def setBoard(bd:Array[Array[BoardLocation]]){
    board = bd;
  }
  
  def getBoard : Array[Array[BoardLocation]] = {
    board
  }
    
  
  def getBoardLocation(x:Int, y:Int) : BoardLocation = {
    (board(y))(x)
  }
  
  def getBoardWidth : Int = {
    boardwidth
  }
  
  def getBoardHeight : Int = {
    boardheight
  }
  
  def setCharacters(chrs:Array[Character]){
    characters = chrs
  }

}