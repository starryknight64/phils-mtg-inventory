$(document).ready(function() {
	var baseURL = $("meta[name=serverURL]").attr("content");
	var expCardID = $("input[name=expansionCardID]").val();
	$.getJSON(baseURL + "/expansionCard/prices/" + expCardID, function(data) {
		var html = "";
		$.each(data, function(key, val) {
			html += "<tr><td><a href='" + val.url + "' target='_blank'>" + key + "</a></td>"
			html += "<td>" + val.low + "</td>"
			html += "<td>" + val.median + "</td>"
			html += "<td>" + val.high + "</td></tr>"
		});
		$("table.card-pricing tbody").html(html);
	});
});
