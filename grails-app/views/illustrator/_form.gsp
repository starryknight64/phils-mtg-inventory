<%@ page import="mtginventory.Illustrator" %>



<div class="fieldcontain ${hasErrors(bean: illustratorInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="illustrator.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${illustratorInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: illustratorInstance, field: 'aliases', 'error')} ">
	<label for="aliases">
		<g:message code="illustrator.aliases.label" default="Aliases" />
		
	</label>
	<g:select name="aliases" from="${mtginventory.Illustrator.list()}" multiple="multiple" optionKey="id" size="5" value="${illustratorInstance?.aliases*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: illustratorInstance, field: 'notAliases', 'error')} ">
	<label for="notAliases">
		<g:message code="illustrator.notAliases.label" default="Not Aliases" />
		
	</label>
	<g:select name="notAliases" from="${mtginventory.Illustrator.list()}" multiple="multiple" optionKey="id" size="5" value="${illustratorInstance?.notAliases*.id}" class="many-to-many"/>
</div>

