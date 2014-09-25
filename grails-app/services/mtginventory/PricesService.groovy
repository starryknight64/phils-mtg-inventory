package mtginventory

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import java.net.URLEncoder
import org.codehaus.groovy.runtime.StackTraceUtils

class PricesService {

	def getAbuPrices(Card card, Expansion expansion) {
        def search = URLEncoder.encode(card.name,"UTF-8")
        def abuURL = "http://www.abugames.com/shop.cgi?command=search&edition=0&cardname=${search}&maxresults=400&displaystyle=list"
        def html = abuURL.toURL().getText()

        def minPrice = Integer.MAX_VALUE
        def maxPrice = -1
        def totalPrice = 0
        def totalPrices = 0

        def endNameIndex = html.indexOf("<div class=\"cardinfo\">")
        for( i in 0 .. html.count("<div class=\"cardinfo\">")) {
            def startNameIndex = html.indexOf("<a",endNameIndex)
            startNameIndex = html.indexOf("<a", startNameIndex)
            startNameIndex = html.indexOf(">", startNameIndex) + 1
            endNameIndex = html.indexOf("<",startNameIndex)
            def abuCardName = html.substring(startNameIndex, endNameIndex).toLowerCase()

            if(abuCardName == card.name.toLowerCase() || abuCardName == card.name.toLowerCase() + " - foil"){
                def moreQuantities = true
                while(moreQuantities) {
                    def startAvailabilityIndex = html.indexOf("<td class=\"itementry\"",endNameIndex)
                    startAvailabilityIndex = html.indexOf(">",startAvailabilityIndex) + 1
                    def endAvailabilityIndex = html.indexOf("<",startAvailabilityIndex)
                    def amtAvailable = html.substring(startAvailabilityIndex, endAvailabilityIndex).replace(" in-stock","").toInteger()

                    //Don't check if amtAvailable > 0 because checking for moreQuantities would be more difficult
                    def startPriceIndex = html.indexOf("<td class=\"itementry\"", endAvailabilityIndex)
                    startPriceIndex = html.indexOf("\$", startPriceIndex) + 1
                    def endPriceIndex = html.indexOf("<", startPriceIndex)
                    def abuPrice = html.substring(startPriceIndex, endPriceIndex).toFloat()

                    //Check amtAvailable here so that amounts of 0 don't get factored into the min/max
                    if( amtAvailable > 0 ) {
                        if (abuPrice < minPrice) {
                            minPrice = abuPrice
                        }
                        if (abuPrice > maxPrice) {
                            maxPrice = abuPrice
                        }
                        totalPrice += (abuPrice * amtAvailable)
                        totalPrices += amtAvailable
                    }

                    def nextStartAvailableIndex = html.indexOf("<td class=\"itementry\"", endPriceIndex)
                    def nextRow = html.indexOf("<div class=\"cardinfo\">", endPriceIndex)
                    moreQuantities = (nextStartAvailableIndex < nextRow)
                    endNameIndex = endPriceIndex
                }
            }
            endNameIndex = html.indexOf("<div class=\"cardinfo\">",endNameIndex)
        }

        minPrice = (minPrice < Integer.MAX_VALUE ? String.format("\$%.2f", minPrice) : "")
        maxPrice = (maxPrice > -1 ? String.format("\$%.2f", maxPrice) : "")
        def avgPrice = (totalPrices > 0 ? String.format("\$%.2f", totalPrice / totalPrices) : "")
        return ["url":abuURL, "low":minPrice, "median":avgPrice, "high":maxPrice]
    }

    def getCcgHousePrices(Card card, Expansion expansion) {
        def search = URLEncoder.encode(card.name + (expansion ? " ${expansion.name}" : ""),"UTF-8")
        def ccgHouseURL = "http://www.ccghouse.com/advanced_search?search[fuzzy_search]=${search}&search[in_stock]=1&buylist_mode=0&search[category_ids_with_descendants][]=8&search[sort]=name&search[direction]=ascend&commit=Search"
        def html = ccgHouseURL.toURL().getText()

        def minPrice = Integer.MAX_VALUE
        def maxPrice = -1
        def totalPrice = 0
        def totalPrices = 0

        def endNameIndex = html.indexOf("<li id=\"product_")
        for( i in 0 .. html.count("<li id=\"product_")) {
            def startNameIndex = html.indexOf("<h4 class=\"title\" itemprop=\"name\">",endNameIndex)
            startNameIndex = html.indexOf("<a", startNameIndex)
            startNameIndex = html.indexOf(">", startNameIndex) + 1
            endNameIndex = html.indexOf("<",startNameIndex)
            def ccgHouseCardName = html.substring(startNameIndex, endNameIndex).toLowerCase()

            if(ccgHouseCardName == card.name.toLowerCase()){
                def startPriceIndex = html.indexOf("<span class=\"price\" itemprop=\"price\">", endNameIndex)
                startPriceIndex = html.indexOf("\$", startPriceIndex) + 1
                def endPriceIndex = html.indexOf("<", startPriceIndex)
                def ccgHousePrice = html.substring(startPriceIndex, endPriceIndex).toFloat()

                def startAvailabilityIndex = html.indexOf("<span class=\"variant-short-info\">",endPriceIndex)
                startAvailabilityIndex = html.indexOf("<em>",startAvailabilityIndex) + "<em>".length()
                def endAvailabilityIndex = html.indexOf("<",startAvailabilityIndex)
                def amtAvailable = html.substring(startAvailabilityIndex, endAvailabilityIndex).replace(" in-stock","").toInteger()
                if( amtAvailable > 0 ) {
                    if (ccgHousePrice < minPrice) {
                        minPrice = ccgHousePrice
                    }
                    if (ccgHousePrice > maxPrice) {
                        maxPrice = ccgHousePrice
                    }
                    totalPrice += (ccgHousePrice * amtAvailable)
                    totalPrices += amtAvailable
                }
            }
        }

        minPrice = (minPrice < Integer.MAX_VALUE ? String.format("\$%.2f", minPrice) : "")
        maxPrice = (maxPrice > -1 ? String.format("\$%.2f", maxPrice) : "")
        def avgPrice = (totalPrices > 0 ? String.format("\$%.2f", totalPrice / totalPrices) : "")
        return ["url":ccgHouseURL, "low":minPrice, "median":avgPrice, "high":maxPrice]
    }

    def getHotSauceGamesPrices(Card card, Expansion expansion) {
        def search = URLEncoder.encode(card.name + (expansion ? " ${expansion.name}" : ""),"UTF-8")
        def hotSauceURL = "http://www.hotsaucegames.com/advanced_search?search[fuzzy_search]=${search}&search[in_stock]=1&buylist_mode=0&search[category_ids_with_descendants][]=1266&search[sort]=name&search[direction]=ascend&commit=Search"
        def html = hotSauceURL.toURL().getText()

        def minPrice = Integer.MAX_VALUE
        def maxPrice = -1
        def totalPrice = 0
        def totalPrices = 0

        def endNameIndex = html.indexOf("<li id=\"product_")
        for( i in 0 .. html.count("<li id=\"product_")) {
            def startNameIndex = html.indexOf("<span itemprop=\"name\">",endNameIndex) + "<span itemprop=\"name\">".length()
            endNameIndex = html.indexOf("<",startNameIndex)
            def hotSauceCardName = html.substring(startNameIndex, endNameIndex).toLowerCase()

            if(hotSauceCardName == card.name.toLowerCase()){
                def startPriceIndex = html.indexOf("<span class=\"price\" itemprop=\"price\">", endNameIndex)
                startPriceIndex = html.indexOf("\$", startPriceIndex) + 1
                def endPriceIndex = html.indexOf("<", startPriceIndex)
                def hotSaucePrice = html.substring(startPriceIndex, endPriceIndex).toFloat()

                def startAvailabilityIndex = html.indexOf("<span class=\"variant-short-info\">",endPriceIndex)
                startAvailabilityIndex = html.indexOf("<em>",startAvailabilityIndex) + "<em>".length()
                def endAvailabilityIndex = html.indexOf("<",startAvailabilityIndex)
                def amtAvailable = html.substring(startAvailabilityIndex, endAvailabilityIndex).replace(" available","").toInteger()
                if( amtAvailable > 0 ) {
                    if (hotSaucePrice < minPrice) {
                        minPrice = hotSaucePrice
                    }
                    if (hotSaucePrice > maxPrice) {
                        maxPrice = hotSaucePrice
                    }
                    totalPrice += (hotSaucePrice * amtAvailable)
                    totalPrices += amtAvailable
                }
            }
        }

        minPrice = (minPrice < Integer.MAX_VALUE ? String.format("\$%.2f", minPrice) : "")
        maxPrice = (maxPrice > -1 ? String.format("\$%.2f", maxPrice) : "")
        def avgPrice = (totalPrices > 0 ? String.format("\$%.2f", totalPrice / totalPrices) : "")
        return ["url":hotSauceURL, "low":minPrice, "median":avgPrice, "high":maxPrice]
    }

    def getStrikeZoneOnlinePrices(Card card, Expansion expansion) {
        def search = URLEncoder.encode(card.name + (expansion ? " ${expansion.name}" : ""),"UTF-8")
        def strikeZoneURL = "http://shop.strikezoneonline.com/TUser?T=${search}&MC=CUSTS&MF=B&BUID=637&ST=D&CMD=Search"
        def html = strikeZoneURL.toURL().getText()

        def minPrice = Integer.MAX_VALUE
        def maxPrice = -1
        def totalPrice = 0
        def totalPrices = 0

        def endNameIndex = html.indexOf("<TR class='ItemTableRow")
        for( i in 0 .. html.count("<TR class='ItemTableRow")) {
            def startNameIndex = html.indexOf("<TD>",endNameIndex)
            startNameIndex = html.indexOf("<a",startNameIndex)
            startNameIndex = html.indexOf(">",startNameIndex) + 1
            endNameIndex = html.indexOf("</a>",startNameIndex)
            def strikeZoneCardName = html.substring(startNameIndex, endNameIndex).toLowerCase().replace("<strong>","").replace("</strong>","")

            if(strikeZoneCardName == card.name.toLowerCase()){
                def startAvailabilityIndex = html.indexOf("<TD align=right>", endNameIndex) + "<TD align=right>".length()
                def endAvailabilityIndex = html.indexOf("<",startAvailabilityIndex)
                def amtAvailable = html.substring(startAvailabilityIndex, endAvailabilityIndex).toInteger()
                if( amtAvailable > 0 ) {
                    def startPriceIndex = html.indexOf("<span name=ItemPrice>", endAvailabilityIndex) + "<span name=ItemPrice>".length()
                    def endPriceIndex = html.indexOf("<", startPriceIndex)
                    def strikeZonePrice = html.substring(startPriceIndex, endPriceIndex).toFloat()
                    if (strikeZonePrice < minPrice) {
                        minPrice = strikeZonePrice
                    }
                    if (strikeZonePrice > maxPrice) {
                        maxPrice = strikeZonePrice
                    }
                    totalPrice += (strikeZonePrice * amtAvailable)
                    totalPrices += amtAvailable
                }
            }
        }

        minPrice = (minPrice < Integer.MAX_VALUE ? String.format("\$%.2f", minPrice) : "")
        maxPrice = (maxPrice > -1 ? String.format("\$%.2f", maxPrice) : "")
        def avgPrice = (totalPrices > 0 ? String.format("\$%.2f", totalPrice / totalPrices) : "")
        return ["url":strikeZoneURL, "low":minPrice, "median":avgPrice, "high":maxPrice]
    }

    def getTrollAndToadPrices(Card card, Expansion expansion) {
        def search = URLEncoder.encode(card.name + (expansion ? " ${expansion.name}" : ""),"UTF-8")
        def tatURL = "http://www.trollandtoad.com/products/search.php?search_words=${search}&search_category=1041&search_order=relevance_desc&in_stock=yes"
        def html = tatURL.toURL().getText()

        def minPrice = Integer.MAX_VALUE
        def maxPrice = -1
        def totalPrice = 0
        def totalPrices = 0

        def endNameIndex = 0
        for( i in 0 .. html.count("<div class=\"search_result_wrapper\">")) {
            def startNameIndex = html.indexOf("<div class=\"search_result_text\">",endNameIndex)
            startNameIndex = html.indexOf("<a",startNameIndex)
            startNameIndex = html.indexOf(">",startNameIndex) + 1
            endNameIndex = html.indexOf("</a>",startNameIndex)
            def tatCardName = html.substring(startNameIndex, endNameIndex).toLowerCase().replace("<strong>","").replace("</strong>","")

            if(tatCardName == card.name.toLowerCase() || tatCardName == card.name.toLowerCase() + " - foil"){
                def moreQuantities = true
                while(moreQuantities){
                    def startPriceIndex = html.indexOf("<td class=\"price_text\">", endNameIndex)
                    startPriceIndex = html.indexOf("\$", startPriceIndex) + 1
                    def endPriceIndex = html.indexOf("<", startPriceIndex)
                    def tatPrice = html.substring(startPriceIndex, endPriceIndex).toFloat()

                    def startQtyIndex = html.indexOf("search_result_conditions_qty", endPriceIndex)
                    startQtyIndex = html.indexOf(">", startQtyIndex) + 1
                    def endQtyIndex = html.indexOf("</select>", startQtyIndex)
                    def quantities = html.substring(startQtyIndex, endQtyIndex).toLowerCase().split("<option value='")
                    def qty = quantities[-1].substring(0,quantities[-1].indexOf("'")).toInteger()

                    if (tatPrice < minPrice) {
                        minPrice = tatPrice
                    }
                    if (tatPrice > maxPrice) {
                        maxPrice = tatPrice
                    }
                    totalPrice += (tatPrice * qty)
                    totalPrices += qty

                    def nextStartQtyIndex = html.indexOf("search_result_conditions_qty", endQtyIndex)
                    def nextRow = html.indexOf("<div class=\"search_result_text\">", endQtyIndex)
                    moreQuantities = (nextStartQtyIndex < nextRow)
                    endNameIndex = endQtyIndex
                }
            }
        }

        minPrice = (minPrice < Integer.MAX_VALUE ? String.format("\$%.2f", minPrice) : "")
        maxPrice = (maxPrice > -1 ? String.format("\$%.2f", maxPrice) : "")
        def avgPrice = (totalPrices > 0 ? String.format("\$%.2f", totalPrice / totalPrices) : "")
        return ["url":tatURL, "low":minPrice, "median":avgPrice, "high":maxPrice]
    }

    def getAmazonPrices(Card card, Expansion expansion) {
        def search = URLEncoder.encode("${card.name} mtg","UTF-8")
        def amazonURL = "http://www.amazon.com/s/rh=n:165793011,n:166220011,n:166242011,n:2522049011,k:${search},p_89:Magic:%20the%20Gathering"
        def html = amazonURL.toURL().getText()

        def minPrice = Integer.MAX_VALUE
        def maxPrice = -1
        def totalPrice = 0
        def totalPrices = 0

        def namePattern = ~/magic:? the gathering(: | - )(.+?)( (\(.+\)|-).+)/
        def endNameIndex = 0
        for( i in 0 .. html.count("<span class=\"lrg bold\">")) {
            def startNameIndex = html.indexOf("<span class=\"lrg bold\">",endNameIndex) + "<span class=\"lrg bold\">".length()
            endNameIndex = html.indexOf("<",startNameIndex)
            def amazonCardName = html.substring(startNameIndex, endNameIndex).toLowerCase()

            def matcher = namePattern.matcher(amazonCardName)
            if(matcher.find()){
                amazonCardName = matcher.group(2)
            } else {
                continue
            }

            if(amazonCardName == card.name.toLowerCase()){
                def startPriceIndex = html.indexOf("<span class=\"bld lrg red\">", endNameIndex)
                startPriceIndex = html.indexOf("\$", startPriceIndex) + 1
                def endPriceIndex = html.indexOf("<", startPriceIndex)
                def amazonPrice = html.substring(startPriceIndex, endPriceIndex).toFloat()

                if (amazonPrice < minPrice) {
                    minPrice = amazonPrice
                }
                if (amazonPrice > maxPrice) {
                    maxPrice = amazonPrice
                }
                totalPrice += amazonPrice
                totalPrices++
            }
        }

        minPrice = (minPrice < Integer.MAX_VALUE ? String.format("\$%.2f", minPrice) : "")
        maxPrice = (maxPrice > -1 ? String.format("\$%.2f", maxPrice) : "")
        def avgPrice = (totalPrices > 0 ? String.format("\$%.2f", totalPrice / totalPrices) : "")
        return ["url":amazonURL, "low":minPrice, "median":avgPrice, "high":maxPrice]
    }

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

    def getEbayPrices(Card card, Expansion expansion) {
        def search = URLEncoder.encode( card.name + " mtg", "UTF-8" )
        def ebayURL = "http://www.ebay.com/sch/Individual-Cards-/38292/i.html?_sop=15&LH_BIN=1&_ipg=200&_dcat=38292&_nkw=" + search
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
                    if (qty2 > qty && (qty2 < 2010 || qty2 > 2016) && (qty2 < 1993 || qty2 > 1996)) {
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

    def getTcgplayerPrices(Card card,Expansion expansion) {
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
