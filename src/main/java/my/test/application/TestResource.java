package my.test.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path(TestResource.PATH)
public final class TestResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestResource.class);

  public static final String PATH = "/test";

  @GET
  public void redirectToResUrl() {
    LOGGER.info("GET /test has been called");
  }
}
