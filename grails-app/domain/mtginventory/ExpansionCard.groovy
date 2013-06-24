package mtginventory

class ExpansionCard {
    Card card
    Expansion expansion
    String flavorText
    CardRarity rarity
    Illustrator illustrator
    String collectorNumber
    String priceLow
    String priceMid
    String priceHigh
    String variation

    static belongsTo = Expansion

    static hasMany = [manas: Mana,
        types: CardType]

    static mapping = {
        flavorText sqlType: "text"
    }

    static constraints = {
        card unique: ['card','expansion']
        flavorText nullable: true
        priceLow nullable: true
        priceMid nullable: true
        priceHigh nullable: true
        variation nullable: true
    }

    String toString() {
        "${card.name}: ${expansion.name}"
    }
}
