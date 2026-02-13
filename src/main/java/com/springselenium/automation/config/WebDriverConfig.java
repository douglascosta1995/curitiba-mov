package com.springselenium.automation.config;

import io.github.bonigarcia.wdm.WebDriverManager;
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
        chromeOptions.addArguments("--headless=new"); // important (new headless mode)
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--window-size=1920,1080");
		chromeOptions.setExperimentalOption("prefs", chromePrefs);
		chromeOptions.addArguments("--remote-allow-origins=*");
		chromeOptions.addArguments("start-maximized");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-popup-blocking");
		chromePrefs.put("autofill.profile_enabled", false);
		chromePrefs.put("profile.password_manager_leak_detection", false);
        chromePrefs.put("download.default_directory", "/Users/douglascosta/Documents/Automation/douglas-automation-demo-project/src/main/downloads");
        chromeOptions.setExperimentalOption("prefs", chromePrefs);

		WebDriverManager.chromedriver().clearDriverCache().setup();
		return new ChromeDriver(chromeOptions);
	}
	
	@Bean
	public WebDriverWait webDriverWait(WebDriver driver) {
		return new WebDriverWait(driver, Duration.ofSeconds(timeout));
	}

}
