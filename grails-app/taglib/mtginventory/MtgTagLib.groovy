package mtginventory

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * IndexTagLib
 * A taglib library provides a set of reusable tags to help rendering the views.
 */
class MtgTagLib {
    static namespace = "mtg"

    def renderText = { attrs ->
        String text = attrs.text
        Pattern pattern = ~/\{.+?\}/
        Matcher m = pattern.matcher( text )
        while( m.find() ) {
            def symbol = m.group()
            Mana mana = Mana.findBySymbol( symbol.replace("/","") )
            def symbolImage = "${symbol.replace('{','').replace('}','').replace("/","")}.gif"
            def symbolImageFile = grailsApplication.parentContext.getResource("images/mana/${symbolImage}")
            
            if( mana || symbolImageFile?.exists() ) {
                String manaImage = g.img( dir:"images/mana", file:"${symbolImage}", style:"max-height: 20px; max-width: 20px")
                text = text.replace( symbol, manaImage )
            }
        }
        out << text.replace("\n","<br>")
    }
}
