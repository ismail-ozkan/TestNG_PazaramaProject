package com.pazarama.tests;

import com.pazarama.utilities.Driver;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BaseTest {


    @BeforeMethod
    public void setup() {
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown() {
        Driver.closeDriver();
    }

}
