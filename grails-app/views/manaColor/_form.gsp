<%@ page import="mtginventory.ManaColor" %>



<div class="fieldcontain ${hasErrors(bean: manaColorInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="manaColor.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${manaColorInstance?.name}"/>
</div>

