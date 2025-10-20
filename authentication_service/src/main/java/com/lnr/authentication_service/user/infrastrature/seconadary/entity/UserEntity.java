package com.lnr.authentication_service.user.infrastrature.seconadary.entity;


import com.lnr.authentication_service.auth.domain.account.vo.UserEmail;
import com.lnr.authentication_service.auth.domain.account.vo.UserLastSeen;
import com.lnr.authentication_service.shared.jpa.AbstractAuditingEntity;
import com.lnr.authentication_service.user.domain.profile.vo.*;
import com.lnr.authentication_service.user.domain.user.aggrigate.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserEntity extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_seq")
    @SequenceGenerator(name = "user_seq",sequenceName = "user_sequence",initialValue = 1,allocationSize = 1)
    private Long id; // database primary key

    @Column(nullable = false, unique = true)
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

    @Column(name = "last_seen", nullable = false)
    private java.time.Instant lastSeen;

    public static UserEntity toEntity(User user) {
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
        if (user.getLastSeen() == null) {
            throw new IllegalArgumentException("LastSeen must not be null");
        }

        return UserEntity.builder()
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
                .lastSeen(user.getLastSeen().value())
                .build();
    }


    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("UserEntity must not be null");
        }

        // Map phone
        UserPhoneNumber phone = null;
        if (entity.getCountryCode() != null && entity.getPhoneNumber() != null) {
            phone = new UserPhoneNumber(entity.getCountryCode(), entity.getPhoneNumber());
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
        }

        // Map image
        UserImage image = null;
        if (entity.getImageData() != null && entity.getImageType() != null) {
            image = new UserImage(entity.getImageData(), entity.getImageType());
        }

        return new User(
                new UserPublicId(entity.getPublicId()),
                new UserFirstName(entity.getFirstName()),
                new UserLastName(entity.getLastName()),
                new UserEmail(entity.getEmail()),
                null, // password is not loaded in domain for security reasons
                new UserLastSeen(entity.getLastSeen()),
                address,
                phone,
                image,
                new UserDbId(entity.getId())
        );
    }




}

