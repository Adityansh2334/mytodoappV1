$(function(){
    $('#modal-open').modal({
        keyboard: true,
        backdrop: "static",
        show:false,
        
    }).on('show', function(){
          var getIdFromRow = $(event.target).closest('td').data('id');
        //make your ajax call populate items or what even you need
        $(this).find('#details').html($('67'+getIdFromRow+'hh'))
    });
});