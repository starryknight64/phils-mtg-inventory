package mtginventory

class ManaColor {

    String name

    static constraints = {
        name unique: true
    }

    String toString() {
        "${name}"
    }
}
