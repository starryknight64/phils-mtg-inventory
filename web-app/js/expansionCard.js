$(document).ready(function() {
	var baseURL = $("meta[name=serverURL]").attr("content");
	var expCardID = $("input[name=expansionCardID]").val();
	$.getJSON(baseURL + "/priceSource/all", function(data) {
		$.each(data, function() {
			$.getJSON(baseURL + "/expansionCard/prices/" + expCardID + "?source=" + this.name, function(data) {
				var html = "";
				$.each(data, function(key, val) {
					html += "<tr><td><a href='" + val.url + "' target='_blank'>" + key + "</a></td>"
					html += "<td>" + val.low + "</td>"
					html += "<td>" + val.median + "</td>"
					html += "<td>" + val.high + "</td></tr>"
				});
				$("table.card-pricing tbody").append(html);
			});
		});
		$(".myspinner").hide();
	}).done(function(){
		
	});
});
