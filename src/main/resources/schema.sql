CREATE TABLE auditorium
(
  id BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  number_of_seats BIGINT(20) NOT NULL
);
CREATE TABLE auditorium_vip_seat
(
  auditorium_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  vipSeats BIGINT(20),
  CONSTRAINT vip_seat_auditorium_fk FOREIGN KEY (auditorium_id) REFERENCES auditorium (id)
);
CREATE INDEX vip_seat_auditorium_fk ON auditorium_vip_seat (auditorium_id);
CREATE TABLE discount_statistics
(
  id BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  birthday_discounts BIGINT(20),
  wholesale_discounts BIGINT(20)
);
CREATE TABLE event
(
  id BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  base_price DOUBLE NOT NULL,
  rating INT(11)
);
CREATE TABLE event_auditorium
(
  event_id BIGINT(20) NOT NULL,
  date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  auditorium_id BIGINT(20) NOT NULL,
  CONSTRAINT `PRIMARY` PRIMARY KEY (event_id, date),
  CONSTRAINT event_auditorium_event_fk FOREIGN KEY (event_id) REFERENCES event (id),
  CONSTRAINT event_auditorium_auditorium_fk FOREIGN KEY (auditorium_id) REFERENCES auditorium (id)
);
CREATE INDEX event_auditorium_auditorium_fk ON event_auditorium (auditorium_id);
CREATE TABLE event_date
(
  event_id BIGINT(20) NOT NULL,
  air_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT event_date_event_fk FOREIGN KEY (event_id) REFERENCES event (id)
);
CREATE INDEX event_date_event_fk ON event_date (event_id);
CREATE TABLE event_statistics
(
  accessed_by_name BIGINT(20) NOT NULL,
  prices_queried BIGINT(20) NOT NULL,
  booked_tickets BIGINT(20) NOT NULL,
  id BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT
);
CREATE TABLE ticket
(
  id BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_id BIGINT(20),
  event_id BIGINT(20),
  date_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  seat BIGINT(20),
  price DOUBLE,
  CONSTRAINT ticket_user_fk FOREIGN KEY (user_id) REFERENCES user (id),
  CONSTRAINT ticket_event_fk FOREIGN KEY (event_id) REFERENCES event (id)
);
CREATE INDEX ticket_event_fk ON ticket (event_id);
CREATE INDEX ticket_user_fk ON ticket (user_id);
CREATE TABLE user
(
  id BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  email VARCHAR(255),
  birth_day DATETIME DEFAULT CURRENT_TIMESTAMP
);