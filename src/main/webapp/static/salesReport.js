
function getSalesReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/report/salesReport";
}

function getSalesReportList(){
    console.log("Hello");
    var $form = $("#salesReport-form");
    var json = toJson($form);
	var url = getSalesReportUrl();
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
           'Content-Type': 'application/json'
         },
	   success: function(data) {
	   		displaySalesReportList(data);
	   },
	   error: handleAjaxError
	});
}

function displaySalesReportList(data){
	var $tbody = $('#salesReport-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		+ '<td>' + e.revenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function init(){
	$('#find-report').click(getSalesReportList);

}

$(document).ready(init);