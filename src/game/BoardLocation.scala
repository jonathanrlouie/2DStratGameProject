package game

class BoardLocation(tile : Char, c : CharacterUnit, x: Int, y: Int) {
  var isWall : Boolean = false
  val boardx = x
  val boardy = y
  val spriteSize = 40
  
  var sprite : Sprite = _
  
  tile match {
    case 'W' => isWall = true
    case 'G' => sprite = new Grass
    case 'S' => sprite = new Sand
    // TODO: reduce cases to 1 case only; try converting to ASCII first
    case 'a' => sprite = c
    case _ => 
  }
  
  if (sprite.isInstanceOf[CharacterUnit]){
    sprite.asInstanceOf[CharacterUnit].setXandY(spriteSize*boardx,spriteSize*boardy)
  }
  /** may need to be changed later to accomodate tiles that can be passsed
   * 
   */
  def isPassable : Boolean = {
    hasSprite
  }
  
  def hasSprite : Boolean = {
    sprite != null
  }
  
  def getSprite : Sprite = sprite
  
  def addSprite(spr : Sprite) {
    sprite = spr
  }

}