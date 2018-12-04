<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>登陆</title>
		<link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
	</head>
	<body>
		<div class="container">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<form class="form-horizontal" role="form" method="get" action="/sell/seller/login">
						<div class="form-group">
							 <label for="inputPassword3" class="col-sm-2 control-label">openid</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="openid" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								 <button type="submit" class="btn btn-default">登陆</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>

</html>