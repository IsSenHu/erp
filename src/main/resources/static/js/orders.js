$(function () {
    $("#add").click(function () {
        $.ajax({
            type : 'post',
            url : '/listCurrencyUnit',
            success : function (data) {
                $(data).each(function (index, obj) {
                    var select = $("#currencyUnitId");
                    select.empty();
                    select.append('<option value="' + obj.currencyUnitId +'">' + obj.name + '</option>');
                });
            }
        });
        $("#dialog").prop("title", "添加订单");
        $("#dialog").dialog({
            width : 500,
            height : 335,
            draggable : true,
            //打开对话框时是否使用特效
            show : "slide",
            //关闭对话框时是否使用特效动画
            hide : "slide",
            closeOnEscape : false,
            buttons: {
                "确定": function() {
                    var payType = $("#payType").val();
                    var currencyUnitId = $("#currencyUnitId").val();
                    var description = $("#description").val().trim();
                    $.ajax({
                        type : 'post',
                        url : '/saveOrder',
                        contentType : 'application/json;charset=UTF-8',
                        data : JSON.stringify({
                            "payType" : payType,
                            "currencyUnitId" : currencyUnitId,
                            "description" : description
                        }),
                        success : function (data) {
                            if(data.code == 200){
                                location.reload();
                            }else {
                                $("#resultMessage").html("保存失败");
                                $("#result").dialog();
                            }
                        }
                    });
                },
                "取消": function() {
                    $(this).dialog("close");
                }
            }
        });
    });
    $("#search").click(function () {
        var orderId = $("#id").val().trim();
        var outOrderId = $("#outOrderId").val().trim();
        var receiveOrderId = $("#receiveOrderId").val().trim();
        var status = $("#status").val();
        location.href = '/orders?orderId=' + orderId + '&outOrderId=' + outOrderId + '&receiveOrderId=' + receiveOrderId + '&status=' + status;
    });
    $(".addItem").click(function () {
        var orderId = $(this).val();
        $("#create").val(orderId);
        $.ajax({
            type : 'post',
            url : '/supplierGoodList',
            success : function (data) {
                console.log(data);
                var select = $("#supplierGoodId");
                select.empty();
                $("#currentInventory").val(data[0].currentInventory);
                $(data).each(function (index, obj) {
                    select.append('<option value="' + obj.supplierGoodId +'">' + obj.name +'</option>');
                });
            }
        });
    });
    $("#supplierGoodId").change(function () {
        var supplierGoodId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/findSupplierGoodById',
            data : 'supplierGoodId=' + supplierGoodId,
            success : function (data) {
                $("#currentInventory").val(data.currentInventory);
            }
        });
    });
    $("#create").click(function () {
        var orderId = $(this).val();
        var supplierGoodId = $("#supplierGoodId").val();
        var number = $("#number").val().trim();
        $.ajax({
            type : 'post',
            url : '/saveItem',
            contentType : 'application/json;charset=UTF-8',
            data : JSON.stringify({
                "orderId" : orderId,
                "supplierGoodId" : supplierGoodId,
                "number" : number
            }),
            success : function (data) {
                if(data.code == 200){
                    $("#success").html("添加成功");
                    $("#message").html("");
                    window.setTimeout(function () {
                        location.reload();
                    }, 1500)
                }else if(data.code == 400){
                    var errors = data.data;
                    $(errors).each(function (index, obj) {
                        if(obj.field == 'number'){
                            showError("#number", obj.defaultMessage);
                        }
                    });
                }else if(data.code == 401) {
                    $("#success").html("");
                    $("#message").html(data.data);
                }else {
                    $("#success").html("");
                    $("#message").html("添加失败");
                }
            }
        });
    });

    $("#number").focus(resetInput);

    $(".items").click(function () {
        var orderId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/findAllItemByOrderId',
            data : 'orderId=' + orderId,
            success : function (data) {
                var table = $("#item");
                table.empty();
                table.append('<tr>' +
                                '<th style="width: 33%;">采购商品</th>' +
                                '<th style="width: 33%;">采购数量</th>' +
                                '<th>金额</th>' +
                             '</tr>');
                $(data).each(function (index, obj) {
                    table.append('<tr><td>' + obj.good.name +'</td><td>' + obj.number +'</td><td>' + obj.money +'元</td></tr>');
                })
            }
        });
    });

    $(".can").click(function () {
        var orderId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/setOrderStatus',
            data : 'orderId=' + orderId + '&flag=1',
            success : function (data) {
                if(data.code == 200){
                    location.reload();
                }else if(data.code == 401){
                    $("#resultMessage").html(data.data);
                    $("#result").dialog();
                }else {
                    $("#resultMessage").html("审核失败");
                    $("#result").dialog();
                }
            }
        });
    });

    $(".cant").click(function () {
        var orderId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/setOrderStatus',
            data : 'orderId=' + orderId + '&flag=2',
            success : function (data) {
                if(data.code == 200){
                    location.reload();
                }else {
                    $("#resultMessage").html("审核失败");
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