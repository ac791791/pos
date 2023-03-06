function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}
function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderItem";
}

// Later will be updated with maximum orderId and will be used in Deleting a order while canceling.
var maxOrderId=0;


//BUTTON ACTIONS


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
function createOrderItem(){

    var $form = $("#order-add-form");
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
    	   		document.getElementById("order-add-form").reset();
                getOrderItemAddList();
    	   },
    	   error: handleAjaxError
    	});

    	return false;
}


function createOrder(){
    var url=getOrderUrl()+"/single";
    $.ajax({
    	   url: url,
    	   type: 'POST',

    	   success: function(response) {
    	         getOrderId();
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

function getOrderId(){
    var url=getOrderUrl()+"/single";
    $.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   		maxOrderId=data.id;
    	   		$("#order-add-form input[name=orderId]").val(data.id);
    	   		$('#add-order-modal').modal('toggle');
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

function getOrderItemAddList(){

    var orderId = maxOrderId;
	var url = getOrderItemUrl() + "/" + orderId;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderItemAddList(data);
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
    	   		getOrderItemAddList();
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
            getOrderItemAddList();
            $('#edit-orderItem-modal').modal('hide');
	   },
	   error: handleAjaxError
	});

	return false;
}



//UI DISPLAY METHODS

function displayOrderList(data){


	var $tbody = $('#order-table').find('tbody');
    	$tbody.empty();
    	data.sort((a, b) => b.id - a.id);
    	for(var i in data){
    		var e = data[i];
    	var dateObj = data[i].time;

        var date = new Date(dateObj.year, dateObj.monthValue - 1, dateObj.dayOfMonth, dateObj.hour, dateObj.minute, dateObj.second, dateObj.nano / 1000000);
        // Output: Tue Feb 01 2022 00:00:00 GMT-0500 (Eastern Standard Time)
        const options = { weekday: 'short', month: 'short', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', timeZoneName: 'short' };
        const formattedDate = date.toLocaleString('en-US', options);
        // Output: "Mon, Feb 27, 2023, 12:27:11 PM"


        		var buttonHtml = '<button class="deleteButtons" onclick="confirmDeleteOrder(' + e.id + ')">Delete</button>'
        		buttonHtml += ' <button class="tableButtons"  onclick="displayOrder('+e.id+')">Edit</button>'
        		buttonHtml += ' <button class="tableButtons"  onclick="getOrderItemList(' + e.id + ')">View</button>'
    		var row = '<tr>'
    		+ '<td>' + e.id + '</td>'
    		+ '<td>' + formattedDate + '</td>'
    		+ '<td>' + buttonHtml + '</td>'
    		+ '</tr>';
            $tbody.append(row);
}
}
function confirmDeleteOrder(id) {
  if (confirm("Are you sure you want to delete this Order?")) {
    deleteOrder(id);
  }
  }
function confirmDeleteOrderItem(id) {
  if (confirm("Are you sure you want to delete this OrderItem?")) {
    deleteOrderItem(id);
  }
  }

function displayOrderItemList(data){


	var $tbody = $('#view-order-table').find('tbody');
    	$tbody.empty();
    	for(var i in data){
    		var e = data[i];

    		var row = '<tr>'
    		+ '<td>' + e.barcode + '</td>'
    		+ '<td>' + e.quantity + '</td>'
    		+ '<td>' + parseFloat(e.sellingPrice).toFixed(2) + '</td>'
    		+ '</tr>';

            $tbody.append(row);
}
}

function displayOrderItemUpdateList(data){


	var $tbody = $('#update-order-table').find('tbody');
    	$tbody.empty();

    	for(var i in data){
    		var e = data[i];


        		var buttonHtml = '<button class="deleteButtons" onclick="confirmDeleteOrderItem(' + e.id + ')">Delete</button>'
        		buttonHtml += ' <button class="tableButtons" onclick="displayEditOrderItem(' + e.id + ')" >Edit</button>'
    		var row = '<tr>'
    		+ '<td>' + e.barcode + '</td>'
    		+ '<td>' + e.quantity + '</td>'
    		+ '<td>' + parseFloat(e.sellingPrice).toFixed(2) + '</td>'
    		+ '<td>' + buttonHtml + '</td>'
    		+ '</tr>';

            $tbody.append(row);
}
}



function displayOrderItemAddList(data){


	var $tbody = $('#add-order-table').find('tbody');
    	$tbody.empty();

    	for(var i in data){
    		var e = data[i];


        		var buttonHtml = '<button class="deleteButtons" onclick="confirmDeleteOrderItem(' + e.id + ')">Delete</button>'
        		buttonHtml += ' <button class="tableButtons" onclick="displayEditOrderItem(' + e.id + ')" >Edit</button>'
    		var row = '<tr>'
    		+ '<td>' + e.barcode + '</td>'
    		+ '<td>' + e.quantity + '</td>'
    		+ '<td>' + parseFloat(e.sellingPrice).toFixed(2) + '</td>'
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
	getOrderItemAddList();
}

function closeAddModal() {
     $('#add-order-modal').modal('hide');
     getOrderList();
}

function cancelOrder(){
    deleteOrder(maxOrderId);
    $('#add-order-modal').modal('hide');
}


//INITIALIZATION CODE
function init(){

    $('#orderItem-edit-form').submit(updateOrderItem);
	$('#refresh-data').click(getOrderList);
	$('#order-edit-form').submit(addOrderItem);
	$('#order-add-form').submit(createOrderItem);
	$('#create-order').click(createOrder);
	$('#cancel-order').click(cancelOrder);
	$('#cancelTop-order').click(cancelOrder);
	$('#ad-order').click(closeAddModal);



}

$(document).ready(getOrderList);
$(document).ready(init);
