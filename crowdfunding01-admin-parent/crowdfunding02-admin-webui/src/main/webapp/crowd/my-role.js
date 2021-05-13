//声明专门的函数显示权限的树形结构
function fillAuthTree() {

    // 1.发送 Ajax 请求查询 Auth 数据
    var ajaxReturn = $.ajax({
        "url":"assign/get/all/auth.json",
        "type":"post",
        "dataType":"json",
        "async":false
    });

    if(ajaxReturn.status !== 200) {
        layer.msg("请求处理出错！响应状态码是："+ajaxReturn.status+"说明是："+ajaxReturn.statusText);
        return ;
    }
    // 2.从响应结果中获取 Auth 的 JSON 数据
    // 从服务器端查询到的 list 不需要组装成树形结构，这里我们交给 zTree 去组装
    var authList = ajaxReturn.responseJSON.data;

    // 3.准备对 zTree 进行设置的 JSON 对象
    var setting = {
        data:{
            simpleData: {

                enable:true,
                // 使用 categoryId 属性关联父节点，不用默认的 pId 了
                pIdKey: "categoryId"
            },
            key:{
                // 使用 title 属性显示节点名称，不用默认的 name 作为属性名了
                name:"title"
            }
        },
        check:{
            enable: true
        }
    };

    // 4.生成树形结构
    // <ul id="authTreeDemo" class="ztree"></ul>
    $.fn.zTree.init($("#authTreeDemo"), setting, authList);

    // 获取 zTreeObj 对象
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");

    // 调用 zTreeObj 对象的方法，把节点展开
    zTreeObj.expandAll(true);

    // 5.查询已分配的 Auth 的 id 组成的数组
    ajaxReturn = $.ajax({

        "url":"assign/get/assigned/auth/id/by/role/id.json",
        "type":"post",
        "data":{
            "roleId":window.roleId
        },
        "dataType":"json",
        "async":false
    });
    if(ajaxReturn.status !== 200) {
        layer.msg("请求错误,错误代码="+ajaxReturn.status+"错误提示是"+ajaxReturn.statusText);
        return ;
    }

    // 从响应结果中获取 authIdArray
    var authIdArray = ajaxReturn.responseJSON.data;

    // 6.根据 authIdArray 把树形结构中对应的节点勾选上
    // ①遍历 authIdArray
    for(var i = 0; i < authIdArray.length; i++) {

        var authId = authIdArray[i];
     // ②根据 id 查询树形结构中对应的节点
        var treeNode = zTreeObj.getNodeByParam("id", authId);

     // ③将 treeNode 设置为被勾选
     // checked 设置为 true 表示节点勾选
        var checked = true;

        // checkTypeFlag 设置为 false，表示不“联动”，不联动是为了避免把不该勾选的勾选上
        var checkTypeFlag = false;
     // 执行
        zTreeObj.checkNode(treeNode, checked, checkTypeFlag);
    }

}

// 声明专门的函数显示确认模态框
function removeRole(roleArray) {

    // 声明 追加显示角色名的div中的的id
    var roleNameDiv = $("#roleNameDiv");

    // 打开模拟框
    $("#confirmModal").modal("show");

    // 由于是数据追加，需要每次清除旧数据
    roleNameDiv.empty();

    // 根据id删除role角色，但在两个函数中都需要使用同一个id，设置为全局常量
    window.roleIdArray = [];

    // 遍历roleArray数组
    for(var i = 0;i < roleArray.length;i++){

        var role = roleArray[i];
        var roleName = role.name;
        // 追加动态数据
        roleNameDiv.append(roleName+"</br>");

        var roleId = role.id;

        // 调用数据对象的push()方法存入新元素
        window.roleIdArray.push(roleId);
    }
}


// 执行分页，生成页面效果，任何时候调用这个函数都会重新加载页面
function generatorPage() {

    // 1、获取分页数据
    var pageInfo = getPageInfoRemote();
    fillTableBody(pageInfo);

}

// 远程访问服务器端程序获取pageInfo数据
function getPageInfoRemote() {

    var pageInfo = null;

    // 通过Ajax请求来获取服务器端的数据
    var ajaxResult =$.ajax({

        url:"role/get/page/info.json",
        type:"post",
        dataType:"json",
        data:{
            "pageNum":window.pageNum,
            "pageSize":window.pageSize,
            "keyword":window.keyword
        },
        async:false

    });

    //打印一下获取到的数据；可以利用数据来进行下面的判断
    console.log(ajaxResult);

    // 判断当前响应码状态是否为200;判断的是服务器是否成功响应了
    var statusCode = ajaxResult.status;

    // 如果状态码不是200，说明发生了错误或者其他意外情况，显示提示消息，让当前函数停止执行
    if(statusCode !== 200){
        layer.msg("失败!响应状态码="+ajaxResult.status+"说明信息"+ajaxResult.statusText);
        return null;
    }

    // 如果响应状态是200，说明请求处理成功，获取pageInfo
    var resultEntity = ajaxResult.responseJSON;

    // 从resultEntity中获取result，来判断是否获取到了数据
    var result = resultEntity.result;

    //判断result是否成功了
    if(result === "FAILED"){
        layer.msg(resultEntity.message);
        return  null;
    }

    // 确认result成功后来获取PageInfo信息
    pageInfo = resultEntity.data;

    // 返回pageInfo
    return pageInfo;

}

// 填充表格
function fillTableBody(pageInfo) {

    //页面id，便于追加内容
    var rolePageBody = $("#rolePageBody");

    // 由于翻页是追加数据，导致数据都在一页写入，因此每次每次翻页调用方法清除tbody中的旧数据
    rolePageBody.empty();

    //防止未查询到数据也回显示页码导航条不美观，因此每次调用都会清除旧数据
    $("Pagination").empty();

    //判断pageInfo对象是否有效
    if(pageInfo == null || pageInfo.list == null ||pageInfo.list.length === 0){

        rolePageBody.append("<tr><td colspan='4'>抱歉！未查询到你搜索的数据！！</td></tr>");//根据前端页面表格跨列数
        return ;
    }

    // 有效就是用pageInfo中的list属性值填充body
    for(var i = 0;i < pageInfo.list.length;i++){

        //在js中集合变成数组了，
        var role = pageInfo.list[i];

        var roleId = role.id;

        var roleName = role.name;

        var numberTd = "<td>"+(i+1)+"</td>";
        var checkboxTd ="<td><input id='"+roleId+"' class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>"+roleName+"</td>";

        var checkBtn = "<button id='"+roleId+"' type='button' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";
        var pencilBtn = "<button id='"+roleId+"' type='button' class='btn btn-primary btn-xs pencilBtn '><i class=' glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button id='"+roleId+"' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";

        var tr = "<tr>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>";

        rolePageBody.append(tr);

    }

    // 调用生成分页导航条函数
    generatorNavigator(pageInfo);

}

// 生成分页页码导航条
function generatorNavigator(pageInfo) {

    // 获取总记录数
    var totalRecord = pageInfo.total;

    // 声明相关属性
    var properties = {

        num_edge_entries: 3,                            //边缘页数
        num_display_entries:5,                          //主题页数
        callback:paginationCallBack,                  //指定用户点击“翻页”的按钮时跳转页面的回调函数
        items_per_page: pageInfo.pageSize,  //每页要显示的数据的数量
        // Pagination内部使用PageIndex来管理页码，PageIndex从0开始，但pageNum是从1开始所以需要 - 1
        current_page:pageInfo.pageNum - 1,
        prev_text: "上一页",                             //上一页按钮上显示的文本
        next_text: "下一页"                                //下一页按钮上显示的文

    };

    //调用pagination()函数
    $("#RolePagination").pagination(totalRecord,properties);

}

// 翻页时的回调函数
function paginationCallBack(pageIndex,jQuery) {

    // 修改window对象的pageNum属性
    window.pageNum = pageIndex + 1;

    // 调用分页函数
    generatorPage();

    //取消页码超链接的默认行为
    return false;

}