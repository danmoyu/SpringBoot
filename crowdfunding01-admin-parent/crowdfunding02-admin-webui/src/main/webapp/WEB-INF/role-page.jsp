<%--
  Created by IntelliJ IDEA.
  User: 10671
  Date: 2021/5/10
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="crowd/my-role.js" charset="utf-8"></script>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">

    $(function () {

        // 1.为分页操作准备初始化数据
        window.pageNum = 1;
        window.pageSize = 10;
        window.keyword = "";

        // 2.调用执行分页的函数，显示分页效果
        generatorPage();

        // 3、根据关键字查询信息
        $("#searchBtn").click(function () {

            // 将输入的关键字方法window的全局常量中：val()：获取文本框中的值
            window.keyword = $("#keywordInput").val();

            // 调用分页函数，执行分页信息
            generatorPage();
        });

        // 4、增加角色
        $("#showAddModalBtn").click(function () {

            $("#addModal").modal("show");
        });

        // 5、给新增模态框中的保存按钮绑定单击响应函数
        $("#saveRoleBtn").click(function () {

            // 利用子代元素和属性选择器来定位到这条数据
            // // ①获取用户在文本框中输入的角色名称
            // #addModal 表示找到整个模态框
            // 空格表示在后代元素中继续查找
            // [name=roleName]表示匹配 name 属性等于 roleName 的元素
            var roleNameId = $("#addModal [name='roleName']");

            // 获取用户在文本框中输入的角色名称
            var roleName = $.trim(roleNameId.val());

            $.ajax({

                url:"role/save.json",
                type:"post",
                dataType:"json",
                data:{

                    "name":roleName
                },

                "success":function (response) {

                    var result = response.result;

                    if (result === "SUCCESS"){
                        layer.msg("添加成功");

                        // 将页码定位到最后一页，新增数据那一页
                        window.pageNum = 999999;

                        // 重新加载分页数据
                        generatorPage();
                    }

                    if(result === "FAILED"){
                        layer.msg("添加失败"+response.message)
                    }

                },
                "error":function (response) {
                    layer.msg(response.status+"错误信息"+response.statusText);
                }

            });

            // 关闭模态框
            $("#addModal").modal("hide");

            // 清理模态框
            roleNameId.val("");

        });

        // 给更新按钮绑定一个点击事件
        // $(".pencilBtn").click(function () {
        //
        //     // 结果：出现的效果除了显示的第一次点击修改有弹窗之外，翻页之后就会失去弹窗的效果
        //     $("#editModal").modal("show");
        // });

        // 6、使用jquery的on函数可以解决上面的问题
        // ①首先找到所有“动态生成”的元素所附着的“静态”元素
        // ②on()函数的第一个参数是事件类型
        // ③on()函数的第二个参数是找到真正要绑定事件的元素的选择器
        // ③on()函数的第三个参数是事件的响应函数
        $("#rolePageBody").on("click",".pencilBtn",function () {

            //打开动态模拟框
            $("#editModal").modal("show");

            // DOM操作：  获取表格中当前行中的角色名称:this表示当前按钮本身
            var roleName = $(this).parent().prev().text();

            // 获取当前角色的id
            // 原因  id='"+roleId+"' type='button',这段代码中我们把roleId设置到了id属性中
            // 为了让执行更新的按钮能够获取到roleId的值，直接放到全局变量上
            window.roleId = this.id;

            // 将获取到的role角色名添加到模态框的文本框；回显数据
            $("#editModal [name=roleName]").val(roleName);

        });

        //7、模态框中点击更新按钮发送更新请求需要携带上id
        $("#updateRoleBtn").click(function () {

            //从文本框中取出角色的名称
            var roleName = $("#editModal [name=roleName]").val();

            // 发送ajax请求，执行更新操作
            $.ajax({

                url:"role/update.json",
                type: "post",
                data: {
                    "id":window.roleId,
                    "name":roleName
                },
                dataType: "json",
                success:function (response) {
                    var result = response.result;

                    if (result === "SUCCESS"){
                        layer.msg("更新成功");

                        // 重新加载分页数据
                        generatorPage();
                    }

                    if(result === "FAILED"){
                        layer.msg("添加失败"+response.message)
                    }

                },

                error:function (response) {

                    layer.msg(response.status+""+response.statusText);

                }

            });

            // 关闭模态框
            $("#editModal").modal("hide");

        });

        //8、单条删除按钮
        $("#rolePageBody").on("click",".removeBtn",function() {


            // 获取表格中当前行中的角色名称:this表示当前按钮本身
            // 当前节点的父节点的兄弟节点（前一个节点）
            var roleName = $(this).parent().prev().text();
            var roleArray = [{
                id: this.id,
                name: roleName
            }];

            //调用函数，打开模态框以及传递role对象
            removeRole(roleArray);

        });

        // 9、给总的checkbox绑定响应函数
        $("#summaryBox").click(function () {

            // 获取当前多选框的自身状态
            var currentStatus = this.checked;

            // 用总的多选框的属性赋值给其他的多选框
            $(".itemBox").prop("checked",currentStatus);

        });

        // 10、全选、全不选的反向操作
        $("#rolePageBody").on("click",".itemBox",function () {

            // 获取当前动态数据中多选框的数量
            var checkedBoxCount = $(".itemBox:checked").length;

            // 获取全部.itemBox的数量
            var total = $(".itemBox").length;

            // 比较选中的多选框和总数量的多选框的结果来为总多选框设置状态
            $("#summaryBox").prop("checked",checkedBoxCount===total);
        });

        // 11、给批量删除的按钮绑定点击响应函数
        $("#batchRemoveBtn").click(function () {


            // 定义一个数组来存放多个选中的role角色
            var roleArray = [];

            // 找到当前选中的多选框并且遍历拿到role的id和角色名
            $(".itemBox:checked").each(function () {

                // 使用this引用当前遍历得到多选框的id值（角色id ）
                var roleId = this.id;

                // 通过DOM操作获取角色名
                var roleName = $(this).parent().next().text();

                var role = {
                    id:roleId,
                    name:roleName
                };

                return roleArray.push(role);
            });

            if(roleArray.length === 0){
                layer.msg("你还未选中要删除的角色");
                return ;
            }

            //调用函数，打开模态框以及传递role对象
            removeRole(roleArray);
        });

        // 12、再确认模拟框中 给删除按钮绑定单击响应函数
        $("#removeRoleBtn").click(function () {

            // 将全局变量数组window.roleIdArray对象转化成json字符串
            var requestBody = JSON.stringify(window.roleIdArray);

            $.ajax({

                url:"role/remove/by/id/array.json",
                type:"post",
                data:requestBody,
                contentType:"application/json;charset=utf-8",
                dataType:"json",
                success:function (response) {

                    var result = response.result;

                    if (result === "SUCCESS"){
                        layer.msg("删除成功");

                        // 重新加载分页数据
                        generatorPage();
                    }

                    if(result === "FAILED"){
                        layer.msg("删除失败"+response.message)
                    }
                },
                error:function (response) {

                    layer.msg(response.status+""+response.statusText);
                }

            });

            // 关闭模态框
            $("#confirmModal").modal("hide");
        });

        // 13.给分配权限按钮绑定单击响应函数
        $("#rolePageBody").on("click",".checkBtn",function () {

            // 把当前角色id存入全局常量中
            window.roleId = this.id;

            // 打开模态框
            $("#assignModal").modal("show");

            // 在模态框中装载树 Auth 的形结构数据
            fillAuthTree();
        });

        // 14.给分配权限模态框中的“分配”按钮绑定单击响应函数
        $("#assignBtn").click(function () {

            // 收集树形结构的各个节点中被勾选的节点
            // 1、声明一个专门的数组来存放树形节点的id
            var authIdArray = [];

            // 2、获取zTreeObj对象
            var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");

            // 3、获取全部被勾选的节点
            var checkedNodes = zTreeObj.getCheckedNodes(true);

            // 4、遍历checkedNodes
            for(var i = 0;i < checkedNodes.length;i++){

                var checkedNode = checkedNodes[i];

                var authId = checkedNode.id;

                authIdArray.push(authId);
            }

            // 将需要发送的角色id以及权限id数组进行包装
            var requestBody = {

                "authIdArray":authIdArray,

                // 为了服务器端 handler 方法能够统一使用 List<Integer>方式接收数据，roleId 也存 入数组
                "roleId":[window.roleId]
            };

            // 将json对象转换成json字符串
            requestBody = JSON.stringify(requestBody);

            $.ajax({
                "url":"assign/do/role/assign/auth.json",
                "type":"post",
                "data":requestBody,
                "contentType":"application/json;charset=UTF-8",
                "dataType":"json",
                "success":function(response){

                    var result = response.result;

                    if(result === "SUCCESS") {

                        layer.msg("操作成功！"); }

                    if(result === "FAILED") {

                        layer.msg("操作失败！"+response.message); }

                    },
                "error":function(response) {

                    layer.msg(response.status+" "+response.statusText);
                }
            });

            $("#assignModal").modal("hide");

        });


    });

</script>
<body>

<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" id="searchBtn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <%--批量删除--%>
                    <button type="button" id="batchRemoveBtn" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <%-- 弹出增加角色的模态框--%>
                    <button type="button" id="showAddModalBtn" class="btn btn-primary" style="float:right;">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                                <tr>
                                    <th width="30">#</th>
                                    <th width="30" ><input id="summaryBox" type="checkbox"></th>
                                    <th>名称</th>
                                    <th width="100">操作</th>
                                </tr>
                            </thead>

                            <tbody id="rolePageBody">

                                <%--追加内容区域--%>

                            </tbody>

                            <tfoot>
                                <tr>
                                    <td colspan="6" align="center">
                                        <!-- 这里显示分页 -->
                                        <div id="RolePagination" class="pagination"></div>
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/model-role-add.jsp"%>
<%@ include file="/WEB-INF/model-role-edit.jsp"%>
<%@ include file="/WEB-INF/model-role-remove.jsp"%>
<%@ include file="/WEB-INF/model-role-assign-auth.jsp"%>
</body>
</html>
