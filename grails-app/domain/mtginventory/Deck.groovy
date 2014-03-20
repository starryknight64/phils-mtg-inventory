package mtginventory

class Deck {
	
	String name
	String description
	String createdBy
	Date dateCreated
	Date lastUpdated
	DeckType type
	ExpansionCard commander

	static hasMany = [ cards : InventoryCard, sideboardCards : InventoryCard ]

	static mapping = {
		description sqlType: "text"
        cards cascade: "all-delete-orphan"
        sideboardCards cascade: "all-delete-orphan"
	}
	
	static constraints = {
		name unique: ["createdBy"], nullable: false
		description nullable: true
		commander nullable: true
	}
}
