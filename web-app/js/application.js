function addCardToChanged( select ) {
	var thisClass = select.children().children(":selected").attr("class");
	if( thisClass == "to-inventory" ) {
		$("select[name=as]").attr("disabled","");
	} else {
		$("select[name=as]").removeAttr("disabled");		
	}
}

$( document ).ready(function() {
	addCardToChanged( $("select[name=to]") );
	
	$(".card .card-image").click( function() {
		$(".add-card-form").toggle();
		return false;
	});

	$("select[name=to]").change( function() {
		addCardToChanged( $(this) );
	});
	
	$(".add-card-button").click( function() {
		var form = $(this).closest("form");
		var action = form.attr("action");
		var selectTo = $("select[name=to] :selected").attr("class");
		if( selectTo == "to-deck" ) {
			form.attr("action", action+"Deck/addCard" );
		} else {
			form.attr("action", action+"Inventory/addCard" );
		}
		form.submit();
	});
});

