package mtginventory

class ExpansionCard {

    Card card
    Expansion expansion
    String flavorText
    CardRarity rarity
    Illustrator illustrator
    String collectorNumber
	String imageName

    static belongsTo = Expansion

    static hasMany = [prices: ExpansionCardPrice]

    static mapping = {
        flavorText sqlType: "text"
    }

    static constraints = {
        card unique: ["card","expansion","collectorNumber"]
        flavorText nullable: true
        collectorNumber nullable: true
		imageName nullable: true
    }

    String toString() {
        "${card.name}: ${expansion.name}"
    }
}
