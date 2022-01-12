from connector import cnx

table_cursor = cnx.cursor()

table_cursor.execute("drop view if exists car_view;")
table_cursor.execute("drop table if exists car_quantity;")
table_cursor.execute("drop table if exists leasing_info;")
table_cursor.execute("drop table if exists customer;")
table_cursor.execute("drop table if exists car;")

table_cursor.execute("DROP TABLE IF EXISTS `car`;")
table_cursor.execute("CREATE TABLE `Car` ("
                     "`id` int(11) NOT NULL AUTO_INCREMENT,"
                     "`model` varchar(45) NOT NULL,"
                     "`manufacture` varchar(45) NOT NULL,"
                     "`product_year` int(11) NOT NULL,"
                     "`category` varchar(45) NOT NULL,"
                     "PRIMARY KEY (`id`)"
                     ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;"
                     )

insert_sql = "INSERT INTO car (id, model, manufacture, product_year, category)" \
             "VALUES (%s, %s, %s, %s, %s);"
values = [
    (1, 'Audi', 'Audi Company', 2000, 'Sedan'),
    (2, 'Chevrolet Corvette', 'Chevrolet Company', 2000, 'SUV'),
    (3, 'BMW M8', 'BMW', 2002, 'Sedan'),
    (4, 'Benz xc300', 'Benz Company', 2002, 'Coupe'),
    (5, 'Kia i8', 'Kia Motors', 2000, 'Minivan'),
    (6, 'Toyota caprice', 'Toyota Motors', 2000, 'Sports'),
    (7, 'May bach gls600', 'May bach Company', 2000, 'SUV')
]

table_cursor.executemany(insert_sql, values)


table_cursor.execute("DROP TABLE IF EXISTS `Customer`;")
table_cursor.execute("CREATE TABLE `Customer` ("
                     "`c_id` int(11) NOT NULL AUTO_INCREMENT,"
                     "`first_name` varchar(45) NOT NULL,"
                     "`surname` varchar(45) NOT NULL,"
                     "`gender` varchar(45) NOT NULL,"
                     "`address` varchar(45) NOT NULL,"
                     "`social_security_number` varchar(45) NOT NULL,"
                     "PRIMARY KEY (`c_id`),"
                     "UNIQUE KEY `id_UNIQUE` (`c_id`)"
                     ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;"
                     )

insert_sql = "INSERT INTO Customer (c_id, first_name, surname, gender, address, social_security_number)" \
             "VALUES (%s, %s, %s, %s, %s, %s);"
values = [
    (1, 'Ali', 'Rod', 'male', 'Sweden', '9908129898'),
    (2, 'Elate', 'Soltana', 'female', 'Vaxjo', '8908122236'),
    (3, 'Jafar', 'Anahi', 'female', 'Wetland', '5312122267'),
    (4, 'Samad', 'Koch', 'Other', 'Stockholm', '6606127656'),
    (5, 'Raine', 'Rastafarian', 'male', 'Norway', '8302121269'),
    (6, 'Raouf', 'Sandiness', 'male', 'Poland', '7604120923'),
    (7, 'Gholam', 'Mohorovicic', 'female', 'Los Angels', '4603181212')
]

table_cursor.executemany(insert_sql, values)

table_cursor.execute("DROP TABLE IF EXISTS `Leasing_info`;")
table_cursor.execute("CREATE TABLE `Leasing_info` ("
                     "`car_id` int(11) NOT NULL,"
                     "`customer_id` int(11) NOT NULL,"
                     "`date` date NOT NULL,"
                     "`car_return_date` date NOT NULL,"
                     "KEY `customer_id` (`customer_id`),"
                     "KEY `car_id` (`car_id`),"
                     "CONSTRAINT `car_id` FOREIGN KEY (`car_id`) REFERENCES `Car` (`id`),"
                     "CONSTRAINT `customer_id` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`c_id`)"
                     ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;"
                     )

insert_sql = "INSERT INTO Leasing_info (car_id, customer_id, date, car_return_date)" \
             "VALUES (%s, %s, %s, %s);"
values = [
    (1, 2, '2010-01-10', '2010-02-10'),
    (2, 2, '2010-01-10', '2010-02-10'),
    (3, 2, '2010-01-10', '2010-02-10'),
    (4, 1, '2010-01-10', '2010-02-10'),
    (5, 1, '2010-01-10', '2010-02-10'),
    (6, 3, '2010-01-10', '2010-02-10'),
    (7, 3, '2010-01-10', '2010-02-10'),
    (1, 3, '2010-01-10', '2010-02-10')
]

table_cursor.executemany(insert_sql, values)

table_cursor.execute("DROP TABLE IF EXISTS `Car_Quantity`;")
table_cursor.execute("CREATE TABLE `Car_Quantity` ("
                     "`quantity_id` int(11) NOT NULL AUTO_INCREMENT,"
                     "`car_id` int(11) NOT NULL,"
                     "`quantity` int(11) NOT NULL,"
                     "PRIMARY KEY (`quantity_id`),"
                     "KEY `quantity_car_id` (`car_id`),"
                     "CONSTRAINT `quantity_car_id` FOREIGN KEY (`car_id`) REFERENCES `Car` (`id`) ON DELETE CASCADE "
                     "ON UPDATE CASCADE "
                     ") ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;"
                     )

insert_sql = "INSERT INTO Car_Quantity (quantity_id, car_id, quantity)" \
             "VALUES (%s, %s, %s);"
values = [
    (1, 1, 4),
    (2, 2, 4),
    (3, 3, 3),
    (4, 4, 3),
    (5, 5, 3),
    (6, 6, 1),
    (7, 7, 2)
]

table_cursor.executemany(insert_sql, values)

table_cursor.execute("DROP VIEW IF EXISTS `car_view`;")
table_cursor.execute("CREATE VIEW `car_view` AS "
                     "select `Car`.`id` AS `id`,`Car`.`model` AS `model`,`Car`.`manufacture` AS `manufacture`,"
                     "`Car`.`product_year` AS `product_year`,`Car`.`category` AS `category`,"
                     "`Car_Quantity`.`quantity_id` AS `quantity_id`,`Car_Quantity`.`car_id` AS `car_id`,"
                     "`Car_Quantity`.`quantity` AS `quantity` "
                     "from (`Car` join `Car_Quantity` on((`Car`.`id` = `Car_Quantity`.`car_id`)));"
                     )

cnx.commit()
table_cursor.close()
cnx.close()
