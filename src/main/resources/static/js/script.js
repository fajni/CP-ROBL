$(document).ready(function(){
    $("#add").fadeOut(5000);
    $("#update").fadeOut(5000);
    $("#delete").fadeOut(5000);
});

function getProduct(button, type){

    const row = button.parentNode.parentNode;

    const id = row.cells[0].innerText;
    const imageUrl = row.cells[1].querySelector('img').getAttribute('src');
    const title = row.cells[2].innerText;
    const price = parseInt(row.cells[3].innerText.replace('$', ''));
    const number = row.cells[4].innerText;
    const description = row.cells[5].innerText;

    console.log(id, imageUrl, title, price, number, description, type);

    setDataOnUpdateModal(id, title, price, number, description, type);
}

function setDataOnUpdateModal(id, title, price, number, description, type){

    document.getElementById('updateId').value = id;
    //document.getElementById('updateImageUrl').value = imageUrl;
    document.getElementById('updateTitle').value = title;
    document.getElementById('updatePrice').value = price;
    document.getElementById('updateNumber').value = number;
    document.getElementById('updateDescription').value = description;
    document.getElementById('updateType').value = type;
}

/*  UNUSED  */
function sleep(miliseconds) {
   var currentTime = new Date().getTime();

   while (currentTime + miliseconds >= new Date().getTime()) {
   }
}