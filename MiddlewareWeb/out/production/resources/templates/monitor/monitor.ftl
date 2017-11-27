<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" content="text/html">
    <meta name="viweport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="../../static/favicon.ico">

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

    <!--Sidebar style-->
    <link rel="stylesheet" href="/css/style-sidebar.css" type="text/css">

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
        <div class="navbar-brand">
            <img alt="sinnowa" style="max-width:120px;margin-top:-14px;" src="/sinnowa.png">
        </div>
        <div class="col-md-offset-2">
            <ul class="nav navbar-nav">
                <li class="active"><a>监控</a> </li>
                <li><a href="/query" target="_blank">查询</a> </li>
            </ul>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a>Settings</a></li>
                <li><a>Help</a></li>
            </ul>
        </div>
    </div>
    <!--Sidebar-->
    <div>
        <div class="row">
            <div class="col-sm-3 col-md-2">
                <ul class="nav sidebar-nav">
                    <li class="sidebar-brand">
                        <a>监控模式</a>
                    </li>
                    <li><a href="#monitorSample"><i class="fa"></i>样本监控</a></li>
                    <li><a href="#monitorDevice"><i class="fa"></i>仪器监控</a></li>
                </ul>
            </div>
        </div>
    </div>
</nav>
<div class="container col-lg-8 col-lg-offset-2" id="mainContext"></div>
</body>
<script type="text/javascript">
    var intervalId=-1;
    $(document).ready(function () {
        $("li").on("click",function () {
            var href=$(this).find("a").attr('href');
            if(RegExp("#").test(href))
            {
                href=href.replace("#","");
                clearInterval(intervalId);
                $.get(href,function (data) {
                    $('#mainContext').html(data,new function () {
                        setTimeout("getIntervalID()",500);
                    });
                });
                //阻止跳转
                $(this).parents('li').addClass('active').siblings('li').removeClass('active');
                return false;
            }
        });
    });
    var getIntervalID=function () {
        $.ajax({
            type:"GET",
            url:"/monitor/getIntervalID",
            dataType:"json",
            contentType:"charset=utf-8",
            success:function (data) {
                if(data.hasOwnProperty("intervalID"))
                {
                    intervalId=data["intervalID"];
                }
            },
            error:function () {
                alert("获取定时器ID失败");
            }
        })
    }
</script>
</html>
