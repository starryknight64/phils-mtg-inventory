package mtginventory

class Inventory {

    String name

    static hasMany = [cards: InventoryCard]

    static constraints = {
        name unique: true
    }

    String toString() {
        "${name}"
    }
}
