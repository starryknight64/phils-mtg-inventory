package mtginventory

import java.util.Random
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.net.URLEncoder
import groovy.json.JsonSlurper
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType

/**
 * IndexTagLib
 * A taglib library provides a set of reusable tags to help rendering the views.
 */
class MtgTagLib {
    static namespace = "mtg"
	
	def renderPrice = { attrs ->
		def expansionCard = attrs.expansionCard
		def priceSource = attrs.priceSource
		if( expansionCard && priceSource ) {
			ExpansionCardPrice price = ExpansionCardPrice.findByExpansionCardAndSource( expansionCard, priceSource )
			if( !price || price?.lastUpdated?.plus(1) < new Date() ) {
				def pricingREST = new HTTPBuilder( priceSource.rest )
				def q = [cardname:"${expansionCard.card.name}",setname:"${expansionCard.expansion.name}"]
				def pricing = []
				try {
					pricing = pricingREST.get( query: q, contentType: ContentType.JSON )
				} catch(Exception ex){}
				def low = pricing.size() > 2 ? pricing.get(0) : null
				def median = pricing.size() > 1 ? pricing.get(1) : pricing.size() == 1 ? pricing.get(0) : null 
				def high = pricing.size() > 2 ? pricing.get(2) : null
				if( price ) {
					price.source = priceSource
					price.expansionCard = expansionCard
					price.low = low
					price.median = median
					price.high = high
					if( price.isDirty() ) {
						price.save()
					}
				} else {
					price = new ExpansionCardPrice( source: priceSource, expansionCard:expansionCard, low:low,median:median,high:high ).save()
				}
			}
			out << """
				<tr>
					<td><a href="${priceSource.website}" target="_blank">${priceSource.name}</a></td>
					<td class="price">${price?.low ?: ""}</td>
					<td class="price">${price?.median ?: ""}</td>
					<td class="price">${price?.high ?: ""}</td>
				</tr>"""
		}
	}
	
	def renderRandomCard = { attrs ->
		Random rand = new Random()
		def max = Card.count
		if( max <= 0 ) {
			return
		}
		def card = Card.get( rand.nextInt( max ) )
		while( !card ) {
			card = Card.get( rand.nextInt( max ) )
		}
		out << renderExpansionCardImage( expansionCard: card.expansionCards.first() )
	}

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
                String manaImage = g.img( dir:"images/mana", file:"${symbolImage}", class:"card-symbol")
                text = text.replace( symbol, manaImage )
            }
        }
        out << text?.replace("\n\n","<br>").replace("\n","<br>")
    }
    
    def renderExpansionCardImage = { attrs ->
        def expansionCard = attrs.expansionCard
		def width = attrs.width ?: "200px"
        def img = """<img src="http://mtgimage.com/set/${expansionCard.expansion.expansionCodes.find{ it.author == "mtgsalvation" }?.code}/${expansionCard.imageName}.jpg" class="card-image" width="${width}">"""
        out << """${g.link( controller:"ExpansionCard", action:"show", id:expansionCard.id, img)}"""
    }
	
	def renderExpansionIcon = { attrs ->
		def expansion = attrs.expansion
		def expansionName = expansion?.name?.replace('&','and')?.replace(':','')?.replace(' Core Set','')?.replace('"','')
		def expansionRarity = attrs.expansionCard?.rarity?.name?.replace("Basic Land","Common")?.replace("Mythic Rare","Mythic") ?: "Common"
		out << """<a href="/MtGInventory/ExpansionCard/list?expansionID=${expansion.id}" class="expansion-icon">${g.img( dir:"images/expansions", file:"${expansionName}_${expansionRarity}.gif")}</a>"""
	}
	
	def renderExpansion = { attrs ->
		def expansionCard = attrs.expansionCard
		def expansion = expansionCard?.expansion ?: attrs.expansion
		def expStyle = attrs.expStyle
		def withSymbol = attrs.withSymbol
		if( withSymbol ) {
			out << renderExpansionIcon( expansion: expansion )
			out << " "
		}
		out << """<a href="/MtGInventory/ExpansionCard/list?expansionID=${expansion.id}" class="expansion-name">${expansion.name}</a>"""
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
            card.types.sort{ a,b -> a.id <=> b.id }.each {
                out << "${it.name} "
            }
            out << "</i>"
			out << "<i class='expansion'>"
			out << renderExpansion( expansion: expansionCard.expansion, withSymbol: true )
			out << "</i>"
            if( card.text ) {
                out << "<hr><b>"
                out << mtg.renderText( text:card.text )
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
