# Sainsbury Webscrapper

Java Developer Role - Sainsbury Java exercise - December 2018

## Description

Webscrapper that gets passed an URL via the application config file and extracts its HTML. From the HTML, the application
will extract all urls containing ```berries-cherries-currants```.

For each of these urls, information about kcals, price, description and title are extracted, when available.

There is a fallback CSS query for kcal, for nutritional tables with a different structure.

## Technologies

This is a Spring application, built with Java 8 and packaged using Maven.

External dependencies: 
* JSoup
* Jackson
* Spring
* Log4j
* Spring-test

## Runbook

* To build the application use the maven wrapper provided with the following command: ```./mvnw clean install```
* To run the tests use the following command: ```./mvnw test```
* To run the application in any IDE, run the ```WebScrapper``` class
* To run the application from command line, use the following command: ````./mvnw exec:java````

No command line parameters or environment variables set are required, since all configuration is driven from config files