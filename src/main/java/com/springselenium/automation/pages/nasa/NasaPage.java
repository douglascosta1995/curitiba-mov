package com.springselenium.automation.pages.nasa;

import com.springselenium.automation.annotation.LazyComponent;
import com.springselenium.automation.pages.AbstractPage;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import static org.testng.AssertJUnit.fail;

@LazyComponent
public class NasaPage extends AbstractPage {
        @Value("${application.url}")
    private String url;

    final String fromEmail = System.getenv("EMAIL_USERNAME");
    final String appPassword = System.getenv("EMAIL_PASSWORD");
    final String toEmail = "douglascastro2010@hotmail.com, leoribas.22@gmail.com, mizael.otelakoski@gmail.com, marinevescruz32@gmail.com, josewman@gmail.com, brauliomkt@gmail.com";
    //final String toEmail = "douglascastro2010@hotmail.com";

    private final By nasa_header = By.id("header-logo");

    private final By btn_entrarcpf = By.id("nomeCidadao");

    private final By btn_cpf = By.xpath("//*[@id='documento']");

    private final By btn_pwd = By.id("senha");

    private final By btn_prox = By.id("btnProximo");

    private final By btn_entrar = By.id("btnSenhaProximo");

    private final By btn_novaReserva = By.id("btnNovaReserva");

    private final By select_Atividade = By.id("selectAtividade");

    private final By select_Regional = By.id("selectNucleo");

    private final By select_Unidade = By.id("selectUnidade");

    private final By select_Sugestao = By.id("selectSugestao");

    private final By field_qtypessoas = By.id("capacidadePessoas");

    private final By btn_avancar = By.id("btnConfirmaCapacidade");

    private final By btn_calendar = By.id("dataReferencia");

    private final By btn_buscar = By.id("btnConfirmaData");

    private final By btnCarregarMais = By.id("btnCarregarMais");


    public void sendEmail(List<String> slots) {


        //final String toEmail = "leoribas.22@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, appPassword);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );

            message.setSubject("ALERT - Reservas Disponíves para horários definidos");

            StringBuilder body = new StringBuilder();
            body.append("As reservas com os horários de preferência definidos são:\n\n");

            for (String slot : slots) {
                body.append(slot).append("\n");
            }
            body.append("\nAcesse o site agora para garantir a reserva: https://reservadeespaco-curitibaemmovimento.curitiba.pr.gov.br/questionario\n\n");


            message.setText(body.toString());

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmailSummary(List<String> slots) {

        //final String toEmail = "leoribas.22@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, appPassword);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );

            message.setSubject("Resumo de Reservas do Dia");

            StringBuilder body = new StringBuilder();
            body.append("As reservas abertas no momento são:\n\n");

            for (String slot : slots) {
                body.append(slot).append("\n");
            }
            body.append("\nAcesse o site agora para garantir a reserva: https://reservadeespaco-curitibaemmovimento.curitiba.pr.gov.br/questionario\n\n");


            message.setText(body.toString());

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean isAt() {
        return wait.until((d) -> driver.findElement(nasa_header).isDisplayed());
    }

    public void goTo() {
        driver.get(url);
    }

    public void clickEntrarComCpf() {
        driver.findElement(btn_entrarcpf).click();
    }

    public void sendCredentials(String cpf, String password) throws InterruptedException {
        // Create a WebDriverWait instance with a timeout of 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until the element is clickable (visible and enabled)
        WebElement cpfBtn = wait.until(
                ExpectedConditions.elementToBeClickable(btn_cpf)
        );
        cpfBtn.sendKeys(cpf);
        driver.findElement(btn_prox).click();

        // Wait until the element is clickable (visible and enabled)
        WebElement pwdBtn = wait.until(
                ExpectedConditions.elementToBeClickable(btn_pwd)
        );
        pwdBtn.sendKeys(password);
        Thread.sleep(1000);
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        int attempts = 0;

        while (attempts < 5) {  // avoid infinite loop
            try {

                WebElement enterBtn = wait.until(
                        ExpectedConditions.elementToBeClickable(btn_entrar)
                );

                enterBtn.click();

                // Wait a short moment to allow page update
                Thread.sleep(1000);

                // If button is no longer visible → exit loop
                if (driver.findElements(btn_entrar).isEmpty()) {
                    System.out.println("Button disappeared. Success.");
                    break;
                }

            } catch (Exception e) {
                System.out.println("Button no longer clickable or gone.");
                break;
            }

            attempts++;
        }

    }


    public boolean checkBtnNovaReserva(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(btn_novaReserva));
            return true;

        } catch (TimeoutException e) {
            return false; // unreachable, but Java requires it
        }

    }

    public void selectVolleyData() throws InterruptedException {
        driver.findElement(btn_novaReserva).click();
        //Thread.sleep(1000);
        //Select selectAtividade = new Select(driver.findElement(select_Atividade));
        //selectAtividade.selectByValue("20");
        Thread.sleep(1000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        wait.until(driver -> {
            Select dropdown = new Select(driver.findElement(select_Atividade));
            return dropdown.getOptions()
                    .stream()
                    .anyMatch(option -> option.getAttribute("value").equals("20"));
        });

        Select selectAtividade = new Select(driver.findElement(select_Atividade));
        selectAtividade.selectByValue("20");

        Select selectRegional = new Select(driver.findElement(select_Regional));
        selectRegional.selectByValue("2");
        Select selectUnidade = new Select(driver.findElement(select_Unidade));
        selectUnidade.selectByValue("7099");
        Thread.sleep(1000);

        Select selectSugestao = new Select(driver.findElement(select_Sugestao));
        selectSugestao.selectByValue("true");

        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until the element is clickable (visible and enabled)
        WebElement qtypessoas = wait.until(
                ExpectedConditions.elementToBeClickable(field_qtypessoas)
        );
        qtypessoas.sendKeys("18");
        Thread.sleep(1000);
        driver.findElement(btn_avancar).click();
        Thread.sleep(1000);

        WebElement dateInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(btn_calendar)
        );

        String minDate = dateInput.getAttribute("min");
        System.out.println("DEBUG - minDate: " + minDate);
        System.out.println("DEBUG - System today: " + LocalDate.now());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate targetDate = LocalDate.now().plusDays(1);
        String formattedDate = targetDate.format(formatter);


        System.out.println("DEBUG - Selected date: " + formattedDate);

        dateInput.clear();
        dateInput.sendKeys(formattedDate);
        dateInput.click();

        dateInput.sendKeys(Keys.TAB);

        WebElement btnAvancar = wait.until(
                ExpectedConditions.elementToBeClickable(btn_buscar)
        );
        btnAvancar.click();
        Thread.sleep(5000);

    }

    public void selectSuitableDate() throws InterruptedException {

        By loadMoreBtn = By.id("btnCarregarMais");
        By listItems = By.cssSelector(".resultado");

        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(loadMoreBtn),
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.resultado"))
        ));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


        while (true) {

            List<WebElement> buttons = driver.findElements(loadMoreBtn);
            if (buttons.isEmpty()) break;

            int previousSize = driver.findElements(listItems).size();

            WebElement button = wait.until(
                    ExpectedConditions.presenceOfElementLocated(loadMoreBtn)
            );

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", button);

            wait.until(ExpectedConditions.elementToBeClickable(button));

            js.executeScript("arguments[0].click();", button);

            try {
                wait.until(driver ->
                        driver.findElements(listItems).size() > previousSize
                );
            } catch (TimeoutException e) {
                break;
            }
        }

    }

    public List<String> returnListAvailableDates() {

        System.out.println("==================================");
        System.out.println("DEBUG - System timezone: " + ZoneId.systemDefault());
        System.out.println("DEBUG - LocalDate.now(): " + LocalDate.now());
        System.out.println("==================================");


        List<String> filteredSlots = new ArrayList<>();
        List<String> allSlots = new ArrayList<>();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div.resultado")
        ));

        List<WebElement> resultados = driver.findElements(
                By.cssSelector("div.resultado")
        );

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        System.out.println("DEBUG - resultados size: " + resultados.size());

        for (WebElement r : resultados) {
            System.out.println("DEBUG HTML: " + r.getText());
        }

        for (WebElement resultado : resultados) {

            String dateText = resultado.findElement(
                    By.cssSelector("h3.data span")
            ).getText().trim();

            if (dateText.isEmpty()) continue;

            LocalDate date = LocalDate.parse(dateText, dateFormatter);

            List<WebElement> horarios = resultado.findElements(
                    By.cssSelector("span.hora:not(.d-none)")
            );

            for (WebElement horario : horarios) {

                String timeText = horario.getText().trim();
                if (timeText.isEmpty()) continue;

                LocalTime time = LocalTime.parse(timeText, timeFormatter);

                String slot = dateText + " - " + timeText;

                // Save ALL slots
                allSlots.add(slot);

                // Apply filter
                if (
                        (date.getDayOfWeek() == DayOfWeek.FRIDAY &&
                                !time.isBefore(LocalTime.of(18, 0)))
                                ||
                                (date.getDayOfWeek() == DayOfWeek.SATURDAY &&
                                        !time.isBefore(LocalTime.of(16, 0)))
                ) {
                    filteredSlots.add(slot);
                }
            }
        }

        // Save both files
        //saveToFile("savedSlots.txt", filteredSlots);
        saveToFile("allSlots.txt", allSlots);

        return filteredSlots;
    }

    private void saveToFile(String fileName, List<String> slots) {
        try {
            Files.write(
                    Paths.get(fileName),
                    slots,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveSlotsToFile(List<String> slots) throws IOException {

        Path path = Paths.get("savedSlots.txt");

        Files.write(path, slots);
    }

    public List<String> readSlotsFromFile() throws IOException {

        Path path = Paths.get("savedSlots.txt");

        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        return Files.readAllLines(path);
    }

    public List<String> getNewSlots(List<String> currentSlots,
                                    List<String> savedSlots) {

        List<String> newSlots = new ArrayList<>(currentSlots);
        newSlots.removeAll(savedSlots);

        return newSlots;
    }

    public List<String> readSlotsFromFile(String fileName) throws IOException {

        Path path = Paths.get(fileName);

        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        return Files.readAllLines(path);
    }


    //private List<String> lastPreferredSlots = new ArrayList<>();

    public List<String>  filterPreferredSchedule() throws IOException {

        List<String> preferredSlots = returnListAvailableDates();

        System.out.println("\n==============================");
        System.out.println("Preferred Available Slots:");
        System.out.println("==============================");

        if (!preferredSlots.isEmpty()) {
            preferredSlots.forEach(System.out::println);
        } else {
            System.out.println("No preferred spots available at the moment.");
        }

        System.out.println("==============================\n");

        saveSlotsToFile(preferredSlots);

        // store in memory for notify step
        return preferredSlots;
    }


    //public void notifyGroup() throws IOException {
    //    List<String> currentPreferred = readSlotsFromFile("savedSlots.txt");
    //    List<String> previouslyNotified = readSlotsFromFile("notifiedSlots.txt");

    //    List<String> newSlots = currentPreferred.stream()
    //            .filter(slot -> !previouslyNotified.contains(slot))
    //            .toList();

     //   if (!newSlots.isEmpty()) {

    //        System.out.println("New preferred slots found. Sending email...");

      //      sendEmail(newSlots);

      //      saveSlotsToFile(currentPreferred); // overwrite notifiedSlots.txt
      //      Files.write(Paths.get("notifiedSlots.txt"), currentPreferred);

     //   } else {

    //        System.out.println("No new preferred slots to notify.");
      //  }
    //}

    public void notifyGroup(List<String> slots) throws IOException {

        if (slots == null || slots.isEmpty()) {

            System.out.println("No preferred slots available. No email sent.");
            return;
        }

        System.out.println("Preferred slots found. Sending email...");
        sendEmail(slots);
    }


    private List<String> dailySlots;


    public void sendDailySummary(){
        if (dailySlots == null || dailySlots.isEmpty()) {

            sendEmailSummary(List.of("No available slots at the moment."));

            System.out.println("Daily summary sent: No slots available.");
            return;
        }

        sendEmailSummary(dailySlots);

        System.out.println("Daily summary sent with " + dailySlots.size() + " slots.");
    }

    public void checkAvailableDates() throws IOException {
        Path path = Paths.get("allSlots.txt");

        if (!Files.exists(path)) {
            System.out.println("Snapshot file not found.");
            dailySlots = List.of(); // empty list
            return;
        }

        dailySlots = Files.readAllLines(path);

        System.out.println("Loaded " + dailySlots.size() + " slots from file.");
    }
}
