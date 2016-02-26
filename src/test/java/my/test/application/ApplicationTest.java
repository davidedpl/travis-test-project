package my.test.application;

import static com.jayway.restassured.RestAssured.given;

import org.eclipse.jetty.http.HttpStatus;
import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

public class ApplicationTest {

  @ClassRule
  public static final DropwizardAppRule<ExampleConfiguration> rule =
      new DropwizardAppRule<ExampleConfiguration>(
          TestApplication.class,
          ResourceHelpers.resourceFilePath("configuration/test.yml"));

  @Test
  public void redirectToResUrl() {
    given()
        .when()
        .get(baseUrl() + TestResource.PATH)
        .then().statusCode(HttpStatus.NO_CONTENT_204);
  }

  private static String baseUrl() {
    return "http://localhost:" + rule.getLocalPort();
  }
}