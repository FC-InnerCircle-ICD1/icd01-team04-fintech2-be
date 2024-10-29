CREATE DATABASE IF NOT EXISTS incer_celler;
CREATE DATABASE IF NOT EXISTS incer_payment;
CREATE DATABASE IF NOT EXISTS incer_member;

GRANT ALL PRIVILEGES ON incer_celler.* TO 'admin'@'%';
GRANT ALL PRIVILEGES ON incer_payment.* TO 'admin'@'%';
GRANT ALL PRIVILEGES ON incer_member.* TO 'admin'@'%';

FLUSH PRIVILEGES;
