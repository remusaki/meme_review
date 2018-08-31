<?php

require "../Classes/Meme.php";
require "../Classes/DatabaseHandler.php";

$rating = new Rating();
$rating->setMeme_id(filter_input(INPUT_POST, "meme_id", FILTER_SANITIZE_STRING));
$handler = new DatabaseHandler();
$result = $handler->getAllMemeRating($rating);
echo json_encode(array("status" => "success", "result" => $result));
?>