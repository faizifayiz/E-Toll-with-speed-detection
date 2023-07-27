  <?php
include("connection.php");

$email=$_POST['email'];
$password=$_POST['password'];

//$email="mk@gmail.com";
//$password="777";

$sqli ="SELECT * FROM register where email='$email' and password='$password'";

$result = mysqli_query($con,$sqli);


if(mysqli_num_rows($result)>0){
	
	$row=mysqli_fetch_assoc($result);	
	$data[]=array('id'=> $row['id'], 'name'=> $row['name'], 'vehicle_no'=> $row['vehicle_no']);  
	
	echo json_encode($data);

}
else{
	echo "failed";
}


?>