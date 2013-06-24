
<%@ page import="mtginventory.Mana" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'mana.label', default: 'Mana')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-mana" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-mana" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'mana.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="symbol" title="${message(code: 'mana.symbol.label', default: 'Symbol')}" />
					
						<g:sortableColumn property="cmc" title="${message(code: 'mana.cmc.label', default: 'Cmc')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${manaInstanceList}" status="i" var="manaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${manaInstance.id}">${fieldValue(bean: manaInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: manaInstance, field: "symbol")}</td>
					
						<td>${fieldValue(bean: manaInstance, field: "cmc")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${manaInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
