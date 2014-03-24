<%@ page import="mtginventory.Deck" %>



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
	<g:textField name="description" value="${deckInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: deckInstance, field: 'commander', 'error')} ">
	<label for="commander">
		<g:message code="deck.commander.label" default="Commander" />
		
	</label>
	<g:select id="commander" name="commander.id" from="${deckInstance?.commander ? [deckInstance?.commander] : []}" optionKey="id" value="${deckInstance?.commander?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: deckInstance, field: 'cards', 'error')} ">
	<label for="cards">
		<g:message code="deck.cards.label" default="Cards" />
		
	</label>
	<g:select name="cards" from="${deckInstance?.cards ?: []}" multiple="multiple" optionKey="id" size="5" value="${deckInstance?.cards?.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: deckInstance, field: 'createdBy', 'error')} ">
	<label for="createdBy">
		<g:message code="deck.createdBy.label" default="Created By" />
		
	</label>
	<g:textField name="createdBy" value="${deckInstance?.createdBy}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: deckInstance, field: 'sideboardCards', 'error')} ">
	<label for="sideboardCards">
		<g:message code="deck.sideboardCards.label" default="Sideboard Cards" />
		
	</label>
	<g:select name="sideboardCards" from="${deckInstance?.sideboardCards ?: []}" multiple="multiple" optionKey="id" size="5" value="${deckInstance?.sideboardCards?.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: deckInstance, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="deck.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="type" name="type.id" from="${mtginventory.DeckType.list()}" optionKey="id" required="" value="${deckInstance?.type?.id}" class="many-to-one"/>
</div>

