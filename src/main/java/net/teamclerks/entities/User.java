package net.teamclerks.entities;

import com.fasterxml.jackson.annotation.*;
import com.techempower.data.annotation.*;
import com.techempower.gemini.pyxis.*;

import net.teamclerks.*;


/**
 * Represents a user of the application. Basic attributes
 * such as username, e-mail address, and password are inherited from
 * BasicWebUser.
 */
@CachedEntity
public class User
     extends BasicWebUser
{

    //
    // Member methods.
    //

    /**
     * Constructor.
     */
    public User() {
        this(Application.getInstance().getSecurity());
    }

    public User(Security security) {
        super(security);
    }

    public UserView view()
    {
      return new UserView(); 
    }
    
    public class UserView
    {
      @JsonProperty
      public long id()
      {
        return getId();
      }
      @JsonProperty
      public String name()
      {
        return getUserFirstname() + " " + getUserLastname();
      }
    }
    
}
