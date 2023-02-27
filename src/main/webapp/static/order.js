var jsonList=[];

function toggleAdd(){
 $('#input-order-modal').modal('toggle');
}

  function addNameField() {
       var $form = $("#order-input-form");
       var json = toJson($form);
       var orderItem = JSON.parse(json);
       jsonList.push(orderItem);
       var tableBody = document.getElementById("input-form-table");
       var barcode = document.getElementById("inputBarcode").value;
       var quantity = document.getElementById("inputQuantity").value;
       var mrp = document.getElementById("inputMrp").value;
       var row = tableBody.insertRow();
       var cell1 = row.insertCell();
       var cell2 = row.insertCell();
       var cell3 = row.insertCell();
       cell1.innerHTML = barcode;
       cell2.innerHTML = quantity;
       cell3.innerHTML = mrp;
       document.getElementById("order-input-form").reset();
  }

function addOrderItem(){

    var $form = $("#order-edit-form");
    var orderId = $("#order-edit-form input[name=orderId]").val();
    	var json = toJson($form);
    	var url = getOrderItemUrl();

    	$.ajax({
    	   url: url,
    	   type: 'POST',
    	   data: json,
    	   headers: {
           	'Content-Type': 'application/json'
           },
    	   success: function(response) {
    	   		document.getElementById("order-edit-form").reset();
                getOrderItemUpdateList();
    	   },
    	   error: handleAjaxError
    	});

    	return false;
}

function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}
function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderItem";
}



//BUTTON ACTIONS
function addOrder(event){
    console.log(jsonList);
	var url = getOrderUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: JSON.stringify(jsonList),
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	         jsonList=[];
	         var tableBody = document.getElementById("input-form-table");
	         tableBody.innerHTML="";
             window.close();
	   		getOrderList();
	   },
	   error: handleAjaxError
	});

	return false;
}




function getOrderList(){
	var url = getOrderUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderList(data);
	   },
	   error: handleAjaxError
	});
}
function getOrderItemList(id){
    $('#view-order-modal').modal('toggle');
	var url = getOrderItemUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderItemList(data);
	   },
	   error: handleAjaxError
	});
}
function getOrderItemUpdateList(){

    var orderId = $("#order-edit-form input[name=orderId]").val();
	var url = getOrderItemUrl() + "/" + orderId;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderItemUpdateList(data);
	   },
	   error: handleAjaxError
	});
}




function deleteOrder(id){
	var url = getOrderUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getOrderList();
	   },
	   error: handleAjaxError
	});
}
function deleteOrderItem(id){
    var url=getOrderItemUrl()+"/"+id;

    $.ajax({
    	   url: url,
    	   type: 'DELETE',
    	   success: function(data) {

    	   		getOrderItemUpdateList(id);
    	   },
    	   error: handleAjaxError
    	});

}

function updateOrderItem(event){

	//Get the ID
	var id = $("#orderItem-edit-form input[name=id]").val();
	var barcode = $("#orderItem-edit-form input[name=barcode]").val();


	var url = getOrderItemUrl() + "/" + id;


	//Set the values to update
	var $form = $("#orderItem-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	        document.getElementById("orderItem-edit-form").reset();
	   		getOrderItemUpdateList();
	   },
	   error: handleAjaxError
	});

	return false;
}



//UI DISPLAY METHODS

function displayOrderList(data){


	var $tbody = $('#order-table').find('tbody');
    	$tbody.empty();
    	for(var i in data){
    		var e = data[i];
    	var dateObj = data[i].time;

        var date = new Date(dateObj.year, dateObj.monthValue - 1, dateObj.dayOfMonth, dateObj.hour, dateObj.minute, dateObj.second, dateObj.nano / 1000000);
        // Output: Tue Feb 01 2022 00:00:00 GMT-0500 (Eastern Standard Time)


        		var buttonHtml = '<button class="deleteButtons" onclick="deleteOrder(' + e.id + ')">Delete</button>'
        		buttonHtml += ' <button class="tableButtons"  onclick="displayOrder('+e.id+')">Edit</button>'
        		buttonHtml += ' <button class="tableButtons"  onclick="getOrderItemList(' + e.id + ')">View</button>'
    		var row = '<tr>'
    		+ '<td>' + e.id + '</td>'
    		+ '<td>' + date + '</td>'
    		+ '<td>' + buttonHtml + '</td>'
    		+ '</tr>';
            $tbody.append(row);
}
}

function displayOrderItemList(data){


	var $tbody = $('#view-order-table').find('tbody');
    	$tbody.empty();
    	for(var i in data){
    		var e = data[i];

    		var row = '<tr>'
    		+ '<td>' + e.productId + '</td>'
    		+ '<td>' + e.quantity + '</td>'
    		+ '<td>' + e.sellingPrice + '</td>'
    		+ '</tr>';

            $tbody.append(row);
}
}

function displayOrderItemUpdateList(data){


	var $tbody = $('#update-order-table').find('tbody');
    	$tbody.empty();

    	for(var i in data){
    		var e = data[i];


        		var buttonHtml = '<button class="deleteButtons" onclick="deleteOrderItem(' + e.id + ')">Delete</button>'
        		buttonHtml += ' <button class="tableButtons" onclick="displayEditOrderItem(' + e.id + ')" >Edit</button>'
    		var row = '<tr>'
    		+ '<td>' + e.barcode + '</td>'
    		+ '<td>' + e.quantity + '</td>'
    		+ '<td>' + e.sellingPrice + '</td>'
    		+ '<td>' + buttonHtml + '</td>'
    		+ '</tr>';

            $tbody.append(row);
}
}

function displayEditOrderItem(id){
	var url = getOrderItemUrl() + "/single/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderItem(data);
	   },
	   error: handleAjaxError
	});
}

function displayOrderItem(data){
	$("#orderItem-edit-form input[name=quantity]").val(data.quantity);
	$("#orderItem-edit-form input[name=sellingPrice]").val(data.sellingPrice);
	$("#orderItem-edit-form input[name=id]").val(data.id);
	$("#orderItem-edit-form input[name=barcode]").val(data.barcode);

	$('#edit-orderItem-modal').modal('toggle');
}



function displayOrder(id){

	$("#order-edit-form input[name=orderId]").val(id);
	$('#edit-order-modal').modal('toggle');
	getOrderItemUpdateList();
}


//INITIALIZATION CODE
function init(){
    $('#insert-order').click(toggleAdd);
    $('#add-cart-order').click(addNameField);
	$('#add-order').click(addOrder);
    $('#orderItem-edit-form').submit(updateOrderItem);
	$('#refresh-data').click(getOrderList);
	$('#order-edit-form').submit(addOrderItem);

}

$(document).ready(init);
$(document).ready(getOrderList);