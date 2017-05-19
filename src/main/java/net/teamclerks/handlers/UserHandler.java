package net.teamclerks.handlers;

import com.techempower.gemini.*;
import com.techempower.gemini.input.*;
import com.techempower.gemini.input.validator.*;
import com.techempower.gemini.path.annotation.*;
import com.techempower.gemini.pyxis.*;
import com.techempower.gemini.pyxis.handler.*;

import net.teamclerks.*;
import net.teamclerks.entities.*;

public class UserHandler extends SecureMethodUriHandler<Context,User>
{
  public UserHandler(Application app)
  {
    super(app, "UsHa");
  }
  
  @Path("updatePassword")
  @Put
  public boolean updatePassword()
  {    
    final Input input = new ValidatorSet(
        new RequiredValidator("newPassword"),
        new RequiredValidator("currentPassword"),
        new PasswordComplexityValidator("newPassword", app().getSecurity()),
        new PasswordValidator("currentPassword", app().getSecurity()))
      .process(context());
    
    if (input.passed())
    {
      user().setUserPassword(query().get("newPassword"));
      
      return json();
    }
    
    return json(input);
  }

  private class PasswordValidator extends ElementValidator
  {
    private final PyxisSecurity security;
    
    public PasswordValidator(String elementName, PyxisSecurity security)
    {
      super(elementName);
      this.security = security;
    }
    
    @Override
    public void process(Input input)
    {
      final String userValue = getUserValue(input);
      if (userValue.length() > 0)
      {
        final PyxisUser user = security.getUser(input.context());
        if (user != null)
        {
          if(!security.passwordTest(user, userValue))
          {
            input.addError(getElementName(), "Current password is incorrect.");
          }
        }
      }
    }
  }
}