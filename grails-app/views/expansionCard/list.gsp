<%@ page import="mtginventory.ExpansionCard" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'expansionCard.label', default: 'ExpansionCard')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
            <div id="list-expansionCard" class="content scaffold-list" role="main">
                <h1>Cards${listMessage} (${expansionCardInstanceTotal})</h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table>
                <thead>
                    <tr>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <g:each in="${expansionCardInstanceList}" status="i" var="expansionCardInstance">
                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <mtg:renderResult result="${expansionCardInstance}" />
                    </tr>
                </g:each>
                </tbody>
            </table>
            <div class="pagination">
                <g:paginate total="${expansionCardInstanceTotal}" params="${params}" />
            </div>
        </div>
    </body>
</html>
