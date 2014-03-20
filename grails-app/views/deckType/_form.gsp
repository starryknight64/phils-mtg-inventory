<%@ page import="mtginventory.DeckType" %>



<div class="fieldcontain ${hasErrors(bean: deckTypeInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="deckType.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${deckTypeInstance?.name}"/>
</div>

