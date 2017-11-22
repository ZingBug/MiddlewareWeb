<#assign base=request.contextPath />
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" http-equiv="content-type" content="text/html">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../static/favicon.ico">

    <!--jquery-->
    <script type="text/javascript" src="/jquery/jquery-3.2.1.min.js"></script>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css" >
    <script src="/bootstrap-3.3.7/js/bootstrap.min.js"></script>

    <!-- Custom styles for this template -->
    <link rel="stylesheet" type="text/css" href="/css/style-login.css">


</head>

<body>

<div class="form-signin">
    <h2 class="form-signin-heading">Please sign in</h2>
    <label  class="sr-only">Email address</label>
    <input type="text" id="username" name="username" class="form-control" placeholder="User Name" required autofocus>
    <label  class="sr-only">Password</label>
    <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
    <div class="checkbox">
        <label>
            <input type="checkbox" value="remember-me"> Remember me
        </label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit" onclick="Login()">Sign in</button>
</div> <!-- /container -->

<script type="text/javascript" src="/jquery/jquery.md5.js"></script>
<script type="text/javascript">
    function Login() {
        <!--对密码进行带盐的MD5加密-->
        var salt="{*404||407||409||sinnowa#}";
        var pwd=$("#password").val();
        var md5Pwd=$.md5(pwd+salt);
        var jsondata={"username":$("#username").val(),"password":md5Pwd};
        $.ajax({
            type:"POST",
            url:"login",
            //data:{"username":$("#username").val(),"password":md5Pwd},
            data:JSON.stringify(jsondata),
            dataType:"JSON",
            contentType:"application/json;charset=utf-8",
            success: function(data){
                if(data.result!="success")
                {
                    //window.location.replace("http://www.hao123.com");
                    alert("用户名错误");
                }
                else
                {
                    window.location.replace("/query")
                }
            }
        });
    }
</script>


</body>
</html>
