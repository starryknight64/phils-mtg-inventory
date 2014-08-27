package mtginventory

import java.util.regex.Matcher
import java.util.regex.Pattern
import groovy.json.JsonSlurper
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType

class ImportDBController {

	private Set<CardType> getTypes2( List superTypes, List types, List subTypes ) {
		Set allTypes = []
		CardTypeType typeType = CardTypeType.findByName("Supertype")
		superTypes?.each {
			CardType type = CardType.findByName( it )
			if( !type ) {
				type = new CardType(name: it, type: typeType).save(flush:true)
			}
			allTypes.add(type)
		}
		typeType = CardTypeType.findByName("Type")
		types?.each {
			CardType type = CardType.findByName( it )
			if( !type ) {
				type = new CardType(name: it, type: typeType).save(flush:true)
			}
			allTypes.add(type)
		}
		typeType = CardTypeType.findByName("Subtype")
		subTypes?.each {
			CardType type = CardType.findByName( it )
			if( !type ) {
				type = new CardType(name: it, type: typeType).save(flush:true)
			}
			allTypes.add(type)
		}
		return allTypes
	}

	List<Mana> getManaCost( String manaCost ) {
		Pattern pattern = ~/\{.+?\}/
		Matcher m = pattern.matcher( manaCost.replace( "/", "" ) )
		List manas = []
		while( m.find() ) {
			def manaSymbol = m.group()
			Mana mana = Mana.findBySymbol( manaSymbol )
			if( !mana ) {
				ManaColor colorless = ManaColor.findByName("colorless")
				mana = new Mana(name:"?",symbol:manaSymbol,cmc:"?")
				mana.addToColors(colorless)
				if (!mana.save()) {
					mana.errors.each { println it }
				}
			}
			manas.add( mana )
		}
		return manas
	}

	def addArtistAlias(Integer id1, Integer id2) {
		Illustrator artist1 = Illustrator.get(id1)
		Illustrator artist2 = Illustrator.get(id2)
		artist1.addToAliases( artist2 )
		artist1.removeFromNotAliases( artist2 )
		artist1.save()
		artist2.addToAliases( artist1 )
		artist2.removeFromNotAliases( artist1 )
		artist2.save()
		render "done"
	}

	def addArtistNonAlias(Integer id1, Integer id2) {
		Illustrator artist1 = Illustrator.get(id1)
		Illustrator artist2 = Illustrator.get(id2)
		artist1.addToNotAliases( artist2 )
		artist1.removeFromAliases( artist2 )
		artist1.save()
		artist2.addToNotAliases( artist1 )
		artist2.removeFromAliases( artist1 )
		artist2.save()
		render "done"
	}

	def checkArtists() {
		def status = ""
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
							status += out "<a href='/MtGInventory/importDB/addArtistAlias?id1=${artist.id}&id2=${artist2.id}'>Add Alias</a>&nbsp;&nbsp;&nbsp;&nbsp;"
							status += out "<a href='/MtGInventory/importDB/addArtistNonAlias?id1=${artist.id}&id2=${artist2.id}'>Add Not Alias</a>&nbsp;&nbsp;&nbsp;&nbsp;"
							status += out "Possible duplicate: \"${artist.id} - ${artist.name}\" and \"${artist2.id} - ${artist2.name}\"<br>"
						}
					}
				}
			}
		}
		status += out "done!"
		return status
	}

	def out( String text ) {
		System.out.println text
		try {
			render text
		} catch(Exception ex){
		}
		text
	}

	//    SET foreign_key_checks = 0;
	//    DROP TABLE card;
	//    DROP TABLE expansion_card;
	//    DROP TABLE expansion_card_card_type;
	//    DROP TABLE expansion_card_mana;

	def doImport( String updateThisExp, Boolean forceUpdate ) {
		def status = ""
		def mtgJSON = new HTTPBuilder("http://mtgjson.com/json/")

		status += out "Getting data from mtgjson.com ...<br>"
		def allSets = mtgJSON.get( path: "${updateThisExp ?: "AllSets"}-x.json", contentType: ContentType.JSON )
		if( updateThisExp ) {
			allSets = [allSets]
		}
		//        InputStream inputFile = getClass().classLoader.getResourceAsStream("10E-x.json")
		//        def oneSet = new JsonSlurper().parseText(inputFile.text)
		//                def allSets = new JsonSlurper().parseText(inputFile.text)
		//        def allSets = [oneSet]
		allSets.each{ set ->
			def expansionJSON = updateThisExp ? set : set.value
			def expansionName = expansionJSON.name
			def expansionCode = expansionJSON.code
			def expansionReleaseDate = Date.parse("yyyy-MM-dd", expansionJSON.releaseDate)
			def expansionCards = expansionJSON.cards
			status += out "$expansionName<br>"

			Expansion expansion = Expansion.findByCodeOrCode( expansionCode, expansionCode.startsWith("p") ? expansionCode.substring(1) : expansionCode )
			if( !expansion ){
				expansion = new Expansion(name: expansionName, code: expansionCode, releaseDate: expansionReleaseDate, totalCards: expansionCards?.size())
				if( !expansion.save() ) {
					expansion.errors.each { println it }
				}
			}

			if( !expansion ) {
				def i=1
			}
			def jsonHash = expansion.jsonHash()
			if( jsonHash.value != "${expansionJSON.hashCode()}" || forceUpdate ) {
				jsonHash.value = "${expansionJSON.hashCode()}"
				def hashMismatch = false
				if( jsonHash.isDirty() ) {
					jsonHash.save(flush:true)
					hashMismatch = true
				}

				if( forceUpdate || hashMismatch ) {
					if( expansion.name != expansionName ) {
						expansion.name = expansionName
					}
					if( expansion.releaseDate != expansionReleaseDate ) {
						expansion.releaseDate = expansionReleaseDate
					}
					if( expansion.totalCards != expansionCards?.size() ) {
						expansion.totalCards = expansionCards?.size()
					}
					if( expansion.code != expansionCode ) {
						expansion.code = expansionCode
					}
					if( expansion.isDirty() ) {
						expansion.save()
					}
				}

				def legalities = []

				for( Map expCard : expansionCards ) {
					def cardName = expCard.get( "name" )
					def manaCost = expCard.get( "manaCost" ) ?: ""
					def superTypes = expCard.get( "supertypes" )
					def types = expCard.get( "types" )
					def subTypes = expCard.get( "subtypes" )
					def rarity = expCard.get( "rarity" )
					def text = expCard.get( "text" )
					def flavorText = expCard.get( "flavor" )
					def artist = expCard.get( "artist" ).replace("“","\"").replace("”", "\"")
					def number = expCard.get( "number" )
					def power = expCard.get( "power" ) ?: expCard.get( "hand" )
					def toughness = expCard.get( "toughness" ) ?: expCard.get( "life" )
					def loyalty = expCard.get( "loyalty" )
					def imageName = expCard.get( "imageName" )
					def legalityMapping = expCard.get( "legalities" )
					status += out "    $cardName<br>"

					Card card = Card.findByNameAndPowerAndToughnessAndLoyalty( cardName, power, toughness, loyalty )
					if( !card ) {
						Card card2 = Card.findByName( cardName )
						if( card2 ) {
							status += out "        Found duplicate card of same name!<br>"
						}
						card = new Card(name:cardName,text:text,power:power,toughness:toughness,loyalty:loyalty)

						getManaCost( manaCost )?.each { card.addToManas( it ) }

						getTypes2( superTypes, types, subTypes )?.each { card.addToTypes( it ) }
						card.save()
					} else if( forceUpdate || hashMismatch ) {
						if( card.text != text ) {
							card.text = text
						}

						List<Mana> totalManaCost = getManaCost( manaCost )
						def updateManaCost = false
						for( Mana mana : totalManaCost ) {
							if( !card.manas?.contains( mana ) ) {
								updateManaCost = true
								break
							}
						}
						if( updateManaCost ) {
							card.manas?.clear()
							totalManaCost.each { card.addToManas( it ) }
						}

						def updateCardTypes = false
						Set<CardType> cardTypes = getTypes2( superTypes, types, subTypes )
						for( CardType type : cardTypes ) {
							if( !card.types?.contains( type ) ) {
								updateCardTypes = true
								break
							}
						}
						if( updateCardTypes ) {
							card.types?.clear()
							cardTypes.each { card.addToTypes( it ) }
						}

						if( card.isDirty() ) {
							card.save()
						}
					}

					legalityMapping.each{k,v->
						Legality legality = Legality.findByName(k) ?: new Legality(name: k)
						if(!legality.save()){
							legality.errors.each { println it }
						}
						CardLegalityType cardLegalityType = CardLegalityType.findByName(v) ?: new CardLegalityType(name: v)
						if( !cardLegalityType.save() ){
							cardLegalityType.errors.each { println it }
						}
						if( cardLegalityType.name != "Legal" ) {
							CardLegality cardLegality = CardLegality.findByCardAndLegality(card, legality) ?: new CardLegality(card:card, cardLegalityType:cardLegalityType, legality:legality)
							if(!cardLegality.save()){
								cardLegality.errors.each { println it }
							}
						}
						
						if( !legalities.contains( legality ) ) {
							legalities.add( legality )
						}
					}

					if( expansion.expansionCards?.find{ it.card == card && it.collectorNumber == number } == null ) {
						status += out "        Not found! Adding to DB...<br>"
						Illustrator illustrator = Illustrator.findByName( artist ) ?: new Illustrator(name: artist).save()
						if( !illustrator ) {
							status += out "        Illustrator '$artist' not found! Stopping...<br>"
							return
						}

						CardRarity cardRarity = CardRarity.findByName( rarity ) ?: new CardRarity(name: rarity, acronym: "?").save()
						if( !cardRarity ) {
							status += out "        Card Rarity '$rarity' not found! Stopping...<br>"
							return
						}

						ExpansionCard expansionCard = new ExpansionCard(card: card,expansion: expansion, flavorText:flavorText,rarity:cardRarity,illustrator:illustrator,collectorNumber: number,imageName:imageName)

						if (!expansionCard.save()) {
							expansionCard.errors.each { println it }
						}
					} else if( forceUpdate || hashMismatch ) {
						ExpansionCard expansionCard = ExpansionCard.findByCardAndExpansionAndCollectorNumber( card, expansion, number )
						if( !expansionCard ) {
							status += out "        Couldn't find ExpansionCard for '$card' in '$expansion' with collector number '$number'!<br>"
						} else {
							if( expansionCard.imageName != imageName ) {
								expansionCard.imageName = imageName
							}
							Illustrator illustrator = Illustrator.findByName( artist ) ?: new Illustrator(name: artist).save()
							if( expansionCard.illustrator != illustrator ) {
								expansionCard.illustrator = illustrator
							}
							CardRarity cardRarity = CardRarity.findByName( rarity ) ?: new CardRarity(name: rarity, acronym: "?").save()
							if( expansionCard.rarity != cardRarity ) {
								expansionCard.rarity = cardRarity
							}
							if( expansionCard.isDirty() ) {
								status += out "        updated!<br>"
								expansionCard.save()
							}
						}
					}
				}

				boolean updatedLegalities = false
				legalities.each {
					if( !expansion.legalities?.contains( it ) ) {
						expansion.addToLegalities( it )
						updatedLegalities = true
					}
				}
				def expansionLegalities = expansion.legalities?.toArray()
				for(int i=0; i<expansionLegalities?.size(); i++) {
					def legality = expansionLegalities[i]
					if( legality && !legalities.contains( legality ) ) {
						expansion.removeFromLegalities( legality )
						updatedLegalities = true
					}
				}
				if( updatedLegalities ) {
					if(!expansion.save()){
						expansion.errors.each { println it }
					}
				}
			} else {
				status += out "    Update unnecessary! Skipping...<br>"
			}
		}
		status += out "done"
		return status
	}

	def index() {
		render "<a href='/MtGInventory/importDB/checkArtists'>Check Artists</a><br>"
		render "<a href='/MtGInventory/importDB/doImport?forceUpdate=true'>Do Import</a><br>"
	}
}
