package game

trait MeleeWeapon extends Weapon{
	def attack(unit: CharacterUnit, board: Board){
		val adjSprite = if (unit.isFacingRight){
			board.getBoardLocation(unit.getBoardX+1,unit.getBoardY).getSprite
		} else {
			board.getBoardLocation(unit.getBoardX-1,unit.getBoardY).getSprite
		}
		if (adjSprite.isInstanceOf[CharacterUnit]){
		    val otherUnit = adjSprite.asInstanceOf[CharacterUnit]
			otherUnit.setHp(otherUnit.getHp-(attackPwr+unit.getAtkMod))
			if (otherUnit.getHp <= 0){
			  otherUnit.die
			  unit.incScore
			}
		}
		println("whack!")
	}
}