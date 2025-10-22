package com.lnr.authentication_service.user.infrastrature.seconadary.entity;


import com.lnr.authentication_service.shared.domain.user.vo.UserEmail;
import com.lnr.authentication_service.shared.jpa.AbstractAuditingEntity;
import com.lnr.authentication_service.user.domain.profile.aggrigate.UserProfile;
import com.lnr.authentication_service.user.domain.profile.aggrigate.UserProfileBuilder;
import com.lnr.authentication_service.user.domain.profile.vo.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true,callSuper = false)
public class UserProfileEntity extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_seq")
    @SequenceGenerator(name = "user_seq",sequenceName = "user_sequence",initialValue = 1,allocationSize = 1)
    private Long id; // database primary key

    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private UUID publicId; // exposed to frontend

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    // Address fields
    @Column(name = "address_street")
    private String street;
    @Column(name = "address_city")
    private String city;
    @Column(name = "address_zip_code")
    private String zipCode;
    @Column(name = "address_country")
    private String country;

    // Phone number

    @Column(name = "phone_country_code")
    private String countryCode;


    @Column(name = "phone_number")
    private Long phoneNumber;

    // Image
    @Lob
    @Column(name = "image_data", columnDefinition = "bytea")
    private byte[] imageData;

    @Column(name = "image_type")
    private String imageType;



    public static UserProfileEntity toEntity(UserProfile user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        if (user.getFirstName() == null || user.getFirstName().value() == null) {
            throw new IllegalArgumentException("First name must not be null");
        }
        if (user.getLastName() == null || user.getLastName().value() == null) {
            throw new IllegalArgumentException("Last name must not be null");
        }
        if (user.getUserEmail() == null || user.getUserEmail().value() == null) {
            throw new IllegalArgumentException("Email must not be null");
        }
        if (user.getPublicId() == null) {
            throw new IllegalArgumentException("PublicId must not be null");
        }


        return UserProfileEntity.builder()
                .publicId(user.getPublicId().value())
                .firstName(user.getFirstName().value())
                .lastName(user.getLastName().value())
                .email(user.getUserEmail().value())
                .street(user.getUserAddress().street() != null ? user.getUserAddress().street() : null)
                .city(user.getUserAddress().city() != null ? user.getUserAddress().city() : null)
                .zipCode(user.getUserAddress().zipCode() != null ? user.getUserAddress().zipCode() : null)
                .country(user.getUserAddress().country() != null ? user.getUserAddress().country() : null)
                .countryCode(user.getUserPhoneNumber().countryCode() != null ? user.getUserPhoneNumber().countryCode() : null)
                .phoneNumber(user.getUserPhoneNumber().number() != 0 ? user.getUserPhoneNumber().number() : null)
                .imageData(user.getUserImage().value()!= null ? user.getUserImage().value() : null)
                .imageType(user.getUserImage().imagetype() != null ? user.getUserImage().imagetype() : null)
                .build();
    }


    public static UserProfile toDomain(UserProfileEntity entity) {

        UserProfileBuilder entityBuilder= UserProfileBuilder.userProfile();
        if (entity == null) {
            throw new IllegalArgumentException("UserEntity must not be null");
        }

        // Map phone
        UserPhoneNumber phone = null;
        if (entity.getCountryCode() != null && entity.getPhoneNumber() != null) {
            phone = new UserPhoneNumber(entity.getCountryCode(), entity.getPhoneNumber());

            entityBuilder.userPhoneNumber(phone);
        }

        // Map address
        UserAddress address = null;
        if (entity.getStreet() != null || entity.getCity() != null ||
                entity.getZipCode() != null || entity.getCountry() != null) {
            address = new UserAddress(
                    entity.getStreet(),
                    entity.getCity(),
                    entity.getZipCode(),
                    entity.getCountry()
            );

            entityBuilder.userAddress(address);
        }

        // Map image
        UserImage image = null;
        if (entity.getImageData() != null && entity.getImageType() != null) {
            image = new UserImage(entity.getImageData(), entity.getImageType());

            entityBuilder.userImage(image);
        }

        return  entityBuilder.firstName(new UserFirstName(entity.firstName)).lastName(new UserLastName(entity.lastName)).userEmail(new UserEmail(entity.email)).build();
    }




}

