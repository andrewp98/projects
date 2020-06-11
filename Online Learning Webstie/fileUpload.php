<?php
session_start();
$db = mysqli_connect('localhost', 'root', '', 'site');
$target_dir = "uploads/";
$username = $_SESSION['username'];

if(isset($_FILES["file"])) {
    $target_file = $target_dir . basename($_FILES["file"]["name"]);
    $query = "INSERT INTO fileupload (username, filePath) 
	VALUES('$username', '$target_file')";
	mysqli_query($db, $query);
	if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file);) {
        echo "The file ". basename( $_FILES["file"]["name"]). " has been uploaded.";
    } else {
        echo "Sorry, there was an error uploading your file.";
    }
}

?>