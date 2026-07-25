package selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

class CatalogueTest {

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
    void testBrowseCatalogByNonExistingCategory() {
        driver.get("http://localhost:8080/");

        driver.findElement(By.id("search")).sendKeys("xyz");
        driver.findElement(By.id("searchBtn")).click();

        WebElement resultsTitle = driver.findElement(By.tagName("h1"));
        assertEquals("Désolé, nous n'avons pas d'article correspondant à la catégorie 'xyz' à ce moment", resultsTitle.getText());
    }

    @Test
    void testBrowseCatalogByNoCategorySpecified() {
        driver.get("http://localhost:8080/");

        driver.findElement(By.id("searchBtn")).click();

        List<WebElement> books = driver.findElements(By.cssSelector("[id^='title-']"));
        assertEquals(5, books.size());
    }
    
    @Test
    void testBrowseCatalogByNonEmptyCategory() {
        driver.get("http://localhost:8080/admin");

        driver.findElement(By.id("loginId")).sendKeys("admin");
        driver.findElement(By.id("loginPasswd")).sendKeys("password");
        driver.findElement(By.id("loginBtn")).click();

        driver.get("http://localhost:8080/admin");

        driver.findElement(By.id("addBook-category")).sendKeys("fiction");
        driver.findElement(By.id("addBook-id")).sendKeys("dune003");
        driver.findElement(By.id("addBook-title")).sendKeys("Dune");
        driver.findElement(By.id("addBook-authors")).sendKeys("Frank Herbert");
        driver.findElement(By.id("longDescription")).sendKeys("Livre sur la planète Dune, les vers des sables");
        driver.findElement(By.id("cost")).sendKeys("29.99");
        driver.findElement(By.name("addBook")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("feedback"),"Livre ajouté avec succès"));

        driver.findElement(By.id("search")).sendKeys("fiction");
        driver.findElement(By.id("searchBtn")).click();

        WebElement resultsTitle = driver.findElement(By.tagName("h1"));
        assertEquals("Nous avons actuellement les articles suivants dans la catégorie 'fiction'", resultsTitle.getText());

        WebElement bookTitle = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.id("title-dune003")));

        assertEquals("Dune", bookTitle.getText());

        List<WebElement> books = driver.findElements(By.cssSelector("[id^='title-']"));
        assertEquals(1, books.size());

        driver.findElement(By.id("del-dune003")).click();

        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait2.until(ExpectedConditions.stalenessOf(bookTitle));
    }
}
