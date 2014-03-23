
<%@ page import="mtginventory.Expansion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'expansion.label', default: 'Expansion')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="list-expansion" class="content scaffold-list" role="main">
			<h1>Expansions (${Expansion.count()})</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'expansion.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="symbol" title="${message(code: 'expansion.symbol.label', default: 'Symbol')}" />
					
						<g:sortableColumn property="releaseDate" title="${message(code: 'expansion.releaseDate.label', default: 'Release Date')}" />
					
						<g:sortableColumn property="totalCards" title="${message(code: 'expansion.totalCards.label', default: 'Total Cards')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${expansionInstanceList}" status="i" var="expansionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link controller="expansionCard" action="list" params="[expansionID: expansionInstance.id]">${fieldValue(bean: expansionInstance, field: "name")}</g:link></td>
					
						<td><mtg:renderExpansionIcon expansion="${expansionInstance}" /></td>
					
						<td><g:formatDate date="${expansionInstance.releaseDate}" format="yyyy-MM-dd" /></td>
					
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
