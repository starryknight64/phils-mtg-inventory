
<%@ page import="mtginventory.ExpansionCardPrice" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'expansionCardPrice.label', default: 'ExpansionCardPrice')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-expansionCardPrice" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-expansionCardPrice" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list expansionCardPrice">
			
				<g:if test="${expansionCardPriceInstance?.low}">
				<li class="fieldcontain">
					<span id="low-label" class="property-label"><g:message code="expansionCardPrice.low.label" default="Low" /></span>
					
						<span class="property-value" aria-labelledby="low-label"><g:fieldValue bean="${expansionCardPriceInstance}" field="low"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${expansionCardPriceInstance?.median}">
				<li class="fieldcontain">
					<span id="median-label" class="property-label"><g:message code="expansionCardPrice.median.label" default="Median" /></span>
					
						<span class="property-value" aria-labelledby="median-label"><g:fieldValue bean="${expansionCardPriceInstance}" field="median"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${expansionCardPriceInstance?.high}">
				<li class="fieldcontain">
					<span id="high-label" class="property-label"><g:message code="expansionCardPrice.high.label" default="High" /></span>
					
						<span class="property-value" aria-labelledby="high-label"><g:fieldValue bean="${expansionCardPriceInstance}" field="high"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${expansionCardPriceInstance?.expansionCard}">
				<li class="fieldcontain">
					<span id="expansionCard-label" class="property-label"><g:message code="expansionCardPrice.expansionCard.label" default="Expansion Card" /></span>
					
						<span class="property-value" aria-labelledby="expansionCard-label"><g:link controller="expansionCard" action="show" id="${expansionCardPriceInstance?.expansionCard?.id}">${expansionCardPriceInstance?.expansionCard?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${expansionCardPriceInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="expansionCardPrice.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${expansionCardPriceInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${expansionCardPriceInstance?.source}">
				<li class="fieldcontain">
					<span id="source-label" class="property-label"><g:message code="expansionCardPrice.source.label" default="Source" /></span>
					
						<span class="property-value" aria-labelledby="source-label"><g:link controller="priceSource" action="show" id="${expansionCardPriceInstance?.source?.id}">${expansionCardPriceInstance?.source?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${expansionCardPriceInstance?.id}" />
					<g:link class="edit" action="edit" id="${expansionCardPriceInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
