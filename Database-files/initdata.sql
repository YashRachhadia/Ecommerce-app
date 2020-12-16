/*
 * Initial data in the Authorities and Users table for Admin:
 * ---------------------------------------------------------
 * 
 * Username: admin, Password: admin
 * 
 * (Password will be inserted in the encrypted format into the database. The password is encrypted using BCryptPasswordEncoder).
 * 
 */

-- Authorities table:
INSERT INTO `springboot-ecommerce`.`authorities` (`authorities_id`, `authority`, `username`) VALUES ('1', 'ROLE_ADMIN', 'admin');

-- Users table:
INSERT INTO `springboot-ecommerce`.`users` (`users_id`, `customer_id`, `enabled`, `password`, `username`) VALUES ('1', '0', b'1', '$2a$10$sTgPUNcpmNzPpNoyMoyCNOu2TISiY.kxxs7TtxyVxvQBTpXgjcqBq', 'admin');
