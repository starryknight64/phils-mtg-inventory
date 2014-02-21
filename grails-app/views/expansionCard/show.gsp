
<%@ page import="mtginventory.ExpansionCard" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'expansionCard.label', default: 'ExpansionCard')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-expansionCard" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                </ul>
            </div>
            <div id="show-expansionCard" class="content scaffold-show" role="main">
                <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table>
                <tr>
                    <g:set var="cardInstance" value="${expansionCardInstance?.card}" />
                    <td style="width: 1px;"><img width="339px" src="http://mtgimage.com/set/${expansionCardInstance.expansion.expansionCodes.find{ it.author == "mtgsalvation" }?.code}/${cardInstance?.name}.jpg"></td>
                    <td>
                        <ol class="property-list expansionCard">

                            <g:if test="${expansionCardInstance?.card}">
                                <li class="fieldcontain">
                                    <span id="card-label" class="property-label"><g:message code="expansionCard.card.label" default="Card" /></span>

                                    <span class="property-value" aria-labelledby="card-label"><g:link controller="card" action="show" id="${expansionCardInstance?.card?.id}">${expansionCardInstance?.card?.name}</g:link></span>

                                </li>
                            </g:if>

                            <g:if test="${expansionCardInstance?.manas}">
                                <li class="fieldcontain">
                                    <span id="manas-label" class="property-label"><g:message code="expansionCard.manas.label" default="Manas" /></span>

                                    <span class="property-value" aria-labelledby="manas-label">
                                        <g:each in="${expansionCardInstance.manas.sort{ a,b -> b.id <=> a.id }}" var="m">
                                            <g:link controller="mana" action="show" id="${m.id}"><g:img dir="images/mana" file="${m.symbol.replace('{','').replace('}','')}.gif" style="max-height: 20px; max-width: 20px"/></g:link>
                                        </g:each>
                                    </span>

                                </li>
                            </g:if>

                            <g:if test="${expansionCardInstance?.types}">
                                <li class="fieldcontain">
                                    <span id="types-label" class="property-label"><g:message code="expansionCard.types.label" default="Types" /></span>

                                    <span class="property-value" aria-labelledby="types-label">
                                        <g:each in="${expansionCardInstance.types.sort{ a,b -> a.id <=> b.id }}" var="t">
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

                            <g:if test="${expansionCardInstance?.text}">
                                <li class="fieldcontain">
                                    <span id="text-label" class="property-label"><g:message code="card.text.label" default="Text" /></span>

                                    <span class="property-value" aria-labelledby="text-label"><mtg:renderText text="${expansionCardInstance.text}" /></span>

                                </li>
                            </g:if>

                            <g:if test="${cardInstance?.power}">
                                <li class="fieldcontain">
                                    <span id="power-label" class="property-label"><g:message code="card.power.label" default="Power" /></span>

                                    <span class="property-value" aria-labelledby="power-label"><g:fieldValue bean="${cardInstance}" field="power"/></span>

                                </li>
                            </g:if>

                            <g:if test="${cardInstance?.toughness}">
                                <li class="fieldcontain">
                                    <span id="toughness-label" class="property-label"><g:message code="card.toughness.label" default="Toughness" /></span>

                                    <span class="property-value" aria-labelledby="toughness-label"><g:fieldValue bean="${cardInstance}" field="toughness"/></span>

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

                            <g:if test="${expansionCardInstance?.priceLow}">
                                <li class="fieldcontain">
                                    <span id="priceLow-label" class="property-label"><g:message code="expansionCard.priceLow.label" default="Price Low" /></span>

                                    <span class="property-value" aria-labelledby="priceLow-label"><g:fieldValue bean="${expansionCardInstance}" field="priceLow"/></span>

                                </li>
                            </g:if>

                            <g:if test="${expansionCardInstance?.priceMid}">
                                <li class="fieldcontain">
                                    <span id="priceMid-label" class="property-label"><g:message code="expansionCard.priceMid.label" default="Price Mid" /></span>

                                    <span class="property-value" aria-labelledby="priceMid-label"><g:fieldValue bean="${expansionCardInstance}" field="priceMid"/></span>

                                </li>
                            </g:if>

                            <g:if test="${expansionCardInstance?.priceHigh}">
                                <li class="fieldcontain">
                                    <span id="priceHigh-label" class="property-label"><g:message code="expansionCard.priceHigh.label" default="Price High" /></span>

                                    <span class="property-value" aria-labelledby="priceHigh-label"><g:fieldValue bean="${expansionCardInstance}" field="priceHigh"/></span>

                                </li>
                            </g:if>

                            <g:if test="${expansionCardInstance?.variation}">
                                <li class="fieldcontain">
                                    <span id="variation-label" class="property-label"><g:message code="expansionCard.variation.label" default="Variation" /></span>

                                    <span class="property-value" aria-labelledby="variation-label"><g:fieldValue bean="${expansionCardInstance}" field="variation"/></span>

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
                                            <table>
                                                <g:each in="${cardInstance.expansionCards.sort{ a,b -> b.expansion.releaseDate <=> a.expansion.releaseDate}}" var="e">
                                                <g:if test="${expansionCardInstance != e}">
                                                        <tr style="height: 27px;"><td style="text-align: right; width: 40px;"><g:link controller="expansionCard" action="show" id="${e.id}"><g:img dir="images/expansions" file="${e.expansion.name.replace('&','and').replace(':','').replace(' Core Set','').replace('"','')}_${e.rarity.name.replace("Basic Land","Common").replace("Mythic Rare","Mythic")}.gif" style="max-width: 30px; max-height: 20px; padding-right: 10px;"/></g:link></td><td><g:link controller="expansionCard" action="show" id="${e.id}">${e?.expansion?.encodeAsHTML()}</g:link></td></tr>
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
