function getInventoryReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventoryReport";
}

function getInventoryReportList(){
	var url = getInventoryReportUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayInventoryReportList(data);
	   },
	   error: handleAjaxError
	});
}

function displayInventoryReportList(data){
	var $tbody = $('#inventoryReport-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

$(document).ready(getInventoryReportList);