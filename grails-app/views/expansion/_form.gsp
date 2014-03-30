<%@ page import="mtginventory.Expansion" %>



<div class="fieldcontain ${hasErrors(bean: expansionInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="expansion.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${expansionInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionInstance, field: 'releaseDate', 'error')} ">
	<label for="releaseDate">
		<g:message code="expansion.releaseDate.label" default="Release Date" />
		
	</label>
	<g:datePicker name="releaseDate" precision="day"  value="${expansionInstance?.releaseDate}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: expansionInstance, field: 'totalCards', 'error')} ">
	<label for="totalCards">
		<g:message code="expansion.totalCards.label" default="Total Cards" />
		
	</label>
	<g:field name="totalCards" type="number" value="${expansionInstance.totalCards}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: expansionInstance, field: 'expansionCards', 'error')} ">
	<label for="expansionCards">
		<g:message code="expansion.expansionCards.label" default="Expansion Cards" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${expansionInstance?.expansionCards?}" var="e">
    <li><g:link controller="expansionCard" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="expansionCard" action="create" params="['expansion.id': expansionInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'expansionCard.label', default: 'ExpansionCard')])}</g:link>
</li>
</ul>

</div>
