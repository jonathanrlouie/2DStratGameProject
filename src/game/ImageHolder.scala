package game

import org.newdawn.slick.Image

class ImageHolder {
	val directory: String = "res/"
	@scala.reflect.BeanProperty
	val imgmap: Map[String, Image] = 
		Map("axe" -> new Image(directory + "Axe.png"),
		    "background" -> new Image(directory + "Background.png"),
		    "cursor" -> new Image(directory + "Cursor.png"))
	
}