
<%@ page import="mtginventory.Deck" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'deck.label', default: 'Deck')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-deck" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-deck" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'deck.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'deck.description.label', default: 'Description')}" />
					
						<th><g:message code="deck.commander.label" default="Commander" /></th>
					
						<g:sortableColumn property="createdBy" title="${message(code: 'deck.createdBy.label', default: 'Created By')}" />
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'deck.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'deck.lastUpdated.label', default: 'Last Updated')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${deckInstanceList}" status="i" var="deckInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${deckInstance.id}">${fieldValue(bean: deckInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: deckInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: deckInstance, field: "commander")}</td>
					
						<td>${fieldValue(bean: deckInstance, field: "createdBy")}</td>
					
						<td><g:formatDate date="${deckInstance.dateCreated}" /></td>
					
						<td><g:formatDate date="${deckInstance.lastUpdated}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${deckInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
