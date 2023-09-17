# Pazarama Project

This project is a QA automation framework built using TestNG framework with Selenium and Java. It provides a structured approach for automating browser tests, ensuring efficient test case management and easy to maintenance. Maven as the build automation tool for managing dependencies and executing tests. TestNG is for both crating project structure and validation of test results.
<br />

## Steps to Create Project

### 1. Create a maven project called

> PazaramaTaskProject

### 2. Under `pom.xml`

> 1. add below property section

```xml

<properties>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

> 2. Add dependencies

```xml

<dependencies>

  <dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>3.141.59</version>
  </dependency>

  <!-- https://mvnrepository.com/artifact/io.github.bonigarcia/webdrivermanager -->
  <dependency>
    <groupId>io.github.bonigarcia</groupId>
    <artifactId>webdrivermanager</artifactId>
    <version>5.5.2</version>
  </dependency>

    <!-- https://mvnrepository.com/artifact/org.testng/testng -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.7.1</version>
        <scope>test</scope>
    </dependency>

    <!-- https://mvnrepository.com/artifact/com.github.javafaker/javafaker -->
    <dependency>
        <groupId>com.github.javafaker</groupId>
        <artifactId>javafaker</artifactId>
        <version>1.0.2</version>
    </dependency>

    <!--If you want to get rid of SLF4J Failed to load message from the console -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>1.7.32</version>
    </dependency>

</dependencies>
 ```

> 3. Add Build Plugins

```xml
<build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0-M5</version>
                <configuration>
                    <suiteXmlFiles>
                        <suiteXmlFile>src\test\resources\testng.xml</suiteXmlFile>
                    </suiteXmlFiles>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

### 3. Create a package called `com.pazarama` under `src/test/java`

> 1. under `com.pazarama` package, create `pages`, `tests` and `utilities `packages

> 2. Add a `configuration.properties` file to store some credentials in order to avoid hard coding if we need and determine which type of browser we use. Add following information:

```properties
browser=chrome
homepageUrl=https://www.pazarama.com/
email=ismailtestmail.qa@gmail.com
password=testpassword12
firstname=ismail
lastname=test
```

### 4. Create `resources directory` under project level

> We store `testng.xml` file which contains the configuration settings and test suite information for our test automation framework. Placing it in the resources directory ensures that it is easily accessible and well-organized within our project structure.

### 5. Under `tests` package,

* Create separate java classes for corresponding feature of the application to write test cases source code.
* Create `BaseTest` class for determining common steps for each `@Test` like `Driver.getDriver().manage().window().maximize();`
  and `Driver.closeDriver();` for all scenarios by using ` @BeforeMethod` and `@AfterMethod` annotations which come from TestNG.

#### 6. Under `pages` package, create each page of application corresponding `Java Classes` to store each page related web elements to achieve _Page Object Model_

> 1. Create BasePage java abstract class and add a constructor with `PageFactory.initElements()` method to extend this class from other pages classes. We achieve reusability in this way.

```java
 public abstract class BasePage {
    public BasePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }
}
```

### 7. Under Utilities Package

##### Driver Util Class

* Implementation of _Singleton Design Pattern_.
* Switch between different _browser types_ easily
* `private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();` object allow us to
  create a new separate instance of driver for each test in parallel testing.
* I determined which type of driver which I use in `configuration.properties` file as `chrome`. Then Driver util class returns me ChromeDriver with following code:
```java
switch (browserType) {
   ccase "chrome":
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        //options.setBinary("C:\\Program Files\\SeleniumDrivers\\chrome-win64\\chrome-win64\\chrome.exe");
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.DRIVER, Level.ALL);
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability("goog:loggingPrefs", logPrefs);
        driverPool.set(new ChromeDriver(options));
        break;
```
<br/>

##### ConfigurationReader Util Class

* We use for getting some important test data from properties file. Like:
    - BrowserType
    - Username
    - Password
    - BaseURL

      <br/>
##### Browser Util Class

* There are a lot of useful and helpful methods to use throughout the automation project

### 8. Reporting
We determine which type of report after executing tests in `testng.xml` file as following statement.
```xml
<listeners>
      <listener class-name="org.testng.reporters.EmailableReporter" />
</listeners>
```

This project structure releases a **html report** under the target/surefire-reports folder with the name of `index.html` file. We can see all tests results in a single file in detail.
#### 9. Chrome Driver issue

After 116 version of Chrome, WebDriverManager doesn't support this one. In order to handle this problem, download
newest `chromedriver.exe` and put it in the project directory.
