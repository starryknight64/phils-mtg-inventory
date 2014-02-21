/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mtginventory;

/**
 *
 * @author Phillip
 */
class PageableList extends ArrayList {

    public static class Page extends ArrayList {

        private int total

        public Page() {
            super()
            this.total = 0
        }

        public Page(Collection collection, int totalCount){
            super(collection)
            this.total = totalCount
        }

        public int getTotalCount() {
            return this.total
        }
    }

    public PageableList() {
        super()
    }

    public PageableList(Collection collection){
        super(collection)
    }

    /* Formulated from http://www.intelligrape.com/blog/2010/09/14/grails-pagination-on-filtered-list/ */
    public Collection getNextPage( Map params ) {
        int max = Math.min(params.max?.toInteger() ?: 100, 100)
        int offset = params.offset?.toInteger() ?: 0
        int total = this.size()
        Page page = []
        if( total > 0 ) {
            int upperLimit = findUpperIndex(offset, max, total)
            page = new Page(this.getAt(offset..upperLimit), total)
        }
        return page
    }

    private int findUpperIndex(int offset, int max, int total) {
        max = offset + max - 1
        if (max >= total) {
            max -= max - total + 1
        }
        return max
    }
}

