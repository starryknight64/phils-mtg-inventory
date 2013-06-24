package mtginventory

class CardTypeType {

    String name

    static constraints = {
        name unique: true
    }

    String toString() {
        "${name}"
    }
}
