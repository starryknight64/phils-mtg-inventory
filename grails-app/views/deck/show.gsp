
<%@ page import="mtginventory.Deck" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'deck.label', default: 'Deck')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-deck" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-deck" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list deck">
			
				<g:if test="${deckInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="deck.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${deckInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${deckInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="deck.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${deckInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${deckInstance?.commander}">
				<li class="fieldcontain">
					<span id="commander-label" class="property-label"><g:message code="deck.commander.label" default="Commander" /></span>
					
						<span class="property-value" aria-labelledby="commander-label"><g:link controller="expansionCard" action="show" id="${deckInstance?.commander?.id}">${deckInstance?.commander?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${deckInstance?.cards}">
				<li class="fieldcontain">
					<span id="cards-label" class="property-label"><g:message code="deck.cards.label" default="Cards" /></span>
					
						<g:each in="${deckInstance.cards}" var="c">
						<span class="property-value" aria-labelledby="cards-label"><g:link controller="inventoryCard" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${deckInstance?.createdBy}">
				<li class="fieldcontain">
					<span id="createdBy-label" class="property-label"><g:message code="deck.createdBy.label" default="Created By" /></span>
					
						<span class="property-value" aria-labelledby="createdBy-label"><g:fieldValue bean="${deckInstance}" field="createdBy"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${deckInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="deck.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${deckInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${deckInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="deck.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${deckInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${deckInstance?.sideboardCards}">
				<li class="fieldcontain">
					<span id="sideboardCards-label" class="property-label"><g:message code="deck.sideboardCards.label" default="Sideboard Cards" /></span>
					
						<g:each in="${deckInstance.sideboardCards}" var="s">
						<span class="property-value" aria-labelledby="sideboardCards-label"><g:link controller="inventoryCard" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${deckInstance?.type}">
				<li class="fieldcontain">
					<span id="type-label" class="property-label"><g:message code="deck.type.label" default="Type" /></span>
					
						<span class="property-value" aria-labelledby="type-label"><g:link controller="deckType" action="show" id="${deckInstance?.type?.id}">${deckInstance?.type?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${deckInstance?.id}" />
					<g:link class="edit" action="edit" id="${deckInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
