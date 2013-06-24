package mtginventory

class ExpansionCode {

    String author
    String code
    Expansion expansion

    static belongsTo = Expansion

    static constraints = {
        author unique: ['code']
    }

    String toString() {
        "${author} - ${code}"
    }
}
