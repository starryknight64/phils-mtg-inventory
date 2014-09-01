
<%@ page import="mtginventory.ExpansionCard" %>
<%@ page import="mtginventory.Deck" %>
<%@ page import="mtginventory.Inventory" %>
<%@ page import="mtginventory.PriceSource" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'expansionCard.label', default: 'ExpansionCard')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
        <g:javascript src="expansionCard.js" />
    </head>
    <body>
            <div id="show-expansionCard" class="content scaffold-show" role="main">
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table>
                <tr class="card">
                    <g:set var="cardInstance" value="${expansionCardInstance?.card}" />
                    <td style="width: 330px;">
                    <div class="add-card-form">
                        <form action="${createLink(uri: "/")}">
                            <g:hiddenField name="expansionCardID" value="${expansionCardInstance?.id}" />
                            <table class="add-card-table">
	                            <tr>
	                                <td><label for="add">Add</label></td>
	                                <td>
	                                   <select name="add">
	                                       <g:each in="${[1,2,3,4]}">
                                                <option id="add-${it}">${it}</option>
	                                       </g:each>
                                           <option id="add-other">...</option>
                                       </select>
                                    </td>
	                            </tr>
	                            <tr>
	                                <td><label for="to">to</label></td>
	                                <td>
	                                   <select name="to">
                                           <optgroup label="Deck">
                                               <g:each in="${Deck.list()}">
                                                   <option class="to-deck" value="${it.id}">${it.name}</option>
                                               </g:each>
                                           </optgroup>
                                           <optgroup label="Inventory">
                                               <g:each in="${Inventory.list()}">
                                                   <option class="to-inventory" value="${it.id}">${it.name}</option>
                                               </g:each>
                                           </optgroup>
                                       </select>
                                    </td>
	                            </tr>
	                            <tr>
	                                <td><label for="as">as</label></td>
	                                <td>
	                                   <select name="as">
	                                       <option id="as-main-deck">Main Deck</option>
	                                       <option id="as-sideboard">Sideboard</option>
	                                       <g:set var="typeNames" value="${expansionCardInstance?.card?.types?.name}" />
	                                       <g:if test="${typeNames?.contains( "Legendary" ) && typeNames?.contains( "Creature" )}">
	                                           <option id="as-commander">Commander</option>
	                                       </g:if>
                                       </select>
                                    </td>
	                            </tr>
	                            <tr>
	                                <td></td>
	                                <td><input type="button" class="add-card-button" value="Add"></td>
                                </tr>
                            </table>
                        </form>
                    </div>
                        <mtg:renderExpansionCardImage expansionCard="${expansionCardInstance}" width="339px" />
                        <table class="card-pricing">
                            <thead><tr><th>Seller</th><th>Low</th><th>Mid</th><th>High</th></tr></thead>
                            <tbody>
                               <tr><td colspan="4" style="text-align: center;"><g:img dir="images" file="spinner.gif" /></td></tr>
                            </tbody>
                        </table>
                    </td>
                    <td>
                        <ol class="property-list expansionCard">

                            <g:if test="${expansionCardInstance?.card}">
                                <li class="fieldcontain">
                                    <span id="card-label" class="property-label"><g:message code="expansionCard.card.label" default="Card" /></span>

                                    <span class="property-value" aria-labelledby="card-label"><g:link controller="card" action="show" id="${expansionCardInstance?.card?.id}">${expansionCardInstance?.card?.name}</g:link></span>

                                </li>
                            </g:if>

                            <g:if test="${cardInstance?.manas}">
                                <li class="fieldcontain">
                                    <span id="manas-label" class="property-label"><g:message code="expansionCard.manas.label" default="Manas" /></span>

                                    <span class="property-value" aria-labelledby="manas-label">
                                        <g:each in="${cardInstance.manas.sort{ a,b -> b.id <=> a.id }}" var="m">
                                            <g:link controller="mana" action="show" id="${m.id}"><g:img dir="images/mana" file="${m.symbol.replace('{','').replace('}','')}.gif" style="max-height: 20px; max-width: 20px"/></g:link>
                                        </g:each>
                                    </span>

                                </li>
                            </g:if>

                            <g:if test="${cardInstance?.types}">
                                <li class="fieldcontain">
                                    <span id="types-label" class="property-label"><g:message code="expansionCard.types.label" default="Types" /></span>

                                    <span class="property-value" aria-labelledby="types-label">
                                        <g:each in="${cardInstance.types.sort{ a,b -> a.id <=> b.id }}" var="t">
                                        <g:link controller="cardType" action="show" id="${t.id}">${t?.name?.encodeAsHTML()}</g:link>&nbsp;
                                        </g:each>
                                    </span>

                                </li>
                            </g:if>

                            <g:if test="${expansionCardInstance?.rarity}">
                                <li class="fieldcontain">
                                    <span id="rarity-label" class="property-label"><g:message code="expansionCard.rarity.label" default="Rarity" /></span>

                                    <span class="property-value" aria-labelledby="rarity-label"><g:link controller="cardRarity" action="show" id="${expansionCardInstance?.rarity?.id}">${expansionCardInstance?.rarity?.encodeAsHTML()}</g:link></span>

                                </li>
                            </g:if>
                            
                            <g:if test="${expansionCardInstance?.expansion}">
                                <li class="fieldcontain">
                                    <span id="expansion-label" class="property-label"><g:message code="expansionCard.expansion.label" default="Expansion" /></span>

                                    <span class="property-value" aria-labelledby="expansion-label"><mtg:renderExpansion withSymbol="${true}" expansionCard="${expansionCardInstance}" /></span>

                                </li>
                            </g:if>

                            <g:if test="${expansionCardInstance?.card?.text}">
                                <li class="fieldcontain">
                                    <span id="text-label" class="property-label"><g:message code="card.text.label" default="Text" /></span>

                                    <span class="property-value" aria-labelledby="text-label"><mtg:renderText text="${expansionCardInstance.card.text}" /></span>

                                </li>
                            </g:if>

                            <g:if test="${cardInstance?.power && cardInstance?.toughness}">
                                <li class="fieldcontain">
                                    <g:if test="${cardInstance?.types?.name?.contains( "Vanguard" )}">
                                        <span id="hand-life-label" class="property-label">Hand/Life</span>
                                        <span class="property-value" aria-labelledby="hand-life-label"><i>Hand Modifier:</i> <b>${cardInstance.power >= 0 ? "+" : "-"}${cardInstance.power}</b> / <i>Life Modifier:</i> <b>${cardInstance.toughness >= 0 ? "+" : "-"}${cardInstance.toughness}</b></span>
                                    </g:if>
                                    <g:else>
	                                    <span id="power-toughness-label" class="property-label">P/T</span>
	                                    <span class="property-value" aria-labelledby="power-toughness-label">${cardInstance.power} / ${cardInstance.toughness}</span>
                                    </g:else>
                                </li>
                            </g:if>

                            <g:if test="${cardInstance?.loyalty}">
                                <li class="fieldcontain">
                                    <span id="loyalty-label" class="property-label"><g:message code="card.loyalty.label" default="Loyalty" /></span>

                                    <span class="property-value" aria-labelledby="loyalty-label"><g:fieldValue bean="${cardInstance}" field="loyalty"/></span>

                                </li>
                            </g:if>

                            <g:if test="${expansionCardInstance?.flavorText}">
                                <li class="fieldcontain">
                                    <span id="flavorText-label" class="property-label"><g:message code="expansionCard.flavorText.label" default="Flavor Text" /></span>

                                    <span class="property-value" aria-labelledby="flavorText-label"><i>${expansionCardInstance.flavorText.replace("\n\n","<br>").replace("\n","<br>")}</i></span>

                                </li>
                            </g:if>

                            <g:if test="${expansionCardInstance?.illustrator}">
                                <li class="fieldcontain">
                                    <span id="illustrator-label" class="property-label"><g:message code="expansionCard.illustrator.label" default="Illustrator" /></span>

                                    <span class="property-value" aria-labelledby="illustrator-label"><mtg:renderIllustrator illustrator="${expansionCardInstance?.illustrator}" /></span>

                                </li>
                            </g:if>
                            
                            <g:if test="${expansionCardInstance?.collectorNumber}">
                                <li class="fieldcontain">
                                    <span id="collectorNumber-label" class="property-label"><g:message code="expansionCard.collectorNumber.label" default="Collector Number" /></span>

                                    <span class="property-value" aria-labelledby="collectorNumber-label">${expansionCardInstance?.collectorNumber}</span>

                                </li>
                            </g:if>
                            
                            <g:if test="${cardInstance?.expansionCards}">
                                <g:if test="${cardInstance.expansionCards.size() > 1}">
                                    <li class="fieldcontain">
                                        <span id="expansionCards-label" class="property-label"><g:message code="card.expansionCards.label" default="Expansion Cards" /></span>
                                        <span class="property-value" aria-labelledby="expansionCards-label"> 
                                            <table class="other-expansions">
                                                <g:each in="${cardInstance.expansionCards.sort{ a,b -> b.expansion.releaseDate <=> a.expansion.releaseDate}}">
	                                                <g:if test="${expansionCardInstance != it}">
                                                        <tr>
	                                                       <mtg:renderExpansion tabulated="${true}" withSymbol="${true}" linkToCard="${true}" expansionCard="${it}" />
	                                                    </tr>
	                                                </g:if>
                                                </g:each>
                                            </table>
                                        </span>
                                    </li>
                                </g:if>
                            </g:if>

                        </ol>
                    </td>
                </tr>
            </table>
            <g:form>
                <fieldset class="buttons">
                    <g:hiddenField name="id" value="${expansionCardInstance?.id}" />
                    <g:link class="edit" action="edit" id="${expansionCardInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
