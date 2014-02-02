package mtginventory

class ExpansionCard {

    Card card
    Expansion expansion
    String text
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
        text sqlType: "text"
        flavorText sqlType: "text"
        manas cascade: "all-delete-orphan"
        types cascade: "all-delete-orphan"
    }

    static constraints = {
        card unique: ['card','expansion','collectorNumber']
        text nullable: true
        flavorText nullable: true
        priceLow nullable: true
        priceMid nullable: true
        priceHigh nullable: true
        variation nullable: true
        collectorNumber nullable: true
    }

    String toString() {
        "${card.name}: ${expansion.name}"
    }
}
