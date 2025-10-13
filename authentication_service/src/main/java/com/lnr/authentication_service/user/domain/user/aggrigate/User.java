package com.lnr.authentication_service.user.domain.user.aggrigate;

import com.lnr.authentication_service.shared.error.domain.Assert;
import com.lnr.authentication_service.user.domain.user.vo.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jilt.Builder;

import java.util.UUID;

@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@ToString
public class User {

    @EqualsAndHashCode.Include
    private final UserPublicId publicId;

   //fix
    private final UserFirstName firstName;
   //fix
    private final UserLastName lastName;
    //fix
    private final UserEmail userEmail;
    //fix
    private final UserPassword password;

    private UserAddress userAddress;

    private UserPhoneNumber userPhoneNumber;

    private UserImage userImage;



   //from db
    private UserDbId dbId;

    public User(UserPublicId publicId, UserFirstName firstName, UserLastName lastName, UserEmail userEmail, UserPassword password, UserAddress userAddress, UserPhoneNumber userPhoneNumber, UserDbId dbId,UserImage image) {

        assertAllFields(firstName,lastName,userEmail,password);

        this.password = password;
        this.publicId = publicId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.userPhoneNumber = userPhoneNumber;
        this.dbId = dbId;
        this.userImage=image;
    }



    public void updateAddress(UserAddress userAddress) {
        if (userAddress != null) {
            this.userAddress = userAddress;
        }
    }

    public void updatePhoneNumber(UserPhoneNumber userPhoneNumber) {
        if (userPhoneNumber != null) {
            this.userPhoneNumber = userPhoneNumber;
        }
    }

    public void updateImage(UserImage userImage) {
        if (userImage != null) {
            this.userImage = userImage;
        }
    }






    private void assertAllFields(UserFirstName firstName,UserLastName lastName,UserEmail userEmail,UserPassword password){
        Assert.notNull("FirstName",firstName);
        Assert.notNull("LastName",lastName);
        Assert.notNull("UserEmail",userEmail);
        Assert.notNull("UserPassword",password);


    }
}

