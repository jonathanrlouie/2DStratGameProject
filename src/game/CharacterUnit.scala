package game

import org.newdawn.slick.Graphics

abstract class CharacterUnit(weps: Array[Weapon], teamObj: Team) extends Sprite{
    val team = teamObj
    @scala.reflect.BeanProperty
    var board: Board = _
    @scala.reflect.BeanProperty
	var teamNum : Int = 0
    // board x and y are the x and y on the board
    var boardx : Int = 0
    var boardy : Int = 0
    // xcoord and ycoord are the absolute x and y in pixels
    var xcoord : Int = 0
    var ycoord : Int = 0
    val hp_max : Int
    @scala.reflect.BeanProperty
    var atkMod : Int = 0
    @scala.reflect.BeanProperty
    var hp : Int = 0
    @scala.reflect.BeanProperty
    var dead: Boolean = false
	var movespeed : Int = 0
	// the actual number of tiles a unit can move
	var movement : Int = 0
	var jumpheight : Int = 0
	var weapons = weps
	var selectedWeapon : Int = 0
	var faceR : Boolean = false
	
	/** moves the unit to the specified tile space if valid
	 * 
	 */
    def move{
	  
	}
    
    def incScore{
      team.setScore(team.getScore+1)
    }
    
    // retrieve the movement of a unit
    def getMove : Int = {
      movement;
    }
    
    // retrieve the jump height of a unit
    def getJump : Int = {
      jumpheight;
    }
    
    def die{
      
    }
    
    /** unit uses item given index
     * 
     */
    def useItem(index: Int){
      weapons(index).attack(this,board)
    }
    
    /** sets the unit's direction; true is right while left is false.
     *  The unit's direction is set to left by default
     * 
     * MODIFIES: this
     * EFFECTS: makes the unit face left or right
     */
    def faceRight(b:Boolean){
      faceR = b
    }
    
    def isFacingRight: Boolean = faceR
	
    // get actual coords in pixels
    def getX : Int = xcoord
    def getY : Int = ycoord
    
    // get board coordinates
    def getBoardX: Int = boardx
    def getBoardY: Int = boardy    
    
    def getSelectedWep: Int = selectedWeapon
    def getWeapons: Array[Weapon] = weapons
    
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
    
    def getTeam: Team = team
    
    def setSelectedWep(sel : Int){
      selectedWeapon = sel
    }
    
    def render(g: Graphics){
      
    }
}