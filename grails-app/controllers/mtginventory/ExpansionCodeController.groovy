package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class ExpansionCodeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [expansionCodeInstanceList: ExpansionCode.list(params), expansionCodeInstanceTotal: ExpansionCode.count()]
    }

    def create() {
        [expansionCodeInstance: new ExpansionCode(params)]
    }

    def save() {
        def expansionCodeInstance = new ExpansionCode(params)
        if (!expansionCodeInstance.save(flush: true)) {
            render(view: "create", model: [expansionCodeInstance: expansionCodeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'expansionCode.label', default: 'ExpansionCode'), expansionCodeInstance.id])
        redirect(action: "show", id: expansionCodeInstance.id)
    }

    def show(Long id) {
        def expansionCodeInstance = ExpansionCode.get(id)
        if (!expansionCodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expansionCode.label', default: 'ExpansionCode'), id])
            redirect(action: "list")
            return
        }

        [expansionCodeInstance: expansionCodeInstance]
    }

    def edit(Long id) {
        def expansionCodeInstance = ExpansionCode.get(id)
        if (!expansionCodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expansionCode.label', default: 'ExpansionCode'), id])
            redirect(action: "list")
            return
        }

        [expansionCodeInstance: expansionCodeInstance]
    }

    def update(Long id, Long version) {
        def expansionCodeInstance = ExpansionCode.get(id)
        if (!expansionCodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expansionCode.label', default: 'ExpansionCode'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (expansionCodeInstance.version > version) {
                expansionCodeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'expansionCode.label', default: 'ExpansionCode')] as Object[],
                          "Another user has updated this ExpansionCode while you were editing")
                render(view: "edit", model: [expansionCodeInstance: expansionCodeInstance])
                return
            }
        }

        expansionCodeInstance.properties = params

        if (!expansionCodeInstance.save(flush: true)) {
            render(view: "edit", model: [expansionCodeInstance: expansionCodeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'expansionCode.label', default: 'ExpansionCode'), expansionCodeInstance.id])
        redirect(action: "show", id: expansionCodeInstance.id)
    }

    def delete(Long id) {
        def expansionCodeInstance = ExpansionCode.get(id)
        if (!expansionCodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expansionCode.label', default: 'ExpansionCode'), id])
            redirect(action: "list")
            return
        }

        try {
            expansionCodeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'expansionCode.label', default: 'ExpansionCode'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'expansionCode.label', default: 'ExpansionCode'), id])
            redirect(action: "show", id: id)
        }
    }
}
