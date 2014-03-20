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

    static hasMany = [manas: Mana,
        types: CardType, prices: ExpansionCardPrice]

    static mapping = {
        flavorText sqlType: "text"
        manas cascade: "all-delete-orphan"
        types cascade: "all-delete-orphan"
    }

    static constraints = {
        card unique: ["card","expansion","collectorNumber"]
        flavorText nullable: true
        collectorNumber nullable: true
    }

    String toString() {
        "${card.name}: ${expansion.name}"
    }
}
