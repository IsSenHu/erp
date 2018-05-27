$(function () {
    $("#search").click(function () {
        var supplierGoodId = $("#id").val().trim();
        var name = $("#name").val().trim();
        var supplierId = $("#supplierId").val().trim();
        var status = $("#status").val();
        location.href = '/supplierGoods?supplierGoodId=' + supplierGoodId + '&name=' + name + '&supplierId=' + supplierId + '&status=' + status;
    });
    
    $("#detail").click(function () {
        var supplierGoodId = $(this).val();
        location.href = '/detailSupplierGood?supplierGoodId=' +supplierGoodId;
    });
    
    $(".can").click(function () {
        var supplierGoodId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/canOrNotCanSupply',
            data : 'supplierGoodId=' + supplierGoodId + '&flag=1',
            success : function (data) {
                if(data.code == 200){
                    location.reload();
                }else {
                    $("#resultMessage").html("审查失败");
                    $("#result").dialog();
                }
            }
        });
    });

    $(".cant").click(function () {
        var supplierGoodId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/canOrNotCanSupply',
            data : 'supplierGoodId=' + supplierGoodId + '&flag=2',
            success : function (data) {
                if(data.code == 200){
                    location.reload();
                }else {
                    $("#resultMessage").html("审查失败");
                    $("#result").dialog();
                }
            }
        });
    });

    $(".reset").click(function () {
        var supplierGoodId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/canOrNotCanSupply',
            data : 'supplierGoodId=' + supplierGoodId + '&flag=0',
            success : function (data) {
                if(data.code == 200){
                    location.reload();
                }else {
                    $("#resultMessage").html("重置失败");
                    $("#result").dialog();
                }
            }
        });
    });
});
/*
* 显示输入错误的信息
* */
function showError(select, message) {
    $(select).css("color", "red").val(message).addClass("errorMessage");
}
/*
* 重置输入框
* */
function resetInput() {
    if($(this).hasClass("errorMessage")){
        $(this).css("color", "black").val("").removeClass("errorMessage");
    }
}