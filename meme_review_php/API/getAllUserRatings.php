<?php

require "../Classes/DatabaseHandler.php";


$account = new Account();
$account->setUser_id(filter_input(INPUT_POST, "user_id", FILTER_SANITIZE_STRING));

$handler = new DatabaseHandler();
$result = $handler->getAllUserRatings($account);
echo json_encode(array("status" => "success", "result" => $result));
?>

