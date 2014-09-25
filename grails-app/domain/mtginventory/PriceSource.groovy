package mtginventory

class PriceSource {
	
	String name
	String website
	
	static mapping = {
        sort "name"
    }

    static constraints = {
		name unique: true
		website url: true
    }
}
