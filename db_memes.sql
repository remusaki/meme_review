-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 01, 2018 at 08:31 PM
-- Server version: 10.1.32-MariaDB
-- PHP Version: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_memes`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `addAccount` (IN `pr_lastname` VARCHAR(64), IN `pr_firstname` VARCHAR(64), IN `pr_username` VARCHAR(64), IN `pr_password` TEXT)  NO SQL
INSERT INTO tbl_users (lastname,
                       firstname,
                       username,
                       password)
VALUES (pr_lastname,
        pr_firstname,
        pr_username,
        pr_password)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addMeme` (IN `pr_name` VARCHAR(64), IN `pr_description` VARCHAR(64), IN `pr_fullpath` TEXT, IN `pr_category` TEXT)  NO SQL
INSERT INTO tbl_memes (tbl_memes.name,
                       tbl_memes.description,
                       tbl_memes.fullpath,
                       tbl_memes.category)
VALUES(pr_name,
       pr_description,
       pr_fullpath,
       pr_category)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addRating` (IN `pr_user_id` INT, IN `pr_meme_id` INT, IN `pr_rating_id` INT, IN `pr_rating_comment` TEXT)  NO SQL
INSERT INTO `tbl_user_ratings` (`user_rating_id`, 
                                `user_id`, 
                                `meme_id`, 
                                `rating_id`, 
                                `rating_comment`)
VALUES (NULL, 
        pr_user_id, 
        pr_meme_id, 
        pr_rating_id, 
        pr_rating_comment)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `checkExistMeme_old` (IN `pr_memes_name` VARCHAR(64))  NO SQL
SELECT memes_id FROM tbl_memes
WHERE memes_name = pr_memes_name$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `checkExistRating` (IN `pr_user_id` INT, IN `pr_memes_id` INT)  NO SQL
SELECT tbl_user_ratings.user_rating_id
FROM tbl_user_ratings
JOIN tbl_users on tbl_user_ratings.user_id = tbl_users.user_id
JOIN tbl_memes on tbl_user_ratings.meme_id = tbl_memes.memes_id
JOIN tbl_ratings on tbl_user_ratings.rating_id = tbl_ratings.rating_id
WHERE tbl_users.user_id = pr_user_id AND tbl_memes.memes_id = pr_memes_id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `checkExistUser` (IN `pr_username` VARCHAR(64))  NO SQL
SELECT tbl_users.user_id FROM tbl_users
WHERE tbl_users.username = pr_username$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteMeme` (IN `pr_memes_id` INT)  NO SQL
DELETE FROM tbl_memes WHERE memes_id = pr_memes_id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `forgotPassword` (IN `pr_username` VARCHAR(64), IN `pr_password` TEXT)  NO SQL
UPDATE tbl_users set tbl_users.password = pr_password
WHERE tbl_users.username = pr_username$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllMemeRating` (IN `pr_memes_id` INT)  NO SQL
SELECT tbl_users.lastname, tbl_users.firstname, tbl_user_ratings.rating_id, tbl_user_ratings.rating_comment
FROM tbl_user_ratings
JOIN tbl_users on tbl_user_ratings.user_id = tbl_users.user_id
JOIN tbl_memes on tbl_user_ratings.meme_id = tbl_memes.memes_id
WHERE tbl_memes.memes_id = pr_memes_id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllMemes` ()  NO SQL
SELECT memes_id, name, description, fullpath, timestamp, isApproved, category FROM tbl_memes$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllUserRatings` (IN `pr_user_id` INT)  NO SQL
SELECT tbl_memes.memes_id, tbl_memes.name, tbl_memes.description, tbl_memes.fullpath, tbl_memes.timestamp, tbl_memes.isApproved, tbl_memes.category, tbl_user_ratings.rating_id
FROM tbl_user_ratings
JOIN tbl_users on tbl_user_ratings.user_id = tbl_users.user_id
JOIN tbl_memes on tbl_user_ratings.meme_id = tbl_memes.memes_id
JOIN tbl_ratings on tbl_user_ratings.rating_id = tbl_ratings.rating_id
WHERE tbl_users.user_id = pr_user_id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllUserRatingsASC` (IN `pr_user_id` INT)  NO SQL
SELECT tbl_memes.memes_id, tbl_memes.name, tbl_memes.description, tbl_memes.fullpath, tbl_memes.timestamp, tbl_memes.isApproved, tbl_memes.category, tbl_user_ratings.rating_id
FROM tbl_user_ratings
JOIN tbl_users on tbl_user_ratings.user_id = tbl_users.user_id
JOIN tbl_memes on tbl_user_ratings.meme_id = tbl_memes.memes_id
JOIN tbl_ratings on tbl_user_ratings.rating_id = tbl_ratings.rating_id
WHERE tbl_users.user_id = pr_user_id
ORDER BY tbl_user_ratings.rating_id ASC$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllUserRatingsDESC` (IN `pr_user_id` INT)  NO SQL
SELECT tbl_memes.memes_id, tbl_memes.name, tbl_memes.description, tbl_memes.fullpath, tbl_memes.timestamp, tbl_memes.isApproved, tbl_memes.category, tbl_user_ratings.rating_id
FROM tbl_user_ratings
JOIN tbl_users on tbl_user_ratings.user_id = tbl_users.user_id
JOIN tbl_memes on tbl_user_ratings.meme_id = tbl_memes.memes_id
JOIN tbl_ratings on tbl_user_ratings.rating_id = tbl_ratings.rating_id
WHERE tbl_users.user_id = pr_user_id
ORDER BY tbl_user_ratings.rating_id DESC$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getOneUserRatings` (IN `pr_user_id` VARCHAR(64), IN `pr_memes_id` VARCHAR(64))  NO SQL
SELECT tbl_ratings.rating_id, tbl_user_ratings.rating_comment
FROM tbl_user_ratings
JOIN tbl_users on tbl_user_ratings.user_id = tbl_users.user_id
JOIN tbl_memes on tbl_user_ratings.meme_id = tbl_memes.memes_id
JOIN tbl_ratings on tbl_user_ratings.rating_id = tbl_ratings.rating_id
WHERE tbl_users.user_id = pr_user_id AND tbl_memes.memes_id = pr_memes_id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getUser` (IN `pr_username` VARCHAR(64), IN `pr_password` TEXT)  NO SQL
SELECT tbl_users.user_id, tbl_users.lastname, tbl_users.firstname, tbl_users.account_type FROM tbl_users
WHERE tbl_users.username = pr_username AND tbl_users.password = pr_password$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserDetails` (IN `pr_user_id` INT)  NO SQL
SELECT tbl_users.lastname, tbl_users.firstname, tbl_users.username FROM tbl_users
WHERE tbl_users.user_id = pr_user_id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateAccount` (IN `pr_lastname` VARCHAR(64), IN `pr_firstname` VARCHAR(64), IN `pr_username` VARCHAR(64), IN `pr_password` TEXT)  NO SQL
UPDATE tbl_users set lastname = pr_lastname,
                  	 firstname = pr_firstname,
                 	 password = pr_password
WHERE username = pr_username$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateMemeApproval` (IN `pr_memes_id` INT, IN `pr_approval` TEXT)  NO SQL
UPDATE tbl_memes set tbl_memes.isApproved = pr_approval
WHERE tbl_memes.memes_id = pr_memes_id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateRating` (IN `pr_user_id` INT, IN `pr_meme_id` INT, IN `pr_rating_id` INT, IN `pr_rating_comment` TEXT)  NO SQL
UPDATE tbl_user_ratings SET tbl_user_ratings.rating_id = pr_rating_id,
    						tbl_user_ratings.rating_comment = pr_rating_comment
WHERE tbl_user_ratings.user_id = pr_user_id AND tbl_user_ratings.meme_id = pr_meme_id$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_memes`
--

CREATE TABLE `tbl_memes` (
  `memes_id` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `description` text NOT NULL,
  `fullpath` text NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isApproved` varchar(12) NOT NULL DEFAULT 'pending',
  `category` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_memes`
--

INSERT INTO `tbl_memes` (`memes_id`, `name`, `description`, `fullpath`, `timestamp`, `isApproved`, `category`) VALUES
(1, 'Somebody Toucha My Spaghet', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://img00.deviantart.net/4170/i/2018/002/3/2/somebody_toucha_my_spaghet_by_viii_kun-dbyp8my.jpg', '2018-06-01 18:00:09', 'approved', 'Wholesome'),
(2, 'Meme Review', 'CLAPCLAP', 'https://i.redditmedia.com/l31q-uvdCKDfyBlIBXMoW3FA7X0NZEabyxulNEcUeGA.png?w=1024&s=2055d8a71b13838d6cf8dc49f2ce017f', '2018-06-01 13:23:38', 'approved', 'Dank'),
(3, 'But Can You Do This?', 'is a memorable quote uttered by YouTuber PewDiePie...', 'http://i0.kym-cdn.com/entries/icons/original/000/025/142/can_u_do_this.jpg', '2018-05-30 10:28:25', 'approved', 'Nonsense'),
(5, 'asdasd', 'asdasdasd', 'http://i0.kym-cdn.com/photos/images/newsfeed/001/217/729/f9a.jpg', '2018-06-01 18:02:13', 'approved', 'Nonsense');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_ratings`
--

CREATE TABLE `tbl_ratings` (
  `rating_id` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  `description` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_ratings`
--

INSERT INTO `tbl_ratings` (`rating_id`, `value`, `description`) VALUES
(1, 1, 'Meh'),
(2, 2, 'Hmmm'),
(3, 3, 'Dope'),
(4, 4, 'Dank'),
(5, 5, 'Lit');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE `tbl_users` (
  `user_id` int(11) NOT NULL,
  `lastname` varchar(64) NOT NULL,
  `firstname` varchar(64) NOT NULL,
  `username` varchar(64) NOT NULL,
  `password` text NOT NULL,
  `account_type` varchar(5) NOT NULL DEFAULT 'user'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_users`
--

INSERT INTO `tbl_users` (`user_id`, `lastname`, `firstname`, `username`, `password`, `account_type`) VALUES
(1, 'Garcia', 'Louis', 'admin', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'admin'),
(2, 'Garcia', 'Louis', 'user', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'user'),
(3, 'Labindalawa', 'Diego', 'user2', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'user'),
(4, 'Garcia', 'Louis', 'user3', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'user'),
(5, 'asdasd', 'asdasdasd', 'asdasdasd', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'user');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user_ratings`
--

CREATE TABLE `tbl_user_ratings` (
  `user_rating_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `meme_id` int(11) NOT NULL,
  `rating_id` int(11) NOT NULL,
  `rating_comment` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_memes`
--
ALTER TABLE `tbl_memes`
  ADD PRIMARY KEY (`memes_id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `tbl_ratings`
--
ALTER TABLE `tbl_ratings`
  ADD PRIMARY KEY (`rating_id`),
  ADD UNIQUE KEY `rating_value` (`value`);

--
-- Indexes for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `tbl_user_ratings`
--
ALTER TABLE `tbl_user_ratings`
  ADD PRIMARY KEY (`user_rating_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `meme_id` (`meme_id`),
  ADD KEY `rating_id` (`rating_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_memes`
--
ALTER TABLE `tbl_memes`
  MODIFY `memes_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_users`
--
ALTER TABLE `tbl_users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_user_ratings`
--
ALTER TABLE `tbl_user_ratings`
  MODIFY `user_rating_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_user_ratings`
--
ALTER TABLE `tbl_user_ratings`
  ADD CONSTRAINT `tbl_user_ratings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_users` (`user_id`),
  ADD CONSTRAINT `tbl_user_ratings_ibfk_2` FOREIGN KEY (`meme_id`) REFERENCES `tbl_memes` (`memes_id`),
  ADD CONSTRAINT `tbl_user_ratings_ibfk_3` FOREIGN KEY (`rating_id`) REFERENCES `tbl_ratings` (`rating_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
