
function getReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/report";
}

function getSalesReportList(){

    var $form = $("#salesReport-form");
    var json = toJson($form);
	var url = getReportUrl()+"/salesReport";
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

function getBrandReportList(){


    var $form = $("#brandReport-form");
    var json = toJson($form);
	var url = getReportUrl()+"/brandReport";
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
           'Content-Type': 'application/json'
         },
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

function getInventoryReportList(){

    var $form = $("#inventoryReport-form");
    var json = toJson($form);
	var url = getReportUrl()+"/inventoryReport";
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
           'Content-Type': 'application/json'
         },
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
		+ '<td>'  + e.quantity + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function printTable() {
  var table = document.getElementById("brandReport-table");
  window.print();
}

function generateTsvData(table) {
  let data = '';
  let rows = table.querySelectorAll('tr');
  for (let i = 0; i < rows.length; i++) {
    let cells = rows[i].querySelectorAll('td, th');
    for (let j = 0; j < cells.length; j++) {
      let cellData = cells[j].textContent.replace(/\r?\n|\r/g, '');
      data += cellData + '\t';
    }
    data += '\n';
  }
  return data;
}
function downloadBrandReport(){
 let table = document.getElementById('brandReport-table');
  let tsvData = generateTsvData(table);
  let blob = new Blob([tsvData], {type: 'text/tab-separated-values;charset=utf-8'});
  let url = URL.createObjectURL(blob);
  let a = document.createElement('a');
  a.href = url;
  const currentDate = new Date();
  const currentDateTimeString = currentDate.toLocaleString();
  a.download = 'BrandReport '+currentDateTimeString+'.tsv';
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  URL.revokeObjectURL(url);
}

function downloadInventoryReport(){
 let table = document.getElementById('inventoryReport-table');
  let tsvData = generateTsvData(table);
  let blob = new Blob([tsvData], {type: 'text/tab-separated-values;charset=utf-8'});
  let url = URL.createObjectURL(blob);
  let a = document.createElement('a');
  a.href = url;
  const currentDate = new Date();
  const currentDateTimeString = currentDate.toLocaleString();
  a.download = 'InventoryReport '+currentDateTimeString+'.tsv';
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  URL.revokeObjectURL(url);
}

function downloadSalesReport(){
 let table = document.getElementById('salesReport-table');
  let tsvData = generateTsvData(table);
  let blob = new Blob([tsvData], {type: 'text/tab-separated-values;charset=utf-8'});
  let url = URL.createObjectURL(blob);
  let a = document.createElement('a');
  a.href = url;
  const currentDate = new Date();
  const currentDateTimeString = currentDate.toLocaleString();
  a.download = 'SalesReport '+currentDateTimeString+'.tsv';
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  URL.revokeObjectURL(url);
}


function init(){
	$('#sales-report').click(getSalesReportList);
    $('#brand-report').click(getBrandReportList);
    $('#inventory-report').click(getInventoryReportList);
    $('#download-brand-report').click(downloadBrandReport);
    $('#download-inventory-report').click(downloadInventoryReport);
    $('#download-sales-report').click(downloadSalesReport);

}

$(document).ready(init);