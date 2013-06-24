package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class IllustratorController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [illustratorInstanceList: Illustrator.list(params), illustratorInstanceTotal: Illustrator.count()]
    }

    def create() {
        [illustratorInstance: new Illustrator(params)]
    }

    def save() {
        def illustratorInstance = new Illustrator(params)
        if (!illustratorInstance.save(flush: true)) {
            render(view: "create", model: [illustratorInstance: illustratorInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'illustrator.label', default: 'Illustrator'), illustratorInstance.id])
        redirect(action: "show", id: illustratorInstance.id)
    }

    def show(Long id) {
        def illustratorInstance = Illustrator.get(id)
        if (!illustratorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'illustrator.label', default: 'Illustrator'), id])
            redirect(action: "list")
            return
        }

        [illustratorInstance: illustratorInstance]
    }

    def edit(Long id) {
        def illustratorInstance = Illustrator.get(id)
        if (!illustratorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'illustrator.label', default: 'Illustrator'), id])
            redirect(action: "list")
            return
        }

        [illustratorInstance: illustratorInstance]
    }

    def update(Long id, Long version) {
        def illustratorInstance = Illustrator.get(id)
        if (!illustratorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'illustrator.label', default: 'Illustrator'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (illustratorInstance.version > version) {
                illustratorInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'illustrator.label', default: 'Illustrator')] as Object[],
                          "Another user has updated this Illustrator while you were editing")
                render(view: "edit", model: [illustratorInstance: illustratorInstance])
                return
            }
        }

        illustratorInstance.properties = params

        if (!illustratorInstance.save(flush: true)) {
            render(view: "edit", model: [illustratorInstance: illustratorInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'illustrator.label', default: 'Illustrator'), illustratorInstance.id])
        redirect(action: "show", id: illustratorInstance.id)
    }

    def delete(Long id) {
        def illustratorInstance = Illustrator.get(id)
        if (!illustratorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'illustrator.label', default: 'Illustrator'), id])
            redirect(action: "list")
            return
        }

        try {
            illustratorInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'illustrator.label', default: 'Illustrator'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'illustrator.label', default: 'Illustrator'), id])
            redirect(action: "show", id: id)
        }
    }
}
