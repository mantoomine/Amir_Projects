import sys
import datetime
import time
import print_helper as p_helper
import queries as queries
from connector import cnx


def registration_setting(cursor):
    alternative = p_helper.car_menu()
    if alternative == 1:
        car = p_helper.register_car()
        queries.insert_record(cursor, REGISTER, car)
    elif alternative == 4:
        pass
    elif 2 <= alternative <= 3:
        car = p_helper.remove_car()
        info = queries.car_data(cursor, car[0], car[1])
        if not queries.check_data(REGISTER, info):
            alternatives_setting(alternative, cursor, info)
    else:
        p_helper.invalid_input()


def alternatives_setting(alternative, cursor, info):
    if alternative == 2:
        car_id = info[0]
        queries.drop_database_information(cursor, REGISTER, car_id[0])
    elif alternative == 3:
        car_id = info[0]
        queries.update_database_information(cursor, REGISTER, car_id[0])


def check_data(alternative, checking_num):
    if alternative == CUSTOMER:
        if len(checking_num) == 10:
            m = int(checking_num[2:4])
            d = int(checking_num[4:6])
            if 0 < m < 13:
                if 0 < d < 32:
                    return True
        else:
            p_helper.display_invalid_alternatives(p_helper.social_number())
    elif alternative == LEASING:
        try:
            datetime.date(int(checking_num[0]), int(checking_num[1]), int(checking_num[2]))
            return True
        except ValueError as exception:
            p_helper.print_exception(exception)
            return False


def customer_setting(cursor):
    alternative = p_helper.customer_menu()
    if alternative == 1:
        customer = p_helper.register_customer()
        social_security_number = customer[4]
        if check_data(CUSTOMER, social_security_number):
            queries.insert_record(cursor, CUSTOMER, customer)
    elif alternative == 5:
        pass
    elif 2 <= alternative <= 4:
        social_security_number = p_helper.remove_customer()
        info = queries.customers_info_on_security_number(cursor, social_security_number)
        if not queries.check_data(CUSTOMER, info):
            customer_id = info[0]
            if alternative == 2:
                queries.drop_database_information(cursor, CUSTOMER, customer_id[0])
            elif alternative == 3:
                queries.update_database_information(cursor, CUSTOMER, customer_id[0])
            elif alternative == 4:
                query = "SELECT * FROM `leasing_info` WHERE customer_id = %s"
                cursor.execute(query, (customer_id[0],))
                info = cursor.fetchall()
                if not queries.check_data(LEASING, info):
                    queries.car_leased_on_member(cursor, social_security_number)
    else:
        p_helper.invalid_input()


def leasing_setting(cursor):
    alternative = p_helper.leasing_menu()
    if alternative == 1:
        p_helper.entering_leasing_info()
        social_security_number = p_helper.remove_customer()
        customers = queries.customers_info_on_security_number(cursor, social_security_number)
        if not queries.check_data(CUSTOMER, customers):
            customer_id = customers[0]
            car_number = p_helper.remove_car()
            cars = queries.car_data(cursor, car_number[0], car_number[1])
            if not queries.check_data(REGISTER, cars):
                car_id = cars[0]
                check_leasing_info(car_id, cursor, customer_id)
        else:
            pass
    elif alternative == 2:
        p_helper.entering_returning_car_info()
        social_security_number = p_helper.remove_customer()
        customers = queries.customers_info_on_security_number(cursor, social_security_number)
        if not queries.check_data(CUSTOMER, customers):
            customer_id = customers[0]
            car_number = p_helper.remove_car()
            cars = queries.car_data(cursor, car_number[0], car_number[1])
            if not queries.check_data(REGISTER, cars):
                car_id = cars[0]
                query = "SELECT * FROM `leasing_info` WHERE car_id = %s AND customer_id = %s"
                cursor.execute(query,
                               (car_id[0], customer_id[0]))
                info = cursor.fetchall()
                if not queries.check_data(LEASING, info):
                    queries.quantity_management(cursor, CAR_RETURNING, car_id[0])
                    queries.drop_database_information(
                        cursor, LEASING, (car_id[0], customer_id[0]))
    elif alternative == 3:
        returning = p_helper.return_leased_car()
        if check_data(LEASING, returning):
            date = datetime.date(int(returning[0]), int(returning[1]), int(returning[2]))
            queries.customers_by_return_date(cursor, date)
    elif alternative == 4:
        pass
    else:
        p_helper.invalid_input()


def check_leasing_info(car_id, cursor, customer_id):
    if queries.quantity_management(cursor, LEND, car_id[0]):
        real_time = time.time()
        one_month = 2628000
        returning_date = real_time + one_month
        returning_date = datetime.date.fromtimestamp(int(returning_date))
        real_time = datetime.date.fromtimestamp(int(real_time))
        info = (real_time, returning_date, car_id[0], customer_id[0])
        queries.insert_record(cursor, LEASING, info)


class Start:
    def __init__(self, db_name):
        cursor = db_name.cursor()
        queries.view_database_data(cursor)
        try:
            while True:
                alternative = p_helper.auto_company_menu()
                if alternative == 1:
                    customer_setting(cursor)
                elif alternative == 2:
                    registration_setting(cursor)
                elif alternative == 3:
                    leasing_setting(cursor)
                elif alternative == 4:
                    queries.most_leased_car(cursor)
                elif alternative == 5:
                    queries.high_rated_customer(cursor)
                elif alternative == 6:
                    p_helper.exit_program()
                    sys.exit()
                else:
                    p_helper.invalid_input()
                db_name.commit()
        except KeyboardInterrupt:
            sys.exit(p_helper.terminating_system())


try:
    pass
except Exception as e:
    print(e)
    sys.exit(p_helper.database_connection_error())

CUSTOMER = 'customer'
REGISTER = 'register'
LEASING = 'leasing'
LEND = 'lend'
CAR_RETURNING = 'car_returning'

start = Start(cnx)
cnx.close()
