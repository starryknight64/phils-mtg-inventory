package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class MetaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [metaInstanceList: Meta.list(params), metaInstanceTotal: Meta.count()]
    }

    def create() {
        [metaInstance: new Meta(params)]
    }

    def save() {
        def metaInstance = new Meta(params)
        if (!metaInstance.save(flush: true)) {
            render(view: "create", model: [metaInstance: metaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'meta.label', default: 'Meta'), metaInstance.id])
        redirect(action: "show", id: metaInstance.id)
    }

    def show(Long id) {
        def metaInstance = Meta.get(id)
        if (!metaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'meta.label', default: 'Meta'), id])
            redirect(action: "list")
            return
        }

        [metaInstance: metaInstance]
    }

    def edit(Long id) {
        def metaInstance = Meta.get(id)
        if (!metaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'meta.label', default: 'Meta'), id])
            redirect(action: "list")
            return
        }

        [metaInstance: metaInstance]
    }

    def update(Long id, Long version) {
        def metaInstance = Meta.get(id)
        if (!metaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'meta.label', default: 'Meta'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (metaInstance.version > version) {
                metaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'meta.label', default: 'Meta')] as Object[],
                          "Another user has updated this Meta while you were editing")
                render(view: "edit", model: [metaInstance: metaInstance])
                return
            }
        }

        metaInstance.properties = params

        if (!metaInstance.save(flush: true)) {
            render(view: "edit", model: [metaInstance: metaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'meta.label', default: 'Meta'), metaInstance.id])
        redirect(action: "show", id: metaInstance.id)
    }

    def delete(Long id) {
        def metaInstance = Meta.get(id)
        if (!metaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'meta.label', default: 'Meta'), id])
            redirect(action: "list")
            return
        }

        try {
            metaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'meta.label', default: 'Meta'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'meta.label', default: 'Meta'), id])
            redirect(action: "show", id: id)
        }
    }
}
