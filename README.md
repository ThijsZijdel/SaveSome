# SaveSome
[Side project] Java Application for getting a better overview of your financial situation, budget and possibly SaveSome.


![alt text](hero.png)

## Getting Started

To see the application there are some things that needs to be configured before running it.
So please follow to following document or wait for the first stable release. 

Note: Runnable jar, config file, extendings etc. will be added later.


### Prerequisites

Install a Java IDE to build and run the application

```
I used IntellJ IDEA to build it. See [Built with] for the link
```

Install and configure an SQL host.

```
I used SQL Workbench's local host to run the database
Note: this will probably changed to a embedded sql db or IO System.
```
 

### Installing

When you have an SQL Host and IDE installed u can follow these steps:

Step 1) Creating the database.

```
Run the CreateDB.sql file in your sql host.
```

Step 2) Configuring the connection.

```
Go to the MainApp.java (in /app/..) and edit the connection config.

Keep the first  parameter the same. 
Change the second parameter to your SQL userName.
Change the third  parameter to your SQL userPassword.
```


Step 3) Run the application.

```
And save some!
```



### More information

Project current in development. For the documentation please take a look at the comments in the code. I try to keep these as detailed as possible.




## Built With

* [MySQL](https://www.mysql.com/) - For the database of the application [will be changed]
* [Java](https://www.java.com/) - The language that I build the application in.
* [IntelliJ Idea](https://www.jetbrains.com/idea/) - In what I developed the application.
* [Scenebuilder](http://gluonhq.com/products/scene-builder/) - For building the application scene's.
* [Maven](https://maven.apache.org/) - Dependency Management

### Dependencies/ Libraries 
* [JFoenix](http://www.jfoenix.com/) - For the material design in the application
* [SQL Connector](http://www.jfoenix.com/) - For the connection to the database (MYJbdc)


## Built By

* **Thijs Zijdel** 



## License

This project is licensed under the MIT License - look at [LICENSE.md](LICENSE.md) file for details.