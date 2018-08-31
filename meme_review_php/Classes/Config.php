<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Config
 *
 * @author student
 */
class Config {

    private $host, $user, $password, $database, $link;

    function __construct() {
        $this->host = "localhost";
        $this->user = "root";
        $this->password = "";
        $this->database = "db_memes";
        $this->link = "";

        $this->connect();
    }

    public function connect() {
        $this->link = mysqli_connect($this->host, $this->user, $this->password, $this->database);

        if (!$this->link) {
            die("Failed to Connect: (" . mysqli_connect_errno() . ")");
        }
    }

    function getLink() {
        return $this->link;
    }

}
