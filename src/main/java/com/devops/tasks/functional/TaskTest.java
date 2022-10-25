package com.devops.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TaskTest {
    public WebDriver accessApp() {
        String url = "http://localhost:8001/tasks";
        WebDriver driver = new ChromeDriver();
        driver.navigate().to(url);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;

    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Test
    public void mustSaveTaskWithSuccess() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDateTime today = LocalDateTime.now();
        WebDriver driver = accessApp();

        try {
            //clic add todo
            driver.findElement(By.id("addTodo")).click();

            //write description
            driver.findElement(By.id("task")).sendKeys("Test by selenium ["+today.toLocalTime()+"]");


            //write the field date
            driver.findElement(By.id("dueDate")).sendKeys(tomorrow.format(formatter));

            //click save
            driver.findElement(By.id("saveButton")).click();

            //validate message success
            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Success!", message);

        } finally {
            driver.quit();
        }


    }

    @Test
    public void mustNotSaveTaskFiledTaskEmpty() {
        WebDriver driver = accessApp();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDateTime today = LocalDateTime.now();

        try {
            //clic add todo
            driver.findElement(By.id("addTodo")).click();

            //write the field date
            driver.findElement(By.id("dueDate")).sendKeys(tomorrow.format(formatter));

            //click save
            driver.findElement(By.id("saveButton")).click();

            //validate message success
            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Fill the task description", message);

        } finally {
            driver.quit();
        }

    }
}
