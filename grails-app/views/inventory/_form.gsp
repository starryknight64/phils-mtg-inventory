<%@ page import="mtginventory.Inventory" %>



<div class="fieldcontain ${hasErrors(bean: inventoryInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="inventory.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${inventoryInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: inventoryInstance, field: 'inventoryCards', 'error')} ">
	<label for="inventoryCards">
		<g:message code="inventory.inventoryCards.label" default="Inventory Cards" />
		
	</label>
	<g:select name="cards" from="${mtginventory.InventoryCard.list()}" multiple="multiple" optionKey="id" size="5" value="${inventoryInstance?.cards*.id}" class="many-to-many"/>
</div>

