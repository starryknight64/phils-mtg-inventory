<%@ page import="mtginventory.CardLegality" %>



<div class="fieldcontain ${hasErrors(bean: cardLegalityInstance, field: 'card', 'error')} required">
	<label for="card">
		<g:message code="cardLegality.card.label" default="Card" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="card" name="card.id" from="${mtginventory.Card.list()}" optionKey="id" required="" value="${cardLegalityInstance?.card?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cardLegalityInstance, field: 'cardLegalityType', 'error')} required">
	<label for="cardLegalityType">
		<g:message code="cardLegality.cardLegalityType.label" default="Card Legality Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="cardLegalityType" name="cardLegalityType.id" from="${mtginventory.CardLegalityType.list()}" optionKey="id" required="" value="${cardLegalityInstance?.cardLegalityType?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cardLegalityInstance, field: 'legality', 'error')} required">
	<label for="legality">
		<g:message code="cardLegality.legality.label" default="Legality" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="legality" name="legality.id" from="${mtginventory.Legality.list()}" optionKey="id" required="" value="${cardLegalityInstance?.legality?.id}" class="many-to-one"/>
</div>

