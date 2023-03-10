let brandList = [];

function getProductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}
function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}


//BUTTON ACTIONS
function addProduct(event){
	//Set the values to update
	var $form = $("#product-form");
	var brandCategoryId = $('#brand-category-select').children("option:selected").val();
	$("#product-form input[name=brandCategory]").val(brandCategoryId);

	var json = toJson($form);
	var url = getProductUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {

	   		getProductList();
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateProduct(event){
	$('#edit-product-modal').modal('toggle');
	//Get the ID
	var id = $("#product-edit-form input[name=id]").val();
	var url = getProductUrl() + "/" + id;

	//Set the values to update
	var $form = $("#product-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	        document.getElementById("product-edit-form").reset();
	   		getProductList();
	   },
	   error: handleAjaxError
	});

	return false;
}


function getProductList(){
    document.getElementById("product-form").reset();
	var url = getProductUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProductList(data);
	   },
	   error: handleAjaxError
	});
}

function deleteProduct(id){
	var url = getProductUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getProductList();
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#productFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}

	//Process next row
	var row = fileData[processCount];
	processCount++;

	var json = JSON.stringify(row);
	var url = getProductUrl();

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		uploadRows();
	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayProductList(data){
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var nameLength= (e.name).length;
        if(nameLength>20){
        	var name=(e.name).slice(0,20)+'...';
        }
        else{ var name=e.name;}
        var brandLength= (e.brand).length;
        if(brandLength>20){
        	var brand=(e.brand).slice(0,20)+'...';
        }
        else{ var brand=e.brand;}

        var categoryLength=(e.category).length;
        if(categoryLength>20){
             var category=(e.category).slice(0,20)+'...';
         }
         else{ var category=e.category;}
		var buttonHtml = '<button class="deleteButtons" onclick="confirmDelete(' + e.id + ')">Delete</button>'
		buttonHtml += ' <button class="tableButtons" onclick="displayEditProduct(' + e.id + ')">Edit</button>'
		var row = '<tr>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + brand + '</td>'
		+ '<td>'  + category + '</td>'
		+ '<td>'  + name + '</td>'
		+ '<td>'  + parseFloat(e.mrp).toFixed(2) + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}
function confirmDelete(id) {
  if (confirm("Are you sure you want to delete this Product?")) {
    deleteProduct(id);
  }
  }

function displayEditProduct(id){
	var url = getProductUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProduct(data);
	   },
	   error: handleAjaxError
	});
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#productFile');
	$file.val('');
	$('#productFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#productFile');
	var fileName = $file.val();
	$('#productFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-product-modal').modal('toggle');
}

function displayProduct(data){
	$("#product-edit-form input[name=barcode]").val(data.barcode);
	$("#product-edit-form input[name=brand]").val(data.brand);
	$("#product-edit-form input[name=category]").val(data.category);
	$("#product-edit-form input[name=brandCategory]").val(data.brandCategory);
	$("#product-edit-form input[name=name]").val(data.name);
	$("#product-edit-form input[name=mrp").val(data.mrp);
    $("#product-edit-form input[name=id]").val(data.id);
	$('#edit-product-modal').modal('toggle');
}


function displayBrandCategorySelect(brandCategoryList){
    var brandCategorySelect = $('#brand-category-select');
    brandCategorySelect.empty();
    brandCategorySelect.append("<option value=0 disabled selected>Choose  Brand/Category</option>");
    for(brand of brandCategoryList){
        brandCategorySelect.append("<option value="+brand.id+">"+(brand.brand).slice(0,20)+" :: "+(brand.category).slice(0,20)+"</option>")
    }
}

function setupBrandCategorySelect(){
    var url = getBrandUrl();
    $.ajax({
       url: url,
       type: 'GET',
       success: function(brandData) {
        brandList = [...brandData];
        displayBrandCategorySelect(brandData);
       },
       error: handleAjaxError
    });

}


//INITIALIZATION CODE
function init(){
	$('#product-form').submit(addProduct);
	$('#product-edit-form').submit(updateProduct);
	$('#refresh-data').click(getProductList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getProductList);
$(document).ready(setupBrandCategorySelect);

