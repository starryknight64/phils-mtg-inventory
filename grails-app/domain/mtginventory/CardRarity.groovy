package mtginventory

class CardRarity {

    String name
    String acronym

    static constraints = {
        name unique: true
        acronym unique: true
    }

    String toString() {
        "${name}"
    }
}
