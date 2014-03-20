package mtginventory

class PriceSource {
	
	String name
	String website
	String rest

    static constraints = {
		name unique: true
		website url: true
		rest url: true
    }
}
