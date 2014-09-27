$(document).ready(function() {
	var baseURL = $("meta[name=serverURL]").attr("content");
	var expCardID = $("input[name=expansionCardID]").val();
	var sourceIDs = [];
	$.getJSON(baseURL + "/priceSource/all", function(data) {
		$("table.card-pricing tbody").html("");
		
		$.each(data, function() {
			sourceIDs.push(this.id);
			$("table.card-pricing tbody").append("<tr id='" + this.id + "'><td><a href='" + this.website + "' target='_blank'>" + this.name + "</a></td><td class='loading' colspan='3' style='text-align: center;'>Loading...</td></tr>");
			
			$.getJSON(baseURL + "/expansionCard/prices/" + expCardID + "?source=" + this.name, function(data) {
				var index = $.inArray(data["sourceID"], sourceIDs);
				sourceIDs.splice(index,1);
				var html = "<td><a href='" + data.url + "' target='_blank'>" + data["sourceName"] + "</a></td>"
				html += "<td class='price'>" + data.low + "</td>"
				html += "<td class='price'>" + data.median + "</td>"
				html += "<td class='price'>" + data.high + "</td>"
				$("table.card-pricing tbody tr#" + data["sourceID"]).html(html);
				
				if( sourceIDs.length == 0 ) {
					var minPriceValue = Number.MAX_VALUE;
					var minPrice = null;
					var prices = $("td.price");
					for( var i=0; i<prices.length; i++ ) {
						var price = $(prices[i]).html().replace("$","");
						if( price != "" && price < minPriceValue ) {
							minPriceValue = price;
							minPrice = $(prices[i]);
						}
					}
					
					if( minPrice !== null ) {
						$("td price#lowest").removeAttr("id");
						minPrice.attr("id","lowest");
					}
				}
			});			
		});
	});
});
