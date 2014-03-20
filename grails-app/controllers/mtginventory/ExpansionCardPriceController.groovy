package mtginventory

import org.springframework.dao.DataIntegrityViolationException

class ExpansionCardPriceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [expansionCardPriceInstanceList: ExpansionCardPrice.list(params), expansionCardPriceInstanceTotal: ExpansionCardPrice.count()]
    }

    def create() {
        [expansionCardPriceInstance: new ExpansionCardPrice(params)]
    }

    def save() {
        def expansionCardPriceInstance = new ExpansionCardPrice(params)
        if (!expansionCardPriceInstance.save(flush: true)) {
            render(view: "create", model: [expansionCardPriceInstance: expansionCardPriceInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'expansionCardPrice.label', default: 'ExpansionCardPrice'), expansionCardPriceInstance.id])
        redirect(action: "show", id: expansionCardPriceInstance.id)
    }

    def show(Long id) {
        def expansionCardPriceInstance = ExpansionCardPrice.get(id)
        if (!expansionCardPriceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expansionCardPrice.label', default: 'ExpansionCardPrice'), id])
            redirect(action: "list")
            return
        }

        [expansionCardPriceInstance: expansionCardPriceInstance]
    }

    def edit(Long id) {
        def expansionCardPriceInstance = ExpansionCardPrice.get(id)
        if (!expansionCardPriceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expansionCardPrice.label', default: 'ExpansionCardPrice'), id])
            redirect(action: "list")
            return
        }

        [expansionCardPriceInstance: expansionCardPriceInstance]
    }

    def update(Long id, Long version) {
        def expansionCardPriceInstance = ExpansionCardPrice.get(id)
        if (!expansionCardPriceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expansionCardPrice.label', default: 'ExpansionCardPrice'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (expansionCardPriceInstance.version > version) {
                expansionCardPriceInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'expansionCardPrice.label', default: 'ExpansionCardPrice')] as Object[],
                          "Another user has updated this ExpansionCardPrice while you were editing")
                render(view: "edit", model: [expansionCardPriceInstance: expansionCardPriceInstance])
                return
            }
        }

        expansionCardPriceInstance.properties = params

        if (!expansionCardPriceInstance.save(flush: true)) {
            render(view: "edit", model: [expansionCardPriceInstance: expansionCardPriceInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'expansionCardPrice.label', default: 'ExpansionCardPrice'), expansionCardPriceInstance.id])
        redirect(action: "show", id: expansionCardPriceInstance.id)
    }

    def delete(Long id) {
        def expansionCardPriceInstance = ExpansionCardPrice.get(id)
        if (!expansionCardPriceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'expansionCardPrice.label', default: 'ExpansionCardPrice'), id])
            redirect(action: "list")
            return
        }

        try {
            expansionCardPriceInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'expansionCardPrice.label', default: 'ExpansionCardPrice'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'expansionCardPrice.label', default: 'ExpansionCardPrice'), id])
            redirect(action: "show", id: id)
        }
    }
}
