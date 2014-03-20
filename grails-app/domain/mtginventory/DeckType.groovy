package mtginventory

class DeckType {
	
	String name

    static constraints = {
		name unique: true
    }
}
