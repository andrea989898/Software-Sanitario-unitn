<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="javax.servlet.http.Cookie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="description" content="SoftwareSanitarioUnitn" />
  <meta name="author" content="I magici ragazzi" />

  <title>Healthcare software</title>

  <!-- Bootstrap core CSS -->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom fonts for this template -->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet">
  <link rel="icon" href="<%=request.getContextPath()%>/images/favicon/ssp.png">
  <link href="vendor/simple-line-icons/css/simple-line-icons.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">

  <!-- Custom styles for this template -->
  <link href="<%=request.getContextPath()%>/css_login/landing-page.min.css" rel="stylesheet">

</head>

<body>
    <%
	    Cookie[] cookies=request.getCookies();
	    String email = "", password = "",rememberVal="";
	    if (cookies != null) {
	         for (Cookie cookie : cookies) {
	           if(cookie.getName().equals("cookemail")) {
	             email = cookie.getValue();
	           }
	           if(cookie.getName().equals("cookpass")){
	             password = cookie.getValue();
	           }
	           if(cookie.getName().equals("cookrem")){
	             rememberVal = cookie.getValue();
	           }
	        }
	    }

    %>
  <!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
      <a class="navbar-brand" href="#">Healthcare software</a>
      <button data-toggle="modal" data-target="#login" class="btn btn-secondary"><i class="fa fa-refresh w3-margin-right"></i>Login</button>
    </div>
  </nav>
  <c:choose>
        <c:when test="${error.equals('yes')}">
            <div class="modal fade" id="login" style="display:none" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Login</h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>
                        <div class="modal-body">
                            <form method="POST" action="login.handler">
                                <div class="form-group"><h6>Wrong login previously, wrong email or password! Try again...</h6></div>
                                <div class="form-group"><label class="small mb-1" for="inputEmailAddress">Email</label><input class="form-control py-4" type="email" id="username" name="username" placeholder="mariorossi@email.it" value="<%=email%>" /></div>
                                <div class="form-group"><label class="small mb-1" for="inputPassword">Password</label><input class="form-control py-4" type="password" name="password" id="password" placeholder="password" value="<%=password%>" /></div>
                                <div class="form-group">
                                    <div class="custom-control custom-checkbox"><input class="custom-control-input" id="rememberPasswordCheck" type="checkbox" name="remember" value="1" /><label class="custom-control-label" for="rememberPasswordCheck">Remember password</label></div>
                                </div>
                                <div class="form-group d-flex align-items-center justify-content-between mt-4 mb-0"><button class="btn btn-secondary" type="submit">Login</button><button class="small" data-toggle="modal" data-dismiss="modal" data-target="#forgotpassword">Forgot Password?</button></div>
                            </form>
                        </div>
                        <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
        <c:when test="${newpassword.equals('yes')}">
            <div class="modal fade" id="login" style="display:none" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Login</h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>
                        <div class="modal-body">
                            <form method="POST" action="login.handler">
                                <div class="form-group"><h6>New password sent to your email!</h6></div>
                                <div class="form-group"><label class="small mb-1" for="inputEmailAddress">Email</label><input class="form-control py-4" type="email" id="username" name="username" placeholder="mariorossi@email.it" value="<%=email%>" /></div>
                                <div class="form-group"><label class="small mb-1" for="inputPassword">Password</label><input class="form-control py-4" type="password" name="password" id="password" placeholder="password" value="<%=password%>" /></div>
                                <div class="form-group">
                                    <div class="custom-control custom-checkbox"><input class="custom-control-input" id="rememberPasswordCheck" type="checkbox" name="remember" value="1" /><label class="custom-control-label" for="rememberPasswordCheck">Remember password</label></div>
                                </div>
                                <div class="form-group d-flex align-items-center justify-content-between mt-4 mb-0"><button class="btn btn-secondary" type="submit">Login</button><button class="small" data-toggle="modal" data-dismiss="modal" data-target="#forgotpassword">Forgot Password?</button></div>
                            </form>
                        </div>
                        <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="modal fade" id="login" style="display:none" role="dialog">
              <div class="modal-dialog">
                  <div class="modal-content">
                      <div class="modal-header">
                          <h4 class="modal-title">Login</h4>
                          <button type="button" class="close" data-dismiss="modal">&times;</button>
                      </div>
                      <div class="modal-body">
                          <form method="POST" action="login.handler">
                              <div class="form-group"><label class="small mb-1" for="inputEmailAddress">Email</label><input class="form-control py-4" type="email" id="username" name="username" placeholder="mariorossi@email.it" value="<%=email%>" /></div>
                              <div class="form-group"><label class="small mb-1" for="inputPassword">Password</label><input class="form-control py-4" type="password" name="password" id="password" placeholder="password" value="<%=password%>" /></div>
                              <div class="form-group">
                                  <div class="custom-control custom-checkbox"><input class="custom-control-input" id="rememberPasswordCheck" type="checkbox" name="remember" value="1" /><label class="custom-control-label" for="rememberPasswordCheck">Remember password</label></div>
                              </div>
                              <div class="form-group d-flex align-items-center justify-content-between mt-4 mb-0"><button class="btn btn-secondary" type="submit">Login</button><button class="small" data-toggle="modal" data-dismiss="modal" data-target="#forgotpassword">Forgot Password?</button></div>
                          </form>
                      </div>
                      <div class="modal-footer">
                              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                      </div>
                  </div>
              </div>
          </div>
        </c:otherwise>
    </c:choose>
  
                        
    <div class="modal fade" id="forgotpassword" style="display:none" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Recovery password</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <form method="POST" action="sendEmail.handler">
                        <div class="form-group"><label class="small mb-1" for="inputEmailAddress">Email</label><input class="form-control py-4" type="email" id="email" name="email" placeholder="mariorossi@email.it" /></div>
                        <div class="form-group d-flex align-items-center justify-content-between mt-4 mb-0"><button class="btn btn-secondary" type="submit">Submit</button></div>
                    </form>
                </div>
                <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>                

  <!-- Masthead -->
  <header class="masthead text-white text-center" style="background-image: url('<%=request.getContextPath()%>/img/Sistemasanitario.jpg');">
    <div class="overlay"></div>
    <div class="container" >
      <div class="row">
        <div class="col-xl-9 mx-auto">
          <h1 class="mb-5">Healthcare software with management of exams, visits and recipes.</h1>
        </div>
      </div>
    </div>
  </header>

  <!-- Icons Grid -->
  <section class="features-icons bg-light text-center">
    <div class="container">
      <div class="row">
        <div class="col-lg-4">
          <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">
            <div class="features-icons-icon d-flex">
              <i class="icon-screen-desktop m-auto text-primary"></i>
            </div>
            <h3>Fully Responsive</h3>
            <p class="lead mb-0">This software will look great on any device, no matter the size!</p>
          </div>
        </div>
        <div class="col-lg-4">
          <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">
            <div class="features-icons-icon d-flex">
              <i class="icon-layers m-auto text-primary"></i>
            </div>
            <h3>Lightweight</h3>
            <p class="lead mb-0">Simple, fast and with modal contents!</p>
          </div>
        </div>
        <div class="col-lg-4">
          <div class="features-icons-item mx-auto mb-0 mb-lg-3">
            <div class="features-icons-icon d-flex">
              <i class="icon-check m-auto text-primary"></i>
            </div>
            <h3>Easy to Use</h3>
            <p class="lead mb-0">Easy to use for all people: from young to old!</p>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- Image Showcases -->
  <section class="showcase">
    <div class="container-fluid p-0">
      <div class="row no-gutters">

        <div class="col-lg-6 order-lg-2 text-white showcase-img" style="background-image: url('<%=request.getContextPath()%>/img/iphone7.jpg');"></div>
        <div class="col-lg-6 order-lg-1 my-auto showcase-text">
          <h2>Fully Responsive Design</h2>
          <p class="lead mb-0">When you use this software it will look great on any device, whether it's a phone, tablet, or desktop the page will behave responsively!</p>
        </div>
      </div>
      <div class="row no-gutters">
        <div class="col-lg-6 text-white showcase-img" style="background-image: url('<%=request.getContextPath()%>/img/piuma.jpg');"></div>
        <div class="col-lg-6 my-auto showcase-text">
          <h2>Lightweight</h2>
          <p class="lead mb-0">We have used the best tools to make using the software as fast and easy as possible!</p>
        </div>
      </div>
      <div class="row no-gutters">
        <div class="col-lg-6 order-lg-2 text-white showcase-img" style="background-image: url('<%=request.getContextPath()%>/img/anzianoebambino.jpg');"></div>
        <div class="col-lg-6 order-lg-1 my-auto showcase-text">
          <h2>Easy to Use</h2>
          <p class="lead mb-0">It is easy to use, we have studied the behaviors of different age groups to reach the best compromises!</p>
        </div>
      </div>
    </div>
  </section>

  <!-- Testimonials -->
  <section class="testimonials text-center bg-light">
    <div class="container">
      <h2 class="mb-5">The developers...</h2>
      <div class="row">
        <div class="col-lg-4">
          <div class="testimonial-item mx-auto mb-5 mb-lg-0">
            <img class="img-fluid rounded-circle mb-3" src="<%=request.getContextPath()%>/img/andre.png" alt="">
            <h5>Andrea Bovo</h5>
          </div>
        </div>
        <div class="col-lg-4">
          <div class="testimonial-item mx-auto mb-5 mb-lg-0">
            <img class="img-fluid rounded-circle mb-3" src="<%=request.getContextPath()%>/img/fraservlet.png" alt="">
            <h5>Francesco Di Flumeri</h5>
          </div>
        </div>
        <div class="col-lg-4">
          <div class="testimonial-item mx-auto mb-5 mb-lg-0">
            <img class="img-fluid rounded-circle mb-3" src="<%=request.getContextPath()%>/img/frahtml.png" alt="">
            <h5>Francesco Pozzobon</h5>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- Call to Action -->
  <section class="call-to-action text-white text-center" style="background-image: url('<%=request.getContextPath()%>/img/images.jpg');">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-xl-9 mx-auto">
          <h2 class="mb-4">Ready to get started? Go to the province and register yourself!</h2>
        </div>
      </div>
    </div>
  </section>

  <!-- Footer -->
  <footer class="footer bg-light">
    <div class="container">
      <div class="row">
        <div class="col-lg-6 h-100 text-center text-lg-left my-auto">
          <ul class="list-inline mb-2">
            <li class="list-inline-item">
              <a href="<%=request.getContextPath()%>/download/cookie.pdf" download>Cookies</a>
            </li>
            <li class="list-inline-item">&sdot;</li>
            <li class="list-inline-item">
              <a href="<%=request.getContextPath()%>/download/privacy.pdf" download>Privacy Policy</a>
            </li>
          </ul>
          <p class="text-muted small mb-4 mb-lg-0">&copy; Introduzione programmazione web 2019/2020</p>
        </div>
      </div>
    </div>
  </footer>

  <!-- Bootstrap core JavaScript -->
  <script src="<%=request.getContextPath()%>/vendor/jquery/jquery.min.js"></script>
  <script src="<%=request.getContextPath()%>/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/linkers/connector.js"></script>

</body>

</html>
