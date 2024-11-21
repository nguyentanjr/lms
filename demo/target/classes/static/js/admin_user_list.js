$(document).on("click", ".remove-user", function () {
    let row = $(this).closest("tr");
    let userId = row.data("user-id");
    $("#remove-modal").modal("show");
    $("#remove-modal").data("userId", userId);
});
$(document).on("click", ".remove-confirm", function () {
    let userId = $("#remove-modal").data("userId");
    let row = $("tr").filter(function () {
        return $(this).data("userId") === userId;
    })
    let editRow = row.next(".edit-row");
    if(editRow.length !== 0) {
        editRow.fadeOut();
    }
    $("#remove-modal").modal("hide");
    $.ajax({
        url: "/remove-user",
        method: "get",
        data: {userId: userId},
        success: function (response) {
            console.log(response);
            if (response) {
                row.fadeOut();
                $("#successModalRemoving").modal("show");
            } else {
                $("#errorModalRemoving").modal("show");
            }
        }
    })
})

$(document).on("click", ".edit-user", function () {

    let rowList = $(this).closest("tr");
    let userId = rowList.find(".user-id").text();
    let username = rowList.find(".user-username").text();
    let fullName = rowList.find(".user-fullName").text();
    let email = rowList.find(".user-email").text();
    let phoneNumber = rowList.find(".user-phoneNumber").text();
    let gender = rowList.find(".user-gender").text();
    let registrationDate = rowList.find(".user-registrationDate").text();
    let role = rowList.find(".user-role").text();
    let editRow = rowList.next(".edit-row");

    if (editRow.length > 0) {
        $(".edit-row").remove();
    } else {
        let row = `
        <tr class="edit-row">
            <td>
                <input type="text" class="form-control userId text-center" value="${userId}" readonly>
            </td>
            <td>
                <input type="text" class="form-control username text-center" value="${username}" readonly>
            </td>
            <td>
                <input type="text" class="form-control fullName text-center" value="${fullName}">
            </td>
            <td>
                <input type="email" class="form-control email text-center" value="${email}">
            </td>
            <td>
                <input type="tel" class="form-control phoneNumber text-center" pattern="^[0-9]{10}$" value="${phoneNumber}">
            </td>
            <td>
                <select class="form-control gender text-center">
                    <option value="Male" ${gender === 'Male' ? 'selected' : ''}>Male</option>
                    <option value="Female" ${gender === 'Female' ? 'selected' : ''}>Female</option>
                    <option value="Other" ${gender === 'Other' ? 'selected' : ''}>Other</option>
                </select>
            </td>
            <td>
                <input type="text" class="form-control registrationDate text-center" value="${registrationDate}" readonly>
            </td>
            <td>
                <input type="text" class="form-control role text-center" value="${role}" readonly>
            </td>
           <td>
                <button type="submit" class="btn btn-primary update mr-2">Update</button>
            </div>
    </td>
        </tr>
    `;
        rowList.after(row);
        $(document).on("click", ".close", function () {
            $(this).closest("tr").remove();
        })
    }
});


$(document).on("click", ".update", function (e) {
    e.preventDefault();
    let rowList = $(this).closest("tr");
    let userId = rowList.find(".userId").val();
    let email = rowList.find(".email").val();
    let phoneNumber = rowList.find(".phoneNumber").val();
    let fullName = rowList.find(".fullName").val();
    userId = parseInt(userId,10);
    let myRow = $(".row-list").filter(function () {
        return $(this).data("user-id") === userId;
    })
    myRow.find(".user-fullName").text(fullName);
    myRow.find(".user-email").text(email);
    myRow.find(".user-phoneNumber").text(phoneNumber);

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

    if (!fullName) {
        toastr.error("Full Name is required");
        return false;
    }
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!email.match(emailPattern)) {
        toastr.error("Please enter a valid email address");
        return false;
    }
    const phonePattern = /^[0-9]{10}$/;
    if (!phoneNumber.match(phonePattern)) {
        toastr.error("Please enter a valid phone number");
        return false;
    }
    let row = $(this).closest("tr");
    let data = {
        userId: row.find(".userId").val(),
        username: row.find(".username").val(),
        fullName: row.find(".fullName").val(),
        email: row.find(".email").val(),
        phoneNumber: row.find(".phoneNumber").val(),
        gender: row.find(".gender").val(),
        registrationDate: row.find(".registrationDate").val(),
        role: row.find(".role").val(),
    };
    $.ajax({
        url: "/update-user",
        method: "post",
        data: JSON.stringify(data),
        contentType: "application/json"
    })

    toastr.success("Update successfully!")
})

$(document).on("click",".view-user-book-borrowed",function () {
    let userId = $(this).closest("tr").find(".user-id").text();
    $("#userHasBorrowed").modal("show");
    $.ajax({
        url: "/show-books-user-borrowed-for-admin",
        method: "get",
        data: {userId : userId},
        success: function (response) {
            $(".list-borrowed").empty();
            console.log(response[1]);
            response.forEach(item => {
                let currentDate = new Date();
                let dueDate = new Date(item.dueDate);
                let status = currentDate > dueDate ? "Past Due Date" : "Within Due Date";
                let row = `
                <tr data-item-id = ${item.id}>
                            <td>${item.id}</td>
                            <td>${item.title}</td>
                            <td>${item.dateBorrowed}</td>
                            <td>${item.dueDate}</td>
                            <td>${status}</td>
                        </tr>
                `
                $(".list-borrowed").append(row);
            })
        }

    })

})
