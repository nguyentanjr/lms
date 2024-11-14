$(document).ready(function () {

})

$(document).on("click", ".remove-user", function () {
    let removeButton = $(this).parents("tr");
    let userId = removeButton.find(".user-id").text()
    console.log(userId);
    $("#remove-modal").modal("show");
    $(document).on("click", ".remove-confirm", function () {
        removeButton.hide();
        $("#remove-modal").modal("hide");
        $("#successModalRemoving").modal("show");
        console.log(userId);
        $.ajax({
            url: "/remove-user",
            method: "get",
            data: {userId: userId}
        })
    })
})