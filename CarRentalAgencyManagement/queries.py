import print_helper as p_helper

CUSTOMER = 'customer'
REGISTER = 'register'
LEASING = 'leasing'
LEND = 'lend'
CAR_RETURNING = 'car_returning'


def high_rated_customer(cursor):
    query = """Select concat(first_name, ' ', surname) as Customer_Name, count(c_id) as Leasing_number
                            from `customer`
                            JOIN leasing_info on leasing_info.customer_id = customer.c_id
                            GROUP BY Customer_Name
                            HAVING Leasing_number > 0
                            ORDER BY Leasing_number DESC;
                            """
    cursor.execute(query)
    info = cursor.fetchall()
    p_helper.print_high_rated_customer()
    count = 0
    for customer in info:
        count = count + 1
        p_helper.customer_rate(count, customer)
        if count == 3:
            break


def most_leased_car(cursor):
    query = "Select model as car_model,manufacture as car_company, category as car_category, product_year as year ," \
            "count(id) as Leasing_number " \
            " from car " \
            " JOIN leasing_info on leasing_info.car_id = car.id " \
            " GROUP BY car_model,car_company, car_category,year " \
            "HAVING Leasing_number > 0" \
            " ORDER BY Leasing_number DESC "

    cursor.execute(query)
    info = cursor.fetchall()
    p_helper.print_high_rated_car()
    count = 0
    for car in info:
        count = count + 1
        p_helper.car_rate(car, count)
        if count == 3:
            break


def customers_by_return_date(cursor, date):
    query = """ 
                            Select first_name, surname
                            From `customer`
                            JOIN leasing_info on customer.c_id = leasing_info.customer_id
                            where car_return_date in (
                                select car_return_date
                                from leasing_info
                                where car_return_date = '%s'
                                )"""
    cursor.execute(query, (date,))
    info = cursor.fetchall()
    check_print_customers(info)


def check_print_customers(info):
    if not check_data('returning', info):
        p_helper.customers_on_return_date()
        for customer in info:
            p_helper.customers_name_on_return_date(customer)


def check_data(alternatives, product):
    if len(product) == 0:
        p_helper.display_invalid_alternatives(alternatives)
        return True
    else:
        return False


def customers_info_on_security_number(cursor, social_security_number):
    query = "SELECT c_id FROM `customer` WHERE social_security_number = '%s' "
    cursor.execute(query, (social_security_number,))
    info = cursor.fetchall()
    return info


def car_data(cursor, model, product_years):
    query = "SELECT id FROM `car` WHERE model = %s AND product_year = %s"
    cursor.execute(query, (model, product_years))
    info = cursor.fetchall()
    return info


def insert_record(cursor, alternatives, part):
    if alternatives == CUSTOMER:
        query = "INSERT IGNORE INTO `customer` " \
                "(first_name, surname, gender, address, social_security_number) VALUES " \
                "(%s, %s, %s, %s, %s) "
        info = (part[0], part[1], part[2], part[3], int(part[4]))
        cursor.execute(query, info)
        p_helper.add_customer_to_database()
    elif alternatives == REGISTER:
        query = 'INSERT IGNORE INTO car (model, manufacture, product_year, category) VALUES (%s, %s, %s, %s)'
        info = (part[0], part[1], part[2], part[3])
        cursor.execute(query, info)

        registered = cursor.lastrowid
        query = 'INSERT IGNORE INTO car_quantity (quantity, car_id) VALUES (%s, %s)'
        info = (part[4], registered)
        cursor.execute(query, info)
        p_helper.add_quantity_to_database()
    else:
        query = 'INSERT IGNORE INTO `leasing_info` (date, car_return_date, car_id, customer_id) VALUES (%s, ' \
                '%s, %s, %s) '
        cursor.execute(query,
                       (part[0], part[1], part[2], part[3]))
        p_helper.add_leasing_info_to_database()


def drop_database_information(cursor, alternatives, primary_number):
    if alternatives == CUSTOMER:
        query = "delete from `customer` where c_id = '%s' "
        cursor.execute(query, (primary_number,))
        p_helper.drop_customer_from_database()
    elif alternatives == REGISTER:
        query = "delete from `car` WHERE id = %s"
        cursor.execute(query, (primary_number,))
        p_helper.drop_car_from_database()
    else:
        query = "delete from `leasing_info` WHERE car_id = %s AND customer_id = %s"
        cursor.execute(query,
                       (primary_number[0], primary_number[1]))
        p_helper.drop_leasing_info_from_database()


def update_database_information(cursor, alternatives, primary_number):
    if alternatives == CUSTOMER:
        customer = p_helper.register_customer()
        query = "UPDATE `customer` SET first_name = %s, surname = %s, gender = %s, address = %s, " \
                "social_security_number = %s where customer.c_id = %s; "
        info = (customer[0], customer[1], customer[2], customer[3], int(customer[4]), primary_number)
        cursor.execute(query, info)
        p_helper.update_customer_in_database()
    elif alternatives == REGISTER:
        car = p_helper.register_car()
        query = "UPDATE car SET model = %s, manufacture = %s , product_year = %s, category = %s where car.id = %s;"
        info = (car[0], car[1], car[2], car[3], primary_number)
        cursor.execute(query, info)
        p_helper.update_car_in_database()
    elif alternatives == LEND:
        query = "UPDATE car_quantity SET quantity = quantity - 1 where car_quantity.car_id = %s;"
        cursor.execute(query, (primary_number,))
    elif alternatives == CAR_RETURNING:
        query = "UPDATE car_quantity SET quantity = quantity + 1 where car_quantity.car_id = %s;"
        cursor.execute(query, (primary_number,))


def car_leased_on_member(cursor, primary_number):
    query = """
                        select *
                        From car
                        where id in(
                            SELECT car_id
                            From leasing_info
                            JOIN `customer` ON leasing_info.customer_id = customer.c_id
                            WHERE social_security_number = '%s'
                        )"""
    cursor.execute(query, (primary_number,))
    info = cursor.fetchall()
    p_helper.print_cars_per_customer()
    count = 0
    iterate_records(count, info)


def iterate_records(count, info):
    for car in info:
        count = count + 1
        p_helper.print_car_models(car, count)


def quantity_management(cursor, alternatives, car_id):
    query = "SELECT quantity from car_view where car_id = %s"
    cursor.execute(query, (car_id,))
    info = cursor.fetchone()
    quantity = int(info[0])
    if quantity == 0:
        p_helper.car_out_of_quantity()
        return False
    else:
        update_database_information(cursor, alternatives, car_id)
        return True


def view_database_data(cursor):
    cursor.execute("SET NAMES utf8mb4")
    cursor.execute("SET CHARACTER SET utf8mb4")
    cursor.execute("SET character_set_connection=utf8mb4")
    cursor.execute("DROP VIEW IF EXISTS car_view")
    mysql_view = """CREATE VIEW car_view As
                    select *
                    from car
                    join car_quantity on car.id = car_quantity.car_id;"""
    cursor.execute(mysql_view)
