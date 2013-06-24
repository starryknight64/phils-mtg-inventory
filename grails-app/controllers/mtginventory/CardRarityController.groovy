package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class CardRarityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [cardRarityInstanceList: CardRarity.list(params), cardRarityInstanceTotal: CardRarity.count()]
    }

    def create() {
        [cardRarityInstance: new CardRarity(params)]
    }

    def save() {
        def cardRarityInstance = new CardRarity(params)
        if (!cardRarityInstance.save(flush: true)) {
            render(view: "create", model: [cardRarityInstance: cardRarityInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'cardRarity.label', default: 'CardRarity'), cardRarityInstance.id])
        redirect(action: "show", id: cardRarityInstance.id)
    }

    def show(Long id) {
        def cardRarityInstance = CardRarity.get(id)
        if (!cardRarityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardRarity.label', default: 'CardRarity'), id])
            redirect(action: "list")
            return
        }

        [cardRarityInstance: cardRarityInstance]
    }

    def edit(Long id) {
        def cardRarityInstance = CardRarity.get(id)
        if (!cardRarityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardRarity.label', default: 'CardRarity'), id])
            redirect(action: "list")
            return
        }

        [cardRarityInstance: cardRarityInstance]
    }

    def update(Long id, Long version) {
        def cardRarityInstance = CardRarity.get(id)
        if (!cardRarityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardRarity.label', default: 'CardRarity'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (cardRarityInstance.version > version) {
                cardRarityInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'cardRarity.label', default: 'CardRarity')] as Object[],
                          "Another user has updated this CardRarity while you were editing")
                render(view: "edit", model: [cardRarityInstance: cardRarityInstance])
                return
            }
        }

        cardRarityInstance.properties = params

        if (!cardRarityInstance.save(flush: true)) {
            render(view: "edit", model: [cardRarityInstance: cardRarityInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'cardRarity.label', default: 'CardRarity'), cardRarityInstance.id])
        redirect(action: "show", id: cardRarityInstance.id)
    }

    def delete(Long id) {
        def cardRarityInstance = CardRarity.get(id)
        if (!cardRarityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardRarity.label', default: 'CardRarity'), id])
            redirect(action: "list")
            return
        }

        try {
            cardRarityInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'cardRarity.label', default: 'CardRarity'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cardRarity.label', default: 'CardRarity'), id])
            redirect(action: "show", id: id)
        }
    }
}
