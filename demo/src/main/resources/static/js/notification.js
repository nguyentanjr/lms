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

function showNotification(message) {
    $.ajax({
        url: "/get-all-notification-current-user",
        method: "get",
        success: function (response) {
            $('.notification-list').empty();
            response.forEach(function (notification)         {
                let message = notification.message;
                let timestamp = notification.timestamp;
                let list = `
                            <div class="notification-card card">
                            <div class="card-body d-flex align-items-center">
                                <div class="notification-icon bg-info mr-3">
                                    <i class="fas fa-user"></i>
                                </div>
                                <div class="flex-grow-1">
                                    <b class="mb-1">Public Notification</b>
                                    <p class="mb-1">${message}</p>
                                    <small class="timestamp">
                                        <i class="far fa-clock mr-1"></i>${timestamp}
                                    </small>
                                </div>
                            </div>
                        </div>
    `
                $('.notification-list').prepend(list);
            })

        }
    });
}
function checkCondition() {
    $.ajax({
        url: "/process-reservation",
        method: "get"
    })
}
$(document).ready(function() {
    connect();
    //setInterval(checkCondition,2000);
    $.ajax({
        url: "/get-all-notification-current-user",
        method: "get",
        success: function (response) {
            response.forEach(function (notification) {
                let message = notification.message;
                let timestamp = notification.timestamp;
                let list = `
    <div class="notification-card card">
                            <div class="card-body d-flex align-items-center">
                                <div class="notification-icon bg-info mr-3">
                                    <i class="fas fa-user"></i>
                                </div>
                                <div class="flex-grow-1">
                                    <b class="mb-1">Private Announcement</b>
                                    <p class="mb-1">${message}</p>
                                    <small class="timestamp">
                                        <i class="far fa-clock mr-1"></i>${timestamp}
                                    </small>
                                </div>
                            </div>
                        </div>
    `
                $('.notification-list').prepend(list);
            })

        }
    })
});