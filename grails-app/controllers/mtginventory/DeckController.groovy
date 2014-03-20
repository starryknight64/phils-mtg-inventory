package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class DeckController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
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
