package com.springselenium.automation.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebDriverConfig {
	
	@Value("${default.timeout:30}")
	private int timeout;

    @Bean
    @Primary
    public WebDriver chromeDriver() {

        Map<String, Object> chromePrefs = new HashMap<>();
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments("--headless=new");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--window-size=1920,1080");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("start-maximized");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-popup-blocking");
        chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
        chromeOptions.addArguments("--lang=pt-BR"); // good addition

        chromePrefs.put("autofill.profile_enabled", false);
        chromePrefs.put("profile.password_manager_leak_detection", false);
        chromePrefs.put("download.default_directory",
                "/Users/douglascosta/Documents/Automation/douglas-automation-demo-project/src/main/downloads");

        chromeOptions.setExperimentalOption("prefs", chromePrefs);

        WebDriverManager.chromedriver().clearDriverCache().setup();

        // 1️⃣ Create driver first
        ChromeDriver driver = new ChromeDriver(chromeOptions);

        // 2️⃣ Immediately override timezone
        driver.executeCdpCommand(
                "Emulation.setTimezoneOverride",
                Map.of("timezoneId", "America/Sao_Paulo")
        );

        System.out.println(
                ((JavascriptExecutor) driver)
                        .executeScript("return Intl.DateTimeFormat().resolvedOptions().timeZone")
        );


        // 3️⃣ Return driver
        return driver;
    }


    @Bean
	public WebDriverWait webDriverWait(WebDriver driver) {
		return new WebDriverWait(driver, Duration.ofSeconds(timeout));
	}

}
