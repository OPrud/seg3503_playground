package selenium;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

class CustomerTest {

    static Process server;
    private WebDriver driver;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "target/BookstoreApp-0.1.0.jar");
        server = pb.start();
        Thread.sleep(5000);
    }

    @BeforeEach
    void setUp() {
        // Pick your browser
        // driver = new FirefoxDriver();
        // driver = new SafariDriver();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://localhost:8080/");
        // wait to make sure Selenium is done loading the page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title")));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterAll
        public static void tearDownAfterClass() throws Exception {
        server.destroy();
    }

    @Test
    void testAddBookToCart() {
        driver.get("http://localhost:8080/");

        driver.findElement(By.id("searchBtn")).click();
        
        WebElement addButton = driver.findElement(By.id("order-rowling001"));
        assertTrue(addButton.isDisplayed());
        addButton.click();

        driver.findElement(By.id("cartLink")).click();

        WebElement quantity = driver.findElement(By.id("rowling001"));
        assertEquals("1", quantity.getAttribute("value"));
    }

    @Test
    void testAddSameBookTwice() {
        driver.get("http://localhost:8080/");

        driver.findElement(By.id("searchBtn")).click();
        
        driver.findElement(By.id("order-rowling001")).click();
        
        WebElement addButtonAgain = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.id("order-rowling001")));
        addButtonAgain.click();
        
        driver.findElement(By.id("cartLink")).click();

        WebElement quantity = driver.findElement(By.id("rowling001"));
        assertEquals("2", quantity.getAttribute("value"));
    }

    @Test
    void testViewCart() {
        driver.get("http://localhost:8080/");
        driver.findElement(By.id("searchBtn")).click();
        driver.findElement(By.id("cartLink")).click();

        // Vérifie le panier vide
        assertTrue(driver.findElements(By.name("numItems")).isEmpty());

        // Vérifie un panier avec un élément

        driver.findElement(By.id("searchBtn")).click();
        
        driver.findElement(By.id("order-rowling001")).click();

        driver.findElement(By.id("cartLink")).click();

        assertEquals("rowling001", driver.findElement(By.xpath("//td[text()='rowling001']")).getText());
        assertEquals("The Harry Potter Series", driver.findElement(By.xpath("//td[text()='The Harry Potter Series']")).getText());
        assertEquals("$59.95", driver.findElement(By.id("totrowling001")).getText());
        assertEquals("1", driver.findElement(By.id("rowling001")).getAttribute("value"));
        assertTrue(driver.findElement(By.className("updatebt")).isDisplayed());
        assertTrue(driver.findElement(By.name("checkout")).isDisplayed());
    }

    @Test
    void testUpdateBookQuantity() {
        driver.get("http://localhost:8080/");

        driver.findElement(By.id("searchBtn")).click();
        
        WebElement addButton = driver.findElement(By.id("order-rowling001"));
        assertTrue(addButton.isDisplayed());
        addButton.click();

        driver.findElement(By.id("cartLink")).click();

        WebElement quantity = driver.findElement(By.id("rowling001"));
        quantity.clear();
        quantity.sendKeys("3");

        driver.findElement(By.name("updateOrder")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeToBe(By.id("rowling001"), "value", "3"));

        assertEquals("3", driver.findElement(By.id("rowling001")).getAttribute("value"));
        assertEquals("$179.85", driver.findElement(By.id("totrowling001")).getText());
    }

    @Test
    void testRemoveBookWhenQuantityZero() {
        driver.get("http://localhost:8080/");

        driver.findElement(By.id("searchBtn")).click();
        
        WebElement addButton = driver.findElement(By.id("order-rowling001"));
        assertTrue(addButton.isDisplayed());
        addButton.click();

        driver.findElement(By.id("cartLink")).click();

        WebElement quantity = driver.findElement(By.id("rowling001"));
        quantity.clear();
        quantity.sendKeys("0");
        driver.findElement(By.name("updateOrder")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeToBe(By.id("rowling001"),"value","0"));
        driver.findElement(By.id("searchBtn")).click();
        driver.findElement(By.id("cartLink")).click();
        assertTrue(driver.findElements(By.name("numItems")).isEmpty());
    }

    @Test
    void testCheckout() {
        driver.get("http://localhost:8080/");

        driver.findElement(By.id("searchBtn")).click();

        driver.findElement(By.id("order-rowling001")).click();
        driver.findElement(By.id("order-hall001")).click();

        driver.findElement(By.id("cartLink")).click();
        driver.findElement(By.name("checkout")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("order_total")));

        assertTrue(driver.findElement(By.id("order_date")).isDisplayed());
        assertEquals("$127.88", driver.findElement(By.id("order_total")).getText());
        assertEquals("$12.99", driver.findElement(By.id("order_taxes")).getText());
        assertEquals("$15.00", driver.findElement(By.id("order_shipping")).getText());
    }
}
