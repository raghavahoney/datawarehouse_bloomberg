<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload Deals</title>
<link href="<c:url value='/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/css/app.css' />" rel="stylesheet"></link>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
</head>

<body>
	<div class="generic-container" >
	<div align="center">
		<span class="lead">Bloomberg DataWarehouse</span>
		</div>
		<div class="panel panel-default">

			<div class="panel-heading">
				<span class="lead">Import Deals</span>
			</div>
			<div class="uploadcontainer">
				<form:form method="POST" modelAttribute="uploadForm"
					action="upload" enctype="multipart/form-data"
					class="form-horizontal">

					<div class="row">
						<div class="form-group col-md-12">
							<br> <label class="col-md-3 control-lable" for="file"
								style="margin-left: 10px">Select Deals File(.csv)</label>
							<div class="col-md-7">
								<br>
								<table id="fileTable">
									<tr>

										<td><input type="file" name="file" id="file"
											class="form-control input-sm" />
											<div class="has-error">
												<form:errors path="file" class="help-inline" />
											</div></td>
											
											<td><input type="submit" value="Upload"
								class="btn btn-primary btn-sm"></td>
									</tr>
								</table>

							</div>
						</div>
					</div>
				</form:form>
			</div>
			
			<div class="panel-heading">
				<span class="lead">Inquire Results</span>
			</div>
			<div class="uploadcontainer">
				<form:form method="POST" modelAttribute="uploadForm"
					action="getSummary" enctype="multipart/form-data"
					class="form-horizontal">

					<div class="row">
						<div class="form-group col-md-12">
							<br> <label class="col-md-3 control-lable" for="file"
								style="margin-left: 10px">Enter File Name</label>
							<div class="col-md-7">
								<br>
								<table id="fileTable">
									<tr>

										<td><input type="text" name="fileName" id="fileName"
											class="form-control input-sm" />
											<div class="has-error">
												<form:errors path="fileName" class="help-inline" />
											</div></td>
											<td>
											<input type="submit" value="Inquire"
								class="btn btn-primary btn-sm">
											</td>
									</tr>
								</table>

							</div>
						</div>
					</div>

				</form:form>
			</div>
		</div>
		<div align="center">
	<c:forEach var="message" items="${messages}">
		<span class="alert ${message.key}">${message.value}</span>
		<br />
	</c:forEach>
	</div>
	</div>
</body>
</html>