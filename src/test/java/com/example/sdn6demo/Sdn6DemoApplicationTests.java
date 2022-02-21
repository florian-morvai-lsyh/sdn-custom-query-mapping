package com.example.sdn6demo;

import com.example.sdn6demo.model.Customer;
import com.example.sdn6demo.model.Fleet;
import com.example.sdn6demo.model.Vehicle;
import com.example.sdn6demo.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {Sdn6DemoApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Sdn6DemoApplicationTests {

    private static final String NEO4J_DOCKER_IMAGE_NAME = "neo4j";
    private static final String NEO4J_DOCKER_IMAGE_TAG = "4.0.11-enterprise";

    @Container
    private static final Neo4jContainer<?> container;

    static {
        container = new Neo4jContainer<>(new DockerImageName(NEO4J_DOCKER_IMAGE_NAME, NEO4J_DOCKER_IMAGE_TAG).toString())
                .withAdminPassword(null)
                .withEnv("NEO4J_ACCEPT_LICENSE_AGREEMENT", "yes");
        container.start();
    }

    @Autowired
    private CustomerRepository customerRepository;

    @LocalServerPort
    private int port;

    private final Vehicle vehicle = Vehicle.builder().name("vehicle").build();
    private final Fleet fleet = Fleet.builder().name("fleet").build();
    private final Customer customer = Customer.builder().name("customer").build();

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.neo4j.uri", container::getBoltUrl);
    }

    @BeforeAll
    void initData() {
        vehicle.setFleet(fleet);
        fleet.setVehicles(Collections.singletonList(vehicle));
        fleet.setCustomer(customer);
        customer.setFleets(Collections.singletonList(fleet));

        customerRepository.save(customer);
    }

    @Test
    void derivedQuery() {
        final List<Customer> loaded = ImmutableList.copyOf(customerRepository.findAll(2));

        assertFalse(loaded.isEmpty());

        final Customer loadedCustomer = loaded.get(0);
        assertEquals(customer, loadedCustomer);
        assertNotNull(loadedCustomer.getFleets());
        assertFalse(loadedCustomer.getFleets().isEmpty());

        final Fleet loadedFleet = loadedCustomer.getFleets().get(0);
        assertEquals(fleet, loadedFleet);
        assertNotNull(loadedFleet.getVehicles());
        assertFalse(loadedFleet.getVehicles().isEmpty());

        final Vehicle loadedVehicle = loadedFleet.getVehicles().get(0);
        assertEquals(vehicle, loadedVehicle);
    }

    @Test
    void customQuery() {
        final List<Customer> loaded = customerRepository.findCustomCustomer(Collections.singletonList("customer"));

        assertFalse(loaded.isEmpty());

        final Customer loadedCustomer = loaded.get(0);
        assertEquals(customer, loadedCustomer);
        assertNotNull(loadedCustomer.getFleets());
        assertFalse(loadedCustomer.getFleets().isEmpty());

        final Fleet loadedFleet = loadedCustomer.getFleets().get(0);
        assertEquals(fleet, loadedFleet);
        assertNotNull(loadedFleet.getVehicles());
        assertFalse(loadedFleet.getVehicles().isEmpty());

        final Vehicle loadedVehicle = loadedFleet.getVehicles().get(0);
        assertEquals(vehicle, loadedVehicle);
    }

}
