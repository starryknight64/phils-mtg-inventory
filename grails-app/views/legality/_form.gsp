<%@ page import="mtginventory.Legality" %>



<div class="fieldcontain ${hasErrors(bean: legalityInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="legality.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${legalityInstance?.name}"/>
</div>

