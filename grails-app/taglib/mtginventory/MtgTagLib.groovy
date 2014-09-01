package mtginventory

import java.util.Random
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.net.URLEncoder
import java.net.HttpURLConnection
import java.net.URL
import groovy.json.JsonSlurper
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType

/**
 * IndexTagLib
 * A taglib library provides a set of reusable tags to help rendering the views.
 */
class MtgTagLib {
    static namespace = "mtg"
	
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
		def imgURL = "http://mtgimage.com/set/${expansionCard.expansion.code}/${expansionCard.imageName}.jpg"
		if( !expansionCard.imageName ) {
			imgURL = "http://mtgimage.com/card/${expansionCard.card.name}.jpg"
		}
        def img = """<img src="${imgURL}" class="card-image" width="${width}">"""
        out << """${g.link( controller:"ExpansionCard", action:"show", id:expansionCard.id, img)}"""
    }
	
	def renderExpansionIcon = { attrs ->
		def expansion = attrs.expansion ?: attrs.expansionCard?.expansion
		def rarity = attrs.expansionCard?.rarity?.name?.replace("Basic Land","c")?.substring(0, 1) ?: "c"
		def imgURL = getImageURL(expansionCode:expansion.code,rarity:rarity) 
		def img = """<img src="${imgURL}">"""
		if( attrs.linkToCard ) {
			out << """<a href="/MtGInventory/ExpansionCard/show/${attrs.expansionCard.id}" class="expansion-icon">${img}</a>"""
		} else {
			out << """<a href="/MtGInventory/ExpansionCard/list?expansionID=${expansion.id}" class="expansion-icon">${img}</a>"""
		}
	}
	
	def getImageURL = { attrs ->
		def expansionCode = attrs.expansionCode
		def rarity = (attrs.rarity ?: "c").toLowerCase()
        InputStream inputFile = getClass().classLoader.getResourceAsStream("setimages.json")
        def setImages = new JsonSlurper().parseText(inputFile.text)
        setImages.each { expCode, rarities ->
			if(expansionCode == expCode) {
				def url = "http://mtgimage.com/symbol/set/${expansionCode}/"
				if(rarities.toLowerCase().contains(rarity)){
					url += rarity
				} else if(rarities != "") {
					url += rarities.split(" ")[0]
				} else {
					out << ""
					return
				}
				url += "/32.png"
				out << url
				return
			}
		}
		out << ""
	}

	def renderExpansion = { attrs ->
		def expansionCard = attrs.expansionCard
		def expansion = expansionCard?.expansion ?: attrs.expansion
		def expStyle = attrs.expStyle
		def withSymbol = attrs.withSymbol
		def tabulated = attrs.tabulated
		def linkToCard = attrs.linkToCard
		if( withSymbol ) {
			def icon = renderExpansionIcon( expansion: expansion, expansionCard: expansionCard, linkToCard: linkToCard )
			if( tabulated ) {
				out << "<td>${icon}</td>"
			} else {
				out << "${icon} "
			}
		}
		
		if( tabulated ) {
			out << "<td>"
		}
		if( linkToCard ) {
			out << """<a href="/MtGInventory/ExpansionCard/show/${expansionCard.id}" class="expansion-name">"""
		} else {
			out << """<a href="/MtGInventory/ExpansionCard/list?expansionID=${expansion.id}" class="expansion-name">"""
		}
		out << "${expansion.name}</a>"
		if( tabulated ){
			out << "</td>"
		}
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
			out << renderExpansion( expansion: expansionCard.expansion, expansionCard: expansionCard, withSymbol: true )
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
				if( card.types.name.contains("Vanguard") ) {
					out << "<span class='card-hand-life'><i>Hand Modifier:</i> <b>${card.power.toInteger() >= 0 ? "+" : ""}${card.power}</b> / <i>Life Modifier:</i> <b>${card.toughness.toInteger() >= 0 ? "+" : ""}${card.toughness}</b></span>"
				} else {
                	out << "<span class='card-pt'><b>${card.power} / ${card.toughness}</b></span>"
				}
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
