<%--
  Created by IntelliJ IDEA.
  User: 10671
  Date: 2021/5/10
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<div id="confirmModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">尚筹网系统弹窗</h4>
            </div>
            <div class="modal-body">
                <h5>请确认是否要删除下列角色</h5>
                <div id="roleNameDiv" style="text-align: center;"></div>
            </div>
            <div class="modal-footer">
                <button type="button" id="removeRoleBtn" class="btn btn-primary">确认删除</button>
            </div>
        </div>
    </div>
</div>
