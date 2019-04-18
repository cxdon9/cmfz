<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<script>
    $(function () {
        var tb = [{
            iconCls: 'icon-add',
            text: '添加',
            handler: function () {
                //alert('编辑按钮')
                $('#dd_banner').dialog('open');
            }
        }, '-', {
            iconCls: 'icon-edit',
            text: '修改',
            handler: function () {
                $('#dg_banner').edatagrid('saveRow');
            }
        }, '-', {
            iconCls: 'icon-delete',
            text: '删除',
            handler: function () {
                $('#dg_banner').edatagrid('destroyRow');
                //$('#dg_banner').edatagrid('load');
            }
        }, '-', {
            iconCls: 'icon-save',
            text: '保存',
            handler: function () {
                //alert('帮助按钮')
                $('#dg_banner').edatagrid('saveRow');
            }
        }];

        $("#dg_banner").edatagrid({
            method: "get",
            url: "${pageContext.request.contextPath}/banner/selectAll",
            destroyUrl: "${pageContext.request.contextPath}/banner/deleteBanner",
            updateUrl: "${pageContext.request.contextPath}/banner/updateBanner",
            fit: true,
            fitColumns: true,
            pagination: true,
            pageSize: 5,
            pageList: [5, 10, 15, 20],
            toolbar: tb,
            columns: [[
                {
                    field: 'title', title: '标题', width: 100, editor: {
                        type: 'text',
                        options: {required: true}
                    }
                },
                {
                    field: 'status', title: '状态', width: 100, editor: {
                        type: 'numberbox',
                        options: {required: true}
                    }
                },
                {field: 'creat_date', title: '日期', width: 100},
            ]],
            view: detailview,
            detailFormatter: function (rowIndex, rowData) {
                console.log(rowData)
                return '<table><tr>' +
                    '<td rowspan=2 style="border:0"><img src="${pageContext.request.contextPath}/img/shouye/' + rowData.img_path + '" style="height:50px;"></td>' +
                    '<td style="border:0">' +
                    '<p>描述: ' + rowData.title + '</p>' +
                    '<p>Status: ' + rowData.status + '</p>' +
                    '</td>' +
                    '</tr></table>';
            }
        })
    })

    function addBanner() {
        $('#ff').form('submit', {
            url: '${pageContext.request.contextPath}/banner/addbanner',
            onSubmit: function () {
                return $("#ff").form("validate");
            },
            success: function (data) {
                data = JSON.parse(data);
                if (data.isadd) {
                    $('#dg_banner').edatagrid('load');
                    $('#dd_banner').dialog('close');
                } else {
                    alert("添加失败, 请重新添加")
                }
            }
        });
    }
</script>


<table id="dg_banner"></table>


<div id="dd_banner" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addBanner();
				}
			},{
				text:'关闭',
				handler:function(){}
			}]">
    <form id="ff" method="post" enctype="multipart/form-data">
        <div>
            <label for="name">标题:</label>
            <input id="name" class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>
        <input class="easyui-filebox" style="width:150px" name="file">
    </form>

</div>