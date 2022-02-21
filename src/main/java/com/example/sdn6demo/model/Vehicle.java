package com.example.sdn6demo.model;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vehicle {

    @Id
    @GeneratedValue
    private Long id;

    @EqualsAndHashCode.Include
    private String name;

    @ToString.Exclude
    @Relationship(type = "HAS_VEHICLE", direction = Relationship.INCOMING)
    private Fleet fleet;

}
