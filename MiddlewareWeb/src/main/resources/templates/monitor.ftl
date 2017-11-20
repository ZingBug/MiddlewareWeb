<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" content="text/html">
    <meta name="viweport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="../static/favicon.ico">

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

    <!--IE10 viewport hack for Surface/desktop Windows 8 bug-->
    <link rel="stylesheet" href="/css/ie10-viewport-bug-workaround.css" type="text/css">
    <script src="/js/ie10-viewport-bug-workaround.js" type="text/javascript"></script>

    <style>
        canvas{
            -moz-user-select: none;
            -webkit-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-defalut navbar-inverse navbar-static-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">监控</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Settings</a></li>
                <li><a href="#">Help</a></li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>
<hr>
<p></p>
<div class="container">
    <hr>
    <p></p>
    <div id="sampleCountSection">
        <div id="sampleCount" align="center">
            <label class="control-label">设备检测样本数</label>
        </div>
        <div id="toolbar-sampleCount">
            <button id="clearAll-sampleCount" class="btn btn-danger">
                <i class="glyphicon glyphicon-remove"></i>清空数据显示
            </button>
        </div>
        <table id="table-sampleCount"></table>
    </div>
    <div id="realtimeSampleSection">
        <div id="realtimeSample" align="center">
            <label class="control-label">实时样本监控</label>
        </div>
        <div id="toolbar-realtimeSample">
            <button id="clearAll-realtimeSample" class="btn btn-danger">
                <i class="glyphicon glyphicon-remove"></i>清空数据显示
            </button>
        </div>
        <table id="table-realtimeSample"></table>
    </div>
</div>
</body>
<script type="text/javascript">
    $(function () {
        setInterval(getRealSample,5000);
        $('#clearAll-sampleCount').click(function () {
            $('#table-sampleCount').bootstrapTable('removeAll')
        });
        $('#clearAll-realtimeSample').click(function () {
            $('#table-realtimeSample').bootstrapTable('removeAll');
        });
    });
    var cur_device="DS";
    var formatDateTime=function (date) {
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
    var getRealSample=function () {
        $.ajax({
            type:"GET",
            url:"/monitor/"+cur_device,
            dataType:"json",
            contentType:"charset=utf-8",
            success:function (jsonText) {
                var rows=[];
                for(var key in jsonText)
                {
                    if(jsonText.hasOwnProperty(key))
                    {
                        var value=jsonText[key];
                        var t=formatDateTime(new Date(value.time));
                        rows.push({
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
                $('#table-realtimeSample').bootstrapTable('prepend',rows);
            },
            error:function () {
                //获取失败的情况
            }
        })
    }
    $('#table-sampleCount').bootstrapTable({
        toolbar:'#toolbar-sampleCount',
        cache:false,//是否使用缓存，默认是true
        striped:true,//是否显示行间隔色
        pagination:true,//是否显示分页
        sortable:true,//是否启用排序
        sortName:'deviceName',
        sortOrder:'asc',
        pageSize:10,//每页的记录行数
        pageList:[10,25,50,100],//可供选择的每页的行数
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
        detailView:false,//是否显示父子表
        columns:[{
            field:'deviceName',
            title:'设备名',
            sortable:true
        },{
            field:'sampleCount0',
            title:'0-2点'
        },{
            field:'sampleCount2',
            title:'2-4点'
        },{
            field:'sampleCount4',
            title:'4-6点'
        },{
            field:'sampleCount6',
            title:'6-8点'
        },{
            field:'sampleCount8',
            title:'8-10点'
        },{
            field:'sampleCount10',
            title:'10-12点'
        },{
            field:'sampleCount12',
            title:'12-14点'
        },{
            field:'sampleCount14',
            title:'14-16点'
        },{
            field:'sampleCount16',
            title:'16-18点'
        },{
            field:'sampleCount18',
            title:'18-20点'
        },{
            field:'sampleCount20',
            title:'20-22点'
        },{
            field:'sampleCount22',
            title:'22-24点'
        }]
    });
    $('#table-realtimeSample').bootstrapTable({
        toolbar:'#toolbar-realtimeSample',
        cache:false,//是否使用缓存，默认是true
        striped:true,//是否显示行间隔色
        pagination:true,//是否显示分页
        sortable:true,//是否启用排序
        sortName:'time',
        sortOrder:'desc',
        pageSize:10,//每页的记录行数
        pageList:[10,25,50,100],//可供选择的每页的行数
        search:true,//是否显示表格搜索
        strictSearch:true,//设置为true启用 全匹配搜索，否则为模糊搜索
        searchOnEnterKey:false,//设置为true时，按回车触发搜索方法，否则自动触发搜索方法
        showColumns:true,//是否显示所有的列
        showRefresh:true,//是否显示刷新按钮
        minimumCountColumns:2,//最小允许的列数
        clickToSelect:true,//是否启动点击选中行
        height:500,//行高，如果没有设置heigh属性，表格自动根据记录条数设置表格高度
        uniqueId:"sampleId",//每一行的唯一标识，一般为主键列
        showToggle:true,//是否显示详细视图和列表视图的切换按钮
        cardView:false,//是否显示详细视图
        detailView:true,//是否显示父子表
        columns:[{
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
            url:"/monitor/"+cur_device+"/"+"details?sampleId="+row.sampleId,//与查询那块相似，但这个只是查询新的样本信息
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
                alert("获取数据详情失败!");
            }
        })
    }
</script>
</html>
