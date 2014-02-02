package mtginventory

import org.compass.core.engine.SearchEngineQueryParseException

/**
 * Basic web interface for Grails Searchable Plugin
 *
 * @author Maurice Nicholson
 */
class SearchController {
    def searchableService

    /**
     * Index page with search form and results
     */
    def index = {
        if (!params.q?.trim()) {
            return [:]
        }
        try {
            params.suggestQuery = true
            def searchResult = searchableService.search(params.q, params)
            if( !searchResult.results || searchResult.suggestedQuery ) {
                def suggestedResult = searchableService.search(searchResult.suggestedQuery, params)
                if( !searchResult.results ) {
                    searchResult = suggestedResult
                } else {
                    searchResult.results.addAll(suggestedResult.results)
                }
            }
            return [searchResult: searchResult]
        } catch (SearchEngineQueryParseException ex) {
            return [parseException: true]
        }
    }

    /**
     * Perform a bulk index of every searchable object in the database
     */
    def indexAll = {
        Thread.start {
            searchableService.index()
        }
        render("bulk index started in a background thread")
    }

    /**
     * Perform a bulk index of every searchable object in the database
     */
    def unindexAll = {
        searchableService.unindex()
        render("unindexAll done")
    }
}
