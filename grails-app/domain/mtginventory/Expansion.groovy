package mtginventory

class Expansion {

    String name
    String symbol
    Date preReleaseDate
    Date releaseDate
    Integer totalCards

    static hasMany = [expansionCards: ExpansionCard,
                      expansionCodes: ExpansionCode]

    static constraints = {
        name unique: true
        symbol nullable: true
        preReleaseDate nullable: true
        releaseDate nullable: true
        totalCards nullable: true
    }

    String toString() {
        "${name}"
    }
}
