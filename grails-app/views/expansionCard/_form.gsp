<%@ page import="mtginventory.ExpansionCard" %>



<div class="fieldcontain ${hasErrors(bean: expansionCardInstance, field: 'card', 'error')} required">
	<label for="card">
		<g:message code="expansionCard.card.label" default="Card" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="card" name="card.id" from="${mtginventory.Card.list()}" optionKey="id" required="" value="${expansionCardInstance?.card?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCardInstance, field: 'flavorText', 'error')} ">
	<label for="flavorText">
		<g:message code="expansionCard.flavorText.label" default="Flavor Text" />
		
	</label>
	<g:textField name="flavorText" value="${expansionCardInstance?.flavorText}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCardInstance, field: 'priceLow', 'error')} ">
	<label for="priceLow">
		<g:message code="expansionCard.priceLow.label" default="Price Low" />
		
	</label>
	<g:textField name="priceLow" value="${expansionCardInstance?.priceLow}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCardInstance, field: 'priceMid', 'error')} ">
	<label for="priceMid">
		<g:message code="expansionCard.priceMid.label" default="Price Mid" />
		
	</label>
	<g:textField name="priceMid" value="${expansionCardInstance?.priceMid}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCardInstance, field: 'priceHigh', 'error')} ">
	<label for="priceHigh">
		<g:message code="expansionCard.priceHigh.label" default="Price High" />
		
	</label>
	<g:textField name="priceHigh" value="${expansionCardInstance?.priceHigh}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCardInstance, field: 'variation', 'error')} ">
	<label for="variation">
		<g:message code="expansionCard.variation.label" default="Variation" />
		
	</label>
	<g:textField name="variation" value="${expansionCardInstance?.variation}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCardInstance, field: 'collectorNumber', 'error')} ">
	<label for="collectorNumber">
		<g:message code="expansionCard.collectorNumber.label" default="Collector Number" />
		
	</label>
	<g:textField name="collectorNumber" value="${expansionCardInstance?.collectorNumber}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCardInstance, field: 'expansion', 'error')} required">
	<label for="expansion">
		<g:message code="expansionCard.expansion.label" default="Expansion" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="expansion" name="expansion.id" from="${mtginventory.Expansion.list()}" optionKey="id" required="" value="${expansionCardInstance?.expansion?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCardInstance, field: 'illustrator', 'error')} required">
	<label for="illustrator">
		<g:message code="expansionCard.illustrator.label" default="Illustrator" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="illustrator" name="illustrator.id" from="${mtginventory.Illustrator.list()}" optionKey="id" required="" value="${expansionCardInstance?.illustrator?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCardInstance, field: 'manas', 'error')} ">
	<label for="manas">
		<g:message code="expansionCard.manas.label" default="Manas" />
		
	</label>
	<g:select name="manas" from="${mtginventory.Mana.list()}" multiple="multiple" optionKey="id" size="5" value="${expansionCardInstance?.manas*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCardInstance, field: 'rarity', 'error')} required">
	<label for="rarity">
		<g:message code="expansionCard.rarity.label" default="Rarity" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="rarity" name="rarity.id" from="${mtginventory.CardRarity.list()}" optionKey="id" required="" value="${expansionCardInstance?.rarity?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCardInstance, field: 'types', 'error')} ">
	<label for="types">
		<g:message code="expansionCard.types.label" default="Types" />
		
	</label>
	<g:select name="types" from="${mtginventory.CardType.list()}" multiple="multiple" optionKey="id" size="5" value="${expansionCardInstance?.types*.id}" class="many-to-many"/>
</div>

