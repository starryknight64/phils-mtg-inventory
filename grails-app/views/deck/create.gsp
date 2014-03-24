<%@ page import="mtginventory.Deck" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'deck.label', default: 'Deck')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-deck" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="create-deck" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${deckInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${deckInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" >
				<fieldset class="form">
					<div class="fieldcontain ${hasErrors(bean: deckInstance, field: 'name', 'error')} ">
					    <label for="name">
					        <g:message code="deck.name.label" default="Name" />
					        
					    </label>
					    <g:textField name="name" value="${deckInstance?.name}"/>
					</div>
					
					<div class="fieldcontain ${hasErrors(bean: deckInstance, field: 'description', 'error')} ">
					    <label for="description">
					        <g:message code="deck.description.label" default="Description" />
					        
					    </label>
					    <textarea name="description" value="${deckInstance?.description}"></textarea>
					</div>
					
					<div class="fieldcontain ${hasErrors(bean: deckInstance, field: 'createdBy', 'error')} ">
					    <label for="createdBy">
					        <g:message code="deck.createdBy.label" default="Created By" />
					        
					    </label>
					    <g:textField name="createdBy" value="${deckInstance?.createdBy}"/>
					</div>
					
					<div class="fieldcontain ${hasErrors(bean: deckInstance, field: 'type', 'error')} required">
					    <label for="type">
					        <g:message code="deck.type.label" default="Type" />
					        <span class="required-indicator">*</span>
					    </label>
					    <g:select id="type" name="type.id" from="${mtginventory.DeckType.list()}" optionKey="id" required="" value="${deckInstance?.type?.id}" class="many-to-one"/>
					</div>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
