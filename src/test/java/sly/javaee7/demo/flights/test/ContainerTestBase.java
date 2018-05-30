package sly.javaee7.demo.flights.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.ConfigurableMavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;

import sly.javaee7.commons.crud.Queries;
import sly.javaee7.demo.flights.mock.ControlSimulator;
import sly.javaee7.demo.flights.mock.FlightBuilder;
import sly.javaee7.demo.flights.mock.Initializer;
import sly.javaee7.demo.flights.model.Aircraft;
import sly.javaee7.demo.flights.model.Airport;

public class ContainerTestBase {

	@Deployment
	public static Archive<?> createTestArchive() {

		File[] dependencies = getBuildDependencies();

		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackages(true, Filters.exclude(Initializer.class, ControlSimulator.class), "sly.javaee7")
				.addAsResource("META-INF/persistence.xml").addAsResource("arquillian.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml").addAsWebInfResource("test-ds.xml")
				.addAsLibraries(dependencies);
	}

	private static File[] getBuildDependencies() {

		String[] repos = { "http://repository.primefaces.org/",
				"http://repository.jboss.org/nexus/content/groups/public-jboss",
				"https://repository.jboss.org/nexus/content/repositories",
				"https://repository.jboss.org/nexus/content/repositories/thirdparty-releases" };

		ConfigurableMavenResolverSystem resolver = Maven.configureResolver().withMavenCentralRepo(true);

		for (String repo : repos) {
			resolver.withRemoteRepo(repo, repo, "default");
		}

		File[] dependencies = resolver.resolve("javax:javaee-api:7.0", "org.primefaces:primefaces:6.2",
				"org.apache.commons:commons-lang3:3.7").withTransitivity().asFile();

		return dependencies;
	}

	@Inject
	private FlightBuilder builder;

	@Inject
	private Queries q;

	private void createAircrafts() throws Exception {
		int n = 12;
		for (int i = 0; i < n; i++) {
			Aircraft aircraft = builder.createRandomAircraft();
			assertNotNull(aircraft.getId());
		}
		long cnt = q.getRecordCount(Aircraft.class);
		assertTrue(n <= cnt);
	}

	private void createAirports() throws Exception {
		int n = 12;
		for (int i = 0; i < n; i++) {
			Airport airport = builder.createRandomAirport();
			assertNotNull(airport.getId());
		}
		long cnt = q.getRecordCount(Airport.class);
		assertTrue(n <= cnt);
	}

	@Before
	public void setUp() throws Exception {
		assertNotNull(builder);
		assertNotNull(q);
		createAircrafts();
		createAirports();
	}

}
