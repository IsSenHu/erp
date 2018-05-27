$(function () {
   $("#add").click(function () {
       $("#dialog").prop("title", "添加新商品");
       $("#dialog").dialog({
           width : 600,
           height : 750,
           draggable : true,
           //打开对话框时是否使用特效
           show : "slide",
           //关闭对话框时是否使用特效动画
           hide : "slide",
           closeOnEscape : false,
           buttons: {
               "确定": function() {
                   var name = $("input[name='name']").val().trim();
                   var fromType = $("#fromType").val();
                   var saleNumber = $("#saleNumber").val();
                   var fromWhere = $("#fromWhere").val().trim();
                   var address = $("#address").val().trim();
                   var originPrice = $("#originPrice").val().trim();
                   var nowPrice = $("#nowPrice") ? $("#nowPrice").val().trim() : null;
                   var up2MarketDate = $("#upDate") ? $("#upDate").val() : null;
                   var description = $("#description").val().trim();
                   var shop = $("#shop") ? $("#shop").val().trim() : null;
                   //todo 校验手机号
                   $.ajax({
                       type : 'post',
                       url : '/saveNewGood',
                       contentType : 'application/json;charset=utf-8',
                       data : JSON.stringify({
                           "name" : name,
                           "fromType" : fromType,
                           "saleNumber" : saleNumber,
                           "fromWhere" : fromWhere,
                           "address" : address,
                           "originPrice" : originPrice,
                           "nowPrice" : nowPrice,
                           "up2MarketDate" : up2MarketDate,
                           "description" : description,
                           "shop" : shop
                       }),
                       success : function (data) {
                           if(data.code == 200){
                               location.reload();
                           }else if (data.code == 400){
                               var errors = data.data;
                               $(errors).each(function (index, obj) {
                                   if(obj.field == 'name'){
                                       showError("input[name='name']", obj.defaultMessage)
                                   }else if(obj.field == 'fromType'){
                                       showError("#fromType", obj.defaultMessage)
                                   }else if(obj.field == 'saleNumber'){
                                       showError("#saleNumber", obj.defaultMessage)
                                   }else if(obj.field == 'fromWhere'){
                                       showError("#fromWhere", obj.defaultMessage)
                                   }else if(obj.field == 'address'){
                                       showError("#address", obj.defaultMessage)
                                   }else if(obj.field == 'originPrice'){
                                       showError("#originPrice", obj.defaultMessage)
                                   }
                               });
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
           },
       });
   });
   $("input[name='name']").focus(resetInput);
   $("#saleNumber").focus(resetInput);
   $("#fromWhere").focus(resetInput);
   $("#address").focus(resetInput);
   $("#originPrice").focus(resetInput);
   $("#search").click(function () {
       var materialId = $("#id").val().trim();
       var name = $("#name").val().trim();
       var status = $("#status").val();
       var fromType = $("#from").val();
       location.href = '/newGoods?materialId=' + materialId + '&name=' + name + '&status=' + status + '&fromType=' + fromType;
   });

    $(".add2check").click(function () {
        var materialId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/add2WaitReview',
            data : 'materialId=' + materialId,
            success : function (data) {
                if(data.code == 200){
                    location.reload();
                }else {
                    $("#resultMessage").html("添加到待判断失败");
                    $("#result").dialog();
                }
            }
        });
    });
    
    $(".add2WaitCheck").click(function () {
        var materialId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/add2WaitCheck',
            data : 'materialId=' + materialId,
            success : function (data) {
                if(data.code == 200){
                    location.reload();
                }else {
                    $("#resultMessage").html("添加到待核查失败");
                    $("#result").dialog();
                }
            }
        });
    });
    
    $(".access").click(function () {
        var materialId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/checkNewGood',
            data : 'materialId=' + materialId + '&flag=3',
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

    $(".noAccess").click(function () {
        var materialId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/checkNewGood',
            data : 'materialId=' + materialId + '&flag=4',
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

    $(".deleteRow").click(function () {
        var materialId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/deleteNewGoodById',
            data : 'materialId=' + materialId,
            success : function (data) {
                if(data.code == 200){
                    location.reload();
                }else {
                    $("#resultMessage").html("删除失败");
                    $("#result").dialog();
                }
            }
        });
    });

    $(".updateRow").click(function () {
        var materialId = $(this).val();
        $("#dialog").prop("title", "修改/查看详细信息");
        $.ajax({
            type : 'post',
            url : '/findNewGoodById',
            data : 'materialId=' + materialId,
            success : function (data) {
                $("input[name='name']").val(data.name);
                $("#fromType").empty();
                if(data.fromType == 11){
                    $("#fromType").append(' <option selected value="11">天猫/淘宝</option>');
                    $("#fromType").append(' <option value="12">京东</option>');
                    $("#fromType").append(' <option value="13">人工添加</option>');
                    $("#fromType").append(' <option value="14">其它</option>');
                }else if(data.fromType == 12){
                    $("#fromType").append(' <option value="11">天猫/淘宝</option>');
                    $("#fromType").append(' <option selected value="12">京东</option>');
                    $("#fromType").append(' <option value="13">人工添加</option>');
                    $("#fromType").append(' <option value="14">其它</option>');
                }else if(data.fromType == 14){
                    $("#fromType").append(' <option value="11">天猫/淘宝</option>');
                    $("#fromType").append(' <option value="12">京东</option>');
                    $("#fromType").append(' <option selected value="13">人工添加</option>');
                    $("#fromType").append(' <option value="14">其它</option>');
                }else {
                    $("#fromType").append(' <option value="11">天猫/淘宝</option>');
                    $("#fromType").append(' <option value="12">京东</option>');
                    $("#fromType").append(' <option value="13">人工添加</option>');
                    $("#fromType").append(' <option selected value="14">其它</option>');
                }
                $("#saleNumber").val(data.saleNumber);
                $("#fromWhere").val(data.fromWhere);
                $("#address").val(data.address);
                $("#originPrice").val(data.originPrice);
                $("#nowPrice").val(data.nowPrice);
                $("#upDate").datepicker('setDate', new Date(data.up2MarketDate));
                $("#shop").val(data.shop);
                $("#description").val(data.description);
                $("#dialog").dialog({
                    width : 600,
                    height : 750,
                    draggable : true,
                    //打开对话框时是否使用特效
                    show : "slide",
                    //关闭对话框时是否使用特效动画
                    hide : "slide",
                    closeOnEscape : false,
                    buttons: {
                        "确定": function() {
                            var name = $("input[name='name']").val().trim();
                            var fromType = $("#fromType").val();
                            var saleNumber = $("#saleNumber").val();
                            var fromWhere = $("#fromWhere").val().trim();
                            var address = $("#address").val().trim();
                            var originPrice = $("#originPrice").val().trim();
                            var nowPrice = $("#nowPrice") ? $("#nowPrice").val().trim() : null;
                            var up2MarketDate = $("#upDate") ? $("#upDate").val() : null;
                            var description = $("#description").val().trim();
                            var shop = $("#shop") ? $("#shop").val().trim() : null;
                            //todo 校验手机号
                            $.ajax({
                                type : 'post',
                                url : '/saveNewGood',
                                contentType : 'application/json;charset=utf-8',
                                data : JSON.stringify({
                                    "materialId" : materialId,
                                    "name" : name,
                                    "fromType" : fromType,
                                    "saleNumber" : saleNumber,
                                    "fromWhere" : fromWhere,
                                    "address" : address,
                                    "originPrice" : originPrice,
                                    "nowPrice" : nowPrice,
                                    "up2MarketDate" : up2MarketDate,
                                    "description" : description,
                                    "shop" : shop
                                }),
                                success : function (data) {
                                    if(data.code == 200){
                                        location.reload();
                                    }else if (data.code == 400){
                                        var errors = data.data;
                                        $(errors).each(function (index, obj) {
                                            if(obj.field == 'name'){
                                                showError("input[name='name']", obj.defaultMessage)
                                            }else if(obj.field == 'fromType'){
                                                showError("#fromType", obj.defaultMessage)
                                            }else if(obj.field == 'saleNumber'){
                                                showError("#saleNumber", obj.defaultMessage)
                                            }else if(obj.field == 'fromWhere'){
                                                showError("#fromWhere", obj.defaultMessage)
                                            }else if(obj.field == 'address'){
                                                showError("#address", obj.defaultMessage)
                                            }else if(obj.field == 'originPrice'){
                                                showError("#originPrice", obj.defaultMessage)
                                            }
                                        });
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
                    },
                });
            }
        });
    });

    $(".canProduct").click(function () {
        var materialId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/productOrNotProduct',
            data : 'materialId=' + materialId + '&flag=5',
            success : function (data) {
                if (data.code == 200){
                    location.reload();
                }else {
                    $("#resultMessage").html("设置失败");
                    $("#result").dialog();
                }
            }
        });
    });

    $(".cantProduct").click(function () {
        var materialId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/productOrNotProduct',
            data : 'materialId=' + materialId + '&flag=6',
            success : function (data) {
                if (data.code == 200){
                    location.reload();
                }else {
                    $("#resultMessage").html("设置失败");
                    $("#result").dialog();
                }
            }
        });
    });

    $(".create").click(function () {
        var newGoodId = $(this).val();
        location.href = '/createSupplierGood?newGoodId=' + newGoodId;
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
/**
 * 获取ajax请求，返回数据
 * */
function myAjax(type, data, url, fun) {
    $.ajax({
        type : type,
        url : url,
        data : data,
        success : fun
    });
}