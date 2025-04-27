package com.enigma.restaurant.constant;

public class ApiEndpoint {

    //MENU
    public static final String MENU = "/api/menus";
    public static final String MENU_ID = "/search/{id}";
    public static final String MENU_UPDATE = "/update/{id}";
    public static final String MENU_DELETE = "/delete/{id}";



    //CUSTOMER
    public static final String CUSTOMER = "api/customer";
    public static final String CUSTOMER_UPDATE = "/update/{id}";
    public static final String CUSTOMER_DELETE = "/delete/{id}";
    public static final String CUSTOMER_SEARCH = "/search/{id}";


    //TRANSACTION
    public static final String TRANSACTION = "/api/transactions";
    public static final String TRANSACTION_SEARCH = "/search/{id}";
    public static final String TRANSACTION_DELETE = "/delete/{id}";




}
