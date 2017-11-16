<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" content="text/html">
    <meta name="viweport" content="width=device-width, initial-scale=1">

    <!--jquery-->
    <script type="text/javascript" src="/jquery/jquery-3.2.1.min.js"></script>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css" >
    <script src="/bootstrap-3.3.7/js/bootstrap.min.js"></script>

    <!--datetimepicker-->
    <script src="/js/moment-with-locales.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="/css/bootstrap-datetimepicker.min.css" type="text/css">
    <script src="/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>

    <!--Bootstrap Table-->
    <link rel="stylesheet" href="/css/bootstrap-table.min.css" type="text/css">
    <script src="/js/bootstrap-table.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap-table-zh-CN.min.js" type="text/javascript"></script>

</head>
<body>
<hr>
<div class="container">
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
            var rows=[];
            $('#table').bootstrapTable('load',rows);

        });
        $('#query').click(function () {
            var datetime=$('#datetime').val()+" 00:00:00";
            var device=$('#device').val();
            $.ajax({
                type:"GET",
                url:"/query/"+device+"/"+"sampleByTime?"+"time="+datetime,
                dataType:"json",
                contentType:"charset=utf-8",
                success:function (jsonText) {
                    var rows=[];
                    $('#table').bootstrapTable('load',rows);
                    for(var key in jsonText)
                    {
                        if(jsonText.hasOwnProperty(key))
                        {
                            var value=jsonText[key];
                            var t=formatDateTime(new Date(value.time));
                            rows.push({
                                id:value.id,
                                sampleId:value.sampleId,
                                patientId:value.patientId,
                                firstName:value.firstName,
                                sex:value.sex,
                                device:value.device,
                                sampleKind:value.sampleKind,
                                time:t
                            });
                        }
                    }
                    //$('#table').bootstrapTable('append',rows);
                    $('#table').bootstrapTable('load',rows);
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
        sortName:'sampleId',
        sortOrder:'asc',
        pageSize:10,//每页的记录行数
        pageList:[10,25,50,100],//可供选择的每页的行数
        search:true,//是否显示表格搜索
        strictSearch:true,
        showColumns:true,//是否显示所有的列
        showRefresh:true,//是否显示刷新按钮
        minimumCountColumns:2,//最小允许的列数
        clickToSelect:true,//是否启动点击选中行
        height:500,//行高，如果没有设置heigh属性，表格自动根据记录条数设置表格高度
        uniqueId:"id",//每一行的唯一标识，一般为主键列
        showToggle:true,//是否显示详细视图和列表视图的切换按钮
        cardView:false,//是否显示详细视图
        detailView:false,//是否显示父子表
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
        },]
    });
</script>
</html>