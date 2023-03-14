# SalesInventoryMS

A full stack web application that allows authorized users to manage and track inventory, sales, and customers.

Technologies: Spring Boot, Spring JPA, Spring Security, Maven, Java, MySQL, Thymeleaf, HTML, CSS, BootStrap, JavaScript, JUnit

Using spring security, users of applications must be logged in as an authorized user to access the application. Registration is provided to become an authorized user. 

Features:
1. Dashboard 
- users can track total revenue, total profit, total sales, low inventory items, top selling items
- graph displaying total sales/profit/revenue per month
2. Inventory
- users can perform CRUD operations for items in inventory
- updating item info automatically updates item info associated with sales 
3. Sales 
- users can perform CRUD operations for sales
- quantity of items sold in a sale will automatically update inventory counts, revenue and profit
4. Customers
- users can perform CRUD operations for customers
- updating customer info automatically updates customer info associated with sales


