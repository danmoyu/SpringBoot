﻿<%--
  Created by IntelliJ IDEA.
  User: 10671
  Date: 2021/5/10
  Time: 17:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<div id="addModal" class="modal fade" tabindex="-1" role="dialog">
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
                <form class="form-signin" role="form">
                    <div class="form-group has-success has-feedback">
                        <input type="text" name="roleName" class="form-control"
                               placeholder="请输入角色名称" autofocus>

                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" id="saveRoleBtn" class="btn btn-primary">添加</button>
            </div>
        </div>
    </div>
</div>
