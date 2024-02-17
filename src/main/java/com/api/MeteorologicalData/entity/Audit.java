package com.api.MeteorologicalData.entity;

import com.api.MeteorologicalData.security.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity class for Audit table
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "audit")
public class Audit {

    /**
     * Primary key of the Audit table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * JSON string of the requests made to the server
     */
    @NotBlank
    private String requests;

    /**
     * Date and time of the request made to the server
     */
    @NotNull
    private Date dateRequets;

    /**
     * JSON string of the responses received from the server
     */
    @NotBlank
    @Column(columnDefinition = "JSON")
    private String responts;

    /**
     * User who made the request
     */
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

}
