$(function () {
    $("#add").click(function () {
        //获取供应商列表，并弹出添加面板
        $.ajax({
            type : 'post',
            url : '/findAllSupplier',
            success : function (data) {
                var select = $("#select");
                select.empty();
                $(data).each(function (index, obj) {
                    select.append('<option value="' + obj.supplierId + '">' + obj.name + '</option>');
                });
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
                            var phone = $("input[name='newPhone']").val().trim();
                            var email = $("input[name='newEmail']").val().trim();
                            var supplierId = $("#select").val();
                            //todo 校验手机号
                            $.ajax({
                                type : 'post',
                                url : '/saveContacts',
                                contentType : 'application/json;charset=utf-8',
                                data : JSON.stringify({
                                    "name" : name,
                                    "phone" : phone,
                                    "email" : email,
                                    "supplierId" : supplierId
                                }),
                                success : function (data) {
                                    if(data.code == 200){
                                        location.reload();
                                    }else if (data.code == 400){
                                        var errors = data.data;
                                        $(errors).each(function (index, obj) {
                                            if(obj.field == 'name'){
                                                showError("input[name='name']", obj.defaultMessage)
                                            }else if(obj.field == 'email'){
                                                showError("input[name='newEmail']", obj.defaultMessage)
                                            }else if(obj.field == 'phone'){
                                                showError("input[name='newPhone']", obj.defaultMessage)
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
    $("input[name='name']").focus(resetInput);
    $("input[name='newPhone']").focus(resetInput);
    $("input[name='newEmail']").focus(resetInput);

    $(".deleteRow").click(function () {
        var contactsId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/deleteContacts',
            data : 'contactsId=' + contactsId,
            success : function (data) {
                if(data.code == 200){
                    location.reload();
                }else if(data.code == 500){
                    $("#resultMessage").html("删除失败");
                    $("#result").dialog();
                }
            }
        });
    });
    
    $(".updateRow").click(function () {
       var contactsId = $(this).val();
       $.ajax({
           type : 'post',
           url : '/findContactsById',
           data : 'contactsId=' + contactsId,
           success : function (data) {
               $("input[name='name']").val(data.data.name);
               $("input[name='newPhone']").val(data.data.phone);
               $("input[name='newEmail']").val(data.data.email);
               var select = $("#select");
               select.empty();
               $(data.list).each(function (index, obj) {
                  if(obj.supplierId == data.data.supplierId){
                      select.append('<option selected value="' + obj.supplierId + '">' + obj.name + '</option>');
                  }else {
                      select.append('<option value="' + obj.supplierId + '">' + obj.name + '</option>');
                  }
               });
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
                           var phone = $("input[name='newPhone']").val().trim();
                           var email = $("input[name='newEmail']").val().trim();
                           var supplierId = $("#select").val();
                           //todo 校验手机号
                           $.ajax({
                               type : 'post',
                               url : '/saveContacts',
                               contentType : 'application/json;charset=utf-8',
                               data : JSON.stringify({
                                   "contactsId" : contactsId,
                                   "name" : name,
                                   "phone" : phone,
                                   "email" : email,
                                   "supplierId" : supplierId
                               }),
                               success : function (data) {
                                   if(data.code == 200){
                                       location.reload();
                                   }else if (data.code == 400){
                                       var errors = data.data;
                                       $(errors).each(function (index, obj) {
                                           if(obj.field == 'name'){
                                               showError("input[name='name']", obj.defaultMessage)
                                           }else if(obj.field == 'email'){
                                               showError("input[name='newEmail']", obj.defaultMessage)
                                           }else if(obj.field == 'phone'){
                                               showError("input[name='newPhone']", obj.defaultMessage)
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
    
    $("#search").click(function () {
        var contactsId = $("#id").val().trim();
        var name = $("#name").val().trim();
        var supplierId = $("#supplierId").val().trim();
        location.href = '/contacts?contactsId=' + contactsId + '&name=' + name + '&supplierId=' + supplierId;
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