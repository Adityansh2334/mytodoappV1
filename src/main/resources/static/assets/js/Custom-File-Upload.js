var fileInput = document.getElementById('user_group_logo');

fileInput.onchange = function(e){
    var fullPath = fileInput.value;
    if (fullPath) {
        var startIndex = (fullPath.indexOf('\\') >= 0 ? fullPath.lastIndexOf('\\') : fullPath.lastIndexOf('/'));
        var filename = fullPath.substring(startIndex);
        if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0) {
            filename = filename.substring(1,9);
        }
        $('#user_group_label').text(filename);
    }
};
//Other Hacks Below
$("#login1").click(function() {
    $('#signin').modal('hide');
    $('html, body').animate({
        scrollTop: $("#signinsec").offset().top
    }, 2000);
});

// $('.navbar-collapse login').click(function(){
//     $(".navbar-collapse").collapse('hide');
// });

function navHide(){
    $(".navbar-collapse").collapse('hide');
}
