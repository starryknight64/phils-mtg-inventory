package mtginventory

class Expansion {
    static searchable = {
        spellCheck "include"
    }

    String name
	String code
    Date releaseDate
    Integer totalCards

    static hasMany = [expansionCards: ExpansionCard]

    static constraints = {
        name unique: true
        code unique: true
        releaseDate nullable: true
        totalCards nullable: true
    }

    String toString() {
        "${name}"
    }
}
