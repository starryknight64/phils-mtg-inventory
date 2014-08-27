package mtginventory

class CardLegality {
	
	Card card
	CardLegalityType cardLegalityType
	Legality legality

    static constraints = {
		card unique: "legality"
    }
}
