import mysql.connector

cnx = mysql.connector.connect(
    host="127.0.0.1",
    user='root',
    password='root',
    database='lnu_car_company',
    unix_socket='/Applications/MAMP/tmp/mysql/mysql.sock',
)
