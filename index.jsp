<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<!-- Script to Activate the Carousel -->
<script>
	$('.carousel').carousel({
		interval : 2000
	//changes the speed
	})
</script>
</head>
<body>

	<jsp:include page="include/header.jsp" />

	<div class="container">
		<div class="row ">
			<div class="box">
				<div class="col-lg-6 text-center">
					<div id="carousel-example-generic" class="carousel slide"
						data-ride="carousel">
						<!-- data-ride="carousel" 자동 슬라이드 기능 -->

						<ol class="carousel-indicators hidden-xs">
							<li data-target="#carousel-example-generic" data-slide-to="0"
								class="active"></li>
							<li data-target="#carousel-example-generic" data-slide-to="1"></li>
							<li data-target="#carousel-example-generic" data-slide-to="2"></li>
						</ol>
						<div class="carousel-inner">
							<div class="item active">
								<img class="img-responsive img-full" src="img/huracan.jpg">
							</div>
							<div class="item">
								<img class="img-responsive img-full" src="img/m3.jpg">
								<!-- img-full 옵션 class -->
							</div>
							<div class="item">
								<img class="img-responsive img-full" src="img/911.jpg">
							</div>
						</div>
						<a class="left carousel-control" href="#carousel-example-generic"
							data-slide="prev"> <span class="icon-prev"></span>
						</a> <a class="right carousel-control"
							href="#carousel-example-generic" data-slide="next"> <span
							class="icon-next"></span>
						</a>
					</div>
				</div>

				<div class="col-lg-6 text-center">

					<h2>영상 링크</h2>
					<p>원하는 영상을 링크해 보세요.</p>
					<hr>
					<h3>영상 자동재생</h3>
					<iframe width="100%" height="485px"
						src="https://www.youtube.com/embed/C1yP_GQ9s4E?autoplay=1"
						frameborder="0"
						allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
						allowfullscreen> </iframe>
				</div>
			</div>
		</div>
	</div>
	<div id="img" align="center">
		<img class="img-responsive img-center" src="img/intro.jpg" alt="">
	</div>
	<jsp:include page="include/footer.jsp" />
</body>
</html>