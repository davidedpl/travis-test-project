package my.test.application;

import com.google.inject.Stage;

import com.hubspot.dropwizard.guice.GuiceBundle;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.flyway.FlywayBundle;
import io.dropwizard.flyway.FlywayFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TestApplication extends Application<ExampleConfiguration> {

  public static void main(String[] args) throws Exception {
    new TestApplication().run(args);
  }

  @Override
  public String getName() {
    return "travis-test-application";
  }

  @Override
  public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
    GuiceBundle<ExampleConfiguration> guiceBundle = GuiceBundle.<ExampleConfiguration>newBuilder()
        .addModule(new TestModule())
        .enableAutoConfig(getClass().getPackage().getName())
        .setConfigClass(ExampleConfiguration.class)
        .build(Stage.DEVELOPMENT);
    // workaround for https://github.com/HubSpot/dropwizard-guice/issues/19

    bootstrap.addBundle(guiceBundle);

    bootstrap.addBundle(new FlywayBundle<ExampleConfiguration>() {
      @Override
      public DataSourceFactory getDataSourceFactory(ExampleConfiguration configuration) {
        return configuration.getDataSourceFactory();
      }
    });
  }

  @Override
  public void run(ExampleConfiguration config, Environment environment) throws Exception {
    // resources, tasks, healthchecks and providers are automatically injected by guice

    new FlywayFactory()
        .build(config.getDataSourceFactory().build(environment.metrics(), "FlyWay"))
        .migrate();
  }

}
