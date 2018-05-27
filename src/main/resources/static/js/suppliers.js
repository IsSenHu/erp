$(function () {
   $("#add").click(function () {
       $("#dialog").dialog({
           width : 500,
           height : 340,
           draggable : true,
           //打开对话框时是否使用特效
           show : "slide",
           //关闭对话框时是否使用特效动画
           hide : "slide",
           closeOnEscape : false,
           buttons: {
               "确定": function() {
                   var name = $("input[name='name']").val().trim();
                   var description = $("#description").val().trim();
                   //todo 校验手机号
                   $.ajax({
                       type : 'post',
                       url : '/saveSupplier',
                       contentType : 'application/json;charset=utf-8',
                       data : JSON.stringify({
                           "name" : name,
                           "description" : description
                       }),
                       success : function (data) {
                           if(data.code == 200){
                               location.reload();
                           }else if (data.code == 400){
                               var errors = data.data;
                               $(errors).each(function (index, obj) {
                                   if(obj.field == 'name'){
                                       showError("input[name='name']", obj.defaultMessage)
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
   
   $(".updateRow").click(function () {
       var supplierId = $(this).val();
       $.ajax({
           type : 'post',
           url : '/findSupplierById',
           data : 'supplierId=' + supplierId,
           success : function (data) {
               $("input[name='name']").val(data.name);
               $("#description").val(data.description);
               $("#dialog").dialog({
                   width : 500,
                   height : 340,
                   draggable : true,
                   //打开对话框时是否使用特效
                   show : "slide",
                   //关闭对话框时是否使用特效动画
                   hide : "slide",
                   closeOnEscape : false,
                   buttons: {
                       "确定": function() {
                           var name = $("input[name='name']").val().trim();
                           var description = $("#description").val().trim();
                           //todo 校验手机号
                           $.ajax({
                               type : 'post',
                               url : '/saveSupplier',
                               contentType : 'application/json;charset=utf-8',
                               data : JSON.stringify({
                                   "supplierId" : supplierId,
                                   "name" : name,
                                   "description" : description
                               }),
                               success : function (data) {
                                   if(data.code == 200){
                                       location.reload();
                                   }else if (data.code == 400){
                                       var errors = data.data;
                                       $(errors).each(function (index, obj) {
                                           if(obj.field == 'name'){
                                               showError("input[name='name']", obj.defaultMessage)
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
                   }
               });
           }
       });
   });
   $(".deleteRow").click(function () {
       var supplierId = $(this).val();
       $.ajax({
           type : 'post',
           url : '/deleteSupplier',
           data : 'supplierId=' + supplierId,
           success : function (data) {
               if(data.code == 200){
                   location.reload();
               }else{
                   $("#resultMessage").html("删除失败");
                   $("#result").dialog();
               }
           }
       });
   });

   $("input[name='name']").focus(resetInput);

    $("#search").click(function () {
        var supplierId = $("#id").val();
        var name = $("#name").val();
        var status = $("#status").val();
        location.href = '/suppliers?supplierId=' + supplierId + '&name=' + name + '&status=' + status;
    });

    $(".changeStatus1").click(function () {
        var supplierId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/accessSupplierOrNot',
            data : 'supplierId=' + supplierId + '&flag=access',
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

    $(".changeStatus2").click(function () {
        var supplierId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/accessSupplierOrNot',
            data : 'supplierId=' + supplierId + '&flag=noAccess',
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

    $(".changeStatus3").click(function () {
        var supplierId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/accessSupplierOrNot',
            data : 'supplierId=' + supplierId,
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

    $(".Disabled").click(function () {
        var supplierId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/accessSupplierOrNot',
            data : 'supplierId=' + supplierId + '&flag=stop',
            success : function (data) {
                if(data.code == 200){
                    location.reload();
                }else {
                    $("#resultMessage").html("停用失败");
                    $("#result").dialog();
                }
            }
        });
    });

    $(".Enabled").click(function () {
        var supplierId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/accessSupplierOrNot',
            data : 'supplierId=' + supplierId + '&flag=start',
            success : function (data) {
                if(data.code == 200){
                    location.reload();
                }else {
                    $("#resultMessage").html("启用失败");
                    $("#result").dialog();
                }
            }
        });
    });

    /**
     * 设置账期
     * */
    $(".setAccount").click(function () {
        var supplierId = $(this).val();
        $("#accountStartDate").prop("disabled", false);
        $.ajax({
            type : 'post',
            url : '/findSupplierById',
            data : 'supplierId=' + supplierId,
            success : function (data) {
                console.log(data);
                if(data.status != 4){
                    $("#info").html('供应商未在账期状态，如果需要请设置账期时间');
                    $("#accountDialog").dialog({
                        width : 500,
                        height : 250,
                        draggable : true,
                        //打开对话框时是否使用特效
                        show : "slide",
                        //关闭对话框时是否使用特效动画
                        hide : "slide",
                        closeOnEscape : false,
                        buttons: {
                            "确定": function() {
                                var accountStartDate = $("#accountStartDate").val();
                                var accountEndDate = $("#accountEndDate").val();
                                //todo 校验手机号
                                $.ajax({
                                    type : 'post',
                                    url : '/setAccount',
                                    contentType : 'application/json;charset=utf-8',
                                    data : JSON.stringify({
                                        "supplierId" : supplierId,
                                        "accountStartDate" : accountStartDate,
                                        "accountEndDate" : accountEndDate
                                    }),
                                    success : function (data) {
                                        if(data.code == 200){
                                            location.reload();
                                        }else if (data.code == 400){
                                            $("#resultMessage").html(data.data);
                                            $("#result").dialog();
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
                }else {
                    $("#info").html('供应商已在账期状态，如果需要请修改账期时间');
                    var accountStartDateNow = data.accountStartDate;
                    var accountEndDate = data.accountEndDate;
                    $("#accountStartDate").datepicker('setDate', accountStartDateNow ? new Date(accountStartDateNow) : new Date());
                    $("#accountStartDate").prop("disabled", true);
                    $("#accountEndDate").datepicker('setDate', accountEndDate ? new Date(accountEndDate) : new Date());$("#accountDialog").dialog({
                        width : 500,
                        height : 300,
                        draggable : true,
                        //打开对话框时是否使用特效
                        show : "slide",
                        //关闭对话框时是否使用特效动画
                        hide : "slide",
                        closeOnEscape : false,
                        buttons: {
                            "确定": function() {
                                var accountEndDate = $("#accountEndDate").val();
                                $.ajax({
                                    type : 'post',
                                    url : '/setAccount',
                                    contentType : 'application/json;charset=utf-8',
                                    data : JSON.stringify({
                                        "supplierId" : supplierId,
                                        "accountStartDate" : accountStartDateNow,
                                        "accountEndDate" : accountEndDate
                                    }),
                                    success : function (data) {
                                        if(data.code == 200){
                                            location.reload();
                                        }else if (data.code == 400){
                                            $("#resultMessage").html(data.data);
                                            $("#result").dialog();
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