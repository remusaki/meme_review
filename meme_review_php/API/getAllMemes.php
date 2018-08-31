<?php

require "../Classes/DatabaseHandler.php";

$handler = new DatabaseHandler();

$result = $handler->getAllMemes();
echo json_encode(array("status" => "success", "result" => $result));
?>

