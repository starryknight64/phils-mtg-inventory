package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class DeckController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }
	
	def addCard() {
		render params
		Deck deck = Deck.get( params.to )
		ExpansionCard expansionCard = ExpansionCard.get( params.expansionCardID )
		Integer add = params.add?.toInteger()
		
		def inventoryCards = []
		def addCardAs = params["as"]
		if( addCardAs == "Main Deck" ) {
			inventoryCards = deck.cards
		} else if( addCardAs == "Sideboard" ) {
			inventoryCards = deck.sideboardCards
		} else if( addCardAs == "Commander" ) {
			inventoryCards = [deck.commander]
		}
		
		if( inventoryCards?.expansionCard?.contains( expansionCard ) ) {
			InventoryCard inventoryCard = inventoryCards.find{ it.expansionCard == expansionCard }
			inventoryCard.amount += add
			inventoryCard.save()
			redirect( action:"show", id:deck.id )
		} else {
			if( addCardAs == "Main Deck" || addCardAs == "Sideboard" ) {
				InventoryCard inventoryCard = new InventoryCard( expansionCard: expansionCard, amount: add ).save()
				if( addCardAs == "Main Deck" ) {
					deck.addToCards( inventoryCard ).save()
				} else {
					deck.addToSideboardCards( inventoryCard ).save()
				}
				redirect( action:"show", id:deck.id )
				return
			} else if( addCardAs == "Commander" ) {
				deck.commander = expansionCard
				deck.save()
				redirect( action:"show", id:deck.id )
				return
			}
		}
		redirect(uri: request.getHeader('referer') )
	}

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [deckInstanceList: Deck.list(params), deckInstanceTotal: Deck.count()]
    }

    def create() {
        [deckInstance: new Deck(params)]
    }

    def save() {
        def deckInstance = new Deck(params)
        if (!deckInstance.save(flush: true)) {
            render(view: "create", model: [deckInstance: deckInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'deck.label', default: 'Deck'), deckInstance.id])
        redirect(action: "show", id: deckInstance.id)
    }

    def show(Long id) {
        def deckInstance = Deck.get(id)
        if (!deckInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'deck.label', default: 'Deck'), id])
            redirect(action: "list")
            return
        }

        [deckInstance: deckInstance]
    }

    def edit(Long id) {
        def deckInstance = Deck.get(id)
        if (!deckInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'deck.label', default: 'Deck'), id])
            redirect(action: "list")
            return
        }

        [deckInstance: deckInstance]
    }

    def update(Long id, Long version) {
        def deckInstance = Deck.get(id)
        if (!deckInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'deck.label', default: 'Deck'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (deckInstance.version > version) {
                deckInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'deck.label', default: 'Deck')] as Object[],
                          "Another user has updated this Deck while you were editing")
                render(view: "edit", model: [deckInstance: deckInstance])
                return
            }
        }

        deckInstance.properties = params

        if (!deckInstance.save(flush: true)) {
            render(view: "edit", model: [deckInstance: deckInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'deck.label', default: 'Deck'), deckInstance.id])
        redirect(action: "show", id: deckInstance.id)
    }

    def delete(Long id) {
        def deckInstance = Deck.get(id)
        if (!deckInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'deck.label', default: 'Deck'), id])
            redirect(action: "list")
            return
        }

        try {
            deckInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'deck.label', default: 'Deck'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'deck.label', default: 'Deck'), id])
            redirect(action: "show", id: id)
        }
    }
}
