<?php

include("connection.php");

$uid=$_POST['uid'];
$report = $_POST['report'];
$feedback = $_POST['feedback'];



$sql ="INSERT INTO feedback (uid,feedback,report) VALUES ('$uid','$feedback','$report')";

if(mysqli_query($con,$sql)){
	
	echo "success";
}
else{

	echo "failed";


?>