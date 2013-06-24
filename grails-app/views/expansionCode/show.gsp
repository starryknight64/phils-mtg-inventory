
<%@ page import="mtginventory.ExpansionCode" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'expansionCode.label', default: 'ExpansionCode')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-expansionCode" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-expansionCode" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list expansionCode">
			
				<g:if test="${expansionCodeInstance?.author}">
				<li class="fieldcontain">
					<span id="author-label" class="property-label"><g:message code="expansionCode.author.label" default="Author" /></span>
					
						<span class="property-value" aria-labelledby="author-label"><g:fieldValue bean="${expansionCodeInstance}" field="author"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${expansionCodeInstance?.code}">
				<li class="fieldcontain">
					<span id="code-label" class="property-label"><g:message code="expansionCode.code.label" default="Code" /></span>
					
						<span class="property-value" aria-labelledby="code-label"><g:fieldValue bean="${expansionCodeInstance}" field="code"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${expansionCodeInstance?.expansion}">
				<li class="fieldcontain">
					<span id="expansion-label" class="property-label"><g:message code="expansionCode.expansion.label" default="Expansion" /></span>
					
						<span class="property-value" aria-labelledby="expansion-label"><g:link controller="expansion" action="show" id="${expansionCodeInstance?.expansion?.id}">${expansionCodeInstance?.expansion?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${expansionCodeInstance?.id}" />
					<g:link class="edit" action="edit" id="${expansionCodeInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
