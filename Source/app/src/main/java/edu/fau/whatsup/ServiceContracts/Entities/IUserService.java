package edu.fau.whatsup.ServiceContracts.Entities;

import edu.fau.whatsup.Entities.Authentication.User;

public interface IUserService extends IEntityService<User> {
    User GetByUsername(String username);
    User GetByEmailAddress(String emailAddress);
}
