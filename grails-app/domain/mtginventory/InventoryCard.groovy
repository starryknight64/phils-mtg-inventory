package mtginventory

class InventoryCard {

    ExpansionCard expansionCard
    Integer amount

    static constraints = {
    }

    String toString() {
        "${amount}x ${expansionCard.card.name}: ${expansionCard.expansion.name}"
    }
}
