CREATE DATABASE IF NOT EXISTS location_db;

USE location_db;

CREATE TABLE IF NOT EXISTS location
(
  id INT PRIMARY KEY AUTO_INCREMENT,
  latitude DOUBLE,
  longitude DOUBLE,
  address VARCHAR(250)
);
