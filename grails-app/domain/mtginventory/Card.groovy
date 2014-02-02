package mtginventory

class Card {
    static searchable = {
        spellCheck "include"
    }

    String name
    String power
    String toughness
    String loyalty

    static hasMany = [expansionCards: ExpansionCard]

    static mapping = {
    }

    static constraints = {
        name unique: ["power", "toughness", "loyalty"]
        power nullable: true
        toughness nullable: true
        loyalty nullable: true
    }

    String toString() {
        "${name}"
    }
}
