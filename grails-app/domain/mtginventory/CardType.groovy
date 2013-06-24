package mtginventory

class CardType {

    String name
    CardTypeType type

    static constraints = {
        name unique: ['type']
    }

    String toString() {
        "${name} - ${type.name}"
    }
}
