function showModal(id){
    console.log(id);
    document.getElementById(id).style.display='block';
}

function closeModal(id) {
    document.getElementById(id).style.display='none'
}

function openDashandSaveName(dashName, id, valueToSelect) {
    var i;
    var x = document.getElementsByClassName("container-fluid");
    for (i = 0; i < x.length; i++) {
        if(x[i].id != "foot" && x[i].id != "modal")   x[i].style.display = "none";  
    }
    var dash = document.getElementById(dashName);
    dash.style.display = "block";
    
    var patientCode = document.getElementById(valueToSelect).getAttribute("name");
    selectElement(id, patientCode);
}

function openDashandClean(dashName, id) {
    var i;
    var x = document.getElementsByClassName("container-fluid");
    for (i = 0; i < x.length; i++) {
        if(x[i].id != "foot" && x[i].id != "modal")   x[i].style.display = "none";  
    }
    var dash = document.getElementById(dashName);
    dash.style.display = "block";
    selectElement(id, 0);
}

function selectElement(id, valueToSelect) {    
    let element = document.getElementById(id);
    element.value = valueToSelect;
}

function openDash(dashName) {
    var i;
    var x = document.getElementsByClassName("container-fluid");
    for (i = 0; i < x.length; i++) {
        if(x[i].id != "foot" && x[i].id != "modal")   x[i].style.display = "none";  
    }
    var dash = document.getElementById(dashName);
    dash.style.display = "block";  
}

// Script to open and close sidebar
function w3_open() {
    document.getElementById("mySidebar").style.display = "block";
    document.getElementById("myOverlay").style.display = "block";
}

function w3_close() {
    document.getElementById("mySidebar").style.display = "none";
    document.getElementById("myOverlay").style.display = "none";
}
function search(id1, id2) {
    // Declare variables 
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById(id1);
    filter = input.value.toUpperCase();
    table = document.getElementById(id2);
    tr = table.getElementsByTagName("tr");

    // Loop through all table rows, and hide those who don't match the search query

    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[2];
      if (td) {
        txtValue = td.textContent || td.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      } 
    }
}
function sortTable(myTable) {
  var table, rows, switching, i, x, y, shouldSwitch, count;
  count = 0;
  table = document.getElementById(myTable);
  switching = true;
  /*Make a loop that will continue until
  no switching has been done:*/
  while (switching) {
    //start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    console.log(rows);
    /*Loop through all table rows (except the
    first, which contains table headers):*/
    for (i = 0; i < (rows.length - 1); i++) {
      //start by saying there should be no switching:
      shouldSwitch = false;
      /*Get the two elements you want to compare,
      one from current row and one from the next:*/
      x = rows[i].getElementsByTagName("TD")[1];
      y = rows[i + 1].getElementsByTagName("TD")[1];
      //check if the two rows should switch place:
      if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
        //if so, mark as a switch and break the loop:
        count++;
        shouldSwitch = true;
        break;
      }
    }
    if (shouldSwitch) {
      /*If a switch has been marked, make the switch
      and mark that a switch has been done:*/
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
    }
  }
  if(count == 0){
    switching = true;
    /*Make a loop that will continue until
    no switching has been done:*/
    while (switching) {
      //start by saying: no switching is done:
      switching = false;
      rows = table.rows;
      /*Loop through all table rows (except the
      first, which contains table headers):*/
      for (i = 1; i < (rows.length - 1); i++) {
        //start by saying there should be no switching:
        shouldSwitch = false;
        /*Get the two elements you want to compare,
        one from current row and one from the next:*/
        x = rows[i].getElementsByTagName("TD")[1];
        y = rows[i + 1].getElementsByTagName("TD")[1];
        //check if the two rows should switch place:
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
          //if so, mark as a switch and break the loop:
          //count++;
          shouldSwitch = true;
          break;
        }
      }
      if (shouldSwitch) {
        /*If a switch has been marked, make the switch
        and mark that a switch has been done:*/
        rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
        switching = true;
      }
    }
  }
}

function openDashGeneralDoctor(dashName) {
    document.getElementById(dashName).style.display = "block";  
}

function openTickets(){
    document.getElementById("Tickets").style.display = "block";  
}

function openExaminations(){ 
    document.getElementById("Examinations").style.display = "block";  
}

function openExams(){
    document.getElementById("Exams").style.display = "block";  
}

function testpass(modulo){
  // Verifico che il campo password sia valorizzato in caso contrario
  // avverto dell'errore tramite un Alert
  if (modulo.password.value == ""){
    alert("Errore: inserire una password!");
    modulo.password.focus();
    return false;
  }
  // Verifico che le due password siano uguali, in caso contrario avverto
  // dell'errore con un Alert
  if (modulo.password.value !== modulo.password_2.value) {
    alert("La password inserita non coincide con la prima!");
    modulo.password.focus();
    modulo.password.select();
    return false;
  }
  return true;
}


function testpassExaminations(modulo){
  // Verifico che il campo password sia valorizzato in caso contrario
  // avverto dell'errore tramite un Alert
  if (modulo.patientExaminations.value == "0"){
    alert("Error: choose the patient!");
    modulo.patientExaminations.focus();
    return false;
  }
  
  if (modulo.type.value == "0"){
    alert("Error: choose the type!");
    modulo.type.focus();
    return false;
  }
  
  if (modulo.doctor.value == "0"){
    alert("Error: choose the doctor!");
    modulo.doctor.focus();
    return false;
  }
  
  if (modulo.analysis.value == ""){
    alert("Error: write an analysis!");
    modulo.analysis.focus();
    return false;
  }
  
  if (modulo.date.value == ""){
    alert("Error: write a date!");
    modulo.date.focus();
    return false;
  }
  
  if (modulo.time.value == ""){
    alert("Error: write a time!");
    //modulo.time.focus();
    return false;
  }
  
  return true;
}

function testpassExams(modulo){
  // Verifico che il campo password sia valorizzato in caso contrario
  // avverto dell'errore tramite un Alert
  if (modulo.patientExams.value == "0"){
    alert("Error: choose the patient!");
    modulo.patientExams.focus();
    return false;
  }
  
  if (modulo.doctor.value == "0"){
    alert("Error: choose the doctor!");
    modulo.doctor.focus();
    return false;
  }
  
  if (modulo.recall.value == "0"){
    alert("Error: select if it is a recall!");
    modulo.recall.focus();
    return false;
  }
  
  if (modulo.analysis.value == "0"){
    alert("Error: write an analysis!");
    modulo.analysis.focus();
    return false;
  }
  
  if (modulo.date.value == ""){
    alert("Error: write a date!");
    modulo.date.focus();
    return false;
  }
  
  if (modulo.time.value == ""){
    alert("Error: write a time!");
    //modulo.time.focus();
    return false;
  }
  
  return true;
}

function testpassDrugs(modulo){
  // Verifico che il campo password sia valorizzato in caso contrario
  // avverto dell'errore tramite un Alert
  if (modulo.patientDrugs.value == "0"){
    alert("Error: choose the patient!");
    modulo.patientDrugs.focus();
    return false;
  }
  
  return true;
}

function putMinDate(){
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();
    if(dd<10){
        dd='0'+dd
    } 
    if(mm<10){
        mm='0'+mm
    }
    today = yyyy+'-'+mm+'-'+dd;
    var dateFields = document.getElementsByName("date");
    for(i=0; i<dateFields.length; i++){
        dateFields[i].setAttribute("min", today);
    }
}

function testpassUploadPhoto(modulo){
    if (modulo.myfile.value == ""){
    alert("Error: choose an image!");
    //modulo.myfile.focus();
    return false;
  }
  
  return true;
}