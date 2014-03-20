<%@ page import="mtginventory.ExpansionCardPrice" %>



<div class="fieldcontain ${hasErrors(bean: expansionCardPriceInstance, field: 'low', 'error')} ">
	<label for="low">
		<g:message code="expansionCardPrice.low.label" default="Low" />
		
	</label>
	<g:textField name="low" value="${expansionCardPriceInstance?.low}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCardPriceInstance, field: 'median', 'error')} ">
	<label for="median">
		<g:message code="expansionCardPrice.median.label" default="Median" />
		
	</label>
	<g:textField name="median" value="${expansionCardPriceInstance?.median}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCardPriceInstance, field: 'high', 'error')} ">
	<label for="high">
		<g:message code="expansionCardPrice.high.label" default="High" />
		
	</label>
	<g:textField name="high" value="${expansionCardPriceInstance?.high}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCardPriceInstance, field: 'expansionCard', 'error')} required">
	<label for="expansionCard">
		<g:message code="expansionCardPrice.expansionCard.label" default="Expansion Card" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="expansionCard" name="expansionCard.id" from="${[expansionCardPriceInstance?.expansionCard]}" optionKey="id" required="" value="${expansionCardPriceInstance?.expansionCard?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCardPriceInstance, field: 'source', 'error')} required">
	<label for="source">
		<g:message code="expansionCardPrice.source.label" default="Source" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="source" name="source.id" from="${mtginventory.PriceSource.list()}" optionKey="id" required="" value="${expansionCardPriceInstance?.source?.id}" class="many-to-one"/>
</div>

