
<%@ page import="mtginventory.Illustrator" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'illustrator.label', default: 'Illustrator')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-illustrator" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-illustrator" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list illustrator">
			
				<g:if test="${illustratorInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="illustrator.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${illustratorInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${illustratorInstance?.aliases}">
				<li class="fieldcontain">
					<span id="aliases-label" class="property-label"><g:message code="illustrator.aliases.label" default="Aliases" /></span>
					
						<g:each in="${illustratorInstance.aliases}" var="a">
						<span class="property-value" aria-labelledby="aliases-label"><g:link controller="illustrator" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${illustratorInstance?.notAliases}">
				<li class="fieldcontain">
					<span id="notAliases-label" class="property-label"><g:message code="illustrator.notAliases.label" default="Not Aliases" /></span>
					
						<g:each in="${illustratorInstance.notAliases}" var="n">
						<span class="property-value" aria-labelledby="notAliases-label"><g:link controller="illustrator" action="show" id="${n.id}">${n?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${illustratorInstance?.id}" />
					<g:link class="edit" action="edit" id="${illustratorInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
