$(function () {
   $(".deliever").click(function () {
       var outOrderId = $(this).val();
       $.ajax({
           type : 'post',
           url : '/outStock',
           data : 'outOrderId=' + outOrderId,
           success : function (data) {
               if(data.code == 200){
                   location.reload();
               }else {
                   $("#resultMessage").html("出库失败");
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