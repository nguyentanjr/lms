
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
    let row = $(this).closest("tr");
    let bookId = row.data("row-id");
    $("#remove-modal").modal("show");
    $("#remove-modal").data("bookId", bookId);
});
    $(document).on("click", ".remove-confirm", function () {
        let bookId = $("#remove-modal").data("bookId");
        console.log(bookId);
        let row = $("tr").filter(function () {
            return $(this).data("row-id") === bookId    ;
        });
        console.log(row);
        row.fadeOut();
        $("#remove-modal").modal("hide");
        $("#successModalRemoving").modal("show");
        $.ajax({
            url: "/admin/remove-book",
            method: "get",
            data: {bookId: bookId}
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

    $("#titleInput").attr("placeholder", title);
    $("#authorsInput").attr("placeholder", authors);
    $("#categoriesInput").attr("placeholder", categories);
    $("#publishedDateInput").attr("placeholder", publishedDate);
    $(document).on("click", ".save-changes", function () {
        $("#editBookModal").modal("hide");
        $("#successModalEditing").modal("show");

        let title = $("#titleInput").val();
        let authors = $("#authorsInput").val();
        let categories = $("#categoriesInput").val();
        let publishedDate = $("#publishedDateInput").val();
        let authorsArray;
        let categoriesArray;

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
            url: "/admin/edit-book",
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
                                <div class="d-flex align-items-center ">
                                    <i class="fas fa-book me-2"></i>
                                    <div>
                                        <div class="fw-bold title">${response[i]}</div>
                                    </div>
                                </div>
                            </div>`);
                    }
                    $("#searchSuggestion").css("width", "800");
                    $("#searchSuggestion").css("opacity", "0.9");
                    $("#searchSuggestion").show();

                }
            })
        } else {
            $("#searchSuggestion").hide();
        }
        $(document).on("click", ".suggestion-item", function () {
            let title = $(this).find(".title").text();
            title = encodeURIComponent(title);
            window.location.href = "/admin/book_list/find?title=" + title;
        })
    })
})

$(document).on("click",".add-book",function () {
    $("#addBookModal").modal("show");
    $(document).on("click", "#saveChanges", function () {
        $("#addBookModal").modal("hide");
        $("#successModalAdding").modal("show");
        let title = $("#titleInputForAdding").val();
        let authors = $("#authorsInputForAdding").val();
        let categories = $("#categoriesForAdding").val();
        let publishedDate = $("#publishedDateInputForAdding").val();
        let quantity = $("#quantityInputForAdding").val();
        let authorsArray;
        let categoriesArray;


        if (authors.trim() !== "") {
            authorsArray = authors.split(',').map(item => item.trim())
        }
        if (categories.trim() !== "") {
            categoriesArray = categories.split(',').map(item => item.trim());
        }
        console.log(title);
        console.log(authorsArray);
        console.log(categoriesArray);
        console.log(publishedDate);
        console.log(quantity);


        let data = {
            title : title,
            authors : authorsArray,
            categories : categoriesArray,
            publishedDate : publishedDate,
            copies_available : quantity
        }

        $.ajax({
            url: "/admin/add-book",
            method: "post",
            contentType: "application/json",
            data: JSON.stringify(data)
        })
    })
})

$(document).on("click", ".filter-badge", function () {
    let filterType = $(this).data("filter");
    let input = $("#searchInput").val();
    $("#filterType").val(filterType);
    if(filterType === "id") {
        $("#searchInput").attr("placeholder","Search by id");
    }
    else if(filterType === "title") {
        $("#searchInput").attr("placeholder","Search by title");
    }
    else if(filterType === "author") {
        $("#searchInput").attr("placeholder","Search by author");
    }
    else if(filterType === "category") {
        $("#searchInput").attr("placeholder","Search by category");
    }

})