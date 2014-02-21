<%@ page import="mtginventory.ExpansionCard" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'expansionCard.label', default: 'ExpansionCard')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-expansionCard" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                </ul>
            </div>
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
