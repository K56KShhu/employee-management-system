# manage-system
![functions](https://github.com/zkyyo/manage-system/blob/master/image/functions.png?raw=true)

![employee_find](https://github.com/zkyyo/manage-system/blob/master/image/employee_find.png?raw=true)

manage-system is a simple java web application developed by zkyyo. you used it to manage the the employees, departments 
and evaluations. this project just use some basic grammar in java 7, so you can use it for a simple pratice.


## Features
- login and authenticate to access the pages
- add, delete, query and update the employee, department and evaluation
- simple fuzzy search
- validate input by regex
- log operations and store in txt file

## Environment
- **java:** 7
- **Tomcat:** apache-tomcat-8.5.12
- **MySQL:** 5.7.17
- **JDBC:** mysql-connector-java-5.1.41

## Install
Clone from github.com:
```
git clone git@github.com:zkyyo/manage-system.git
```

## Configurations
- **SQL_EXAMPLE:** test.268.sql is placeed at the test directory
```
mysql -u your_account -p < test268.sql path
```
- **DATEBASE:** com.zkyyo.www.db.DbConn
```
private static String USER = "root";
private static String PASS = "qaws";
```
- **LOG_HOME** com.zkyyo.www.util.LogUtil
```
private static final String PATH = "/home/xu/java_new_place/log.txt";
```
- **LOGIN_ACCOUNT:** account `101` password `abc`

## License
This software is licensed under MIT license. © 2017 zkyyo


