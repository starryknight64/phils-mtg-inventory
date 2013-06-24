
<%@ page import="mtginventory.CardRarity" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'cardRarity.label', default: 'CardRarity')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-cardRarity" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-cardRarity" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list cardRarity">
			
				<g:if test="${cardRarityInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="cardRarity.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${cardRarityInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cardRarityInstance?.acronym}">
				<li class="fieldcontain">
					<span id="acronym-label" class="property-label"><g:message code="cardRarity.acronym.label" default="Acronym" /></span>
					
						<span class="property-value" aria-labelledby="acronym-label"><g:fieldValue bean="${cardRarityInstance}" field="acronym"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${cardRarityInstance?.id}" />
					<g:link class="edit" action="edit" id="${cardRarityInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
