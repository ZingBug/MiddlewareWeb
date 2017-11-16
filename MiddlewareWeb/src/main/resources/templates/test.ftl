<#assign base=request.contextPath />
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" http-equiv="content-type" content="text/html">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../static/favicon.ico">

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="/bootstrap-4.0.0/dist/css/bootstrap.min.css" type="text/css" >

    <!-- Custom styles for this template -->
    <link rel="stylesheet" type="text/css" href="/css/login.css">
</head>

<body>

<div class="container">

    <form id="loginFrom" class="form-signin">
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
    </form>

</div> <!-- /container -->
</body>
</html>
