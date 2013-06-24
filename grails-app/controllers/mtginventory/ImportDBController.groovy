package mtginventory

import mtginventory.Card
import mtginventory.CardRarity
import mtginventory.CardType
import mtginventory.CardTypeType
import mtginventory.Expansion
import mtginventory.ExpansionCode
import mtginventory.Illustrator
import mtginventory.Inventory
import mtginventory.InventoryCard
import mtginventory.Mana
import mtginventory.ManaColor
import java.util.regex.Matcher
import java.util.regex.Pattern

class ImportDBController {

    Set<CardType> getTypes( String typeLine ) {
        Set types = []
        boolean isPlane = false
        boolean onlySubTypesLeft = false
        CardTypeType subTypeType = CardTypeType.findByName("subtype")
        String planeSubType = ""

        for( String it : typeLine.split( " " ) ) {
            if( it.matches( "[-—]" ) ) {
                onlySubTypesLeft = true
                continue
            }

            if( isPlane && onlySubTypesLeft ) {
                planeSubTypes += "$it ";
                continue
            }

            CardType type = CardType.findByName( it ) ?: new CardType(name: it, type: subTypeType).save(flush:true);
            if( type.name == "plane" ) {
                isPlane = true
            }
            types.add( type )
        }

        if( planeSubType != "" ) {
            planeSubType = planeSubType.trim()
            types.add( CardType.findByName( planeSubType ) ?: new CardType(name: planeSubType, type: subTypeType).save(flush:true) );
        }

        return types
    }

    List<Mana> getManaCost( String manaCost ) {
        Pattern pattern = ~/\{.+?\}/
        Matcher m = pattern.matcher( manaCost )
        List manas = []
        while( m.find() ) {
            Mana mana = Mana.findBySymbol( m.group() ) ?: new Mana(name:"?",symbol:m.group(),cmc:"?").addToColors(name:"colorless").save(flush:true)
            manas.add( mana )
        }
        return manas
    }

    def addArtistAlias(Integer id1, Integer id2) {
        Illustrator artist1 = Illustrator.get(id1)
        Illustrator artist2 = Illustrator.get(id2)
        artist1.addToAliases( artist2 )
        artist1.removeFromNotAliases( artist2 )
        artist1.save(flush:true)
        artist2.addToAliases( artist1 )
        artist2.removeFromNotAliases( artist1 )
        artist2.save(flush:true)
        render "done"
    }

    def addArtistNonAlias(Integer id1, Integer id2) {
        Illustrator artist1 = Illustrator.get(id1)
        Illustrator artist2 = Illustrator.get(id2)
        artist1.addToNotAliases( artist2 )
        artist1.removeFromAliases( artist2 )
        artist1.save(flush:true)
        artist2.addToNotAliases( artist1 )
        artist2.removeFromAliases( artist1 )
        artist2.save(flush:true)
        render "done"
    }

    def checkArtists() {
        def artists = Illustrator.list()
        for( int i=0; i<artists.size(); i++ ) {
            def artist = artists[i]
            String artistName = artist.name

            if( artistName.contains("&") || artistName.contains("//") || artistName.contains("and") ) {
                continue
            }

            if( artistName.contains(" ")) {
                def tempArray = artistName.tokenize( " " )
                artistName = tempArray.subList( 1, tempArray.size() ).join(" ")
                artistName = artistName.replace("Van Der","")

                Matcher m = Pattern.compile( "([\\\"“].+[\\\"”])" ).matcher( artistName )
                if( m.find() ) {
                    artistName = m.replaceAll("")
                }
            }
            artistName.tokenize( " " ).each {
                if( !it.endsWith(".") && it.size() != 1 ) {
                    for( int j=0; j<artists.size(); j++ ) {
                        def artist2 = artists[j]
                        def artistName2 = artist2.name
                        if( j != i && artistName2.contains( it ) && !artist?.aliases?.contains( artist2 ) && !artist?.notAliases?.contains( artist2 ) ) {
                            render "<a href='/MtGInventory/importDB/addArtistAlias?id1=${artist.id}&id2=${artist2.id}'>Add Alias</a>&nbsp;&nbsp;&nbsp;&nbsp;"
                            render "<a href='/MtGInventory/importDB/addArtistNonAlias?id1=${artist.id}&id2=${artist2.id}'>Add Not Alias</a>&nbsp;&nbsp;&nbsp;&nbsp;"
                            render "Possible duplicate: \"${artist.id} - ${artist.name}\" and \"${artist2.id} - ${artist2.name}\"<br>"
                        }
                    }
                }
            }
        }
        render "done!"
    }

    def index() {
        int i=0
        int col_name = i++
        int col_set = i++
        int col_set_code = i++
        int col_id = i++
        int col_type = i++
        int col_power = i++
        int col_toughness = i++
        int col_loyalty = i++
        int col_manacost = i++
        int col_converted_manacost = i++
        int col_artist = i++
        int col_flavor = i++
        int col_color = i++
        int col_generated_mana = i++
        int col_number = i++
        int col_rarity = i++
        int col_rating = i++
        int col_ruling = i++
        int col_variation = i++
        int col_ability = i++
        int col_pricing_low = i++
        int col_pricing_mid = i++
        int col_pricing_high = i++
        int col_watermark = i++
        int col_back_id = i++
        int col_number_int = i++
        int col_name_CN = i++
        int col_name_TW = i++
        int col_name_FR = i++
        int col_name_DE = i++
        int col_name_IT = i++
        int col_name_JP = i++
        int col_name_PT = i++
        int col_name_RU = i++
        int col_name_ES = i++
        int col_name_KO = i++
        int col_fully_handled = i++
        int col_custom_sort = i++
        int col_legality_Block = i++
        int col_legality_Standard = i++
        int col_legality_Extended = i++
        int col_legality_Modern = i++
        int col_legality_Legacy = i++
        int col_legality_Vintage = i++
        int col_legality_Highlander = i++
        int col_legality_French_Commander = i++
        int col_legality_Commander = i++
        int col_legality_Peasant = i++
        int col_legality_Pauper = i++

        //load and split the file
        InputStream inputFile = getClass().classLoader.getResourceAsStream("Gatherer Extracted DB.csv")
        String[] lines  = inputFile.text.split("\r")

        int startIndex = 2
        for( i = startIndex > 2 ? startIndex : 2; i<lines.length; i++ ) {
            String[] row = lines[i].split("\\|\\|");
            String cardName = row[col_name]
            String expansionName = row[col_set]
            String expansionCode = row[col_set_code]

            String typeLine = row[col_type]
            String power = row[col_power] ?: null
            String toughness = row[col_toughness] ?: null
            String loyalty = row[col_loyalty] ?: null
            String manacost = row[col_manacost]
            String illustrator = row[col_artist]
            String flavorText = row[col_flavor] ?: null
            String number = row[col_number]
            String rarity = row[col_rarity]
            String text = row[col_ability] ?: null
            String priceLow = row[col_pricing_low] ?: null
            String priceMid = row[col_pricing_mid] ?: null
            String priceHigh = row[col_pricing_high] ?: null
            String variation = row[col_variation] ?: null

            Expansion expansion = Expansion.findByName( expansionName ) ?: new Expansion(name: expansionName).save()
            ExpansionCode expCode = ExpansionCode.findByCode( expansionCode ) ?: new ExpansionCode( author: "gatherer", code: expansionCode, expansion: expansion ).save()
            List<Card> cards = []
            if( !cardName.contains(" // ")){
                Card card = Card.findByName( cardName ) ?: new Card(name:cardName,text:text,power:power,toughness:toughness,loyalty:loyalty).save()
                cards.add(card)
            }else{
                String[] cardNames = cardName.split(" // ")
                cardNames.each{
                    String fullCardName = "${cardName} ($it)"
                    Card card = Card.findByName( fullCardName ) ?: new Card(name:fullCardName,text:text,power:power,toughness:toughness,loyalty:loyalty).save()
                    cards.add(card)
                }
            }

            String letter = null
            String[] manaCostArray = manacost.split(" // ")
            cards.eachWithIndex{ card, index ->
                String actualCollectorNumber = collectorNumber
                String actualManaCost = manacost
                if( cardName.contains(" // " ) ) {
                    letter = letter?.next() ?: "a"
                    actualCollectorNumber = "$collectorNumber$letter"
                    actualManaCost = manaCostArray[index]
                }
                if( expansion.expansionCards?.find{ it.card == card && it.collectorNumber == actualCollectorNumber && it.variation == variation } == null ) {
                    Illustrator artist = Illustrator.findByName( illustrator ) ?: new Illustrator(name: illustrator).save()
                    List<Mana> totalManaCost = getManaCost( actualManaCost )
                    CardRarity cardRarity = CardRarity.findByAcronym( rarity ) ?: new CardRarity(name: "?", acronym: rarity).save()
                    Set<CardType> types = getTypes( typeLine )
                    ExpansionCard expansionCard = new ExpansionCard(card: card,expansion: expansion, flavorText:flavorText,rarity:cardRarity,illustrator:artist,collectorNumber: number,variation:variation,priceLow: priceLow,priceMid: priceMid, priceHigh: priceHigh)
                    totalManaCost.each {
                        expansionCard.addToManas( it )
                    }
                    types.each {
                        expansionCard.addToTypes( it )
                    }
                    expansionCard.save()
                }
            }
        }
        render "done!"
    }
}
