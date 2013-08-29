package game

class BoardLocation(tile : Char, c : CharacterUnit) {
  var isWall : Boolean = false
  
  var sprite : Sprite = _
  
  tile match {
    case 'W' => isWall = true
    case 'G' => sprite = new Grass
    // TODO: reduce cases to 1 case only; try converting to ASCII first
    case 'a' => sprite = c
    case _ => 
  }
  
  /** may need to be changed later to accomodate tiles that can be passsed
   * 
   */
  def isPassable : Boolean = {
    hasSprite
  }
  
  def hasSprite : Boolean = {
    sprite == null
  }
  
  def addSprite(spr : Sprite) {
    sprite = spr
  }

}