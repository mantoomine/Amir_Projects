import pyinputplus as pyip


def auto_company_menu():
    print('Welcome to the lnu auto company')
    print('Menu')
    print('1: Manage customer')
    print('2: Manage cars')
    print('3: Manage leasing')
    print('4: The most popular car')
    print('5: The most high rated customer')
    print('6: exit')
    return pyip.inputInt('Please enter one of the above options: ')


def car_menu():
    print('1: Add a car')
    print('2: Remove a car')
    print('3: Change the car information')
    print('4: back to the previous menu')
    return pyip.inputInt('Please enter one of the above options: ')


def customer_menu():
    print('1: Add a customer')
    print('2: Remove a customer membership')
    print('3: Change the customers information')
    print('4: Get the information of the car based on the specific customer')
    print('5: Back to the previous menu')
    return pyip.inputInt('Please enter one of the above options: ')


def leasing_menu():
    print('1: Lease a car')
    print('2: Return the leased car')
    print('3: Get the customers information based on the return date')
    print('4: Back to the previous menu')
    return pyip.inputInt('Please enter one of the above options: ')


def register_car():
    print('to register a new car , fill out the following information ')
    car_model = input('Car model: ')
    manufacture = input('Car manufacture: ')
    product_year = pyip.inputInt('Car production year: ')
    c_category = car_category()
    quantity = pyip.inputInt('Quantity: ')
    car = (car_model, manufacture, product_year, c_category, int(quantity))
    return car


def car_category():
    print('Choose on of the below car categories that you are interested in:')
    print('1. Sedan \n2. Coupe \n3. Sports \n4. Station wagon \n5. Hatchback \n6. Convertible \n7. SUV \n8. Minivan '
          '\n9. Pickup truck \n10. Other')
    categories = pyip.inputInt('Enter : ')
    if categories == 1:
        return 'Sedan'
    elif categories == 2:
        return 'Coupe'
    elif categories == 3:
        return 'Sports'
    elif categories == 4:
        return 'Station wagon'
    elif categories == 5:
        return 'Hatchback'
    elif categories == 6:
        return 'Convertible'
    elif categories == 7:
        return 'SUV'
    elif categories == 8:
        return 'Minivan'
    elif categories == 9:
        return 'Pickup truck'
    else:
        return 'Other'


def register_customer():
    print('to register a new customer , fill out the following information ')
    first_name = input('First model: ')
    surname = input('Surname: ')
    gender_specification = gender_menu()
    customer_address = input('Customer address: ')
    social_security_number = pyip.inputNum('Social security number (YYMMDDXXXX): ')
    customer = (first_name, surname, gender_specification, customer_address, social_security_number)
    return customer


def gender_menu():
    categories = pyip.inputInt('1: Male \n2: Female \n3: Other \nEnter: ')
    if categories == 1:
        return 'Male'
    elif categories == 2:
        return 'Female'
    else:
        return 'Other'


def remove_customer():
    social_security_number = pyip.inputInt('Social security number (YYMMDDXXXX): ')
    return social_security_number


def remove_car():
    car_model = input('Car model: ')
    product_year = pyip.inputInt('Car production year: ')
    return car_model, product_year


def invalid_input():
    print('Invalid input')


def return_leased_car():
    car_return_date = input('Enter the return date (YYYY-MM-DD): ')
    year, month, day = car_return_date.split('-')
    return_date = (year, month, day)
    return return_date


def display_invalid_alternatives(alternatives):
    if alternatives == 'customer':
        print('Customer has not been found')
    elif alternatives == 'register':
        print('Registration has not been found')
    elif alternatives == 'leasing':
        print('No information about the entered leasing has been found')
    elif alternatives == 'return':
        print('No information about the entered return date has been found')
    elif alternatives == 'social_security_number':
        print('Wrong social security number format (the correct format = YYMMDDXXXX)')


def exit_program():
    print('Exit the Program, See You!')


def social_number():
    print('social_security_number')


def entering_leasing_info():
    print('Fill out the following information to do a new leasing')


def entering_returning_car_info():
    print('Fill out the following information to return the leased car')


def terminating_system():
    print("Program terminated manually")


def database_connection_error():
    print('Database connection failed')


def print_high_rated_customer():
    print('The most high rated customers (customers with the most leased cars) are:')


def customer_rate(count, customer):
    print('Number %d is Name: %s with the total leased of: %s' % (count, customer[0], customer[1]))


def print_high_rated_car():
    print('The most popular cars (cars which has the highest number of leased) are:')


def car_rate(car, count):
    print('Number %d is The car model: %s with the total number of leased: %s\n\tManufacture: %s Category: %s product '
          'year: %s' % (
              count, car[0], car[4], car[1], car[2], car[3]))


def customers_name_on_return_date(customer):
    print('Customer Name is: %s %s' % (customer[0], customer[1]))


def customers_on_return_date():
    print('The following customers has reached to the return date of their leasing time')


def add_customer_to_database():
    print('A new customer added to the database')


def add_quantity_to_database():
    print('The car quantity data added to the database')


def add_leasing_info_to_database():
    print('The leasing info added to the database')


def drop_customer_from_database():
    print('The entered customer removed from the database')


def drop_car_from_database():
    print('The entered car quantity removed from the database')


def drop_leasing_info_from_database():
    print('The entered leasing information removed from the database')


def update_customer_in_database():
    print('The entered customer information has been updated')


def update_car_in_database():
    print('The entered car information has been updated')


def print_cars_per_customer():
    print('The following cars have been leased by the following customer')


def print_car_models(car, count):
    print('%d car model: %s \n\tManufacture: %s Product Year: %s' % (count, car[1], car[2], car[3]))


def car_out_of_quantity():
    print('The info is not available due to depletion of inventory')


def print_exception(exception):
    print('The exception is: ', exception)
