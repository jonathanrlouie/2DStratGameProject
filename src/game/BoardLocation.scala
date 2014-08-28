package game

class BoardLocation(tile : Char, c : CharacterUnit, x: Int, y: Int) {
  var isWall : Boolean = false
  val boardx = x
  val boardy = y
  val spriteSize = 40
  
  @scala.reflect.BeanProperty
  var sprite : Sprite = null
  
  tile match {
    case 'W' => isWall = true
    case 'G' => sprite = new Grass
    case 'S' => sprite = new Sand
    // TODO: reduce cases to 1 case only; try converting to ASCII first
    case 'a' => sprite = c
    case _ => 
  }
  
  if (sprite.isInstanceOf[Block]){
    isWall = true
  }
  
  if (sprite.isInstanceOf[CharacterUnit]){
    sprite.asInstanceOf[CharacterUnit].setXandY(spriteSize*boardx,spriteSize*boardy)
    sprite.asInstanceOf[CharacterUnit].setBoardXandY(boardx,boardy)
  }
  
  // needs to be fixed to properly work 
  // TODO
  def isPassable : Boolean = {
    //hasSprite
    !isWall
  }
  
  def hasSprite : Boolean = {
    sprite != null
  }
  
  def removeSprite{
    sprite = null
  }
  
  // get the board coordinates of this board location
  def getBoardX: Int = boardx
  def getBoardY: Int = boardy

}