<?php

require "../Classes/Meme.php";
require "../Classes/DatabaseHandler.php";

$meme = new Meme();
$meme->setName(filter_input(INPUT_POST, "name", FILTER_SANITIZE_STRING));
$meme->setDescription(filter_input(INPUT_POST, "description", FILTER_SANITIZE_STRING));
$meme->setFullpath(filter_input(INPUT_POST, "fullpath", FILTER_SANITIZE_STRING));
$meme->setCategory(filter_input(INPUT_POST, "category", FILTER_SANITIZE_STRING));

$handler = new DatabaseHandler();

$status = $handler->addMeme($meme);

echo json_encode($status);

?>