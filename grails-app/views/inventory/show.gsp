
<%@ page import="mtginventory.Inventory" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'inventory.label', default: 'Inventory')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-inventory" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-inventory" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list inventory">
			
				<g:if test="${inventoryInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="inventory.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${inventoryInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${inventoryInstance?.cards}">
				<li class="fieldcontain">
					<span id="inventoryCards-label" class="property-label"><g:message code="inventory.cards.label" default="Inventory Cards" /></span>
					
						<g:each in="${inventoryInstance.cards}" var="i">
						<span class="property-value" aria-labelledby="cards-label"><g:link controller="inventoryCard" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${inventoryInstance?.id}" />
					<g:link class="edit" action="edit" id="${inventoryInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
