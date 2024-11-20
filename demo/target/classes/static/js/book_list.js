/*
   USER CONTROLLER
    */

/* Choose which modal to display */
let currentRow;
let bookId;

$(document).ready(function () {
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
                console.log(status);
            }
        }
    })
    $("#confirm-modal").modal("hide");
    $("#successModalBorrow").modal("show");
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
        data: {bookId : bookId},
        success: "OK"
    })
})
/*
show list cart
 */
$(document).ready(function () {
    let url = new URLSearchParams(window.location.search);
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
                        <tr data-item-id = ${item.id}>
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
    if(url.get("showCart") === 'true') {
        $(".myCart").click();
    }
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
                    $("#searchSuggestion").show();
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

