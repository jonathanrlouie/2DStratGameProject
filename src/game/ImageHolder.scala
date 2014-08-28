package game

import org.newdawn.slick.Image

class ImageHolder {
	val directory: String = "res/"
	val imgmap: Map[String, Image] = 
		Map("axe" -> new Image(directory + "Axe.png"),
		    "background" -> new Image(directory + "Background.png"),
		    "cursor" -> new Image(directory + "Cursor.png"))
	
	def getImage(str: String): Image = {
	  	imgmap(str)
	}	    
	
}