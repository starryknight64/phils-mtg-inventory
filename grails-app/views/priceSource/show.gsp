
<%@ page import="mtginventory.PriceSource" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'priceSource.label', default: 'PriceSource')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-priceSource" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-priceSource" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list priceSource">
			
				<g:if test="${priceSourceInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="priceSource.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${priceSourceInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${priceSourceInstance?.website}">
				<li class="fieldcontain">
					<span id="website-label" class="property-label"><g:message code="priceSource.website.label" default="Website" /></span>
					
						<span class="property-value" aria-labelledby="website-label"><g:fieldValue bean="${priceSourceInstance}" field="website"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${priceSourceInstance?.rest}">
				<li class="fieldcontain">
					<span id="rest-label" class="property-label"><g:message code="priceSource.rest.label" default="Rest" /></span>
					
						<span class="property-value" aria-labelledby="rest-label"><g:fieldValue bean="${priceSourceInstance}" field="rest"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${priceSourceInstance?.id}" />
					<g:link class="edit" action="edit" id="${priceSourceInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
