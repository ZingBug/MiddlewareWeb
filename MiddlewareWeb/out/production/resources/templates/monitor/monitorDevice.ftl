<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" content="text/html">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div>
            <div id="realtimeDevice" align="center">
                <label class="control-label">实时设备监控</label>
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
    $(function () {
        getFirstInfo();
        connect();
    });
    var getFirstInfo = function () {
        $.ajax({
            type:"GET",
            url:"/monitor/device",
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
                rows.push({
                    device:value.device,
                    sampleCount0:value.sampleCount0,
                    sampleCount2:value.sampleCount2,
                    sampleCount4:value.sampleCount4,
                    sampleCount6:value.sampleCount6,
                    sampleCount8:value.sampleCount8,
                    sampleCount10:value.sampleCount10,
                    sampleCount12:value.sampleCount12,
                    sampleCount14:value.sampleCount14,
                    sampleCount16:value.sampleCount16,
                    sampleCount18:value.sampleCount18,
                    sampleCount20:value.sampleCount20,
                    sampleCount22:value.sampleCount22
                });
            }
        }
        $('#table').bootstrapTable('load',rows);
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
    var connect = function () {
        //连接websocket
        var socket=new SockJS('/endpointWisely');//链接SockJS的endpoint命名为"/endpointWisely"
        stompClient=Stomp.over(socket);//使用stomp子协议的websocket客户端
        stompClient.connect({},function (frame) {//链接websocket的服务器端
            console.log('Connected: '+frame);
            stompClient.subscribe('/topic/getRealDevice',function (response) {//订阅/topic/getResponse目标发送的消息。
                displayInfo(JSON.parse(response.body));
            });
        });
    };

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
        detailView:false,//是否显示父子表
        columns:[{
            field:'device',
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
        //目前不需要注册加载子表的事件
    });
</script>
</html>