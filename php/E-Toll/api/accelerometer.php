<?php

include("connection.php");

$uid = $_POST['uid'];
$x = $_POST['x'];
$y = $_POST['y'];
$z = $_POST['z'];
$latitude = $_POST['lat'];
$longitude = $_POST['lng'];
$acc = $_POST['acc'];
$fine = 1000;
$date = date("Y-m-d"); // Example: 2023-07-24 (Year-Month-Day)
$time = date("H:i:s"); // Example: 14:30:45 (Hour:Minute:Second)
$status = "pending"; // Set the status to "pending"

$sql = "INSERT INTO fine (uid, x, y, z, description, latitude, longitude, fine_amount, date, time, status) VALUES ('$uid', '$x', '$y', '$z', '$acc', '$latitude', '$longitude', '$fine', '$date', '$time', '$status')";

if (mysqli_query($con, $sql)) {
    echo "success";
} else {
    echo "failed";
}
?>
