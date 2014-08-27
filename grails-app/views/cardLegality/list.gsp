
<%@ page import="mtginventory.CardLegality" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'cardLegality.label', default: 'CardLegality')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-cardLegality" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-cardLegality" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="cardLegality.card.label" default="Card" /></th>
					
						<th><g:message code="cardLegality.cardLegalityType.label" default="Card Legality Type" /></th>
					
						<th><g:message code="cardLegality.legality.label" default="Legality" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${cardLegalityInstanceList}" status="i" var="cardLegalityInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${cardLegalityInstance.id}">${fieldValue(bean: cardLegalityInstance, field: "card")}</g:link></td>
					
						<td>${fieldValue(bean: cardLegalityInstance, field: "cardLegalityType")}</td>
					
						<td>${fieldValue(bean: cardLegalityInstance, field: "legality")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${cardLegalityInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
