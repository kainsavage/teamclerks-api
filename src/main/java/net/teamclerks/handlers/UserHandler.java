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
  private final static ValidatorSet USERNAME_VALIDATOR = new ValidatorSet(
      new UniquenessValidator("username", User.class, "getUserUsername")
      );
  
  public UserHandler(Application app)
  {
    super(app, "UsHa");
  }
  
  @Path("")
  @Get
  public boolean getUser()
  {
    return json(((User)app().getSecurity().getUser(context())).view());
  }
  
  @Path("updateUsername")
  @Put
  public boolean updateUsername()
  {
    final Input input = USERNAME_VALIDATOR.process(context());;
    
    if(input.passed())
    {
      user().setUserUsername(query().get("username"));
      
      store().put(user());
      
      return json();
    }
    
    return json(input);
  }
  
  @Path("updatePassword")
  @Put
  public boolean updatePassword()
  {    
    final Input input = new ValidatorSet(
        new RequiredValidator("newPassword"),
        new RequiredValidator("currentPassword"),
        new RequiredValidator("repeatPassword"),
        new EqualsValidator("newPassword", "New Password", "repeatPassword", "Repeat Password"),
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
  
  private class EqualsValidator extends ElementValidator
  {
    private final String displayName;
    private final String otherElementName;
    private final String otherDisplayName;
    
    public EqualsValidator(String elementName, String displayName, 
        String otherElementName, String otherDisplayName)
    {
      super(elementName);
      
      this.displayName = displayName;
      this.otherElementName = otherElementName;
      this.otherDisplayName = otherDisplayName;
    }
    
    @Override
    public void process(Input input)
    {
      final String value1 = getUserValue(input);
      final String value2 = input.values().get(this.otherElementName, "");
      
      if(!value1.equals(value2))
      {
        input.addError(getElementName(),
            this.displayName +
            " and " + 
            this.otherDisplayName +
            " must match.");
      }
    }
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