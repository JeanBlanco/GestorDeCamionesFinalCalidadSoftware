package org.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class TruckSteps {
    private WebDriver driver;

    @Given("I access the add truck page")
    public void iAccessAddTruckPage() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") +
                "/src/main/java/edu/unac/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        String commonPrefix = "file:///";
        String relativePath = "/camionesfrontend/index.html";
        String htmlFilePath = commonPrefix + System.getProperty("user.dir") + relativePath;
        driver.get(htmlFilePath);
    }

    @When("I enter the plate and driver of the first truck")
    public void iEnterPlateAndDriverOfFirstTruck() {
        WebElement plateInput = driver.findElement(By.id("plate"));
        WebElement driverInput = driver.findElement(By.id("driver"));
        plateInput.clear();
        driverInput.clear();
        plateInput.sendKeys("ABC123");
        driverInput.sendKeys("Juan Perez");
    }

    @When("I click on add truck button")
    public void iClickOnAddTruckButton() {
        WebElement addButton = driver.findElement(By.xpath("//button[text()='AGREGAR CAMIÓN']"));
        addButton.click();
    }

    @Then("The first truck should appear in the list")
    public void firstTruckShouldAppearInTheList() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement truckList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("truck-list")));
        assertTrue(truckList.getText().contains("ABC123"));
        assertTrue(truckList.getText().contains("Juan Perez"));
    }

    @When("I enter the plate and driver of the second truck")
    public void iEnterPlateAndDriverOfSecondTruck() {
        WebElement plateInput = driver.findElement(By.id("plate"));
        WebElement driverInput = driver.findElement(By.id("driver"));
        plateInput.clear();
        driverInput.clear();
        plateInput.sendKeys("XYZ789");
        driverInput.sendKeys("Carlos Gomez");
    }

    @Then("The second truck should appear in the list")
    public void secondTruckShouldAppearInTheList() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement truckList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("truck-list")));
        assertTrue(truckList.getText().contains("XYZ789"));
        assertTrue(truckList.getText().contains("Carlos Gomez"));
    }

    @When("I search for the first truck by plate")
    public void iSearchForTheFirstTruckByPlate() {
        WebElement searchInput = driver.findElement(By.id("search-input"));
        searchInput.clear();
        searchInput.sendKeys("ABC123");
    }

    @Then("The first truck should be found in the list")
    public void firstTruckShouldBeFoundInTheList() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement truckList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("truck-list")));
        assertTrue(truckList.getText().contains("ABC123"));
    }

    @When("I mark the first truck as on route")
    public void iMarkFirstTruckAsOnRoute() {
        WebElement onRouteButton = driver.findElement(By.xpath("//button[text()='MARCAR EN RUTA']"));
        onRouteButton.click();
    }

    @Then("The first truck should be marked as on route")
    public void firstTruckShouldBeMarkedAsOnRoute() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement truckList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("truck-list")));
        assertTrue(truckList.getText().contains("En ruta"));
    }
}
