package mtginventory

class Legality {
	
	String name

    static constraints = {
		name unique: true
    }
	
    String toString() {
        "${name}"
    }
}
