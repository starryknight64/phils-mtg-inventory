<%@ page import="mtginventory.CardRarity" %>



<div class="fieldcontain ${hasErrors(bean: cardRarityInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="cardRarity.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${cardRarityInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cardRarityInstance, field: 'acronym', 'error')} ">
	<label for="acronym">
		<g:message code="cardRarity.acronym.label" default="Acronym" />
		
	</label>
	<g:textField name="acronym" value="${cardRarityInstance?.acronym}"/>
</div>

