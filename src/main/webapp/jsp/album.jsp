<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<script>
    $(function () {
        var tb = [{
            iconCls: 'icon-search',
            text: '专辑详情',
            handler: function () {
                var c = $('#tt_album').treegrid('getSelected');
                if (c.album_id == null) {
                    c = c.id;
                } else {
                    c = c.album_id;
                }
                selectAlbumByid(c);
            }
        }, '-', {
            iconCls: 'icon-add',
            text: '添加专辑',
            handler: function () {
                $('#dd_album').dialog('open');
            }
        }, '-', {
            iconCls: 'icon-add',
            text: '添加章节',
            handler: function () {
                // alert('帮助按钮')
                selectAllAlbum();
            }
        }, '-', {
            iconCls: 'icon-sport_basketball',
            text: '下载',
            handler: function () {
                var c = $('#tt_album').treegrid('getSelected');
                if (c.album_id == null) {
                    alert("请选择对应的章节...")
                } else {
                    c = c.id;
                }
                download(c);
            }
        }];

        $('#tt_album').treegrid({
            //后台Controller查询所有专辑以及对应的章节集合
            url: '${pageContext.request.contextPath}/album/getAlbum',
            idField: 'id',//用来标识标识树节点   主干树与分支树节点  ID不能有相同  并且使用相同的字段  否则ID冲突
            treeField: 'title',//用来定义树节点   树形表格上要展示的信息   注意使用相同的字段 用来展示对应节点名称
            toolbar: tb,
            fit: true,
            fitColumns: true,
            columns: [[
                {field: 'title', title: '名字', width: 180},
                {field: 'size', title: '章节大小', width: 60},
                {
                    field: 'duration', title: '章节的时长', width: 80, formatter: function (value, rowData, rowIndex) {
                        if (value == null) {
                            return "";
                        } else {
                            var theTime = parseInt(value);// 秒
                            var theTime1 = 0;// 分
                            var theTime2 = 0;// 小时
                            if (theTime > 60) {
                                theTime1 = parseInt(theTime / 60);
                                theTime = parseInt(theTime % 60);
                                if (theTime1 > 60) {
                                    theTime2 = parseInt(theTime1 / 60);
                                    theTime1 = parseInt(theTime1 % 60);
                                }
                            }
                            var result = "" + parseInt(theTime) + "秒";
                            if (theTime1 > 0) {
                                result = "" + parseInt(theTime1) + "分" + result;
                            }
                            if (theTime2 > 0) {
                                result = "" + parseInt(theTime2) + "小时" + result;
                            }
                            return result;
                        }

                    }
                }
            ]]
        });
    })

    function addAlbum() {
        $('#ff_album').form('submit', {
            url: '${pageContext.request.contextPath}/album/addalbum',
            onSubmit: function () {
                return $("#ff_album").form("validate");
            },
            success: function (data) {
                data = JSON.parse(data);
                if (data.isadd) {
                    $('#dd_album').dialog('close');
                    $('#tt_album').treegrid('load');
                } else {
                    alert("添加失败, 请重新添加")
                }
            }
        });
    }

    function selectAlbumByid(c) {
        $.ajax({
            url: "${pageContext.request.contextPath}/album/selectAlbumByid",
            type: "get",
            data: "id=" + c,
            dataType: "json",
            success: function (data) {
                $("#albumTitle").html(data.title);
                $("#albumTime").html(data.publish_date);
                $("#albumScore").html(data.score);
                $("#albumAmount").html(data.amount);
                $("#albumAuthor").html(data.author);
                $("#albumBoardCast").html(data.boardcast);
                $("#albumBrief").html(data.brief);
                $("#albumImg").prop("src", "${pageContext.request.contextPath}/img/audioCollection/" + data.img_path);

                $("#dd_album_display").dialog("open");

            }
        })
    }

    function selectAllAlbum() {
        $.ajax({
            url: "${pageContext.request.contextPath}/album/selectAllAlbum",
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data != null && data.length > 0) {
                    var s2 = $("#album_id").val();
                    if (s2 == null) {
                        var s1 = "";
                        for (var i = 0; i < data.length; i++) {
                            var obj = data[i];
                            s1 += "<option value='" + obj.id + "'>" + obj.title + "</option>"
                        }
                        $("#album_id").prepend(s1);
                    }
                    $("#dd_chapter").dialog("open");
                } else {
                    alert("未查询到专辑,请先添加一个专辑.")
                }

            }
        })
    }

    function addChapter() {
        $('#ff_chapter').form('submit', {
            type: "post",
            url: '${pageContext.request.contextPath}/album/addChapter',
            onSubmit: function () {
                return true;
            },
            success: function (data) {
                data = JSON.parse(data);
                if (data.isadd) {
                    $('#dd_chapter').dialog('close');
                    $('#tt_album').treegrid('load');
                } else {
                    alert("添加失败, 请重新添加")
                }
            }
        });
    }

    function download(cc) {
        window.location.href = ("${pageContext.request.contextPath}/album/download?id=" + cc);
    }
</script>


<table id="tt_album" style="width:600px;height:400px"></table>
<div id="dd_album_display" class="easyui-dialog" title="My Dialog" style="width:400px;height:400px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'确定',
				handler:function(){
				    $('#dd_album_display').dialog('close');
				}
			}]">
    <p>专辑名称：&nbsp; <span id="albumTitle"></span></p>
    <p>专辑评分：&nbsp; <span id="albumScore"></span>分</p>
    <p>专辑章节：&nbsp; <span id="albumAmount"></span></p>
    <p>专辑作者：&nbsp; <span id="albumAuthor"></span></p>
    <p>专辑播音：&nbsp; <span id="albumBoardCast"></span></p>
    <p>出版时间：&nbsp; <span id="albumTime"></span></p>
    <p>描述 ：&nbsp;<span id="albumBrief"></span>
    <p>专辑封面 :<img id="albumImg" width="150px" height="200px"/></p>

</div>
<div id="dd_album" class="easyui-dialog" title="My Dialog" style="width:400px;height:400px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addAlbum();
				}
			},{
				text:'关闭',
				handler:function(){
				    $('#dd_album').dialog('close');
				}
			}]">
    <form id="ff_album" method="post" enctype="multipart/form-data">
        <div>
            <label for="name">标题:</label>
            <input id="name" class="easyui-validatebox" type="text" name="title" data-options="required:true"/>
        </div>
        <div>
            <label for="name">集数:</label>
            <input id="amount" class="easyui-validatebox" type="text" name="amount" data-options="required:true"/>
        </div>
        <div>
            <label for="name">评分:</label>
            <input id="score" class="easyui-validatebox" type="text" name="score" data-options="required:true"/>
        </div>
        <div>
            <label for="name">作者:</label>
            <input id="author" class="easyui-validatebox" type="text" name="author" data-options="required:true"/>
        </div>
        <div>
            <label for="name">播音员:</label>
            <input id="boardcast" class="easyui-validatebox" type="text" name="boardcast" data-options="required:true"/>
        </div>
        <div>
            <label for="name">简介:</label>
            <input id="brief" class="easyui-validatebox" type="text" name="brief" data-options="required:true"/>
        </div>
        <input class="easyui-filebox" style="width:150px" name="file" data-options="buttonText:'选择文件'">
    </form>
</div>

<div id="dd_chapter" class="easyui-dialog" title="My Dialog" style="width:400px;height:400px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
            buttons:[{
				text:'保存',
				handler:function(){
				    addChapter();
				}
			},{
				text:'关闭',
				handler:function(){
				    $('#dd_chapter').dialog('close');
				}
			}]">
    <form id="ff_chapter" method="post" enctype="multipart/form-data">
        <select name="album_id" id="album_id">
        </select>
        <input class="easyui-filebox" style="width:150px" name="file" data-options="buttonText:'选择文件'">
    </form>

</div>
