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

    <!--Custom style-->
    <link rel="stylesheet" href="/css/style-sidebar.css" type="text/css">

</head>
<body>
<nav class="navbar navbar-default navbar-inverse navbar-static-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">查询</a>
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
        <!--Sidebar-->
        <div id="wrapper">
            <div class="overlay"></div>
            <div class="row">
                <div class="col-sm-3 col-md-2">
                    <ul class="nav sidebar-nav">
                        <li class="sidebar-brand">
                            <a>查询方式</a>
                        </li>
                        <li><a href="#queryByTime"><i class="fa"></i>时间</a></li>
                        <li><a href="#queryBySample"><i class="fa"></i>样本</a></li>
                        <li><a href="#queryByDevice"><i class="fa"></i>仪器</a></li>
                        <li><a href="#queryByName"><i class="fa"></i>姓名</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>
<div class="container col-lg-8 col-lg-offset-2" id="mainContext"></div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        $("li").on("click",function () {
            var href=$(this).find("a").attr('href');
            href=href.replace("#","");
            $('#mainContext').empty();
            $.ajax({
                type:"GET",
                url:href,
                success:function (data) {
                    $('#mainContext').html(data);
                }
            });
            //阻止跳转
            $(this).parents('li').addClass('active').siblings('li').removeClass('active');
            return false;
        });
    });
</script>
</html>