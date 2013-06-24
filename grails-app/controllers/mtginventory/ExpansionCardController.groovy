package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class ExpansionCardController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [expansionCardInstanceList: ExpansionCard.list(params), expansionCardInstanceTotal: ExpansionCard.count()]
    }

    def create() {
        [expansionCardInstance: new ExpansionCard(params)]
    }

    def save() {
        def expansionCardInstance = new ExpansionCard(params)
        if (!expansionCardInstance.save(flush: true)) {
            render(view: "create", model: [expansionCardInstance: expansionCardInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'expansionCard.label', default: 'ExpansionCard'), expansionCardInstance.id])
        redirect(action: "show", id: expansionCardInstance.id)
    }

    def show(Long id) {
        def expansionCardInstance = ExpansionCard.get(id)
        if (!expansionCardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expansionCard.label', default: 'ExpansionCard'), id])
            redirect(action: "list")
            return
        }

        [expansionCardInstance: expansionCardInstance]
    }

    def edit(Long id) {
        def expansionCardInstance = ExpansionCard.get(id)
        if (!expansionCardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expansionCard.label', default: 'ExpansionCard'), id])
            redirect(action: "list")
            return
        }

        [expansionCardInstance: expansionCardInstance]
    }

    def update(Long id, Long version) {
        def expansionCardInstance = ExpansionCard.get(id)
        if (!expansionCardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expansionCard.label', default: 'ExpansionCard'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (expansionCardInstance.version > version) {
                expansionCardInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'expansionCard.label', default: 'ExpansionCard')] as Object[],
                          "Another user has updated this ExpansionCard while you were editing")
                render(view: "edit", model: [expansionCardInstance: expansionCardInstance])
                return
            }
        }

        expansionCardInstance.properties = params

        if (!expansionCardInstance.save(flush: true)) {
            render(view: "edit", model: [expansionCardInstance: expansionCardInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'expansionCard.label', default: 'ExpansionCard'), expansionCardInstance.id])
        redirect(action: "show", id: expansionCardInstance.id)
    }

    def delete(Long id) {
        def expansionCardInstance = ExpansionCard.get(id)
        if (!expansionCardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expansionCard.label', default: 'ExpansionCard'), id])
            redirect(action: "list")
            return
        }

        try {
            expansionCardInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'expansionCard.label', default: 'ExpansionCard'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'expansionCard.label', default: 'ExpansionCard'), id])
            redirect(action: "show", id: id)
        }
    }
}
