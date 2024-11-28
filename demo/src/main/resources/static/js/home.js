$(document).ready(function () {
    $.ajax({
        url: "/count-users-online",
        method: "get",
        success: function (response)                 {
            let stat = "We have " +  response[0] + " registered online | " + response[1] + " new registers today";
            $(".stat").text(stat);
        },
    })

})

var socket = new SockJS('/ws'); // Kết nối đến endpoint WebSocket
var stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);

    // Subscribe vào topic để nhận thông báo
    stompClient.subscribe('/topic/notifications', function (messageOutput) {
        var message = JSON.parse(messageOutput.body);
        displayNotification(message);
    });
});

// Hàm hiển thị thông báo lên trang web
function displayNotification(message) {
    var notificationsDiv = document.getElementById("notifications");
    var notificationDiv = document.createElement("div");
    notificationDiv.textContent = message.content;
    notificationsDiv.appendChild(notificationDiv);
}
