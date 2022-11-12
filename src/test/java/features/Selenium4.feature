Feature: As a learner, I want to test Selenium 4 New Features

  Scenario: Capture screenshot of specific web element:
    Given User Navigates To URL: "https://bonigarcia.dev/selenium-webdriver-java/"
    And User clicks on 'Slow calculator'
    And User takes screenshot of 'Calculator' element
    Then User quits The Browser

  Scenario: Open the new window on the browser
    Given User Navigates To URL: "https://bonigarcia.dev/selenium-webdriver-java/"
    And User opens "https://google.co.in/" in a new window
    And User verify total tabs opened are: 2
    Then User quits The Browser

  Scenario: Open a new tab on the browser
    Given User Navigates To URL: "https://bonigarcia.dev/selenium-webdriver-java/"
    And User opens "https://www.crmpro.com/" in a new tab
    And User verify total tabs opened are: 2
    Then User quits The Browser

  Scenario: Object location
    Given User Navigates To URL: "https://bonigarcia.dev/selenium-webdriver-java/"
    And User print the location of the object
    Then User quits The Browser

  Scenario: Login With Relative Locators in Selenium4
    Given User Navigates To URL: "https://accounts.lambdatest.com/login"
    And Login using relative locators
    Then User quits The Browser

  @debug
  Scenario:Chrome DevTools Functionalities
    Given User Set Up DevTools Geolocation
    When User Navigates To URL: "https://my-location.org/"
    Then User quits The Browser
    ## ----- Work On @ParameterTye
    ## ----- Work On @Playwright
    ## ----- Work On @Spring
    ## ----- Work On @Grid
    ## ----- Work On @JsonPath

