<?php
error_reporting(0);
$status=$_REQUEST['status'];
if ($status == "logout")
{
    session_start();
    session_unset();
    session_destroy();
	header("location:../login/login.php");
}
?>
<?php
include("../common/menu.php");
	
?>


    <style>
#right
{
	
float:right;	
color:#333;
font-size:12px;
}
</style>

<style>
#right
{
	
float:right;	
color:#333;
font-size:12px;
}
.userd
{
color:#333;	
}
.red
{
background:#FFECF4;
padding:20px;	
}


#right
{
	
float:right;	
color:#333;
font-size:12px;
}
.userd
{
color:#333;	
}
.red
{
background:#F36;
padding:10px;	
}
.table
{
margin-bottom:10px;
background:#E6F4FF;	
}
.sep
{
height:40px;
background:#666;	
}
</style>
       


        <div class="content" >
           
                <div class="row">
                    <div class="col-md-12">
                       
                           <div class="header">
						   
                            <div class="content all-icons">
                                <div class="row" style="   ">
                                           
                              
                                <div class="font-icon-list col-lg-2 col-md-6 col-sm-9 col-xs-6 col-xs-9">
                                <a href="../user_tbl/select.php">    <div class="font-icon-detail" style="background-color:#FFF"><i class="pe-7s-user"></i>
							
                                     <b> <input type="text" value="USER DETAILS" style="color:#000000">
                                    </div></a>
                                  </div>
								  
								  
                                <div class="font-icon-list col-lg-2 col-md-6 col-sm-6 col-xs-6 col-xs-9">
                                <a href="../toll/select.php">    <div class="font-icon-detail" style="background-color:#FFF"><i class="pe-7s-network"></i>
                                       <b><input type="text" value="Toll Booth" style="color:#000000">
                                    </div></a>
                                  </div>
								  
								   <div class="font-icon-list col-lg-2 col-md-6 col-sm-6 col-xs-6 col-xs-9">
                                <a href="../feedback/select.php">    <div class="font-icon-detail" style="background-color:#FFF"><i class="pe-7s-chat"></i>
                                       <b><input type="text" value="Feedback" style="color:#000000">
                                    </div></a>
                                  </div>
								  
								   <div class="font-icon-list col-lg-2 col-md-6 col-sm-6 col-xs-6 col-xs-9">
                                <a href="../fine/select.php">    <div class="font-icon-detail" style="background-color:#FFF"><i class="pe-7s-news-paper"></i>
                                       <b><input type="text" value="Fine Details" style="color:#000000">
                                    </div></a>
                                  </div>
								  
								   <div class="font-icon-list col-lg-2 col-md-6 col-sm-6 col-xs-6 col-xs-9">
                                <a href="../user_toll/select.php">    <div class="font-icon-detail" style="background-color:#FFF"><i class="pe-7s-safe"></i>
                                       <b><input type="text" value="User Toll" style="color:#000000">
                                    </div></a>
                                  </div>
								  
								    <div class="font-icon-list col-lg-2 col-md-6 col-sm-6 col-xs-6 col-xs-9">
                                <a href="../Payment/select.php">    <div class="font-icon-detail" style="background-color:#FFF"><i class="pe-7s-cash"></i>
                                       <b><input type="text" value="Payment Details" style="color:#000000">
                                    </div></a>
                                  </div>
								  
								 <!--  <div class="font-icon-list col-lg-3 col-md-6 col-sm-6 col-xs-6 col-xs-9">
                                <a href="../reminder_tbl/select.php">    <div class="font-icon-detail"><i class="pe-7s-bell"></i>
                                      <input type="text" value="REMINDERS">
                                    </div></a>
                                  </div> -->
								  
								 

                                </div>

                            </div>
                        </div>
                    </div>

                </div>
                
            </div>
        </div>
    </div>
</div>


</body>

    <!--   Core JS Files   -->
    <script src="../assets/js/jquery-1.10.2.js" type="text/javascript"></script>
	<script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>

	<!--  Checkbox, Radio & Switch Plugins -->
	<script src="../assets/js/bootstrap-checkbox-radio-switch.js"></script>

	<!--  Charts Plugin -->
	<script src="../assets/js/chartist.min.js"></script>

    <!--  Notifications Plugin    -->
    <script src="../assets/js/bootstrap-notify.js"></script>

    <!--  Google Maps Plugin    -->
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>

    <!-- Light Bootstrap Table Core javascript and methods for Demo purpose -->
	<script src="../assets/js/light-bootstrap-dashboard.js"></script>

	<!-- Light Bootstrap Table DEMO methods, don't include it in your project! -->
	<script src="../assets/js/demo.js"></script>

	

</html>
