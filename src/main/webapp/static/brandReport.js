function getBrandReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brandReport";
}

function getBrandReportList(){
	var url = getBrandReportUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrandReportList(data);
	   },
	   error: handleAjaxError
	});
}

function displayBrandReportList(data){
	var $tbody = $('#brandReport-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

$(document).ready(getBrandReportList);

