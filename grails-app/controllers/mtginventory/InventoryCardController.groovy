package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class InventoryCardController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [inventoryCardInstanceList: InventoryCard.list(params), inventoryCardInstanceTotal: InventoryCard.count()]
    }

    def create() {
        [inventoryCardInstance: new InventoryCard(params)]
    }

    def save() {
        def inventoryCardInstance = new InventoryCard(params)
        if (!inventoryCardInstance.save(flush: true)) {
            render(view: "create", model: [inventoryCardInstance: inventoryCardInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'inventoryCard.label', default: 'InventoryCard'), inventoryCardInstance.id])
        redirect(action: "show", id: inventoryCardInstance.id)
    }

    def show(Long id) {
        def inventoryCardInstance = InventoryCard.get(id)
        if (!inventoryCardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'inventoryCard.label', default: 'InventoryCard'), id])
            redirect(action: "list")
            return
        }
		redirect(controller:"ExpansionCard", action: "show", id: inventoryCardInstance.expansionCard.id)
    }

    def edit(Long id) {
        def inventoryCardInstance = InventoryCard.get(id)
        if (!inventoryCardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'inventoryCard.label', default: 'InventoryCard'), id])
            redirect(action: "list")
            return
        }

        [inventoryCardInstance: inventoryCardInstance]
    }

    def update(Long id, Long version) {
        def inventoryCardInstance = InventoryCard.get(id)
        if (!inventoryCardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'inventoryCard.label', default: 'InventoryCard'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (inventoryCardInstance.version > version) {
                inventoryCardInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'inventoryCard.label', default: 'InventoryCard')] as Object[],
                          "Another user has updated this InventoryCard while you were editing")
                render(view: "edit", model: [inventoryCardInstance: inventoryCardInstance])
                return
            }
        }

        inventoryCardInstance.properties = params

        if (!inventoryCardInstance.save(flush: true)) {
            render(view: "edit", model: [inventoryCardInstance: inventoryCardInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'inventoryCard.label', default: 'InventoryCard'), inventoryCardInstance.id])
        redirect(action: "show", id: inventoryCardInstance.id)
    }

    def delete(Long id) {
        def inventoryCardInstance = InventoryCard.get(id)
        if (!inventoryCardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'inventoryCard.label', default: 'InventoryCard'), id])
            redirect(action: "list")
            return
        }

        try {
            inventoryCardInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'inventoryCard.label', default: 'InventoryCard'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'inventoryCard.label', default: 'InventoryCard'), id])
            redirect(action: "show", id: id)
        }
    }
}
