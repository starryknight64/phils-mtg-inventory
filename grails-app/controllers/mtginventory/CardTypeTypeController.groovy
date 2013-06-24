package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class CardTypeTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [cardTypeTypeInstanceList: CardTypeType.list(params), cardTypeTypeInstanceTotal: CardTypeType.count()]
    }

    def create() {
        [cardTypeTypeInstance: new CardTypeType(params)]
    }

    def save() {
        def cardTypeTypeInstance = new CardTypeType(params)
        if (!cardTypeTypeInstance.save(flush: true)) {
            render(view: "create", model: [cardTypeTypeInstance: cardTypeTypeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'cardTypeType.label', default: 'CardTypeType'), cardTypeTypeInstance.id])
        redirect(action: "show", id: cardTypeTypeInstance.id)
    }

    def show(Long id) {
        def cardTypeTypeInstance = CardTypeType.get(id)
        if (!cardTypeTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardTypeType.label', default: 'CardTypeType'), id])
            redirect(action: "list")
            return
        }

        [cardTypeTypeInstance: cardTypeTypeInstance]
    }

    def edit(Long id) {
        def cardTypeTypeInstance = CardTypeType.get(id)
        if (!cardTypeTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardTypeType.label', default: 'CardTypeType'), id])
            redirect(action: "list")
            return
        }

        [cardTypeTypeInstance: cardTypeTypeInstance]
    }

    def update(Long id, Long version) {
        def cardTypeTypeInstance = CardTypeType.get(id)
        if (!cardTypeTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardTypeType.label', default: 'CardTypeType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (cardTypeTypeInstance.version > version) {
                cardTypeTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'cardTypeType.label', default: 'CardTypeType')] as Object[],
                          "Another user has updated this CardTypeType while you were editing")
                render(view: "edit", model: [cardTypeTypeInstance: cardTypeTypeInstance])
                return
            }
        }

        cardTypeTypeInstance.properties = params

        if (!cardTypeTypeInstance.save(flush: true)) {
            render(view: "edit", model: [cardTypeTypeInstance: cardTypeTypeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'cardTypeType.label', default: 'CardTypeType'), cardTypeTypeInstance.id])
        redirect(action: "show", id: cardTypeTypeInstance.id)
    }

    def delete(Long id) {
        def cardTypeTypeInstance = CardTypeType.get(id)
        if (!cardTypeTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cardTypeType.label', default: 'CardTypeType'), id])
            redirect(action: "list")
            return
        }

        try {
            cardTypeTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'cardTypeType.label', default: 'CardTypeType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cardTypeType.label', default: 'CardTypeType'), id])
            redirect(action: "show", id: id)
        }
    }
}
