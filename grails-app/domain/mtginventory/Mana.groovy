package mtginventory

import java.util.regex.Matcher
import java.util.regex.Pattern

class Mana {

    String name
    String symbol
    String cmc

    static hasMany = [colors: ManaColor]

    static constraints = {
        symbol unique: true
    }

    String toString() {
        "${name}"
    }
}
