$(function () {
    $("#name").focus(resetInput);
    $("#add").click(function () {
        $("#dialog").prop("title", "添加品牌");
        $("#dialog").dialog({
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
                    var name = $("#name").val().trim();
                    var description = $("#description").val().trim();
                    $.ajax({
                        type : 'post',
                        url : '/saveBrand',
                        contentType : 'application/json;charset=UTF-8',
                        data : JSON.stringify({
                            "name" : name,
                            "description" : description
                        }),
                        success : function (data) {
                            if(data.code == 200){
                                location.reload();
                            }else if (data.code == 400){
                                $(data.data).each(function (index, obj) {
                                    if(obj.field == 'name'){
                                        showError("#name", obj.defaultMessage);
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
        var brandId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/findBrandById',
            data : 'brandId=' + brandId,
            success : function (data) {
                $("#name").val(data.name);
                $("#description").val(data.description);
                $("#dialog").prop("title", "修改品牌");
                $("#dialog").dialog({
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
                            var name = $("#name").val().trim();
                            var description = $("#description").val().trim();
                            $.ajax({
                                type : 'post',
                                url : '/saveBrand',
                                contentType : 'application/json;charset=UTF-8',
                                data : JSON.stringify({
                                    "brandId" : brandId,
                                    "name" : name,
                                    "description" : description
                                }),
                                success : function (data) {
                                    if(data.code == 200){
                                        location.reload();
                                    }else if (data.code == 400){
                                        $(data.data).each(function (index, obj) {
                                            if(obj.field == 'name'){
                                                showError("#name", obj.defaultMessage);
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
        var brandId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/deleteBrandById',
            data : 'brandId=' + brandId,
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