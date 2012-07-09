<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Bootstrap, from Twitter</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="datepicker/datepicker.css" rel="stylesheet">
<style>
body {
	padding-top: 60px;
	/* 60px to make the container go all the way to the bottom of the topbar */
}
</style>
<link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet">

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>

<body>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="#">Project name</a>
				<div class="nav-collapse">
					<ul class="nav">
						<li class="active"><a href="#">Home</a></li>
						<li><a href="#about">About</a></li>
						<li><a href="#contact">Contact</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>

	<div class="container">

		<h1>Trac FixTime</h1>
		<p>
			Use this document as a way to quick start any new project.<br>
			All you get is this message and a barebones HTML document.
		</p>

		<form class="well" action="ShemaServlet" method="post">
			<fieldset>
				<div class="controls">
					<label class="control-label" for="appendedInputButton">Select shema</label>
					<div class="input-append">
						<select name="shema" class="span3" >
							<c:forEach var="shema" items="${shemaModel.shemas}">
								<c:if test="${shema!=shemaModel.shemaForm.shema}">
									<option><c:out value="${shema}"/></option>								
								</c:if>
								<c:if test="${shema==shemaModel.shemaForm.shema}">
									<option selected><c:out value="${shema}"/></option>								
								</c:if>
							</c:forEach>
						</select>
						<button type="submit" class="btn" type="button">Go!</button>
					</div>
				</div>
			</fieldset>
		</form>
		
		<c:if test="${userModel != null}">
			<form class="well" method="post" action="ProcessServlet">
				<fieldset>
					<div class="controls">
					<label class="control-label" for="appendedInputButton">Select
						user</label>
						<div class="input-append">
							<select class="span3" name="user">
								<c:forEach var="user" items="${userModel.users}">
									<c:if test="${user!=userModel.userForm.user}">
										<option><c:out value="${user}"/></option>
									</c:if>
									<c:if test="${user==userModel.userForm.user}">
										<option selected><c:out value="${user}"/></option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="controls">
						<label class="control-label" for="appendedInputButton">Select
							date</label>
						<div class="input-append">
							<input type="text" name="date" class="span2" placeholder="date" value="${userModel.userForm.date}"/>
						</div>
					</div>
					
					<div class="controls">
						<label class="control-label" for="appendedInputButton">Select
							ticket</label>
						<div class="input-append">
							<input type="text" name="ticket" class="span2" placeholder="ticket" value="${userModel.userForm.ticket}"/>
						</div>
					</div>
					
					<br>
					<button type="submit" class="btn btn-primary">Select</button>
				</fieldset>
				
				<c:if test="${errorModel.ticket}">
					<br/>
					<span class="label label-important">Select ticket</span>
				</c:if>
				<c:if test="${errorModel.user}">
					<br/>
					<span class="label label-important">Select user</span>
				</c:if>
				<c:if test="${errorModel.date}">
					<br/>
					<span class="label label-important">Select date</span>
				</c:if>
				
			</form>
		</c:if>
		
		<c:if test="${errorModel.changetime}">
					<br/>
					<span class="label label-important">Select date</span>
		</c:if>
		<c:forEach var="dir" items="${directoryModel.directories}" varStatus="status">
			<form class="well" method="post" action="SaveServlet">
			<fieldset>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>#</th>
						<th>User</th>
						<th>Time</th>
						<th>Hours</th>
					</tr>
				</thead>
				<tbody>
						<tr>
							<td><c:out value="${dir.ticket}"/></td>
							<td><c:out value="${dir.author}"/></td>
							<td>
								<div class="input-append">
									<input type="text" name="changetime" class="span2" value="<c:out value="${dir.time}"/>" id="dp${status.index}"/>
									<input type="hidden" name="unixtime" class="span2" value="<c:out value="${dir.unixtime}"/>"/>
								</div>
							</td>
							<td><c:out value="${dir.hour}"/></td>
						</tr>
				</tbody>
			</table>
			<button type="submit" class="btn btn-primary">Save changes</button>
			</fieldset>
			</form>
		</c:forEach>



	</div>
	<!-- /container -->

	<script src="js/jquery.js"></script>
	<script src="js/bootstrap-datepicker.js"></script>
	<script></script>
</body>
</html>
