-- 1. Skapa och använd databasen
CREATE DATABASE IF NOT EXISTS `simons_gearhub`;
USE `simons_gearhub`;

-- 2. Rensa befintliga tabeller
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `rentals`;
DROP TABLE IF EXISTS `income`;
DROP TABLE IF EXISTS `tents`;
DROP TABLE IF EXISTS `kayaks`;
DROP TABLE IF EXISTS `bikes`;
DROP TABLE IF EXISTS `members`;
SET FOREIGN_KEY_CHECKS = 1;

-- 3. Skapa tabeller
CREATE TABLE `members` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(64) NOT NULL,
    `last_name` VARCHAR(64) NOT NULL,
    `email` VARCHAR(64) UNIQUE,
    `member_since` DATE
);

CREATE TABLE `bikes` (
 `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
 `name` VARCHAR(255),
 `bike_type` VARCHAR(255),
 `gear_count` INT,
 `price` DECIMAL(10,2),
 `status` VARCHAR(50)
);

CREATE TABLE `kayaks` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255),
  `number_of_seats` INT,
  `has_rudder` TINYINT(1),
  `price` DECIMAL(10,2),
  `status` VARCHAR(50)
);

CREATE TABLE `tents` (
 `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
 `name` VARCHAR(255),
 `capacity` INT,
 `weight` DOUBLE,
 `price` DECIMAL(10,2),
 `status` VARCHAR(50)
);

CREATE TABLE `income` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `amount` DECIMAL(10,2),
  `date` DATE
);

CREATE TABLE `rentals` (
   `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
   `rental_date` DATETIME,
   `return_date` DATETIME,
   `rental_object_id` BIGINT,
   `rental_type` VARCHAR(50),
   `member_id` BIGINT,
   `income_id` BIGINT,
   CONSTRAINT `fk_rentals_member` FOREIGN KEY (`member_id`) REFERENCES `members`(`id`),
   CONSTRAINT `fk_rentals_income` FOREIGN KEY (`income_id`) REFERENCES `income`(`id`)
);

-- 4. Inserts
INSERT INTO `members` (`first_name`, `last_name`, `email`, `member_since`) VALUES
   ('Simon', 'Toivola', 'simon.toivola@mail.com', '2025-09-01'),
   ('Sleven', 'Spielberg', 'sleven.spielberg@hollywood.biz', '1998-07-24'),
   ('Herner', 'Werzog', 'herner.werzog@hollywood.biz', '1972-12-29'),
   ('Soe', 'Zaldaña', 'soe.zaldana@hollywood.biz', '2009-12-18'),
   ('Billie Mobby', 'Grey', 'billie.mobby.grey@hollywood.biz', '2016-07-15'),
   ('Hom', 'Tardy', 'hom.tardy@hollywood.biz', '2012-07-20'),
   ('Kristoffer', 'Nålan', 'kristoffer.nalan@hollywood.biz', '2020-09-03'),
   ('David', 'Lynch-Mob', 'david.lynch-mob@hollywood.biz', '2001-10-19'),
   ('Sara', 'N', 'no-reply@ihaveaboyfriend.com', '2026-01-18');

INSERT INTO `bikes` (`name`, `bike_type`, `gear_count`, `price`, `status`) VALUES
   ('Specialized Stumpjumper EVO', 'MTB', 12, 450.00, 'AVAILABLE'),
   ('Canyon Grizl CF SL 7', 'Gravel', 11, 350.00, 'AVAILABLE'),
   ('Trek Madone SL 6', 'Road', 22, 500.00, 'AVAILABLE'),
   ('Crescent Abisko', 'Hybrid', 24, 250.00, 'AVAILABLE'),
   ('Specialized Turbo Vado 4.0', 'Electric', 10, 650.00, 'AVAILABLE');

INSERT INTO `kayaks` (`name`, `number_of_seats`, `has_rudder`, `price`, `status`) VALUES
  ('Melker Väderö', 1, 1, 400.00, 'AVAILABLE'),
  ('Prijon Kodiak', 1, 1, 350.00, 'AVAILABLE'),
  ('Point 65 Mercury GTX', 1, 1, 450.00, 'AVAILABLE'),
  ('Tahe Marine Lifestyle 420', 1, 0, 300.00, 'AVAILABLE'),
  ('Valley Etain 17.5', 1, 1, 550.00, 'AVAILABLE');

INSERT INTO `tents` (`name`, `capacity`, `weight`, `price`, `status`) VALUES
  ('Hilleberg Keron 4 GT', 4, 5.5, 600.00, 'AVAILABLE'),
  ('Fjällräven Abisko Lite 2', 2, 2.1, 400.00, 'AVAILABLE'),
  ('MSR Hubba Hubba NX', 2, 1.7, 350.00, 'AVAILABLE'),
  ('Helsport Fjellheimen Pro 3', 3, 3.4, 450.00, 'AVAILABLE'),
  ('Big Agnes Copper Spur HV UL3', 3, 1.8, 480.00, 'AVAILABLE');

