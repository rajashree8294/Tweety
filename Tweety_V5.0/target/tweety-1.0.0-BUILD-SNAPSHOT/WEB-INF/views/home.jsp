<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Tweety: A Twitter Clone</title>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/home.css"
	rel="stylesheet">
</head>
<body>
	<div class="container login-container">
		<div class="row">
			<div class="col-md-6 login-form-1">
				<h3>Login</h3>
				<form action="">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Your Email *"
							value="" />
					</div>
					<div class="form-group">
						<input type="password" class="form-control"
							placeholder="Your Password *" value="" />
					</div>
					<div class="form-group">
						<input type="submit" class="btnSubmit" value="Login" />
					</div>
					<div class="form-group">
						<a href="#" class="btnForgetPwd">Forget Password?</a>
					</div>
				</form>
			</div>
			<div class="col-md-6 login-form-2">
				<h3>Tweety: A Twitter Clone</h3>
			</div>
		</div>
	</div>
</body>
</html>