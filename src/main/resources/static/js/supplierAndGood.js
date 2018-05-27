$(function () {
    $.ajax({
        type : 'get',
        url : '/getAllCreateNeed',
        success : function (data) {
            var suppliers = data.suppliers;
            var customsCodes = data.customsCodes;
            var specifications = data.specifications;
            var stockKeepingUnits = data.stockKeepingUnits;
            var brands = data.brands;
            var currencyUnits = data.currencyUnits;
            var types = data.types;
            $(suppliers).each(function (index, obj) {
                $("#supplierId").append('<option value="' + obj.supplierId + '">' + obj.name +'</option>');
            });
            $(customsCodes).each(function (index, obj) {
                $("#customsCodeId").append('<option value="' + obj.id + '">' + obj.name +'</option>');
            });
            $(specifications).each(function (index, obj) {
                $("#specificationId").append('<option value="' + obj.specificationId + '">' + obj.name +'</option>');
            });
            $(stockKeepingUnits).each(function (index, obj) {
                $("#skuId").append('<option value="' + obj.unitId + '">' + obj.sku +'</option>');
            });
            $(brands).each(function (index, obj) {
                $("#brandId").append('<option value="' + obj.brandId + '">' + obj.name +'</option>');
            });
            $(currencyUnits).each(function (index, obj) {
                $("#currencyUnitId").append('<option value="' + obj.currencyUnitId + '">' + obj.name +'</option>');
            });
            $(types).each(function (index, obj) {
                $("#typeId").append('<option value="' + obj.typeId + '">' + obj.name +'</option>');
            });
        }
    });

    $("#submit").click(function () {
        var tag = $("#tag").val().trim();
        var buy = $("#buy").val().trim();
        var sale = $("#sale").val().trim();
        var weight = $("#weight").val().trim();
        var volume = $("#volume").val().trim();
        var trait = $("#trait").val().trim();
        var mater = $("#mater").val().trim();
        var description = $("#description").val().trim();
        var currencyUnitId = $("#currencyUnitId").val();
        var supplierId = $("#supplierId").val();
        var customsCodeId = $("#customsCodeId").val();
        var specificationId = $("#specificationId").val();
        var skuId = $("#skuId").val();
        var brandId = $("#brandId").val();
        var typeId = $("#typeId").val();
        $.ajax({
            type : 'post',
            url : '/saveSupplierGood',
            contentType : 'application/json;charset=UTF-8',
            data : JSON.stringify({
                "name" : $("#name").val(),
                "newGoodId" : $("#materialId").val(),
                "tag" : tag,
                "buy" : buy,
                "sale" : sale,
                "weight" : weight,
                "volume" : volume,
                "trait" : trait,
                "mater" : mater,
                "description" : description,
                "currencyUnitId" : currencyUnitId,
                "supplierId" : supplierId,
                "customsCodeId" : customsCodeId,
                "specificationId" : specificationId,
                "skuId" : skuId,
                "brandId" : brandId,
                "type" : typeId
            }),
            success : function (data) {
                console.log(data);
                if(data.code == 200){
                    $("#dialog").dialog({
                        width : 500,
                        height : 200,
                        draggable : true,
                        //打开对话框时是否使用特效
                        show : "slide",
                        //关闭对话框时是否使用特效动画
                        hide : "slide",
                        closeOnEscape : false
                    });
                }else if(data.code == 400){
                    $("#resultMessage").html("参数错误，请检查输入的参数");
                    $("#result").dialog();
                }else {
                    $("#resultMessage").html("生成失败");
                    $("#result").dialog();
                }
            }
        });
    });
    
    $("#toNew").click(function () {
        location.href = '/newGoods';
    });
    
    $("#toSupplier").click(function () {
        location.href = '/supplierGoods';
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