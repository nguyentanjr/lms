/*
   USER CONTROLLER
    */

/* Choose which modal to display */
let currentRow;
let bookId;

$(document).ready(function () {
    let url = new URLSearchParams(window.location.search);
    if (url.get("showCart") === 'true') {
        $(".myCart").click();
    } else if (url.get("showBorrowedBook") === 'true') {
        $(".view-user-book-borrowed").click();
    }
    else if (url.get("showReservation") === 'true') {
        $(".view-reserved-book").click();
    }
    else if(url.get("showBorrowedHistory") === 'true') {
        $(".view-borrowed-history").click();
    }
    $.ajax({
        url: "/getTokenFromCookie",
        method: "get"
    })
    $.ajax({
        url: "/process-reservation",
        method: "get",
    })
    $(".row-list").each(function () {
        let row = $(this);
        let bookId = row.find(".book-id").text();
        $.ajax({
            url: "/isUser",
            method: "get",
            success: function (response) {
                if (response) {
                    $.ajax({
                        url: "/check-hide-book",
                        method: "get",
                        data: {bookId: bookId},
                        success: function (response) {
                            if (response) {
                                row.addClass("hidden-row");
                            }
                        }
                    })
                }
            }
        })
    })
})

function outputBorrow() {
    $(document).on("click", ".borrow", function () {
        currentRow = $(this).parents("tr");
        bookId = currentRow.find(".book-id").text();
        $.ajax({
            url: "/book_list/borrowed_check/" + bookId,
            method: "get",
            success: function (response) {
                if (response) {
                    $("#hasBorrowed").modal("show");
                } else {
                    $("#confirm-modal").modal("show");
                }
            }
        })

    });
}

/* Handle confirm */
$(document).on("click", ".confirm", function () {

    $.ajax({
        url: "/book_list/get_copies/" + bookId,
        method: "get",
        success: function (response) {
            if (response <= 0) {
                $("#confirm-modal").modal("hide");
                alert("Book copies = 0!");
            } else {
                let availableCopies = response - 1;
                $.ajax({
                    url: "/book_list/set_copies",
                    method: "get",
                    data: {
                        copies: availableCopies,
                        bookId: bookId
                    },
                })
                if (availableCopies === 0) {
                    currentRow.find(".success").hide();
                    currentRow.find(".borrow").hide();
                    currentRow.find(".cart").hide();
                    let status = `
                <span class="badge badge-danger danger">Checked Out</span> 
`
                    currentRow.find(".status").html(status);
                }
                $("#confirm-modal").modal("hide");
                $("#successModalBorrow").modal("show");
            }
        }
    })

})
/*
Add to cart
 */
$(document).on("click", ".cart", function () {
    currentRow = $(this).parents("tr");
    bookId = currentRow.find(".book-id").text();
    toastr.options = {
        "closeButton": true,
        "newestOnTop": true,
        "positionClass": "toast-top-right",
        "preventDuplicates": true,
        "timeOut": "5000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
    $.ajax({
        url: "/check-book-in-cart",
        method: "get",
        data: {bookId: bookId},
        success: function (response) {
            if (response === "has borrowed") {
                toastr.warning("ID " + bookId + ": You had already borrowed this book!");
            }
            if (response === "in cart") {
                toastr.error("ID " + bookId + ": You had already add it to cart!");
            }
            if (response === "not in cart") {
                toastr.success("ID " + bookId + ": Successful add to cart");
            }
        }
    })
    $.ajax({
        url: "/add_to_cart",
        method: "get",
        data: {bookId: bookId},
        success: "OK"
    })
})
/*
show list cart
 */
$(document).on("click", ".myCart", function () {
    $("#cart").modal("show");
    $.ajax({
        url: "/cart_list",
        method: "get",
        success: function (response) {
            if (response.length === 0) {
                $(".cart-confirm").hide();
            } else {
                $(".cart-confirm").show();
            }
            $(".cart-table tbody").empty();
            response.forEach(item => {
                let row = `
                        <tr data-item-id = ${item.id} class="text-center">
                            <td>${item.id}</td>
                            <td>${item.title}</td>
                            <td>${item.authors}</td>
                            <td>${item.categories}</td>
                            
                            <td>
                            <button type="button" class="cart-remove-book">
                            <span>&times;</span>
                            </button>
                            </td>
                        </tr>
`;
                $(".cart-table tbody").append(row);
            });
            $(document).on("click", ".cart-remove-book", function () {
                $(this).closest("tr").remove();
                var itemId = $(this).closest("tr").data("item-id");
                $.ajax({
                    url: "/remove-book-in-cart",
                    method: "get",
                    data: {bookId: itemId},
                    success: function (response) {
                        if (response.length === 0) {
                            $(".cart-confirm").hide();
                        }
                    }
                })
            })
            $(document).on("click", ".cart-confirm", function () {
                $("#cart").modal("hide");
                $("#successModalBorrow").modal("show");
                $(".cart-table tbody").empty();
                $.ajax({
                    url: "/cart-borrow",
                    method: "post",
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(response)
                })
                response = {};
            })
        }
    })
})


/*
ADMIN CONTROLLER
 */

//handle show hide
$(document).ready(function () {
    $(".row-list").each(function () {
        let bookId = $(this).find(".book-id").text();
        let row = $(this);
        $.ajax({
            url: "/check-hide-book",
            method: "get",
            data: {bookId: bookId},
            success: function (response) {
                if (response === true) {
                    row.find(".hide-book").text("Show");
                } else {
                    row.find(".hide-book").text("Hide");
                }
            }
        })
    })
})
$(document).on("click", ".hide-book", function () {
    let bookId = $(this).closest("tr").find(".book-id").text();
    if ($(this).text() === "Hide") {
        $(this).text("Show");
        $.ajax({
            url: "/set-hide-book",
            method: "get",
            data: {
                status: "hide",
                bookId: bookId
            }
        })
    } else {
        $(this).text("Hide");
        $.ajax({
            url: "/set-hide-book",
            method: "get",
            data: {
                status: "show",
                bookId: bookId
            }
        })
    }
})
$(document).on("click", ".remove-book", function () {
    let editButton = $(this).parents("tr");
    let bookId = editButton.find(".book-id").text();
    $("#remove-modal").modal("show");
    $(document).on("click", ".remove-confirm", function () {
        $("#remove-modal").modal("hide");
        editButton.hide();
        $("#successModalRemoving").modal("show");
        $.ajax({
            url: "/remove-book",
            method: "get",
            data: {bookId: bookId}
        })
    })
})
$(document).on("click", ".edit-book", function () {
    $("#editBookModal").modal("show");
    //find attribute to add it on placeholder
    let row = $(this).closest("tr");
    let bookId = $(this).closest("tr").find(".book-id").text();
    let title = $(this).closest("tr").find(".book-title").text();
    let authors = $(this).closest("tr").find(".book-authors").find("li").map(function () {
        return $(this).text().trim();
    }).get().join(',');
    let categories = $(this).closest("tr").find(".book-categories").find("li").map(function () {
        return $(this).text().trim();
    }).get().join(',');
    let publishedDate = $(this).closest("tr").find(".book-publishedDate").text();

    //add it on placeholder
    $("#titleInput").attr("placeholder", title);
    $("#authorsInput").attr("placeholder", authors);
    $("#categoriesInput").attr("placeholder", categories);
    $("#publishedDateInput").attr("placeholder", publishedDate);
    $(document).on("click", ".save-changes", function () {
        $("#editBookModal").modal("hide");
        $("#successModalEditing").modal("show");
        //get attribute from input
        let title = $("#titleInput").val();
        let authors = $("#authorsInput").val();
        let categories = $("#categoriesInput").val();
        let publishedDate = $("#publishedDateInput").val();
        let authorsArray;
        let categoriesArray;

        //check if present
        if (title.trim() !== "") {
            row.find(".book-title").text(title);
        }
        if (authors.trim() !== "") {
            authorsArray = authors.split(',').map(item => item.trim())
            row.find(".book-authors").html(authorsArray.join("<br>"));
        }
        if (categories.trim() !== "") {
            categoriesArray = categories.split(',').map(item => item.trim());
            row.find(".book-categories").html(categoriesArray.join("<br>"));
        }
        if (publishedDate.trim() !== "") {
            row.find(".book-publishedDate").text(publishedDate);
        }
        $.ajax({
            url: "/edit-book",
            method: "post",
            contentType: "application/json",
            data: JSON.stringify({
                id: bookId,
                title: title,
                authors: authorsArray,
                categories: categoriesArray,
                publishedDate: publishedDate
            })
        })
    })
})

$(document).ready(function () {
    $(document).on("input", "#searchInput", function () {
        let query = $(this).val();
        if (query.length > 0) {
            $.ajax({
                url: "/suggest-book",
                method: "get",
                data: {query: query},
                success: function (response) {
                    $("#searchSuggestion").empty();
                    for (let i = 0; i < Math.min(response.length, 5); i++) {
                        let randomNumber = Math.floor(Math.random() * (response.length - 1));
                        $("#searchSuggestion").append(` <div class="suggestion-item">
                                <div class="d-flex align-items-center">
                                    <i class="fas fa-book me-2"></i>
                                    <div>
                                        <div class="fw-bold title">${response[i]}</div>
                                    </div>
                                </div>
                            </div>`);
                    }
                    if ($("#filterType").val() === 'title') {
                        $("#searchSuggestion").show();
                    }
                }
            })
        } else {
            $("#searchSuggestion").hide();
        }
        $(document).on("click", ".suggestion-item", function () {
            let title = $(this).find(".title").text();
            title = encodeURIComponent(title);
            window.location.href = "/book_list/find?title=" + title;
        })
    })
})

$(document).on("click", ".view-user-book-borrowed", function () {
    $("#userHasBorrowed").modal("show");
    $.ajax({
        url: "/get_user_id",
        method: "get",
        success: function (userId) {
            console.log(111);
            $.ajax({
                url: "/show-books-user-borrowed-for-user",
                method: "get",
                data: {userId: userId},
                success: function (response) {
                    $(".list-borrowed").empty();
                    response.forEach(item => {
                        let currentDate = new Date();
                        let dueDate = new Date(item.dueDate);
                        let dateStatus = currentDate > dueDate ? "Past Due Date" : "Within Due Date";
                        let returnStatus = !item.returnStatus ? "Return" : "Returned";
                        let buttonHtml;
                        if (returnStatus === "Return") {
                            buttonHtml = `<button class="btn btn-primary return">${returnStatus}</button>`;
                        } else {
                            buttonHtml = `<button class="btn btn-secondary return" disabled>${returnStatus}</button>`;
                        }
                        let authorsHtml = item.authors.map(author => `<li>${author}</li>`).join("");
                        let categoriesHtml = item.categories.map(category => `<li>${category}</li>`);
                        let row = `
                <tr data-book-id = ${item.id} class="return-row">
                            <td class="item-id">${item.id}</td>
                            <td>${item.title}</td>
                            <td>${item.publishedDate}</td>
                            <td class="list-unstyled">${categoriesHtml}</td>
                            <td class="list-unstyled">${authorsHtml}</td>
                            <td>${item.dateBorrowed}</td>
                            <td>${item.dueDate}</td>
                            <td>
                            <div class="borrowed-status">${dateStatus}</div>
                            </td>
                            <td class="status-button">${buttonHtml}
                            </td>
                        </tr>
                `
                        $(".list-borrowed").append(row);
                        $("#return-modal").data("book-id", item.id);
                        $("tr[data-book-id]").each(function() {
                            let status = $(this).find("td:eq(7)").text().trim();
                            console.log(status);
                            if(status === "Within Due Date") {
                                $(this).find(".borrowed-status").addClass("badge bg-success filter-badge me-2 mb-2");
                            }
                            else {
                                $(this).find(".borrowed-status").addClass("badge bg-warning filter-badge me-2 mb-2");
                            }
                        })
                        if (response.length === 0) {
                            $(".return-all").hide();
                        }
                    })
                }
            })
        }
    })
            $(document).on("click", ".return", function () {
        $("#return-modal").modal("show");
        let row = $(this).closest("tr");
        let bookId = row.find(".item-id").text();
        $("#return-modal").data("bookId", bookId);
    })
    $(document).on("click", ".return-confirm", function () {
        $("#return-modal").modal("hide");
        let bookId = $("#return-modal").data("bookId");
        bookId = parseInt(bookId);
        let row = $(".return-row").filter(function () {
            return $(this).data("book-id") === bookId;
        })
        row.find(".status-button").html(`<button class="btn btn-secondary return" disabled>Returned</button>`);
        $.ajax({
            url: "/save-history-borrowed",
            method: "get",
            data: {bookId: bookId}
        })
            .then(function() {
                return $.ajax({
                    url: "/return-book",
                    method: "post",
                    data: {bookId: bookId},
                    success: function() {
                        console.log("return success");
                    }
                });
            })
            .then(function() {
                return $.ajax({
                    url: "/process-reservation",
                    method: "get",
                    success: function() {
                        console.log("Process reservation completed");
                    }
                });
            })
        $(document).on("click", ".cancel", function () {
            location.reload();
        })

    })
})


$(document).on("click", ".filter-badge", function () {
    let filterType = $(this).data("filter");
    $("#filterType").val(filterType);
    if (filterType === "id") {
        $("#searchInput").attr("placeholder", "Search by id");
    } else if (filterType === "title") {
        $("#searchInput").attr("placeholder", "Search by title");
    } else if (filterType === "author") {
        $("#searchInput").attr("placeholder", "Search by author");
    } else if (filterType === "category") {
        $("#searchInput").attr("placeholder", "Search by category");
    }
})


$(document).on("click", ".placeAHold", function () {
    let bookId = $(this).closest("tr").data("row-id");
    $.ajax({
        url: "/book_list/borrowed_check/" + bookId,
        method: "get",
        success: function (response) {
            if (response) {
                $("#hasBorrowed").modal("show");
            } else {
                toastr.options = {
                    "closeButton": true,
                    "newestOnTop": true,
                    "positionClass": "toast-top-right",
                    "preventDuplicates": true,
                    "timeOut": "5000",
                    "showEasing": "swing",
                    "hideEasing": "linear",
                    "showMethod": "fadeIn",
                    "hideMethod": "fadeOut"
                }
                $.ajax({
                    url: "/check-user-has-reserve-book",
                    method: "get",
                    data: {bookId: bookId},
                    success: function (response) {
                        if (response === "Duplicate") {
                            toastr.error("ID " + bookId + ": You have been reserved this book!")
                        } else {
                            toastr.success("ID " + bookId + ": Reserve book successfully");
                            $.ajax({
                                url: "/reserve-book",
                                method: "post",
                                data: {bookId: bookId},
                                success: function (response) {
                                }
                            })
                        }
                    }
                })

            }
        }
    })

})
$(document).on("click", ".view-reserved-book", function () {
    $.ajax({
        url: "/process-reservation",
        method: "get",
    })
    $("#reservedBook").modal("show");
    $(".list-reserved").empty();
    $.ajax({
        url: "/get_user_id",
        method: "get",
        success: function (userId) {
            $.ajax({
                url: "/show-reserve-book",
                method: "get",
                data: {userId: userId},
                success: function (response) {
                    response.forEach(item => {
                        let row = `
                <tr data-reserve-id = ${item.id}>
                            <td>${item.id}</td>
                            <td>${item.title}</td>
                            <td>${item.reservationDate}</td>
                            <td>
                            <div class="reserve-status">${item.status}</div>
                            </td> 
                            <td>
                            <button type="button" class="reserve-remove-book">
                            <span>&times;</span>
                            </button>
                            </td>
                        </tr>
                `
                        $(".list-reserved").append(row);
                    })
                    $("tr[data-reserve-id]").each(function() {
                        let status = $(this).find("td:eq(3)").text().trim();
                        if(status === "Approved") {
                            $(this).find(".reserve-status").addClass("badge bg-success filter-badge me-2 mb-2");
                            $(this).find(".reserve-remove-book").hide();
                        }
                        else {
                            $(this).find(".reserve-status").addClass("badge bg-warning filter-badge me-2 mb-2");
                        }
                    })
                }
            })
        }
    })
})
$(document).on("click", ".reserve-remove-book", function () {
    $(this).closest("tr").remove();
    let bookId = $(this).closest("tr").data("reserve-id");
    $.ajax({
        url: "/remove-book-in-reserve-list",
        method: "get",
        data: {bookId: bookId},
    })
})

$(document).on("click", ".view-borrowed-history", function () {
    $("#historyBorrowed").modal("show");
    $(".list-borrow-history").empty();
    $.ajax({
        url: "/get_user_id",
        method: "get",
        success: function (userId) {
            $.ajax({
                url: "/show-borrowed-history",
                method: "get",
                data: {userId: userId},
                success: function (response) {
                    $(".list-borrow-history").empty();
                    response.forEach(item => {
                        let categoriesHtml = item.bookCategories.map(category => `<li>${category}</li>`).join("");
                        let authorsHtml = item.bookAuthors.map(author => `<li>${author}</li>`).join("");
                        let row = `
                <tr data-history-id = ${item.id}>
                            <td>${item.bookID}</td>
                            <td>${item.title}</td>
                            <td>${item.publishedDate}</td>
                            <td class="list-unstyled">${categoriesHtml}</td>
                            <td class="list-unstyled">${authorsHtml}</td>
                            <td>${item.dateBorrowed}</td>
                            <td>${item.dueDate}</td>
                            <td>${item.dateReturn}</td>
                            <td>
                            <div class="history-status">${item.returnStatus}</div>
                            </td>
                        </tr>
                `
                        $(".list-borrow-history").append(row);
                        $("tr[data-history-id]").each(function() {
                            let status = $(this).find("td:eq(8)").text().trim();
                            if(status === "On Time") {
                                $(this).find(".history-status").addClass("badge bg-success filter-badge me-2 mb-2");
                            }
                            else {
                                $(this).find(".history-status").addClass("badge bg-warning filter-badge me-2 mb-2");
                            }
                        })
                        //$("#return-modal").data("book-id", item.id);
                    })
                }
            })
        }
    })
})

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
        showNotification(notif.message);
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