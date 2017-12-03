<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" content="text/html">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div>
            <div id="realtimeSample" align="center">
                <label class="control-label">实时样本监控</label>
            </div>
        </div>
    </div>
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
    var cur_device="DS";
    var stompClient=null;
    $(function () {
        getFirstInfo();
        connect();
    });
    var getFirstInfo = function () {
        $.ajax({
            type:"GET",
            url:"/monitor/"+cur_device,
            dataType:"json",
            contentType:"charset=utf-8",
            success:function (jsonText) {
                displayInfo(jsonText);
            },
            error:function () {
                //获取失败的情况
            }
        })
    };
    var displayInfo = function (jsonText) {
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
        $('#table').bootstrapTable('load',rows);
    };
    var connect = function () {
        //连接websocket
        var socket=new SockJS('/endpointWisely');//链接SockJS的endpoint命名为"/endpointWisely"
        stompClient=Stomp.over(socket);//使用stomp子协议的websocket客户端
        stompClient.connect({},function (frame) {//链接websocket的服务器端
            console.log('Connected: '+frame);
            stompClient.subscribe('/topic/getRealDSSample',function (response) {//订阅/topic/getResponse目标发送的消息。
                displayInfo(JSON.parse(response.body));
            });
        });
    };
    $('#clearAll').click(function () {
        $('#table').bootstrapTable('removeAll');
    });
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
    $('#table').bootstrapTable({
        toolbar:'#toolbar',
        cache:false,//是否使用缓存，默认是true
        striped:true,//是否显示行间隔色
        pagination:true,//是否显示分页
        sortable:true,//是否启用排序
        sortName:'time',
        sortOrder:'desc',
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
            url:"/monitor/"+cur_device+"/"+"details?sampleId="+row.sampleId,
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