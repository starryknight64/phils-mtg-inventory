
<%@ page import="mtginventory.ExpansionCardPrice" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'expansionCardPrice.label', default: 'ExpansionCardPrice')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-expansionCardPrice" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-expansionCardPrice" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					<th>
					    
					    <g:message code="expansionCardPrice.expansionCard.label" default="Expansion Card" /></th>

						<g:sortableColumn property="low" title="${message(code: 'expansionCardPrice.low.label', default: 'Low')}" />
					
						<g:sortableColumn property="median" title="${message(code: 'expansionCardPrice.median.label', default: 'Median')}" />
					
						<g:sortableColumn property="high" title="${message(code: 'expansionCardPrice.high.label', default: 'High')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'expansionCardPrice.lastUpdated.label', default: 'Last Updated')}" />
					
						<th><g:message code="expansionCardPrice.source.label" default="Source" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${expansionCardPriceInstanceList}" status="i" var="expansionCardPriceInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
                        <td><g:link action="show" id="${expansionCardPriceInstance.id}">${fieldValue(bean: expansionCardPriceInstance, field: "expansionCard")}</g:link></td>

						<td>${fieldValue(bean: expansionCardPriceInstance, field: "low")}</td>
					
						<td>${fieldValue(bean: expansionCardPriceInstance, field: "median")}</td>
					
						<td>${fieldValue(bean: expansionCardPriceInstance, field: "high")}</td>
					
					
						<td><g:formatDate date="${expansionCardPriceInstance.lastUpdated}" /></td>
					
						<td>${fieldValue(bean: expansionCardPriceInstance, field: "source")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${expansionCardPriceInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
