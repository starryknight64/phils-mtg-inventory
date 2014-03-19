package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class ExpansionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = max ?: 1000
        [expansionInstanceList: Expansion.list(params), expansionInstanceTotal: Expansion.count()]
    }

    def create() {
        [expansionInstance: new Expansion(params)]
    }

    def save() {
        def expansionInstance = new Expansion(params)
        if (!expansionInstance.save(flush: true)) {
            render(view: "create", model: [expansionInstance: expansionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'expansion.label', default: 'Expansion'), expansionInstance.id])
        redirect(action: "show", id: expansionInstance.id)
    }

    def show(Long id) {
        def expansionInstance = Expansion.get(id)
        if (!expansionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expansion.label', default: 'Expansion'), id])
            redirect(action: "list")
            return
        }

        [expansionInstance: expansionInstance]
    }

    def edit(Long id) {
        def expansionInstance = Expansion.get(id)
        if (!expansionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expansion.label', default: 'Expansion'), id])
            redirect(action: "list")
            return
        }

        [expansionInstance: expansionInstance]
    }

    def update(Long id, Long version) {
        def expansionInstance = Expansion.get(id)
        if (!expansionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expansion.label', default: 'Expansion'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (expansionInstance.version > version) {
                expansionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'expansion.label', default: 'Expansion')] as Object[],
                          "Another user has updated this Expansion while you were editing")
                render(view: "edit", model: [expansionInstance: expansionInstance])
                return
            }
        }

        expansionInstance.properties = params

        if (!expansionInstance.save(flush: true)) {
            render(view: "edit", model: [expansionInstance: expansionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'expansion.label', default: 'Expansion'), expansionInstance.id])
        redirect(action: "show", id: expansionInstance.id)
    }

    def delete(Long id) {
        def expansionInstance = Expansion.get(id)
        if (!expansionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expansion.label', default: 'Expansion'), id])
            redirect(action: "list")
            return
        }

        try {
            expansionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'expansion.label', default: 'Expansion'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'expansion.label', default: 'Expansion'), id])
            redirect(action: "show", id: id)
        }
    }
}
