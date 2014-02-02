<%@ page import="mtginventory.Meta" %>



<div class="fieldcontain ${hasErrors(bean: metaInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="meta.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${metaInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: metaInstance, field: 'value', 'error')} ">
	<label for="value">
		<g:message code="meta.value.label" default="Value" />
		
	</label>
	<g:textField name="value" value="${metaInstance?.value}"/>
</div>

