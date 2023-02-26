package org.codeai.ecommerce.validator;

import org.codeai.ecommerce.exceptions.UserValidationException;
import org.codeai.ecommerce.models.User;

public class UserValidator {

  public void validate(User user) {
    // validate user
    if (!isValid(user)) {
      throw new UserValidationException("User is not valid");
    }
  }

  public void validateUserAlreadyExists(User user) {
    // validate user
    if (user.getId() != null) {
      throw new UserValidationException("User already exists");
    }
  }

  public boolean isValid(User user) {
    return user.getId() != null;
  }


}
