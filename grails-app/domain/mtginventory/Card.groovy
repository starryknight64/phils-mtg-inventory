package mtginventory

class Card {

    String name
    String text
    String power
    String toughness
    String loyalty

    static hasMany = [expansionCards: ExpansionCard]

    static mapping = {
        text sqlType: "text"
    }

    static constraints = {
        name unique: true
        text nullable: true
        power nullable: true
        toughness nullable: true
        loyalty nullable: true
    }

    String toString() {
        "${name}"
    }
}
