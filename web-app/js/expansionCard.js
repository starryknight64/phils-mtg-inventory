$(document).ready(function() {
	var baseURL = $("meta[name=serverURL]").attr("content");
	$.getJSON(baseURL + "/expansionCard/prices/1", function(data) {
		var html = "";
		$.each(data, function(key, val) {
			html += "<tr><td>" + key + "</td>"
			html += "<td>" + val.low + "</td>"
			html += "<td>" + val.median + "</td>"
			html += "<td>" + val.high + "</td></tr>"
		});
		$("table.card-pricing tbody").html(html);
	});
});
