$(function () {
    $("#name").focus(resetInput);
    $("#importPreferentialTaxRate").focus(resetInput);
    $("#importGeneralTaxRate").focus(resetInput);
    $("#vAtRate").focus(resetInput);

    $("#add").click(function () {
        $("#dialog").prop("title", "添加海关编码");
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
                    var name = $("#name").val().trim();
                    var importPreferentialTaxRate = $("#importPreferentialTaxRate").val().trim();
                    var importGeneralTaxRate = $("#importGeneralTaxRate").val().trim();
                    var vAtRate = $("#vAtRate").val().trim();
                    $.ajax({
                        type : 'post',
                        url : '/saveCustomsCode',
                        contentType : 'application/json;charset=UTF-8',
                        data : JSON.stringify({
                            "name" : name,
                            "importPreferentialTaxRate" : importPreferentialTaxRate,
                            "importGeneralTaxRate" : importGeneralTaxRate,
                            "vAtRate" : vAtRate
                        }),
                        success : function (data) {
                            if(data.code == 200){
                                location.reload();
                            }else if (data.code == 400){
                                $(data.data).each(function (index, obj) {
                                    if(obj.field == 'name'){
                                        showError("#name", obj.defaultMessage);
                                    }else if(obj.field == 'importPreferentialTaxRate'){
                                        showError("#importPreferentialTaxRate", obj.defaultMessage);
                                    }else if(obj.field == 'importGeneralTaxRate'){
                                        showError("#importGeneralTaxRate", obj.defaultMessage);
                                    }else if(obj.field == 'vAtRate'){
                                        showError("#vAtRate", obj.defaultMessage);
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
    });
    $(".updateRow").click(function () {
        var id = $(this).val();
        $.ajax({
            type : 'post',
            url : '/findCustomsCodeById',
            data : 'id=' + id,
            success : function (data) {
                $("#name").val(data.name);
                $("#importPreferentialTaxRate").val(data.importPreferentialTaxRate);
                $("#importGeneralTaxRate").val(data.importGeneralTaxRate);
                $("#vAtRate").val(data.vAtRate);
                $("#dialog").prop("title", "修改海关编码");
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
                            var name = $("#name").val().trim();
                            var importPreferentialTaxRate = $("#importPreferentialTaxRate").val().trim();
                            var importGeneralTaxRate = $("#importGeneralTaxRate").val().trim();
                            var vAtRate = $("#vAtRate").val().trim();
                            $.ajax({
                                type : 'post',
                                url : '/saveCustomsCode',
                                contentType : 'application/json;charset=UTF-8',
                                data : JSON.stringify({
                                    "id" : id,
                                    "name" : name,
                                    "importPreferentialTaxRate" : importPreferentialTaxRate,
                                    "importGeneralTaxRate" : importGeneralTaxRate,
                                    "vAtRate" : vAtRate
                                }),
                                success : function (data) {
                                    if(data.code == 200){
                                        location.reload();
                                    }else if (data.code == 400){
                                        $(data.data).each(function (index, obj) {
                                            if(obj.field == 'name'){
                                                showError("#name", obj.defaultMessage);
                                            }else if(obj.field == 'importPreferentialTaxRate'){
                                                showError("#importPreferentialTaxRate", obj.defaultMessage);
                                            }else if(obj.field == 'importGeneralTaxRate'){
                                                showError("#importGeneralTaxRate", obj.defaultMessage);
                                            }else if(obj.field == 'vAtRate'){
                                                showError("#vAtRate", obj.defaultMessage);
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
        var id = $(this).val();
        $.ajax({
            type : 'post',
            url : '/deleteCustomsCodeById',
            data : 'id=' + id,
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