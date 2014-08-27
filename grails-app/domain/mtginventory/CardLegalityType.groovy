package mtginventory

class CardLegalityType {
	
	String name

    static constraints = {
		name unique: true
    }
	
    String toString() {
        "${name}"
    }
}
