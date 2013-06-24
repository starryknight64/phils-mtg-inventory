package mtginventory

class Illustrator {

    String name

    static hasMany = [aliases: Illustrator,
                      notAliases: Illustrator]

    static constraints = {
        name unique: true
    }

    String toString() {
        "${name}"
    }
}
