// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTableExams').DataTable();
  $('#dataTableScreamExams').DataTable();
  $('#dataTableScreamExaminations').DataTable();
  $('#dataTableExaminations').DataTable();
  $('#dataTableScreamTickets').DataTable();
  $('#dataTableTickets').DataTable();
  $('#dataTableRecipes').DataTable();
  $('#dataTableDoctors').DataTable();
  $('#dataTablePatients').DataTable();
  $('#dataTablePatientsSpecialist').DataTable();
  $('#dataTableConfirmExaminations').DataTable();
  $('#dataTableConfirmExams').DataTable();
  $('#dataTableRecipesSsp').DataTable();
  var tablesEx = document.getElementsByName('dataTablePatientsExams');
  for(i=0;i<tablesEx.length; i++){
      $('#'+tablesEx[i].id).DataTable();
  }
  var tablesExm = document.getElementsByName('dataTablePatientsExaminations');
  for(i=0;i<tablesExm.length; i++){
      $('#'+tablesExm[i].id).DataTable();
  }
});
