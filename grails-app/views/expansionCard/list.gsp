
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
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="expansionCard.card.label" default="Card" /></th>
					
						<g:sortableColumn property="flavorText" title="${message(code: 'expansionCard.flavorText.label', default: 'Flavor Text')}" />
					
						<g:sortableColumn property="priceLow" title="${message(code: 'expansionCard.priceLow.label', default: 'Price Low')}" />
					
						<g:sortableColumn property="priceMid" title="${message(code: 'expansionCard.priceMid.label', default: 'Price Mid')}" />
					
						<g:sortableColumn property="priceHigh" title="${message(code: 'expansionCard.priceHigh.label', default: 'Price High')}" />
					
						<g:sortableColumn property="variation" title="${message(code: 'expansionCard.variation.label', default: 'Variation')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${expansionCardInstanceList}" status="i" var="expansionCardInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${expansionCardInstance.id}">${fieldValue(bean: expansionCardInstance, field: "card")}</g:link></td>
					
						<td>${fieldValue(bean: expansionCardInstance, field: "flavorText")}</td>
					
						<td>${fieldValue(bean: expansionCardInstance, field: "priceLow")}</td>
					
						<td>${fieldValue(bean: expansionCardInstance, field: "priceMid")}</td>
					
						<td>${fieldValue(bean: expansionCardInstance, field: "priceHigh")}</td>
					
						<td>${fieldValue(bean: expansionCardInstance, field: "variation")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${expansionCardInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
