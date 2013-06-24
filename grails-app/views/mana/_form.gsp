<%@ page import="mtginventory.Mana" %>



<div class="fieldcontain ${hasErrors(bean: manaInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="mana.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${manaInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: manaInstance, field: 'symbol', 'error')} ">
	<label for="symbol">
		<g:message code="mana.symbol.label" default="Symbol" />
		
	</label>
	<g:textField name="symbol" value="${manaInstance?.symbol}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: manaInstance, field: 'cmc', 'error')} ">
	<label for="cmc">
		<g:message code="mana.cmc.label" default="Cmc" />
		
	</label>
	<g:textField name="cmc" value="${manaInstance?.cmc}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: manaInstance, field: 'colors', 'error')} ">
	<label for="colors">
		<g:message code="mana.colors.label" default="Colors" />
		
	</label>
	<g:select name="colors" from="${mtginventory.ManaColor.list()}" multiple="multiple" optionKey="id" size="5" value="${manaInstance?.colors*.id}" class="many-to-many"/>
</div>

