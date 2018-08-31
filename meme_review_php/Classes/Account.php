<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Account
 *
 * @author djmurusaki
 */
class Account implements JsonSerializable {
    
    private $user_id, $lastname, $firstname, $username, $password, $account_type;
    
    function __construct() {
        $this->user_id = "";
        $this->lastname = "";
        $this->firstname = "";
        $this->username = "";
        $this->password = "";
        $this->account_type = "";
    }
    
    public function getUser_id() {
        return $this->user_id;
    }

    public function setUser_id($user_id) {
        $this->user_id = $user_id;
    }

        public function getLastname() {
        return $this->lastname;
    }

    public function getFirstname() {
        return $this->firstname;
    }

    public function getUsername() {
        return $this->username;
    }

    public function getPassword() {
        return $this->password;
    }

    public function setLastname($lastname) {
        $this->lastname = $lastname;
    }

    public function setFirstname($firstname) {
        $this->firstname = trim($firstname);
    }

    public function setUsername($username) {
        $this->username = trim($username);
    }

    public function setPassword($password) {
        if(!empty(trim($password))){
            $this->password = hash("sha256", trim($password));
        }
    }
    public function getAccount_type() {
        return $this->account_type;
    }

    public function setAccount_type($account_type) {
        $this->account_type = $account_type;
    }
    
    public function jsonSerialize() {
        return get_object_vars($this);
    }
}
