package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class CardTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [cardTypeInstanceList: CardType.list(params), cardTypeInstanceTotal: CardType.count()]
    }

    def create() {
        [cardTypeInstance: new CardType(params)]
    }

    def save() {
        def cardTypeInstance = new CardType(params)
        if (!cardTypeInstance.save(flush: true)) {
            render(view: "create", model: [cardTypeInstance: cardTypeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'cardType.label', default: 'CardType'), cardTypeInstance.id])
        redirect(action: "show", id: cardTypeInstance.id)
    }

    def show(Long id) {
        def cardTypeInstance = CardType.get(id)
        if (!cardTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardType.label', default: 'CardType'), id])
            redirect(action: "list")
            return
        }

        [cardTypeInstance: cardTypeInstance]
    }

    def edit(Long id) {
        def cardTypeInstance = CardType.get(id)
        if (!cardTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardType.label', default: 'CardType'), id])
            redirect(action: "list")
            return
        }

        [cardTypeInstance: cardTypeInstance]
    }

    def update(Long id, Long version) {
        def cardTypeInstance = CardType.get(id)
        if (!cardTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardType.label', default: 'CardType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (cardTypeInstance.version > version) {
                cardTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'cardType.label', default: 'CardType')] as Object[],
                          "Another user has updated this CardType while you were editing")
                render(view: "edit", model: [cardTypeInstance: cardTypeInstance])
                return
            }
        }

        cardTypeInstance.properties = params

        if (!cardTypeInstance.save(flush: true)) {
            render(view: "edit", model: [cardTypeInstance: cardTypeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'cardType.label', default: 'CardType'), cardTypeInstance.id])
        redirect(action: "show", id: cardTypeInstance.id)
    }

    def delete(Long id) {
        def cardTypeInstance = CardType.get(id)
        if (!cardTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardType.label', default: 'CardType'), id])
            redirect(action: "list")
            return
        }

        try {
            cardTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'cardType.label', default: 'CardType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cardType.label', default: 'CardType'), id])
            redirect(action: "show", id: id)
        }
    }
}
