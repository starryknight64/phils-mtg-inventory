package mtginventory

class Inventory {

    String name

    static hasMany = [inventoryCards: InventoryCard]

    static constraints = {
        name unique: true
    }

    String toString() {
        "${name}"
    }
}
