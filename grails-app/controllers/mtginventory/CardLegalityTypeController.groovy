package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class CardLegalityTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [cardLegalityTypeInstanceList: CardLegalityType.list(params), cardLegalityTypeInstanceTotal: CardLegalityType.count()]
    }

    def create() {
        [cardLegalityTypeInstance: new CardLegalityType(params)]
    }

    def save() {
        def cardLegalityTypeInstance = new CardLegalityType(params)
        if (!cardLegalityTypeInstance.save(flush: true)) {
            render(view: "create", model: [cardLegalityTypeInstance: cardLegalityTypeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'cardLegalityType.label', default: 'CardLegalityType'), cardLegalityTypeInstance.id])
        redirect(action: "show", id: cardLegalityTypeInstance.id)
    }

    def show(Long id) {
        def cardLegalityTypeInstance = CardLegalityType.get(id)
        if (!cardLegalityTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardLegalityType.label', default: 'CardLegalityType'), id])
            redirect(action: "list")
            return
        }

        [cardLegalityTypeInstance: cardLegalityTypeInstance]
    }

    def edit(Long id) {
        def cardLegalityTypeInstance = CardLegalityType.get(id)
        if (!cardLegalityTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardLegalityType.label', default: 'CardLegalityType'), id])
            redirect(action: "list")
            return
        }

        [cardLegalityTypeInstance: cardLegalityTypeInstance]
    }

    def update(Long id, Long version) {
        def cardLegalityTypeInstance = CardLegalityType.get(id)
        if (!cardLegalityTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardLegalityType.label', default: 'CardLegalityType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (cardLegalityTypeInstance.version > version) {
                cardLegalityTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'cardLegalityType.label', default: 'CardLegalityType')] as Object[],
                          "Another user has updated this CardLegalityType while you were editing")
                render(view: "edit", model: [cardLegalityTypeInstance: cardLegalityTypeInstance])
                return
            }
        }

        cardLegalityTypeInstance.properties = params

        if (!cardLegalityTypeInstance.save(flush: true)) {
            render(view: "edit", model: [cardLegalityTypeInstance: cardLegalityTypeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'cardLegalityType.label', default: 'CardLegalityType'), cardLegalityTypeInstance.id])
        redirect(action: "show", id: cardLegalityTypeInstance.id)
    }

    def delete(Long id) {
        def cardLegalityTypeInstance = CardLegalityType.get(id)
        if (!cardLegalityTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardLegalityType.label', default: 'CardLegalityType'), id])
            redirect(action: "list")
            return
        }

        try {
            cardLegalityTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'cardLegalityType.label', default: 'CardLegalityType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cardLegalityType.label', default: 'CardLegalityType'), id])
            redirect(action: "show", id: id)
        }
    }
}
