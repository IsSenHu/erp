$(function () {
   $("#add").click(function () {
       $.ajax({
           type : "post",
           url : '/findAllNoFinishPayOrder',
           success : function (data) {
               var select = $("#payOrderId");
               select.empty();
               $(data).each(function (index, obj) {
                   select.append('<option value="' + obj.payOrderId +'">' + obj.payOrderId + '</option>');
               })
           }
       });
   });
   
   $("#save").click(function () {
       var number = $("#inNumber").val().trim();
       var qT = $("#qT").val().trim();
       var inNumber = $("#inedNumber").val().trim();
       var payOrderId = $("#payOrderId").val();
       $.ajax({
           type : 'post',
           url : '/saveInOrder',
           contentType : 'application/json;charset=UTF-8',
           data : JSON.stringify({
               "number" : number,
               "qT" : qT,
               "inNumber" : inNumber,
               "payOrderId" : payOrderId
           }),
           success : function (data) {
               if(data.code == 200){
                   $("#message1").html('');
                   $("#success").html('保存成功');
                    window.setTimeout(function () {
                        location.reload();
                    }, 1000);
               }else if(data.code == 400) {
                    var error = data.data;
                    $(error).each(function (index, obj) {
                        if(obj.field == 'number'){
                            showError("#inNumber", obj.defaultMessage);
                        }else if(obj.field == 'qT'){
                            showError("#qT", obj.defaultMessage);
                        }else if(obj.field == 'inNumber'){
                            showError("#inedNumber", obj.defaultMessage);
                        }else {
                            showError("#inOrderId", obj.defaultMessage);
                        }
                    })
               }else if (data.code == 401){
                    $("#message1").html(data.data);
                    $("#success").html('');
               }else {
                   $("#resultMessage").html("保存失败");
                   $("#result").dialog();
               }
           }
       });
   });
   
   $(".noAccess").click(function () {
       var inOrderId = $(this).val();
       $.ajax({
           type : 'post',
           url : '/setInOrderStatus',
           data : 'inOrderId=' + inOrderId + '&flag=2',
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
   
   $(".access").click(function () {
       var inOrderId = $(this).val();
       $.ajax({
           type : 'post',
           url : '/setInOrderStatus',
           data : 'inOrderId=' + inOrderId + '&flag=1',
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

    $(".cancel").click(function () {
        var inOrderId = $(this).val();
        $.ajax({
            type : 'post',
            url : '/setInOrderStatus',
            data : 'inOrderId=' + inOrderId + '&flag=3',
            success : function (data) {
                if(data.code == 200){
                    location.reload();
                }else {
                    $("#resultMessage").html("取消");
                    $("#result").dialog();
                }
            }
        });
    });
   
   $("#search").click(function () {
       var inOrderId = $("#id").val();
       var payOrderId = $("#payOrderId1").val();
       var status = $("#status").val();
       location.href = '/inOrders?inOrderId=' + inOrderId + '&payOrderId=' + payOrderId + '&status=' + status;
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