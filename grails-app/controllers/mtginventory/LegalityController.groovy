package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class LegalityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [legalityInstanceList: Legality.list(params), legalityInstanceTotal: Legality.count()]
    }

    def create() {
        [legalityInstance: new Legality(params)]
    }

    def save() {
        def legalityInstance = new Legality(params)
        if (!legalityInstance.save(flush: true)) {
            render(view: "create", model: [legalityInstance: legalityInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'legality.label', default: 'Legality'), legalityInstance.id])
        redirect(action: "show", id: legalityInstance.id)
    }

    def show(Long id) {
        def legalityInstance = Legality.get(id)
        if (!legalityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'legality.label', default: 'Legality'), id])
            redirect(action: "list")
            return
        }

        [legalityInstance: legalityInstance]
    }

    def edit(Long id) {
        def legalityInstance = Legality.get(id)
        if (!legalityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'legality.label', default: 'Legality'), id])
            redirect(action: "list")
            return
        }

        [legalityInstance: legalityInstance]
    }

    def update(Long id, Long version) {
        def legalityInstance = Legality.get(id)
        if (!legalityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'legality.label', default: 'Legality'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (legalityInstance.version > version) {
                legalityInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'legality.label', default: 'Legality')] as Object[],
                          "Another user has updated this Legality while you were editing")
                render(view: "edit", model: [legalityInstance: legalityInstance])
                return
            }
        }

        legalityInstance.properties = params

        if (!legalityInstance.save(flush: true)) {
            render(view: "edit", model: [legalityInstance: legalityInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'legality.label', default: 'Legality'), legalityInstance.id])
        redirect(action: "show", id: legalityInstance.id)
    }

    def delete(Long id) {
        def legalityInstance = Legality.get(id)
        if (!legalityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'legality.label', default: 'Legality'), id])
            redirect(action: "list")
            return
        }

        try {
            legalityInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'legality.label', default: 'Legality'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'legality.label', default: 'Legality'), id])
            redirect(action: "show", id: id)
        }
    }
}
