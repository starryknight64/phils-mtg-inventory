<%@ page import="mtginventory.CardTypeType" %>



<div class="fieldcontain ${hasErrors(bean: cardTypeTypeInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="cardTypeType.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${cardTypeTypeInstance?.name}"/>
</div>

