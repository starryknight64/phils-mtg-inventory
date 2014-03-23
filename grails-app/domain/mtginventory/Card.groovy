package mtginventory

class Card {
    static searchable = {
        spellCheck "include"
    }

    String name
    String power
    String text
    String toughness
    String loyalty

    static hasMany = [expansionCards: ExpansionCard, manas: Mana, types: CardType]

    static mapping = {
        text sqlType: "text"
        manas cascade: "all-delete-orphan"
        types cascade: "all-delete-orphan"
    }

    static constraints = {
        name unique: ["power", "toughness", "loyalty"]
        text nullable: true
        power nullable: true
        toughness nullable: true
        loyalty nullable: true
    }

    String toString() {
        "${name}"
    }
}
