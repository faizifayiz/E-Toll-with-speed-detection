<?php

include("connection.php");
 
/*$userid = "2";
$lat = "10.0025611";
$lon = "76.3059038";*/

$userid = $_POST['userid'];
$lat = $_POST['lat'];
$lon = $_POST['lon'];


	$string="SELECT ref_num as phone FROM user where id='$userid'";
	$result=mysqli_query($con,$string)or die(mysqli_error());

	//echo $result;


if(mysqli_num_rows($result)>0)
{
	while($row=mysqli_fetch_assoc($result))
	{	
	$ress[]=$row; 
	}

	$sql = "SELECT  phone, SQRT( POW(69.1 * (latitude - '$lat'), 2) + POW(69.1 * ('$lon' - longitude) * COS(latitude / 57.3), 2)) AS distance FROM police_station HAVING distance < 2 ORDER BY distance DESC";
	$res = mysqli_query($con,$sql)or die(mysqli_error());

	//echo $sql;

	while($row=mysqli_fetch_assoc($res))
	{	
	$ress[]=$row; 
	}

	$sql = "SELECT  phone, SQRT( POW(69.1 * (latitude - '$lat'), 2) + POW(69.1 * ('$lon' - longitude) * COS(latitude / 57.3), 2)) AS distance FROM hospital_tbl HAVING distance < 2 ORDER BY distance DESC";
	$res = mysqli_query($con,$sql)or die(mysqli_error());
	while($row=mysqli_fetch_assoc($res))
	{	
	$ress[]=$row; 
	}	

	echo json_encode($ress);	
}
else{
	echo"failed";
}

?>