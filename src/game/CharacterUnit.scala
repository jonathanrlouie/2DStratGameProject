package game

abstract class CharacterUnit extends Sprite{
    var team : Int = 0
    // board x and y are the x and y on the board
    var boardx : Int = 0
    var boardy : Int = 0
    // xcoord and ycoord are the absolute x and y in pixels
    var xcoord : Int = 0
    var ycoord : Int = 0
    var hp_max : Int = 0
    var hp : Int = 0
	var movespeed : Int = 0
	// the actual number of tiles a unit can move
	var movement : Int = 0
	var jumpheight : Int = 0
	var board : Board = _
	var weapons = new Array[Weapon](5)
	var selectedWeapon : Int = 0
	var faceR : Boolean = false
	
	/** type of selection:
	 *  0 = unselected
	 *  1 = selected for movement
	 *  2 = selecting weapon
	 */
	var selected : Int = 0
	
	/** moves the unit to the specified tile space if valid
	 * 
	 */
    def move{
	  
	}
    
    // retrieve the movement of a unit
    def getMove : Int = {
      movement;
    }
    
    // retrieve the jump height of a unit
    def getJump : Int = {
      jumpheight;
    }
    
    /** unit uses item given index
     * 
     */
    def useItem(index: Int){
      weapons(index)
    }
    
    /** sets the unit's direction; true is right while left is false.
     *  The unit's direction is set to left by default
     * 
     * MODIFIES: this
     * EFFECTS: makes the unit face left or right
     */
    def faceDir(b:Boolean){
      faceR = b
    }
    
    def getTeamNum : Int = {
      team
    }
	
    // get actual coords in pixels
    def getX : Int = xcoord
    def getY : Int = ycoord
    
    // get board coordinates
    def getBoardX: Int = boardx
    def getBoardY: Int = boardy    
    
    def getSelectedWep: Int = selectedWeapon
    def getSelected: Int = selected
    def getWeapons: Array[Weapon] = weapons
    
    def setHp(maxhp : Int){
      hp_max = maxhp
      hp = maxhp
    }
    
    def setTeam(teamnum : Int){
      team = teamnum
    }
    
    def setBoard(b:Board){
      board = b
    }
    
    // set absolute x and y in pixels
    def setXandY(x: Int, y: Int){
      xcoord = x
      ycoord = y
    }
    
    // set the board x and y
    def setBoardXandY(x: Int, y: Int){
      boardx = x;
      boardy = y;
    }
    
    def setSelected(sel : Int){
      selected = sel
    }
    
    def setSelectedWep(sel : Int){
      selectedWeapon = sel
    }
}