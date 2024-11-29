$(document).on("click",".push-information",function () {
    $(".notification-modal").modal("show");
})

$(document).on("click",".submit",function () {
    const name = $('#userName').val();
    const email = $('#userEmail').val();

    const formData = {
        message: name,
        email: email
    };
    $.ajax({
        url: '/api/notifications/send',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(formData),
        success: function (response) {
            console.log('Success:', response);
            $('#inputModal').modal('hide');
        },
    });
})

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
