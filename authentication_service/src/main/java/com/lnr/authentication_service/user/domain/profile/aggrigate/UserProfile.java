package com.lnr.authentication_service.user.domain.profile.aggrigate;

import com.lnr.authentication_service.shared.domain.user.vo.UserEmail;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import com.lnr.authentication_service.shared.error.domain.Assert;
import com.lnr.authentication_service.user.domain.profile.vo.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jilt.Builder;

@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@ToString
public class UserProfile {

    @EqualsAndHashCode.Include
    private final UserPublicId publicId;

   //fix
    private final UserFirstName firstName;
   //fix
    private final UserLastName lastName;
    //fix
    private final UserEmail userEmail;


    private UserAddress userAddress;

    private UserPhoneNumber userPhoneNumber;

    private UserImage userImage;


   //from db
    private final UserDbId dbId;

    public UserProfile(UserPublicId publicId, UserFirstName firstName, UserLastName lastName, UserEmail userEmail, UserAddress userAddress, UserPhoneNumber userPhoneNumber, UserImage image,UserDbId dbId) {

        assertAllFields(firstName,lastName,userEmail,dbId);

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






    private void assertAllFields(UserFirstName firstName, UserLastName lastName, UserEmail userEmail,UserDbId dbId){
        Assert.notNull("FirstName",firstName);
        Assert.notNull("LastName",lastName);
        Assert.notNull("UserEmail",userEmail);
        Assert.notNull("UserProfileDbId",dbId);


    }
}

