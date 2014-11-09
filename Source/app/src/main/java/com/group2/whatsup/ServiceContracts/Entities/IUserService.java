package com.group2.whatsup.ServiceContracts.Entities;

import com.group2.whatsup.Entities.Authentication.User;

public interface IUserService extends IEntityService<User> {
    User GetByUsername(String username);
    User GetByEmailAddress(String emailAddress);
}
