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
        Matcher m = pattern.matcher( text ?: "" )
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
        out << text?.replace("\n","<br>")
    }
    
    def renderResult = { attrs ->
        def result = attrs.result
        if( result instanceof Card ) {
            def card = Card.read(result.id)
            def expansionCard = card.expansionCards.iterator()[0]
            out << """
                <td>
                    <img src="http://mtgimage.com/set/${expansionCard.expansion.expansionCodes.find{ it.author == "mtgsalvation" }?.code}/${card.name}.jpg" width="200px">
                </td>
                <td style="padding: 10px 10px;">
                    ${g.link( controller:"Card", action:"show", id:"${card.id}", card.name )}<br>
                """
            expansionCard.types.sort{ a,b -> a.id <=> b.id }.each {
                out << "${it.name} "
            }
            out << "<br>"
            out << mtg.renderText( text:expansionCard.text )
            out << "<br>"
            if( card.power && card.toughness ) {
                out << "${card.power} / ${card.toughness}<br>"
            } else if( card.loyalty ) {
                out << "Loyalty: ${card.loyalty}<br>"
            }
            out << "</td>"
        } else if( result instanceof ExpansionCard ) {
            
        } else if( result instanceof Expansion ) {
            
        }else if( result instanceof Illustrator ) {
            
        }else{
            
        }
    }
}
