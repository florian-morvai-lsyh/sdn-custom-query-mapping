package com.example.sdn6demo.model;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Fleet {

    @Id
    @GeneratedValue
    private Long id;

    @EqualsAndHashCode.Include
    private String name;

    @ToString.Exclude
    @Relationship(type = "HAS_FLEET", direction = Relationship.INCOMING)
    private Customer customer;

    @Relationship(type = "HAS_VEHICLE")
    private List<Vehicle> vehicles;

}
