package my.test.application;

import com.google.inject.Stage;

import com.hubspot.dropwizard.guice.GuiceBundle;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TestApplication extends Application<Configuration> {

  public static void main(String[] args) throws Exception {
    new TestApplication().run(args);
  }

  @Override
  public String getName() {
    return "travis-test-application";
  }

  @Override
  public void initialize(Bootstrap<Configuration> bootstrap) {
    GuiceBundle<Configuration> guiceBundle = GuiceBundle.<Configuration>newBuilder()
        .addModule(new TestModule())
        .enableAutoConfig(getClass().getPackage().getName())
        .setConfigClass(Configuration.class)
        .build(Stage.DEVELOPMENT);
    // workaround for https://github.com/HubSpot/dropwizard-guice/issues/19

    bootstrap.addBundle(guiceBundle);
  }

  @Override
  public void run(Configuration configuration, Environment environment) throws Exception {
    // resources, tasks, healthchecks and providers are automatically injected by guice
  }

}
