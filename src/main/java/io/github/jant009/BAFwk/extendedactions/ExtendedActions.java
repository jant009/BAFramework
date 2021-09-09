package io.github.jant009.BAFwk.extendedactions;

import io.github.jant009.BAFwk.exception.CookieNotInitializedException;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.time.SystemClock;
import net.thucydides.core.guice.Injectors;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtendedActions {
    public static final String EMPTY = "_EMPTY_";
    public static final String NA = "_NA_";

    public static void type(WebElementFacade element, String s, Boolean clearBeforeTyping, Boolean tabAfterTyping) {
        String txt = s;
        if (! s.equalsIgnoreCase(ExtendedActions.NA)) {
            if (s.equalsIgnoreCase(ExtendedActions.EMPTY)) {
                txt = "";
            }

            if (clearBeforeTyping)
                element.clear();

            if (tabAfterTyping)
                element.typeAndTab(s);
            else
                element.type(txt);
        }
    }

    public static void type(WebElementFacade element, String s) {
        type(element, s, true, true);
    }

    public static void scrollToViewAndClick(PageObject page, WebElementFacade element) {
        ((JavascriptExecutor) page.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }

    public static void scrollToView(PageObject page, WebElementFacade element) {
        ((JavascriptExecutor) page.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void waitUntilElementIsVisible(PageObject page, WebElementFacade element, int timeoutInSeconds) {
        WebDriverWait waiter = new WebDriverWait(page.getDriver(), timeoutInSeconds);
        waiter.until(ExpectedConditions.visibilityOf(element));
    }

    public static Object executeJsSCriptOnPage(PageObject page, String jsScript) {
        return ((JavascriptExecutor) page.getDriver()).executeScript(jsScript);
    }

    public static String getCookieValue(PageObject page, String cookieName) throws CookieNotInitializedException {
        try {
            return page.getDriver().manage().getCookieNamed(cookieName).getValue();
        } catch (NullPointerException e) {
            throw new CookieNotInitializedException(cookieName, e);
        }
    }

    public static void waitFor(int timeInMilliseconds) {
        SystemClock clock = Injectors.getInjector().getInstance(SystemClock.class);
        clock.pauseFor(timeInMilliseconds);
    }

    public static void AssertThatValuesAreEqual(String observed, String expected) {
        if (! expected.equalsIgnoreCase(ExtendedActions.NA))
            assertThat(observed).isEqualTo(expected);
    }

    public static void AssertThatValueMatch(String observed, String regexp) {
        if (! regexp.equalsIgnoreCase(ExtendedActions.NA))
            assertThat(observed).matches(Pattern.compile(regexp));
    }

    public static void scrollByPixel(PageObject page, int x, int y ){

        ((JavascriptExecutor) page.getDriver()).executeScript(("window.scrollBy(" + x + "," + y +")"));

    }

    //methode override to get lower text
    public static ExpectedCondition<Boolean> loweredTextToBePresentInElement(
            final WebElementFacade element, final String text) {

        return new ExpectedCondition<Boolean>() {
            @NotNull
            @Override
            public <V> Function<V, Boolean> compose(@NotNull Function<? super V, ? extends WebDriver> before) {
                return null;
            }

            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    String elementText = element.getText().toLowerCase();
                    return elementText.contains(text.toLowerCase());
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("text ('%s') to be present in element %s", text, element);
            }
        };
    }

    public static void waitToTextToBePresentInElement(PageObject page, WebElementFacade element, String text, int timeoutInSeconds){
        WebDriverWait waiter = new WebDriverWait(page.getDriver(), timeoutInSeconds);
        waiter.until(loweredTextToBePresentInElement(element,text.toLowerCase()));
    }

    public static void waitUntilUrlContains(PageObject page, String text, int timeoutInSeconds) {
        WebDriverWait waiter = new WebDriverWait(page.getDriver(), timeoutInSeconds);
        waiter.until(ExpectedConditions.urlContains(text));
    }
}
