
<%@ page import="mtginventory.Expansion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'expansion.label', default: 'Expansion')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-expansion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-expansion" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'expansion.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="symbol" title="${message(code: 'expansion.symbol.label', default: 'Symbol')}" />
					
						<g:sortableColumn property="preReleaseDate" title="${message(code: 'expansion.preReleaseDate.label', default: 'Pre Release Date')}" />
					
						<g:sortableColumn property="releaseDate" title="${message(code: 'expansion.releaseDate.label', default: 'Release Date')}" />
					
						<g:sortableColumn property="totalCards" title="${message(code: 'expansion.totalCards.label', default: 'Total Cards')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${expansionInstanceList}" status="i" var="expansionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${expansionInstance.id}">${fieldValue(bean: expansionInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: expansionInstance, field: "symbol")}</td>
					
						<td><g:formatDate date="${expansionInstance.preReleaseDate}" /></td>
					
						<td><g:formatDate date="${expansionInstance.releaseDate}" /></td>
					
						<td>${fieldValue(bean: expansionInstance, field: "totalCards")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${expansionInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
