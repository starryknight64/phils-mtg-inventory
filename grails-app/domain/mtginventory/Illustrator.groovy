package mtginventory

class Illustrator {
    static searchable = {
        spellCheck "include"
    }

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
