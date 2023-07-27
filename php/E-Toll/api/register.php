<?php

include("connection.php");

$name = $_POST['name'];
$email = $_POST['email'];
$vehicle = $_POST['vehicle'];
$password = $_POST['password'];
$phone = $_POST['phone'];


$sql ="INSERT INTO register (name,email,vehicle_no,password,phone) VALUES ('$name','$email','$vehicle','$password','$phone')";



if(mysqli_query($con,$sql)){
	
	$id = mysqli_insert_id($con);
	$sql1="INSERT INTO wallet_tbl (user_id) VALUES ('$id')";
	mysqli_query($con,$sql1);
	echo "success";
	
}
else{
	
	echo"failed";
}


?>