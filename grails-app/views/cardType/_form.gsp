<%@ page import="mtginventory.CardType" %>



<div class="fieldcontain ${hasErrors(bean: cardTypeInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="cardType.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${cardTypeInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cardTypeInstance, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="cardType.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="type" name="type.id" from="${mtginventory.CardTypeType.list()}" optionKey="id" required="" value="${cardTypeInstance?.type?.id}" class="many-to-one"/>
</div>

