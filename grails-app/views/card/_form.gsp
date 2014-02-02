<%@ page import="mtginventory.Card" %>



<div class="fieldcontain ${hasErrors(bean: cardInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="card.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${cardInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cardInstance, field: 'power', 'error')} ">
	<label for="power">
		<g:message code="card.power.label" default="Power" />
		
	</label>
	<g:textField name="power" value="${cardInstance?.power}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cardInstance, field: 'toughness', 'error')} ">
	<label for="toughness">
		<g:message code="card.toughness.label" default="Toughness" />
		
	</label>
	<g:textField name="toughness" value="${cardInstance?.toughness}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cardInstance, field: 'loyalty', 'error')} ">
	<label for="loyalty">
		<g:message code="card.loyalty.label" default="Loyalty" />
		
	</label>
	<g:textField name="loyalty" value="${cardInstance?.loyalty}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cardInstance, field: 'expansionCards', 'error')} ">
	<label for="expansionCards">
		<g:message code="card.expansionCards.label" default="Expansion Cards" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${cardInstance?.expansionCards?}" var="e">
    <li><g:link controller="expansionCard" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="expansionCard" action="create" params="['card.id': cardInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'expansionCard.label', default: 'ExpansionCard')])}</g:link>
</li>
</ul>

</div>

