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
        out << text?.replace("\n\n","<br>").replace("\n","<br>")
    }
    
    def renderExpansionCardImage = { attrs ->
        def expansionCard = attrs.expansionCard
        def img = """<img src="http://mtgimage.com/set/${expansionCard.expansion.expansionCodes.find{ it.author == "mtgsalvation" }?.code}/${expansionCard.card.name}.jpg" width="200px">"""
        out << """${g.link( controller:"ExpansionCard", action:"show", id:expansionCard.id, img)}"""
    }
	
	def renderExpansion = { attrs ->
		def expansionCard = attrs.expansionCard
		def expansion = expansionCard?.expansion ?: attrs.expansion
		def withSymbol = attrs.withSymbol
		if( withSymbol ) {
			def expansionName = expansion?.name?.replace('&','and')?.replace(':','')?.replace(' Core Set','')?.replace('"','')
			def expansionRarity = expansionCard?.rarity?.name?.replace("Basic Land","Common")?.replace("Mythic Rare","Mythic")
			out << """<a href="/MtGInventory/ExpansionCard/list?expansionID=${expansion.id}">${g.img( dir:"images/expansions", file:"${expansionName}_${expansionRarity}.gif", style:"max-width: 30px; max-height: 20px;" )}</a> """
		}
		out << """<a href="/MtGInventory/ExpansionCard/list?expansionID=${expansion.id}">${expansion.name}</a>"""
	}
	
	def renderIllustrator = { attrs ->
		def illustrator = attrs.illustrator
		def ids = [illustrator?.id]
		ids.addAll(illustrator?.aliases?.id ?: [])
		out << """<a href="/MtGInventory/ExpansionCard/list?illustratorID=${ids.join(",")}">${illustrator?.name}</a>"""
	}
    
	def renderResult = { attrs ->
        def result = attrs.result
        if( result instanceof Card || result instanceof ExpansionCard ) {
            def card = null
            def expansionCard = null
            if( result instanceof Card ) {
                card = Card.read(result.id)
                expansionCard = card.expansionCards?.sort{it.expansion}?.first()
            } else {
                expansionCard = ExpansionCard.read(result.id)
                card = expansionCard.card
            }
            out << "<td class='card-result'>"
            out << renderExpansionCardImage( expansionCard: expansionCard )
            out << "</td>"
            out << """
                <td class='card-result'><div class='card-text'>
					<b>${g.link( controller:"ExpansionCard", action:"show", id:"${expansionCard.id}", card.name )}</b><br>
                <i>"""
            expansionCard.types.sort{ a,b -> a.id <=> b.id }.each {
                out << "${it.name} "
            }
            out << "</i>"
            if( expansionCard.text ) {
                out << "<hr><b>"
                out << mtg.renderText( text:expansionCard.text )
                out << "</b>"
            }
            if( expansionCard.flavorText ) {
                out << "<hr><i>${expansionCard.flavorText.replace("\n\n","<br>").replace("\n","<br>")}</i>"
            }
            out << "</div>"
			if( expansionCard.illustrator ) {
				out << "<span class='card-illustrator'>${g.img( dir:"images", file:"paintbrush_black.png", style:"max-width: 37px; top: 2px; position: relative;")} ${expansionCard.illustrator}</span>"
			}
            if( card.power && card.toughness ) {
                out << "<span class='card-pt'><b>${card.power} / ${card.toughness}</b></span>"
            } else if( card.loyalty ) {
                out << "<span class='card-loyalty'><b><i>Loyalty:</i> ${card.loyalty}</b></span>"
            }
            out << "</td>"
        } else if( result instanceof Expansion ) {
            def expansion = Expansion.read(result.id)
            out << """
                <td style="padding: 5px;">
                    ${g.img( dir:"images/expansions", file:"${expansion.name.replace('&','and').replace(':','').replace(' Core Set','').replace('"','')}_Common.gif", style:"max-width: 30px; max-height: 20px;")}
                </td>
                <td style="padding: 5px;">
                    ${renderExpansion( expansion: expansion )}
                </td>
                <td style="padding: 5px;">
                    Released ${expansion.releaseDate?.format("yyyy-MM-dd")}
                </td>
                <td style="padding: 5px;">
                    ${expansion.totalCards} cards
                </td>
            """
        }else if( result instanceof Illustrator ) {
            def illustrator = Illustrator.read(result.id)
			out << "<td>"
			out << renderIllustrator( illustrator:illustrator )
			out << "</td>"
            if( illustrator.aliases ) {
                out << """
                    <td><b>Also known as:</b>
                    <ul style="padding-left: 20px;">
                """
                illustrator.aliases.sort{ a,b -> a.name <=> b.name }.each {
                    out << "<li>${it}</li>"
                }
                out << "</ul></td>"
            } else {
                out << "<td></td>"
            }
        }else{
            out << "<td>${result}</td>"
        }
    }
}
