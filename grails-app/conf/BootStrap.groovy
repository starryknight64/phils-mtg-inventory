import mtginventory.*
import groovyx.net.http.RESTClient
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType

class BootStrap {
    
    def init = { servletContext ->
        // Check whether the test data already exists.
        if (!Mana.count()) {
            ManaColor red = new ManaColor(name:"red").save(flush: true)
            ManaColor blue = new ManaColor(name:"blue").save(flush: true)
            ManaColor green = new ManaColor(name:"green").save(flush: true)
            ManaColor white = new ManaColor(name:"white").save(flush: true)
            ManaColor black = new ManaColor(name:"black").save(flush: true)
            ManaColor colorless = new ManaColor(name:"colorless").save(flush: true)

            new Mana(name:"Red",symbol:"{R}",cmc:"1").addToColors(red).save()
            new Mana(name:"Blue",symbol:"{U}",cmc:"1").addToColors(blue).save()
            new Mana(name:"Green",symbol:"{G}",cmc:"1").addToColors(green).save()
            new Mana(name:"White",symbol:"{W}",cmc:"1").addToColors(white).save()
            new Mana(name:"Black",symbol:"{B}",cmc:"1").addToColors(black).save()
            for( i in 0..20 ) {
                new Mana(name:"$i",symbol:"{$i}",cmc:i).addToColors(colorless).save()
            }
            new Mana(name:"X",symbol:"{X}",cmc:"0").addToColors(colorless).save()
            new Mana(name:"Red or Green",symbol:"{RG}",cmc:"1").addToColors(red).addToColors(green).save()
            new Mana(name:"Red or White",symbol:"{RW}",cmc:"1").addToColors(red).addToColors(white).save()
            new Mana(name:"Blue or Black",symbol:"{UB}",cmc:"1").addToColors(blue).addToColors(black).save()
            new Mana(name:"Blue or Red",symbol:"{UR}",cmc:"1").addToColors(blue).addToColors(red).save()
            new Mana(name:"Green or White",symbol:"{GW}",cmc:"1").addToColors(green).addToColors(white).save()
            new Mana(name:"Green or Blue",symbol:"{GU}",cmc:"1").addToColors(green).addToColors(blue).save()
            new Mana(name:"White or Blue",symbol:"{WU}",cmc:"1").addToColors(white).addToColors(blue).save()
            new Mana(name:"White or Black",symbol:"{WB}",cmc:"1").addToColors(white).addToColors(black).save()
            new Mana(name:"Black or Red",symbol:"{BR}",cmc:"1").addToColors(black).addToColors(red).save()
            new Mana(name:"Black or Green",symbol:"{BG}",cmc:"1").addToColors(black).addToColors(green).save()
            new Mana(name:"2 Colorless or Red",symbol:"{2R}",cmc:"2").addToColors(red).save()
            new Mana(name:"2 Colorless or Blue",symbol:"{2U}",cmc:"2").addToColors(blue).save()
            new Mana(name:"2 Colorless or Green",symbol:"{2G}",cmc:"2").addToColors(green).save()
            new Mana(name:"2 Colorless or White",symbol:"{2W}",cmc:"2").addToColors(white).save()
            new Mana(name:"2 Colorless or Black",symbol:"{2B}",cmc:"2").addToColors(black).save()
            new Mana(name:"Phyrexian Red",symbol:"{PR}",cmc:"1").addToColors(red).save()
            new Mana(name:"Phyrexian Blue",symbol:"{PU}",cmc:"1").addToColors(blue).save()
            new Mana(name:"Phyrexian Green",symbol:"{PG}",cmc:"1").addToColors(green).save()
            new Mana(name:"Phyrexian White",symbol:"{PW}",cmc:"1").addToColors(white).save()
            new Mana(name:"Phyrexian Black",symbol:"{PB}",cmc:"1").addToColors(black).save()
            new Mana(name:"Snow Mana",symbol:"{S}",cmc:"1").addToColors(colorless).save()
            new Mana(name:"1000000",symbol:"{1000000}",cmc:"1000000").addToColors(colorless).save()
            new Mana(name:"Half White",symbol:"{HW}",cmc:"0.5").addToColors(white).save()
        }
		
		if( !CardRarity.count() ) {
            new CardRarity(name: "Common", acronym: "C").save()
            new CardRarity(name: "Uncommon", acronym: "U").save()
            new CardRarity(name: "Rare", acronym: "R").save()
            new CardRarity(name: "Mythic Rare", acronym: "M").save()
		}
		
		if( !CardTypeType.count() ) {
            CardTypeType superType = new CardTypeType(name:"Supertype").save()
            CardTypeType normalType = new CardTypeType(name:"Type").save()
            CardTypeType subType = new CardTypeType(name:"Subtype").save()

            //from 205.4a:
            //A card can also have one or more supertypes. These are printed directly before its card types. The supertypes are basic, legendary, ongoing, snow, and world.
            new CardType(name:"Basic", type: superType).save()
            new CardType(name:"Legendary", type: superType).save()
            new CardType(name:"Ongoing", type: superType).save()
            new CardType(name:"Snow", type: superType).save()
            new CardType(name:"World", type: superType).save()

            //from 205.2a:
            //The card types are artifact, creature, enchantment, instant, land, plane, planeswalker, scheme, sorcery, tribal, and vanguard.
            new CardType(name:"Artifact", type: normalType).save()
            new CardType(name:"Creature", type: normalType).save()
            new CardType(name:"Enchantment", type: normalType).save()
            new CardType(name:"Instant", type: normalType).save()
            new CardType(name:"Land", type: normalType).save()
            new CardType(name:"Plane", type: normalType).save()
            new CardType(name:"Planeswalker", type: normalType).save()
            new CardType(name:"Sorcery", type: normalType).save()
            new CardType(name:"Tribal", type: normalType).save()
            new CardType(name:"Vanguard", type: normalType).save()
		}
		
		if( !PriceSource.count() ) {
			new PriceSource(name:"TCGPlayer",website:"http://www.tcgplayer.com",rest:"http://magictcgprices.appspot.com/api/tcgplayer/price.json").save()
			new PriceSource(name:"eBay",website:"http://www.ebay.com",rest:"http://magictcgprices.appspot.com/api/ebay/price.json").save()
			new PriceSource(name:"Channel Fireball",website:"http://www.channelfireball.com",rest:"http://magictcgprices.appspot.com/api/cfb/price.json").save()
        }
		
        def mtgJSON = new HTTPBuilder("http://mtgjson.com/json/")
        System.out.println "Checking version of DB from mtgjson.com ..."
        def mtgJSONVersion = mtgJSON.get( path: "version-full.json", contentType: ContentType.JSON )?.version
        def dbVersion = Meta.findByName("version") ?: new Meta(name:"version", value:"").save()
        if( dbVersion.value != mtgJSONVersion ) {
            System.out.println "Version of DB was $mtgJSONVersion, which differs from internal DB of $dbVersion.value!"
            dbVersion.value = mtgJSONVersion
            dbVersion.save()
            //Thread.start {
			System.out.println "http://localhost:8080/MtGInventory/ImportDB/doImport"
            //new ImportDBController().doImport( null, false )
            //}
        } else {
            System.out.println "Versions match!"
        }
    }
    def destroy = {
    }
}
