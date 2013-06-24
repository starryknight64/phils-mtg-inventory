
<%@ page import="mtginventory.Mana" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'mana.label', default: 'Mana')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-mana" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-mana" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list mana">
			
				<g:if test="${manaInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="mana.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${manaInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${manaInstance?.symbol}">
				<li class="fieldcontain">
					<span id="symbol-label" class="property-label"><g:message code="mana.symbol.label" default="Symbol" /></span>
					
						<span class="property-value" aria-labelledby="symbol-label"><g:fieldValue bean="${manaInstance}" field="symbol"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${manaInstance?.cmc}">
				<li class="fieldcontain">
					<span id="cmc-label" class="property-label"><g:message code="mana.cmc.label" default="Cmc" /></span>
					
						<span class="property-value" aria-labelledby="cmc-label"><g:fieldValue bean="${manaInstance}" field="cmc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${manaInstance?.colors}">
				<li class="fieldcontain">
					<span id="colors-label" class="property-label"><g:message code="mana.colors.label" default="Colors" /></span>
					
						<g:each in="${manaInstance.colors}" var="c">
						<span class="property-value" aria-labelledby="colors-label"><g:link controller="manaColor" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${manaInstance?.id}" />
					<g:link class="edit" action="edit" id="${manaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
