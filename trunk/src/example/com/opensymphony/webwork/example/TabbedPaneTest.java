package com.opensymphony.webwork.example;

import webwork.action.ActionSupport;

import java.util.Vector;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TabbedPaneTest extends ActionSupport
{
    static Set getData()
    {
        Map tmpMap = new HashMap();

        tmpMap.put( "Counter", "/SimpleCounter.action" );
        tmpMap.put( "UI Tags", "/TagTest.action" );
        tmpMap.put( "Country List", "/CountryTest.action" );

        return tmpMap.entrySet();
    }

    public Vector getTabs1() { return tabs1; }

    // Attributes --------------------------------------------------------
    private Vector tabs1 = new Vector( TabbedPaneTest.getData() );
}