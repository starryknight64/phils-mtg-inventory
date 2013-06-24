
<%@ page import="mtginventory.Expansion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'expansion.label', default: 'Expansion')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-expansion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-expansion" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list expansion">
			
				<g:if test="${expansionInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="expansion.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${expansionInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${expansionInstance?.symbol}">
				<li class="fieldcontain">
					<span id="symbol-label" class="property-label"><g:message code="expansion.symbol.label" default="Symbol" /></span>
					
						<span class="property-value" aria-labelledby="symbol-label"><g:fieldValue bean="${expansionInstance}" field="symbol"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${expansionInstance?.preReleaseDate}">
				<li class="fieldcontain">
					<span id="preReleaseDate-label" class="property-label"><g:message code="expansion.preReleaseDate.label" default="Pre Release Date" /></span>
					
						<span class="property-value" aria-labelledby="preReleaseDate-label"><g:formatDate date="${expansionInstance?.preReleaseDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${expansionInstance?.releaseDate}">
				<li class="fieldcontain">
					<span id="releaseDate-label" class="property-label"><g:message code="expansion.releaseDate.label" default="Release Date" /></span>
					
						<span class="property-value" aria-labelledby="releaseDate-label"><g:formatDate date="${expansionInstance?.releaseDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${expansionInstance?.totalCards}">
				<li class="fieldcontain">
					<span id="totalCards-label" class="property-label"><g:message code="expansion.totalCards.label" default="Total Cards" /></span>
					
						<span class="property-value" aria-labelledby="totalCards-label"><g:fieldValue bean="${expansionInstance}" field="totalCards"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${expansionInstance?.expansionCards}">
				<li class="fieldcontain">
					<span id="expansionCards-label" class="property-label"><g:message code="expansion.expansionCards.label" default="Expansion Cards" /></span>
					
						<g:each in="${expansionInstance.expansionCards}" var="e">
						<span class="property-value" aria-labelledby="expansionCards-label"><g:link controller="expansionCard" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${expansionInstance?.expansionCodes}">
				<li class="fieldcontain">
					<span id="expansionCodes-label" class="property-label"><g:message code="expansion.expansionCodes.label" default="Expansion Codes" /></span>
					
						<g:each in="${expansionInstance.expansionCodes}" var="e">
						<span class="property-value" aria-labelledby="expansionCodes-label"><g:link controller="expansionCode" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${expansionInstance?.id}" />
					<g:link class="edit" action="edit" id="${expansionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
