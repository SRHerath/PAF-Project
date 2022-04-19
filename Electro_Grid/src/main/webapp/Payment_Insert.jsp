<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Form</title>

<meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="assets/img/favicon.png" rel="icon">
  <link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Open+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&family=Poppins:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&family=Source+Sans+Pro:ital,wght@0,300;0,400;0,600;0,700;1,300;1,400;1,600;1,700&display=swap" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="assets/vendor/aos/aos.css" rel="stylesheet">
  <link href="assets/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
  <link href="assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">

  <!-- Variables CSS Files. Uncomment your preferred color scheme -->
  <link href="assets/css/variables.css" rel="stylesheet">
  <!-- <link href="assets/css/variables-blue.css" rel="stylesheet"> -->
  <!-- <link href="assets/css/variables-green.css" rel="stylesheet"> -->
  <!-- <link href="assets/css/variables-orange.css" rel="stylesheet"> -->
  <!-- <link href="assets/css/variables-purple.css" rel="stylesheet"> -->
  <!-- <link href="assets/css/variables-red.css" rel="stylesheet"> -->
  <!-- <link href="assets/css/variables-pink.css" rel="stylesheet"> -->

  <!-- Template Main CSS File -->
  <link href="assets/css/main.css" rel="stylesheet">

  <!-- =======================================================
  * Template Name: HeroBiz - v2.1.0
  * Template URL: https://bootstrapmade.com/herobiz-bootstrap-business-template/
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->
</head>
<body>

		<center><h1>Payment Details</h1></center><br/><br/><br/>
		
		<div class="container">

        <div class="row gy-5 gx-lg-5">

          <div class="col-lg-4">

            <div class="info">
              <h3>Pay Amount:</h3>
              

             



            </div>

          </div>
		
		
				
			<div class="col-lg-8">
            	
            	<form action="#" method="post" role="form" class="php-email-form" onChange="dis_able()">
             		 <div class="row">
               			 <div class="col-md-6 form-group">
               			 
               			 	<b>Name on Card:</b>
                 			 	<input type="text" name="CardN" class="form-control" id="Cname" placeholder="Card owner's Name" required>
               
               			 </div>
               			 
                		<div class="col-md-6 form-group mt-3 mt-md-0">
                	
                			<b>Card Number:</b>
                  				<input type="text" name="CardNm" class="form-control" id="CNo" placeholder="0000 0000 0000 0000" pattern="[0-9]{16}" required>
               		    </div>
              </div><br/><br/>
              
              
              
                
                			 <b>Expiration Date:</b><br/>
                			 <div class="row">
                			 <div class="col-md-6 form-group">	
								<b>Month:</b>
						 			<select name="PayExMn" class="form-control" id="Month" required>
										<option value="null">MM</option>
										<option value="1">1</option>   	
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>   	
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
									</select>
								</div>	
									
									<div class="col-md-6 form-group mt-3 mt-md-0">
								<b>Year:</b>
						 			<select name="PayExYr" class="form-control" id="Year" required>
										 <option value="null">YY</option>
										 <!--  option value="2022">2022</option-->   	
										 <option value="2023">2023</option>
										 <option value="2024">2024</option>
										 <option value="2025">2025</option>
										 <option value="2026">2026</option>
										 <option value="2027">2027</option>
										 <option value="2028">2028</option>
										 <option value="2029">2029</option>
										 <option value="2030">2030</option>
									</select><br/><br/> 	
									</div>		
              </div>
              
              <div class="row">
              	
              	 <div class="col-md-6 form-group">
               			 
               			 	<b>Amount:</b>
                 			 	<input type="text" name="Amount" class="form-control" id="amount" placeholder="00.00" required>
               
               			 </div>
               			 
              	<div class="col-md-6 form-group">
              
              		     <b>CVC/CVV:</b>
                             <input type="text" name="cvc" class="form-control" id="CV" placeholder="000" pattern="[0-9]{3}" required>
              	</div>
              </div><br/><br/>
             
              <div class="text-center"><button type="submit" name="add"><b>Pay the Bill</b></button></div>
            </form>
          </div><br/><br/>
          
       
		
				
	</div></div>
					
					


</body>
</html>