/**
 * 
 */
$(document).ready(function() {

	$('#confirmModal').on('show.bs.modal', function(e) {
		var zeitmodellid = $(e.relatedTarget).data('id');
		var zeitmodellbezeichnung = $(e.relatedTarget).data('label');
		$("#zeitmodellbezeichnung").text(zeitmodellbezeichnung);
		$("#zeitmodellid").attr("href", "/zeitmodell/loeschen/" + zeitmodellid)
	});
	$("#myInput").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		$("#myTable tr").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
	
	$('#ordertable').DataTable(
			{
			    "ordering": true,
			    "searching": false,
			    "paging": false,
			    "bInfo" : false
			    
			}		
	);
	
});

// $(".modal-body").html(carid);
// // Do Whatever you like to do,
