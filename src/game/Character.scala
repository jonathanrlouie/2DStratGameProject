package game

abstract class Character(b : Board) extends Sprite{
    var team : String = ""
    var xcoord : Int = 0
    var ycoord : Int = 0
    var hp_max : Int = 0
    var hp : Int = 0
	var movespeed : Int = 0
	var jumpheight : Int = 0
	var board : Board = b
	var weapons = new Array[Weapon](5)
	var selectedWeapon : Int = 0
	var faceR : Boolean = false
	
	/** moves the unit to the specified tile space if valid
	 * 
	 */
    def move{
	  
	}
    
    def setHp(maxhp : Int){
      hp_max = maxhp
      hp = maxhp
    }
    
    def setTeam(s : String){
      team = s
    }
    
    /** unit attacks depending on the weapon given
     * 
     */
    def attack(w : Weapon){
      
    }
    
    /** sets the unit's direction; true is right while left is false
     *  The unit's direction is set to left by default
     * 
     * MODIFIES: this
     * EFFECTS: makes the unit face left or right
     */
    def faceRight(){
      faceR = true
    }
    
    def leftRight(){
      faceR = false
    }
    
    def getTeamCol : String = {
      team
    }
	
    
	
}