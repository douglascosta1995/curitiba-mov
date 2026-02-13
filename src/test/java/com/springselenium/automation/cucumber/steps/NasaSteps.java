package com.springselenium.automation.cucumber.steps;
import com.springselenium.automation.pages.nasa.*;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class NasaSteps {
    @Autowired
    NasaPage nasaPage;

    @Autowired
    NasaResultsPage nasaResultsPage;

    @Autowired
    NasaTechnologyPage nasaTechnologyPage;

    @Autowired
    NasaImagePage nasaImagePage;

    @Autowired
    NasaContactPage nasaContactPage;

    @Autowired
    NasaSubmitQuestionPage nasaSubmitQuestionPage;

    @Given("I am on the Login Page")
    public void given_IAmOnTheLoginPage() {
        nasaPage.goTo();
        //Assert.assertTrue(nasaPage.isAt());
    }
    @When("I click Entrar com CPF")
    public void when_IClickEntrarComCpf() {
        // Write code here that turns the phrase above into concrete actions
        nasaPage.clickEntrarComCpf();
    }
    @And("I use the credentials {string} {string}")
    public void and_IUseTheCredentials(String cpf, String password) throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        nasaPage.sendCredentials(cpf,password);
    }

    @Then("the user successfully logs in")
    public void then_TheUserSuccessfullyLogsIn() {
        System.out.println("The login was successfull? " + nasaPage.checkBtnNovaReserva());
    }

    @When("I select volleyball court data")
    public void when_ISelectVolleyballCourtData() throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        nasaPage.selectVolleyData();
    }

    @When("I select the suitable day and time")
    public void when_ISelectTheSuitableDayAndTime() throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        nasaPage.selectSuitableDate();
    }

    @Then("I return a list of available date and time")
    public void then_IReturnAListOfAvailableDateAndTime() throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        nasaPage.returnListAvailableDates();
    }

    @And("I filter available spots for preferred schedule")
    public void and_IFilterAvailableSpotsForPreferredSchedule() throws InterruptedException, IOException {
        // Write code here that turns the phrase above into concrete actions
        nasaPage.filterPreferredSchedule();
    }

    @Then("I notify the group about new matching spots")
    public void then_INotifyTheGroupAboutNewMatchingSpots() throws InterruptedException, IOException {
        // Write code here that turns the phrase above into concrete actions
        nasaPage.notifyGroup();
    }

    @Then("I send daily availability summary")
    public void then_ISendDailyAvailabilitySummary() throws InterruptedException, IOException {
        // Write code here that turns the phrase above into concrete actions
        nasaPage.sendDailySummary();
    }

    @When("I check available dates")
    public void when_ICheckAvailableDates() throws InterruptedException, IOException {
        // Write code here that turns the phrase above into concrete actions
        nasaPage.checkAvailableDates();
    }

}
