package com.example.sdn6demo.repository;

import com.example.sdn6demo.model.Customer;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends Neo4jRepository<Customer, Long> {

    @Query("MATCH (customer:`Customer`) WHERE customer.name IN $names" +
            " RETURN customer, [(customer)-[customer_has_fleet:`HAS_FLEET`]->(fleet:`Fleet`) | [" +
            " customer_has_fleet," +
            " fleet," +
            " [(fleet)-[fleet_has_vehicle:`HAS_VEHICLE`]->(vehicle:`Vehicle`) | [" +
            "    fleet_has_vehicle," +
            "    vehicle" +
            "  ]]" +
            "]]")
    List<Customer> findCustomCustomer(@Param("names") Iterable<String> names);

}
