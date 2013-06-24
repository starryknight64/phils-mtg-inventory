package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class ManaColorController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [manaColorInstanceList: ManaColor.list(params), manaColorInstanceTotal: ManaColor.count()]
    }

    def create() {
        [manaColorInstance: new ManaColor(params)]
    }

    def save() {
        def manaColorInstance = new ManaColor(params)
        if (!manaColorInstance.save(flush: true)) {
            render(view: "create", model: [manaColorInstance: manaColorInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'manaColor.label', default: 'ManaColor'), manaColorInstance.id])
        redirect(action: "show", id: manaColorInstance.id)
    }

    def show(Long id) {
        def manaColorInstance = ManaColor.get(id)
        if (!manaColorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'manaColor.label', default: 'ManaColor'), id])
            redirect(action: "list")
            return
        }

        [manaColorInstance: manaColorInstance]
    }

    def edit(Long id) {
        def manaColorInstance = ManaColor.get(id)
        if (!manaColorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'manaColor.label', default: 'ManaColor'), id])
            redirect(action: "list")
            return
        }

        [manaColorInstance: manaColorInstance]
    }

    def update(Long id, Long version) {
        def manaColorInstance = ManaColor.get(id)
        if (!manaColorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'manaColor.label', default: 'ManaColor'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (manaColorInstance.version > version) {
                manaColorInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'manaColor.label', default: 'ManaColor')] as Object[],
                          "Another user has updated this ManaColor while you were editing")
                render(view: "edit", model: [manaColorInstance: manaColorInstance])
                return
            }
        }

        manaColorInstance.properties = params

        if (!manaColorInstance.save(flush: true)) {
            render(view: "edit", model: [manaColorInstance: manaColorInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'manaColor.label', default: 'ManaColor'), manaColorInstance.id])
        redirect(action: "show", id: manaColorInstance.id)
    }

    def delete(Long id) {
        def manaColorInstance = ManaColor.get(id)
        if (!manaColorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'manaColor.label', default: 'ManaColor'), id])
            redirect(action: "list")
            return
        }

        try {
            manaColorInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'manaColor.label', default: 'ManaColor'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'manaColor.label', default: 'ManaColor'), id])
            redirect(action: "show", id: id)
        }
    }
}
