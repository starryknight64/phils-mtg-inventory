package mtginventory

class InventoryCard {

    ExpansionCard expansionCard
    Integer amount

    static constraints = {
        expansionCard unique: true
    }

    String toString() {
        "${amount}x ${expansionCard.card.name}: ${expansionCard.expansion.name}"
    }
}
