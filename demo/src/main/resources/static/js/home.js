$(document).ready(function () {
    $.ajax({
        url: "/count-users-online",
        method: "get",
        success: function (response) {
            let stat = "We have " +  response[0] + " registered online | " + response[1] + " new registers today";
            $(".stat").text(stat);
        },
    })

})