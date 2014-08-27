package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class CardLegalityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [cardLegalityInstanceList: CardLegality.list(params), cardLegalityInstanceTotal: CardLegality.count()]
    }

    def create() {
        [cardLegalityInstance: new CardLegality(params)]
    }

    def save() {
        def cardLegalityInstance = new CardLegality(params)
        if (!cardLegalityInstance.save(flush: true)) {
            render(view: "create", model: [cardLegalityInstance: cardLegalityInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'cardLegality.label', default: 'CardLegality'), cardLegalityInstance.id])
        redirect(action: "show", id: cardLegalityInstance.id)
    }

    def show(Long id) {
        def cardLegalityInstance = CardLegality.get(id)
        if (!cardLegalityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardLegality.label', default: 'CardLegality'), id])
            redirect(action: "list")
            return
        }

        [cardLegalityInstance: cardLegalityInstance]
    }

    def edit(Long id) {
        def cardLegalityInstance = CardLegality.get(id)
        if (!cardLegalityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardLegality.label', default: 'CardLegality'), id])
            redirect(action: "list")
            return
        }

        [cardLegalityInstance: cardLegalityInstance]
    }

    def update(Long id, Long version) {
        def cardLegalityInstance = CardLegality.get(id)
        if (!cardLegalityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardLegality.label', default: 'CardLegality'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (cardLegalityInstance.version > version) {
                cardLegalityInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'cardLegality.label', default: 'CardLegality')] as Object[],
                          "Another user has updated this CardLegality while you were editing")
                render(view: "edit", model: [cardLegalityInstance: cardLegalityInstance])
                return
            }
        }

        cardLegalityInstance.properties = params

        if (!cardLegalityInstance.save(flush: true)) {
            render(view: "edit", model: [cardLegalityInstance: cardLegalityInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'cardLegality.label', default: 'CardLegality'), cardLegalityInstance.id])
        redirect(action: "show", id: cardLegalityInstance.id)
    }

    def delete(Long id) {
        def cardLegalityInstance = CardLegality.get(id)
        if (!cardLegalityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardLegality.label', default: 'CardLegality'), id])
            redirect(action: "list")
            return
        }

        try {
            cardLegalityInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'cardLegality.label', default: 'CardLegality'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cardLegality.label', default: 'CardLegality'), id])
            redirect(action: "show", id: id)
        }
    }
}
