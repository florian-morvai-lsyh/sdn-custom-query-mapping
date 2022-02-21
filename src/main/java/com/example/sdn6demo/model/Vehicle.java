package com.example.sdn6demo.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
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
    @Relationship(type = "HAS_VEHICLE", direction = Relationship.Direction.INCOMING)
    private Fleet fleet;

}
