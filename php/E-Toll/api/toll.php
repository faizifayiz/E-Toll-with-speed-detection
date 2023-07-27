<?php
include("connection.php");
error_reporting(0);

/*$userid = "2";
$lat = "10.0068311";
$lon = "76.3044052";*/

$userid = $_POST['uid'];
$lat = $_POST['lat'];
$lon = $_POST['lon'];

$sql = "SELECT id, gate_name, amount, SQRT(POW(69.1 * (latitude - '$lat'), 2) + POW(69.1 * ('$lon' - longitude) * COS(latitude / 57.3), 2)) AS distance FROM toll HAVING distance < 1 ORDER BY distance";
$ress = mysqli_query($con, $sql) or die(mysqli_error($con));

if (mysqli_num_rows($ress) > 0) {
    $nearestToll = null;
    $minDistance = PHP_FLOAT_MAX;

    while ($row = mysqli_fetch_assoc($ress)) {
        if ($row['distance'] < $minDistance) {
            $minDistance = $row['distance'];
            $nearestToll = $row;
        }
    }

    if ($nearestToll !== null) {
        $tollAmount = $nearestToll['amount'];
        $boothName = $nearestToll['gate_name'];
        $tid = $nearestToll['id'];

        // Insert data into the user_toll table
        $insertDate = date('Y-m-d'); // Current date in YYYY-MM-DD format
        $insertTime = date('H:i:s'); // Current time in HH:MM:SS format
        $status = "pending";

        $insertQuery = "INSERT INTO user_toll (uid, tid, amount, toll_booth, date, time, status)
                        VALUES ('$userid', '$tid', '$tollAmount', '$boothName', '$insertDate', '$insertTime', '$status')";

        if (mysqli_query($con, $insertQuery)) {
            echo "Data inserted into user_toll successfully.";
        } else {
            echo "Error: " . mysqli_error($con);
        }
    } else {
        echo "Toll detected";
    }
} else {
    echo "failed";
}
?>
