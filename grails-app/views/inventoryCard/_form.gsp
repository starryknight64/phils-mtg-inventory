<%@ page import="mtginventory.InventoryCard" %>



<div class="fieldcontain ${hasErrors(bean: inventoryCardInstance, field: 'expansionCard', 'error')} required">
	<label for="expansionCard">
		<g:message code="inventoryCard.expansionCard.label" default="Expansion Card" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="expansionCard" name="expansionCard.id" from="${mtginventory.ExpansionCard.list()}" optionKey="id" required="" value="${inventoryCardInstance?.expansionCard?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: inventoryCardInstance, field: 'amount', 'error')} required">
	<label for="amount">
		<g:message code="inventoryCard.amount.label" default="Amount" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="amount" type="number" value="${inventoryCardInstance.amount}" required=""/>
</div>

