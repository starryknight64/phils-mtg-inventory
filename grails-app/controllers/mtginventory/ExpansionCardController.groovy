package mtginventory

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.*
import java.net.URLEncoder
import groovy.json.JsonSlurper
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType

class ExpansionCardController {
	
	def pricesService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

	def prices(Long id,String source){
		def json = [:]
		def expansionCard = ExpansionCard.get(id)
		if( expansionCard ) {
			def ps = PriceSource.findByName(source)
			if( ps ) {
				def pricing = null
				ExpansionCardPrice price = ExpansionCardPrice.findByExpansionCardAndSource( expansionCard, ps )
				if( !price || price?.lastUpdated?.plus(1) < new Date() ) {
					def fnName = ps.name.toLowerCase().replaceAll( /\b[a-z]/, { it.toUpperCase() }).replace(" ", "")
					pricing = pricesService."get${fnName}Prices"(expansionCard.card,expansionCard.expansion)
					def low = pricing?.low ?: null
					def median = pricing?.median ?: null
					def high = pricing?.high ?: null
					if( price ) {
						price.low = low
						price.median = median
						price.high = high
						if( price.isDirty() ) {
							price.save()
						}
					} else {
						price = new ExpansionCardPrice( source: ps, expansionCard:expansionCard, low:low,median:median,high:high,url:pricing.url ).save()
					}
				}
				json = ["sourceID":ps.id,"sourceName":ps.name,"url": price?.url ?: priceSource.website, "low":price?.low ?: "","median":price?.median ?: "","high":price?.high ?: ""]
			} else {
				for( def priceSource : PriceSource.list() ) {
					ExpansionCardPrice price = ExpansionCardPrice.findByExpansionCardAndSource( expansionCard, priceSource )
					def pricing = null
					if( !price || price?.lastUpdated?.plus(1) < new Date() ) {
						if( priceSource.name == "TCGPlayer" ) {
							pricing = pricesService.getTCGPlayerPrices(expansionCard.card,expansionCard.expansion)
						} else if( priceSource.name == "eBay" ) {
							pricing = pricesService.getEbayPrices(expansionCard.card      )
						} else if( priceSource.name == "Channel Fireball" ) {
							pricing = pricesService.getChannelFireballPrices(expansionCard.card,expansionCard.expansion)
						}
						def low = pricing?.low ?: null
						def median = pricing?.median ?: null
						def high = pricing?.high ?: null
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
					json.put(priceSource.name,["url": pricing?.url ?: priceSource.website, "low":price?.low ?: "","median":price?.median ?: "","high":price?.high ?: ""])
				}
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
