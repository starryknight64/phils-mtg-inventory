package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class ManaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [manaInstanceList: Mana.list(params), manaInstanceTotal: Mana.count()]
    }

    def create() {
        [manaInstance: new Mana(params)]
    }

    def save() {
        def manaInstance = new Mana(params)
        if (!manaInstance.save(flush: true)) {
            render(view: "create", model: [manaInstance: manaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'mana.label', default: 'Mana'), manaInstance.id])
        redirect(action: "show", id: manaInstance.id)
    }

    def show(Long id) {
        def manaInstance = Mana.get(id)
        if (!manaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'mana.label', default: 'Mana'), id])
            redirect(action: "list")
            return
        }

        [manaInstance: manaInstance]
    }

    def edit(Long id) {
        def manaInstance = Mana.get(id)
        if (!manaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'mana.label', default: 'Mana'), id])
            redirect(action: "list")
            return
        }

        [manaInstance: manaInstance]
    }

    def update(Long id, Long version) {
        def manaInstance = Mana.get(id)
        if (!manaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'mana.label', default: 'Mana'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (manaInstance.version > version) {
                manaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'mana.label', default: 'Mana')] as Object[],
                          "Another user has updated this Mana while you were editing")
                render(view: "edit", model: [manaInstance: manaInstance])
                return
            }
        }

        manaInstance.properties = params

        if (!manaInstance.save(flush: true)) {
            render(view: "edit", model: [manaInstance: manaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'mana.label', default: 'Mana'), manaInstance.id])
        redirect(action: "show", id: manaInstance.id)
    }

    def delete(Long id) {
        def manaInstance = Mana.get(id)
        if (!manaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'mana.label', default: 'Mana'), id])
            redirect(action: "list")
            return
        }

        try {
            manaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'mana.label', default: 'Mana'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'mana.label', default: 'Mana'), id])
            redirect(action: "show", id: id)
        }
    }
}
