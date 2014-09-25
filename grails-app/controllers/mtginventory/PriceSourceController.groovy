package mtginventory

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.*

class PriceSourceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }
	
	def all() {
		render PriceSource.list() as JSON
	}

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [priceSourceInstanceList: PriceSource.list(params), priceSourceInstanceTotal: PriceSource.count()]
    }

    def create() {
        [priceSourceInstance: new PriceSource(params)]
    }

    def save() {
        def priceSourceInstance = new PriceSource(params)
        if (!priceSourceInstance.save(flush: true)) {
            render(view: "create", model: [priceSourceInstance: priceSourceInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'priceSource.label', default: 'PriceSource'), priceSourceInstance.id])
        redirect(action: "show", id: priceSourceInstance.id)
    }

    def show(Long id) {
        def priceSourceInstance = PriceSource.get(id)
        if (!priceSourceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'priceSource.label', default: 'PriceSource'), id])
            redirect(action: "list")
            return
        }

        [priceSourceInstance: priceSourceInstance]
    }

    def edit(Long id) {
        def priceSourceInstance = PriceSource.get(id)
        if (!priceSourceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'priceSource.label', default: 'PriceSource'), id])
            redirect(action: "list")
            return
        }

        [priceSourceInstance: priceSourceInstance]
    }

    def update(Long id, Long version) {
        def priceSourceInstance = PriceSource.get(id)
        if (!priceSourceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'priceSource.label', default: 'PriceSource'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (priceSourceInstance.version > version) {
                priceSourceInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'priceSource.label', default: 'PriceSource')] as Object[],
                          "Another user has updated this PriceSource while you were editing")
                render(view: "edit", model: [priceSourceInstance: priceSourceInstance])
                return
            }
        }

        priceSourceInstance.properties = params

        if (!priceSourceInstance.save(flush: true)) {
            render(view: "edit", model: [priceSourceInstance: priceSourceInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'priceSource.label', default: 'PriceSource'), priceSourceInstance.id])
        redirect(action: "show", id: priceSourceInstance.id)
    }

    def delete(Long id) {
        def priceSourceInstance = PriceSource.get(id)
        if (!priceSourceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'priceSource.label', default: 'PriceSource'), id])
            redirect(action: "list")
            return
        }

        try {
            priceSourceInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'priceSource.label', default: 'PriceSource'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'priceSource.label', default: 'PriceSource'), id])
            redirect(action: "show", id: id)
        }
    }
}
