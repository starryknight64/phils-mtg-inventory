package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class DeckTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [deckTypeInstanceList: DeckType.list(params), deckTypeInstanceTotal: DeckType.count()]
    }

    def create() {
        [deckTypeInstance: new DeckType(params)]
    }

    def save() {
        def deckTypeInstance = new DeckType(params)
        if (!deckTypeInstance.save(flush: true)) {
            render(view: "create", model: [deckTypeInstance: deckTypeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'deckType.label', default: 'DeckType'), deckTypeInstance.id])
        redirect(action: "show", id: deckTypeInstance.id)
    }

    def show(Long id) {
        def deckTypeInstance = DeckType.get(id)
        if (!deckTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'deckType.label', default: 'DeckType'), id])
            redirect(action: "list")
            return
        }

        [deckTypeInstance: deckTypeInstance]
    }

    def edit(Long id) {
        def deckTypeInstance = DeckType.get(id)
        if (!deckTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'deckType.label', default: 'DeckType'), id])
            redirect(action: "list")
            return
        }

        [deckTypeInstance: deckTypeInstance]
    }

    def update(Long id, Long version) {
        def deckTypeInstance = DeckType.get(id)
        if (!deckTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'deckType.label', default: 'DeckType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (deckTypeInstance.version > version) {
                deckTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'deckType.label', default: 'DeckType')] as Object[],
                          "Another user has updated this DeckType while you were editing")
                render(view: "edit", model: [deckTypeInstance: deckTypeInstance])
                return
            }
        }

        deckTypeInstance.properties = params

        if (!deckTypeInstance.save(flush: true)) {
            render(view: "edit", model: [deckTypeInstance: deckTypeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'deckType.label', default: 'DeckType'), deckTypeInstance.id])
        redirect(action: "show", id: deckTypeInstance.id)
    }

    def delete(Long id) {
        def deckTypeInstance = DeckType.get(id)
        if (!deckTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'deckType.label', default: 'DeckType'), id])
            redirect(action: "list")
            return
        }

        try {
            deckTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'deckType.label', default: 'DeckType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'deckType.label', default: 'DeckType'), id])
            redirect(action: "show", id: id)
        }
    }
}
