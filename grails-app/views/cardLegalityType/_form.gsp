<%@ page import="mtginventory.CardLegalityType" %>



<div class="fieldcontain ${hasErrors(bean: cardLegalityTypeInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="cardLegalityType.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${cardLegalityTypeInstance?.name}"/>
</div>

