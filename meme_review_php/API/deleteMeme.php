<?php

require "../Classes/Meme.php";
require "../Classes/DatabaseHandler.php";

$meme = new Meme();
$meme->setMemes_id(filter_input(INPUT_POST, "meme_id", FILTER_SANITIZE_STRING));

$handler = new DatabaseHandler();

$status = $handler->deleteMeme($meme);
echo json_encode($status);

?>