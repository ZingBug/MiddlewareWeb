<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" content="text/html">

</head>
<body>
<hr>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-4">
            <label class="control-label">请选择日期</label>
            <!--指定 date标记-->
            <div class="input-group date" id="datetimepick">
                <input type="text" class="form-control" id="datetime">
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>
            </div>
        </div>
        <div class="col-sm-3">
            <label class="control-label">选择设备：</label>
            <select class="form-control col-sm-3" id="device">
                <option>DS</option>
            </select>
        </div>
        <div class="col-sm-2">
            <label></label>
            <button class="btn btn-block btn-primary" id="query">查询</button>
        </div>
    </div>
    <hr>
    <div id="toolbar">
        <button id="clearAll" class="btn btn-danger">
            <i class="glyphicon glyphicon-remove"></i>清空数据显示
        </button>
    </div>
    <table id="table">
    </table>
</div>
</body>
<script type="text/javascript">
    var cur_device;
    var formatDateTime = function (date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        var minute = date.getMinutes();
        minute = minute < 10 ? ('0' + minute) : minute;
        return y + '-' + m + '-' + d+' '+h+':'+minute;
    };
    $(function () {
        $('#datetimepick').datetimepicker({
            defaultDate:new Date(),
            format:'YYYY-MM-DD',
            locale:moment.locale('zh-cn')
        });
        $('#clearAll').click(function () {
            $('#table').bootstrapTable('removeAll');
        });
        $('#query').click(function () {
            var datetime=$('#datetime').val()+" 00:00:00";
            cur_device=$('#device').val();
            $.ajax({
                type:"GET",
                url:"/query/"+cur_device+"/"+"sampleByTime?"+"time="+datetime,
                dataType:"json",
                contentType:"charset=utf-8",
                success:function (jsonText) {
                    var rows=[];
                    //$('#table').bootstrapTable('load',rows);
                    $('#table').bootstrapTable('removeAll');
                    var i=1;
                    for(var key in jsonText)
                    {
                        if(jsonText.hasOwnProperty(key))
                        {
                            var value=jsonText[key];
                            var t=formatDateTime(new Date(value.time));
                            rows.push({
                                id:i,
                                sampleId:value.sampleId,
                                patientId:value.patientId,
                                firstName:value.firstName,
                                sex:value.sex,
                                device:value.device,
                                sampleKind:value.sampleKind,
                                time:t
                            });
                            i++;
                        }
                    }
                    $('#table').bootstrapTable('append',rows);
                },
                error:function () {
                    alert("获取数据失败！");
                }
            });
        });
    });
    $('#table').bootstrapTable({
        toolbar:'#toolbar',
        cache:false,//是否使用缓存，默认是true
        striped:true,//是否显示行间隔色
        pagination:true,//是否显示分页
        sortable:true,//是否启用排序
        sortName:'id',
        sortOrder:'asc',
        pageSize:10,//每页的记录行数
        pageList:[10,25,50,100,-1],//可供选择的每页的行数
        search:true,//是否显示表格搜索
        strictSearch:true,//设置为true启用 全匹配搜索，否则为模糊搜索
        searchOnEnterKey:false,//设置为true时，按回车触发搜索方法，否则自动触发搜索方法
        showColumns:true,//是否显示所有的列
        showRefresh:true,//是否显示刷新按钮
        minimumCountColumns:2,//最小允许的列数
        clickToSelect:true,//是否启动点击选中行
        height:500,//行高，如果没有设置heigh属性，表格自动根据记录条数设置表格高度
        uniqueId:"id",//每一行的唯一标识，一般为主键列
        showToggle:true,//是否显示详细视图和列表视图的切换按钮
        cardView:false,//是否显示详细视图
        detailView:true,//是否显示父子表
        columns:[{
            field:'id',
            title:'编号',
            sortable:true
        },{
            field:'sampleId',
            title:'样本ID',
            sortable:true
        },{
            field:'patientId',
            title:'病人ID',
            sortable:true
        },{
            field:'firstName',
            title:'病人姓名',
            sortable:true
        },{
            field:'sex',
            title:'性别',
            sortable:true
        },{
            field:'device',
            title:'检测仪器',
            sortable:true
        },{
            field:'sampleKind',
            title:'检测类型',
            sortable:true
        },{
            field:'time',
            title:'检测时间',
            sortable:true
        }],
        //注册加载子表的事件
        onExpandRow:function (index,row,$detail) {
            expandTable(index,row,$detail);
        }
    });
    var expandTable=function (index,row,$detail) {
        var cur_table=$detail.html('<table></table>').find('table');
        $(cur_table).bootstrapTable({
            pageSize:10,
            pageList:[10,20,50,100,-1],
            sortable:true,//是否启用排序
            sortName:'item',
            sortOrder:'asc',
            columns:[{
                field:'item',
                title:'项目名',
                sortable:true
            },{
                field:'fullName',
                title:'项目全称'
            },{
                field:'result',
                title:'结果'
            },{
                field:'unit',
                title:'单位'
            },{
                field:'normalLow',
                title:'正常最低值'
            },{
                field:'normalHigh',
                title:'正常最高值'
            },{
                field:'indicate',
                title:'提示'
            }]
        });
        $.ajax({
            type:"GET",
            url:"/query/"+cur_device+"/"+"details?sampleId="+row.sampleId,
            dataType:"json",
            contentType:"charset=utf-8",
            success:function (jsonText) {
                var rows=[];
                $(cur_table).bootstrapTable('load',rows);
                for(var key in jsonText)
                {
                    if(jsonText.hasOwnProperty(key))
                    {
                        var value=jsonText[key];
                        rows.push({
                            item:value.item,
                            fullName:value.fullName,
                            result:value.result,
                            unit:value.unit,
                            normalLow:value.normalLow,
                            normalHigh:value.normalHigh,
                            indicate:value.indicate
                        });
                    }
                }
                $(cur_table).bootstrapTable('load',rows);
            },
            error:function () {
                alert("获取数据详情失败！");
            }
        });
    }
</script>
</html>