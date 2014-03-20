package mtginventory

class ExpansionCardPrice {

	PriceSource source
	ExpansionCard expansionCard
	Date lastUpdated
	String low
	String median
	String high

	static belongsTo = ExpansionCard

	static constraints = {
		low nullable: true
		median nullable: true
		high nullable: true
	}
}
