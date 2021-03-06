<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title><g:layoutTitle default="Grails"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
        <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
        <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
        <meta name="serverURL" content="${grailsApplication.config.grails.serverURL}" />
        
        <g:javascript src="jquery-1.11.0.js"/>
	    <g:javascript library="application"/>
        
        <g:layoutHead/>
    <r:layoutResources />
</head>
<body>
    <div id="logo" role="banner">
        <table>
            <tr class="banner">
                <td>
                    <a href="${createLink(uri: '/')}"><img height="43px" src="${resource(dir: 'images', file: 'MTGInventory_logo.png')}" alt="MtG Inventory"/></a>
                </td>
                <td>
                    <form action="/MtGInventory/search/index" method="get" id="searchForm" name="searchForm">
                        <input type="text" name="q" value="" size="50" id="q"> <input type="submit" value="Search">
                        <g:select name="domainClass" from="${["Card","Expansion","Illustrator"]}" value="Card" />
                    </form>
                </td>
            </tr>
        </table>
    </div>
	<div class="nav" role="navigation">
		<ul>
            <li><g:link controller="Expansion" class="list" action="list">Expansions</g:link></li>
            <li><g:link controller="Deck" class="list" action="list">Decks</g:link></li>
            <li><g:link controller="Inventory" class="list" action="list">Inventories</g:link></li>
		</ul>
	</div>
	<g:layoutBody/>
    <div class="footer" role="contentinfo"></div>
    <div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>

<r:layoutResources />
</body>
</html>
