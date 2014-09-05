package mtginventory

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import java.net.URLEncoder
import org.codehaus.groovy.runtime.StackTraceUtils

class PricesService {

	def getChannelFireballPrices(Card card, Expansion expansion) {
		def search = URLEncoder.encode(card.name + (expansion ? " ${expansion.name}":""),"UTF-8")
		def cfbURL = "http://store.channelfireball.com/advanced_search?search[fuzzy_search]=${search}&search[category_ids_with_descendants][]=8&search[in_stock]=1"
		def html = cfbURL.toURL().getText()

		def minPrice = Integer.MAX_VALUE
		def maxPrice = -1
		def totalPrice = 0
		def totalPrices = 0

		def endNameIndex = 0
		for (i in 0 .. html.count("grid-item-price")) {
			def startNameIndex = html.indexOf("grid-item-title\">",endNameIndex) + "grid-item-title\">".length()
			endNameIndex = html.indexOf("<",startNameIndex)
			def cfbCardName = html.substring(startNameIndex, endNameIndex).toLowerCase()
			if (cfbCardName == card.name.toLowerCase() || cfbCardName == "${card.name} - Foil".toLowerCase()) {
				def startPriceIndex = html.indexOf("grid-item-price\">", endNameIndex)
				startPriceIndex = html.indexOf("\$", startPriceIndex) + 1
				def endPriceIndex = html.indexOf("<", startPriceIndex)
				def cfbPrice = html.substring(startPriceIndex, endPriceIndex).toFloat()

				if (cfbPrice < minPrice) {
					minPrice = cfbPrice
				}
				if (cfbPrice > maxPrice) {
					maxPrice = cfbPrice
				}
				totalPrice += cfbPrice
				totalPrices++
			}
		}
		
		minPrice = (minPrice < Integer.MAX_VALUE ? String.format("\$%.2f", minPrice) : "")
		maxPrice = (maxPrice > -1 ? String.format("\$%.2f", maxPrice) : "")
		def avgPrice = (totalPrices > 0 ? String.format("\$%.2f", totalPrice / totalPrices) : "")
		return ["url":cfbURL, "low":minPrice, "median":avgPrice, "high":maxPrice]
	}

	def getEbayPrices(Card card) {
		def search = URLEncoder.encode( card.name + " mtg nm", "UTF-8" )
		def ebayURL = "http://www.ebay.com/sch/i.html?_sacat=0&_sop=15&LH_BIN=1&_ipg=200&_nkw=" + search
		def html = ebayURL.toURL().getText()
		
		def minPrice = Integer.MAX_VALUE
		def maxPrice = -1
		def totalPrice = 0
		def totalPrices = 0
		
		def maxIndex = html.indexOf("More items related to")

		def qtyPatterns = [ ~/(\d+)\s*[xX]/, ~/[xX]\s*(\d+)/ ]
		def endNameIndex = 0
		for (i in 0 .. html.count("lvtitle")) {
	        def startNameIndex = html.indexOf("lvtitle\">", endNameIndex) + "lvtitle\">".length()
	        startNameIndex = html.indexOf(">", startNameIndex) + 1
	        endNameIndex = html.indexOf("<", startNameIndex)
	        def listingName = html.substring(startNameIndex,endNameIndex)
			
			if (maxIndex > -1 && endNameIndex > maxIndex) {
				break
			}
			
			def qty = 1
			for( def qtyPattern : qtyPatterns ) {
				def matcher = qtyPattern.matcher(listingName)
				if (matcher.find()) {
					def qty2 = matcher.group(1).toInteger()
					if (qty2 > qty) {
						qty = qty2
					}
				}
			}
			
			if (qty < 1) {
				qty = 1
			}
			
			def startPriceIndex = html.indexOf('span  class="g-b">', endNameIndex)
			startPriceIndex = html.indexOf("\$", startPriceIndex) + 1
			def endPriceIndex = html.indexOf("<", startPriceIndex)
			def ebayPrice = html.substring(startPriceIndex, endPriceIndex).toFloat() / qty
			
			if (ebayPrice < minPrice) {
				minPrice = ebayPrice
			}
			if (ebayPrice > maxPrice) {
				maxPrice = ebayPrice
			}
			
			totalPrice += (ebayPrice * qty)
			totalPrices += qty
		}
		
		minPrice = (minPrice < Integer.MAX_VALUE ? String.format("\$%.2f", minPrice) : "")
		maxPrice = (maxPrice > -1 ? String.format("\$%.2f", maxPrice) : "")
		def avgPrice = (totalPrices > 0 ? String.format("\$%.2f", totalPrice / totalPrices) : "")
		return ["url":ebayURL, "low":minPrice, "median":avgPrice, "high":maxPrice]
	}

	def getTCGPlayerPrices(Card card, Expansion expansion) {
		def tcgPlayerURL = "http://magic.tcgplayer.com/db/magic_single_card.asp?cn=" + URLEncoder.encode(card.name, "UTF-8") + (expansion ? "&sn=" +  URLEncoder.encode(expansion.name, "UTF-8") : "")
		def html = tcgPlayerURL.toURL().getText()
		
		def minPrice = ""
		def avgPrice = ""
		def maxPrice = ""
	
		//   Scrape for the low price
		def startLowIndex = html.indexOf('>L:')
		if (startLowIndex < 0) {
			return ["url":tcgPlayerURL, "low":minPrice, "median":avgPrice, "high":maxPrice]
		}
		
		startLowIndex = html.indexOf("\$", startLowIndex)
		def endLowIndex = html.indexOf("<", startLowIndex)
	
		minPrice = html.substring(startLowIndex, endLowIndex)
	
		//   Scrape for the median price
		def startMidIndex = html.indexOf('>M:')
		startMidIndex = html.indexOf("\$", startMidIndex)
		def endMidIndex = html.indexOf("<", startMidIndex)
		
		avgPrice = html.substring(startMidIndex, endMidIndex)
	
		//   Scrape for the high price
		def startHighIndex = html.indexOf('>H:')
		startHighIndex = html.indexOf("\$", startHighIndex)
		def endHighIndex = html.indexOf("<", startHighIndex)
		
		maxPrice = html.substring(startHighIndex, endHighIndex)
		
		return ["url":tcgPlayerURL, "low":minPrice, "median":avgPrice, "high":maxPrice]
	}
}
