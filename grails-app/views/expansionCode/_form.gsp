<%@ page import="mtginventory.ExpansionCode" %>



<div class="fieldcontain ${hasErrors(bean: expansionCodeInstance, field: 'author', 'error')} ">
	<label for="author">
		<g:message code="expansionCode.author.label" default="Author" />
		
	</label>
	<g:textField name="author" value="${expansionCodeInstance?.author}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCodeInstance, field: 'code', 'error')} ">
	<label for="code">
		<g:message code="expansionCode.code.label" default="Code" />
		
	</label>
	<g:textField name="code" value="${expansionCodeInstance?.code}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionCodeInstance, field: 'expansion', 'error')} required">
	<label for="expansion">
		<g:message code="expansionCode.expansion.label" default="Expansion" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="expansion" name="expansion.id" from="${mtginventory.Expansion.list()}" optionKey="id" required="" value="${expansionCodeInstance?.expansion?.id}" class="many-to-one"/>
</div>

