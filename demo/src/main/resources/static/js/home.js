$(document).ready(function () {
    setUnreadNotification();

})

function countUnreadNotification() {
    $.ajax({
        url: "/process-reservation",
        method: "get",
    })
    $.ajax({
        url: "/count-unread-notification",
        method: "get",
        success: function(response) {
            $('#unreadNotificationCount').text(response);
        }
    })
}


let socket = new SockJS('/ws');
let stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log('Connected to WebSocket: ' + frame);

    stompClient.subscribe('/topic/notifications', function (notification) {
        const notif = JSON.parse(notification.body);
        setUnreadNotification();
    });

    stompClient.subscribe('/user/queue/private', function (notification) {
        const message = JSON.parse(notification.body).message;
        console.log(message);
        setUnreadNotification();
    });
}, function (error) {
    console.error('WebSocket connection error: ', error);
});


function setUnreadNotification() {
    $.ajax({
        url: "/count-unread-notification",
        method: "get",
        success: function(response) {
            $('#unreadNotificationCount').text(response);
        }
    })
}
