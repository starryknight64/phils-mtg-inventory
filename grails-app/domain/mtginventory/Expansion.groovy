package mtginventory

class Expansion {
	static searchable = { spellCheck "include" }

	String name
	String code
	Date releaseDate
	Integer totalCards

	static hasMany = [expansionCards: ExpansionCard, legalities: Legality]

	static constraints = {
		name unique: true
		code unique: true
		releaseDate nullable: true
		totalCards nullable: true
	}

	Meta jsonHash(){
		def hashName = "exp-hash-${id}"
		def hash = Meta.findByName(hashName) ?: new Meta(name:hashName, value:null).save()
		hash
	}

	String toString() {
		"${name}"
	}
}
