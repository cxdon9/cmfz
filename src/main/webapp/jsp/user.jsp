<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<script>
    $(function () {
        var tba = [{
            iconCls: 'icon-add',
            text: '添加',
            handler: function () {
                $('#dd_user').dialog('open');
            }
        }]

        $("#userTable").edatagrid({
            method: 'post',
            url: '${pageContext.request.contextPath}/user/queryAll',
            fit: true,
            fitColumns: true,
            toolbar: tba,
            columns: [[
                {field: 'id', title: '编号', width: 30,},
                {field: 'name', title: '姓名', width: 70,},
                {field: 'dharma', title: '法名', width: 100,},
                {
                    field: 'headImg', title: '头像', width: 100, formatter: function (value, row, index) {
                        return '<img src="${pageContext.request.contextPath}/img/userHead/' + row.headImg + '" width="100px" height="60px">'
                    }
                },
                {
                    field: 'sex', title: '性别', width: 50, formatter: function (value, row, index) {
                        if (value == 0) {
                            return "女";
                        } else {
                            return "男";
                        }
                    }
                },
                {field: 'province', title: '省份', width: 50,},
                {field: 'city', title: '城市', width: 70,},
                {field: 'sign', title: '签名', width: 100,},
                {field: 'status', title: '状态', width: 30,},
                {field: 'phone', title: '电话', width: 100},
                {field: 'password', title: '密码', width: 100},
                {field: 'salt', title: '盐值', width: 50},
                {field: 'createDate', title: '创建日期', width: 100},
                {
                    field: 'masterName', title: '上师', width: 100, formatter: function (value, row, index) {
                        //console.log(row)
                        return row.master.dharma
                    }
                },
                {
                    field: 'masterImg', title: '上师头像', width: 120, formatter: function (value, row, index) {
                        return '<img src="${pageContext.request.contextPath}/img/shangshi/' + row.master.headImg + '" width="100px" height="60px">'
                    }
                },

            ]],
            pagination: true,
            pageSize: 7,
            pageList: [3, 5, 7],
        })
    })

    function addUser() {
        $('#userform').form('submit', {
            url: '${pageContext.request.contextPath}/user/add',
            onSubmit: function () {
                return $("#userform").form("validate");
            },
            success: function (data) {
                var goEasy = new GoEasy({
                    appkey: "BC-f3e9d17c62f245ddaad5835f6a3d7a0b"
                });
                goEasy.publish({
                    channel: "wq",
                    message: data
                });
                $('#dd_user').dialog('close');
                alert("添加成功!!!")
            }
        });
    }
</script>
<table id="userTable"></table>
<div id="dd_user" class="easyui-dialog" title="My Dialog" style="width:400px;height:400px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addUser();
				}
			},{
				text:'关闭',
				handler:function(){}
			}]">
    <form id="userform" method="post" enctype="multipart/form-data">
        <div>
            <label for="name">昵称:</label>
            <input id="name" class="easyui-validatebox" type="text" name="name" data-options="required:true"/>
            <br>
            <label for="dharma">发号:</label>
            <input id="dharma" class="easyui-validatebox" type="text" name="dharma" data-options="required:true"/>
            <br>
            <label for="sex">性别:</label>
            <input id="sex" class="easyui-validatebox" type="text" name="sex" data-options="required:true"/>
            <br>
            <label for="province">省份:</label>
            <input id="province" class="easyui-validatebox" type="text" name="province" data-options="required:true"/>
            <br>
            <label for="city">城市:</label>
            <input id="city" class="easyui-validatebox" type="text" name="city" data-options="required:true"/>
            <br>
            <label for="sign">签名:</label>
            <input id="sign" class="easyui-validatebox" type="text" name="sign" data-options="required:true"/>
            <br>
            <label for="phone">手机:</label>
            <input id="phone" class="easyui-validatebox" type="text" name="phone" data-options="required:true"/>
            <br>
            <label for="password">密码:</label>
            <input id="password" class="easyui-validatebox" type="text" name="password" data-options="required:true"/>
        </div>
        <input class="easyui-filebox" style="width:150px" name="file">
    </form>

</div>