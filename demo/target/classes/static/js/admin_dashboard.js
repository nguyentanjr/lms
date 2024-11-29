


let stompClient;

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/notifications', function (notification) {
            const message = JSON.parse(notification.body).message;
            //  console.log(message);
            showNotification(message);
        });

        stompClient.subscribe('/user/queue/private', function (notification) {
            const message = 'Private: ' + JSON.parse(notification.body).message;
            console.log(message);
            showNotification(message);
        });
    });
}



$(document).ready(function() {
    connect();

})

$(document).on("click", ".push-information", function () {
    $(".notification-modal").modal("show");
    console.log(34);
});

