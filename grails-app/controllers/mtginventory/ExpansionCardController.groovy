package mtginventory

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.*
import java.net.URLEncoder
import groovy.json.JsonSlurper
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType

class ExpansionCardController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

	def prices(Long id){
		def json = [:]
		def expansionCard = ExpansionCard.get(id)
		if( expansionCard ) {
			PriceSource.list().each {priceSource ->
				ExpansionCardPrice price = ExpansionCardPrice.findByExpansionCardAndSource( expansionCard, priceSource )
				if( !price || price?.lastUpdated?.plus(1) < new Date() ) {
					def pricingREST = new HTTPBuilder( priceSource.rest )
					def q = [cardname:"${expansionCard.card.name}",setname:"${expansionCard.expansion.name}"]
					def pricing = []
					try {
						pricing = pricingREST.get( query: q, contentType: ContentType.JSON )
					} catch(Exception ex){
					}
					def low = pricing.size() > 2 ? pricing.get(0) : null
					def median = pricing.size() > 1 ? pricing.get(1) : pricing.size() == 1 ? pricing.get(0) : null
					def high = pricing.size() > 2 ? pricing.get(2) : null
					if( price ) {
						price.source = priceSource
						price.expansionCard = expansionCard
						price.low = low
						price.median = median
						price.high = high
						if( price.isDirty() ) {
							price.save()
						}
					} else {
						price = new ExpansionCardPrice( source: priceSource, expansionCard:expansionCard, low:low,median:median,high:high ).save()
					}
				}
				json.put(priceSource.name,["low":price?.low ?: "","median":price?.median ?: "","high":price?.high ?: ""])
			}
		}
		render json as JSON
	}

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def list = []
        def size = 0
        def listMessage = ""
        if( params.expansionID ) {
            def expansion = Expansion.read(params.expansionID)
            list = ExpansionCard.findAllByExpansion( expansion, params )
            size = ExpansionCard.countByExpansion( expansion, params )
            listMessage = " in <b>${expansion.name}</b>"
        } else if( params.illustratorID ) {
            def illustrators = Illustrator.getAll(params.illustratorID.split(",").collect{it as int})
            list = new PageableList( ExpansionCard.findAllByIllustratorInList( illustrators, [sort:"card.name"] )?.unique{a,b->a.card.id <=> b.card.id} ).getNextPage( params )
            size = list.getTotalCount()
            listMessage = " illustrated by <b>${illustrators[0].name}</b>"
        } else {
            list = ExpansionCard.list(params)
            size = ExpansionCard.count()
        }
        [expansionCardInstanceList: list, expansionCardInstanceTotal: size, listMessage: listMessage, params: params]
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
