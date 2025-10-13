package com.lnr.authentication_service.user.infrastrature.seconadary;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_seq")
    @SequenceGenerator(name = "user_seq",sequenceName = "user_sequence",initialValue = 1,allocationSize = 1)
    private Long dbId; // database primary key

    @Column(nullable = false, unique = true)
    private UUID publicId; // exposed to frontend

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // hashed password

    // Address fields
    @Column
    private String street;
    @Column
    private String city;
    @Column
    private String zipCode;
    @Column
    private String country;

    // Phone number
    @Column
    private String countryCode;
    @Column
    private Long phoneNumber;

    // Image
    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    @Column(name = "image_type")
    private String imageType;

    // Convenience constructor for mapping domain -> entity
    public UserEntity(UUID publicId,
                      String firstName,
                      String lastName,
                      String email,
                      String password,
                      String street,
                      String city,
                      String zipCode,
                      String country,
                      String countryCode,
                      Long phoneNumber,
                      byte[] imageData,
                      String imageType) {
        this.publicId = publicId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
        this.imageData = imageData;
        this.imageType = imageType;
    }
}

