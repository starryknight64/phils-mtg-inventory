package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class InventoryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }
	
	def addCard() {
		render params
		Inventory inventory = Inventory.get( params.to )
		ExpansionCard expansionCard = ExpansionCard.get( params.expansionCardID )
		Integer add = params.add?.toInteger()
		
		if( inventory?.cards?.expansionCard?.contains( expansionCard ) ) {
			InventoryCard inventoryCard = inventory.cards.find{ it.expansionCard == expansionCard }
			inventoryCard.amount += add
			inventoryCard.save()
			redirect( action:"show", id:inventory.id )
			return
		} else if( expansionCard && add > 0 ) {
			InventoryCard inventoryCard = new InventoryCard( expansionCard: expansionCard, amount: add ).save()
			inventory.addToCards( inventoryCard ).save()
			redirect( action:"show", id:inventory.id )
			return
		}
		redirect(uri: request.getHeader('referer') )
	}

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [inventoryInstanceList: Inventory.list(params), inventoryInstanceTotal: Inventory.count()]
    }

    def create() {
        [inventoryInstance: new Inventory(params)]
    }

    def save() {
        def inventoryInstance = new Inventory(params)
        if (!inventoryInstance.save(flush: true)) {
            render(view: "create", model: [inventoryInstance: inventoryInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'inventory.label', default: 'Inventory'), inventoryInstance.id])
        redirect(action: "show", id: inventoryInstance.id)
    }

    def show(Long id) {
        def inventoryInstance = Inventory.get(id)
        if (!inventoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'inventory.label', default: 'Inventory'), id])
            redirect(action: "list")
            return
        }

        [inventoryInstance: inventoryInstance]
    }

    def edit(Long id) {
        def inventoryInstance = Inventory.get(id)
        if (!inventoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'inventory.label', default: 'Inventory'), id])
            redirect(action: "list")
            return
        }

        [inventoryInstance: inventoryInstance]
    }

    def update(Long id, Long version) {
        def inventoryInstance = Inventory.get(id)
        if (!inventoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'inventory.label', default: 'Inventory'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (inventoryInstance.version > version) {
                inventoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'inventory.label', default: 'Inventory')] as Object[],
                          "Another user has updated this Inventory while you were editing")
                render(view: "edit", model: [inventoryInstance: inventoryInstance])
                return
            }
        }

        inventoryInstance.properties = params

        if (!inventoryInstance.save(flush: true)) {
            render(view: "edit", model: [inventoryInstance: inventoryInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'inventory.label', default: 'Inventory'), inventoryInstance.id])
        redirect(action: "show", id: inventoryInstance.id)
    }

    def delete(Long id) {
        def inventoryInstance = Inventory.get(id)
        if (!inventoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'inventory.label', default: 'Inventory'), id])
            redirect(action: "list")
            return
        }

        try {
			inventoryInstance.cards*.delete()
            inventoryInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'inventory.label', default: 'Inventory'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'inventory.label', default: 'Inventory'), id])
            redirect(action: "show", id: id)
        }
    }
}
