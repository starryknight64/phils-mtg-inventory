
<%@ page import="mtginventory.CardLegality" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'cardLegality.label', default: 'CardLegality')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-cardLegality" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-cardLegality" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list cardLegality">
			
				<g:if test="${cardLegalityInstance?.card}">
				<li class="fieldcontain">
					<span id="card-label" class="property-label"><g:message code="cardLegality.card.label" default="Card" /></span>
					
						<span class="property-value" aria-labelledby="card-label"><g:link controller="card" action="show" id="${cardLegalityInstance?.card?.id}">${cardLegalityInstance?.card?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${cardLegalityInstance?.cardLegalityType}">
				<li class="fieldcontain">
					<span id="cardLegalityType-label" class="property-label"><g:message code="cardLegality.cardLegalityType.label" default="Card Legality Type" /></span>
					
						<span class="property-value" aria-labelledby="cardLegalityType-label"><g:link controller="cardLegalityType" action="show" id="${cardLegalityInstance?.cardLegalityType?.id}">${cardLegalityInstance?.cardLegalityType?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${cardLegalityInstance?.legality}">
				<li class="fieldcontain">
					<span id="legality-label" class="property-label"><g:message code="cardLegality.legality.label" default="Legality" /></span>
					
						<span class="property-value" aria-labelledby="legality-label"><g:link controller="legality" action="show" id="${cardLegalityInstance?.legality?.id}">${cardLegalityInstance?.legality?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${cardLegalityInstance?.id}" />
					<g:link class="edit" action="edit" id="${cardLegalityInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
