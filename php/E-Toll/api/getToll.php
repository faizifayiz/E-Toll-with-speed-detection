<?php

include("connection.php");

$uid = $_REQUEST['uid'];

$sql = "Select * from user_toll WHERE uid='$uid' and status='pending'";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result))
		$data["data"][] = $row;
	echo json_encode($data);
}
else{
	echo "failed";
}

?>