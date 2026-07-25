package selenium;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

class AdministratorTest {

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
    void testValidAdminLogin() {
        driver.get("http://localhost:8080/admin");

        driver.findElement(By.id("loginId")).sendKeys("admin");
        driver.findElement(By.id("loginPasswd")).sendKeys("password");
        driver.findElement(By.id("loginBtn")).click();

        driver.get("http://localhost:8080/admin");

        WebElement addForm = driver.findElement(By.id("addBook-category"));

        assertNotNull(addForm);
    }

    @Test
    void testInvalidAdminLogin() {
        driver.get("http://localhost:8080/admin");

        driver.findElement(By.id("loginId")).sendKeys("admin");
        driver.findElement(By.id("loginPasswd")).sendKeys("wrongPassword");
        driver.findElement(By.id("loginBtn")).click();

        WebElement error = driver.findElement(By.xpath("//div[contains(text(),'Nom d')]"));
        assertTrue(error.isDisplayed());
        assertEquals("Nom d'utilisateur et/ou mot de passe incorrect", error.getText());
    }

    @Test
    void testAdminSignOut() {
        driver.get("http://localhost:8080/admin");

        driver.findElement(By.id("loginId")).sendKeys("admin");
        driver.findElement(By.id("loginPasswd")).sendKeys("password");
        driver.findElement(By.id("loginBtn")).click();

        driver.get("http://localhost:8080/admin");

        driver.findElement(By.cssSelector("input[value='Déconnexion']")).click();

        WebElement confirmDeconnexion = driver.findElement(By.xpath("//div[contains(text(),'Vous avez')]"));
        assertTrue(confirmDeconnexion.isDisplayed());
        assertEquals("Vous avez été déconnecté", confirmDeconnexion.getText());
    }

    @Test
    void testAddBookValid() {
        driver.get("http://localhost:8080/admin");

        driver.findElement(By.id("loginId")).sendKeys("admin");
        driver.findElement(By.id("loginPasswd")).sendKeys("password");
        driver.findElement(By.id("loginBtn")).click();

        driver.get("http://localhost:8080/admin");

        driver.findElement(By.id("addBook-category")).sendKeys("fiction");
        driver.findElement(By.id("addBook-id")).sendKeys("dune001");
        driver.findElement(By.id("addBook-title")).sendKeys("Dune");
        driver.findElement(By.id("addBook-authors")).sendKeys("Frank Herbert");
        driver.findElement(By.id("longDescription")).sendKeys("Livre sur la planète Dune, les vers des sables");
        driver.findElement(By.id("cost")).sendKeys("29.99");
        driver.findElement(By.name("addBook")).click();

        WebElement feedback = driver.findElement(By.id("feedback"));
        WebElement feedbackTitle = feedback.findElement(By.tagName("h2"));
        assertEquals("Livre ajouté avec succès", feedbackTitle.getText());

        driver.get("http://localhost:8080/admin/catalog");

        driver.findElement(By.id("search")).sendKeys("fiction");
        driver.findElement(By.id("searchBtn")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement bookTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title-dune001")));

        assertEquals("Dune", bookTitle.getText());

        driver.findElement(By.id("del-dune001")).click();

        wait.until(ExpectedConditions.stalenessOf(bookTitle));
    }
    
    @Test
    void testAddBookInvalidId() {
        driver.get("http://localhost:8080/admin");

        driver.findElement(By.id("loginId")).sendKeys("admin");
        driver.findElement(By.id("loginPasswd")).sendKeys("password");
        driver.findElement(By.id("loginBtn")).click();

        driver.get("http://localhost:8080/admin");

        driver.findElement(By.id("addBook-category")).sendKeys("fiction");
        driver.findElement(By.id("addBook-id")).sendKeys("abc");
        driver.findElement(By.id("addBook-title")).sendKeys("Invalid Book");
        driver.findElement(By.id("addBook-authors")).sendKeys("Author");
        driver.findElement(By.id("longDescription")).sendKeys("Description");
        driver.findElement(By.id("cost")).sendKeys("10");
        driver.findElement(By.name("addBook")).click();

        WebElement feedback = driver.findElement(By.id("feedback"));
        assertTrue(feedback.isDisplayed());

        WebElement errorTitle = feedback.findElement(By.tagName("h2"));
        assertEquals("Erreurs de validation", errorTitle.getText());

        WebElement errorMessage = feedback.findElement(By.tagName("li"));
        assertEquals("L'identificateur du livre doit être entre 5 et 8 caractères long", errorMessage.getText());
    }

    @Test
    void testAddBookIDAlreadyExisting() {
        driver.get("http://localhost:8080/admin");

        driver.findElement(By.id("loginId")).sendKeys("admin");
        driver.findElement(By.id("loginPasswd")).sendKeys("password");
        driver.findElement(By.id("loginBtn")).click();

        driver.get("http://localhost:8080/admin");

        driver.findElement(By.id("addBook-category")).sendKeys("fiction");
        driver.findElement(By.id("addBook-id")).sendKeys("12345");
        driver.findElement(By.id("addBook-title")).sendKeys("Invalid Book");
        driver.findElement(By.id("addBook-authors")).sendKeys("Author");
        driver.findElement(By.id("longDescription")).sendKeys("Description");
        driver.findElement(By.id("cost")).sendKeys("10");
        driver.findElement(By.name("addBook")).click();

        driver.findElement(By.name("addBook")).click(); // Second ajout d'un livre avec le même ID

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement feedback = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("feedback")));
        assertTrue(feedback.isDisplayed());

        WebElement errorTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='feedback']//h2")));
        assertEquals("Livre avec le même identifiant existe déjà", errorTitle.getText());

        driver.findElement(By.id("searchBtn")).click();

        WebElement bookTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title-12345")));

        driver.findElement(By.id("del-12345")).click();

        wait.until(ExpectedConditions.stalenessOf(bookTitle));
    }

    @Test
    void testRemoveBook() {
        driver.get("http://localhost:8080/admin");

        driver.findElement(By.id("loginId")).sendKeys("admin");
        driver.findElement(By.id("loginPasswd")).sendKeys("password");
        driver.findElement(By.id("loginBtn")).click();

        driver.get("http://localhost:8080/admin");

        driver.findElement(By.id("addBook-category")).sendKeys("fiction");
        driver.findElement(By.id("addBook-id")).sendKeys("dune002");
        driver.findElement(By.id("addBook-title")).sendKeys("Dune");
        driver.findElement(By.id("addBook-authors")).sendKeys("Frank Herbert");
        driver.findElement(By.id("longDescription")).sendKeys("Livre sur la planète Dune, les vers des sables");
        driver.findElement(By.id("cost")).sendKeys("29.99");
        driver.findElement(By.name("addBook")).click();

        driver.get("http://localhost:8080/admin/catalog");

        driver.findElement(By.id("search")).sendKeys("fiction");
        driver.findElement(By.id("searchBtn")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement bookTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title-dune002")));
        assertEquals("Dune", bookTitle.getText());

        WebElement deleteButton = driver.findElement(By.id("del-dune002"));
        deleteButton.click();

        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait2.until(ExpectedConditions.stalenessOf(bookTitle));

        assertTrue(driver.findElements(By.id("title-dune002")).isEmpty());
    }
}
