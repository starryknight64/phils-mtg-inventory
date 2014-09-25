<%@ page import="mtginventory.PriceSource" %>



<div class="fieldcontain ${hasErrors(bean: priceSourceInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="priceSource.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${priceSourceInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: priceSourceInstance, field: 'website', 'error')} ">
	<label for="website">
		<g:message code="priceSource.website.label" default="Website" />
		
	</label>
	<g:field type="url" name="website" value="${priceSourceInstance?.website}"/>
</div>

