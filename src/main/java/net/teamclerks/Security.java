package net.teamclerks;

import com.techempower.cache.*;
import com.techempower.gemini.*;
import com.techempower.gemini.pyxis.*;
import com.techempower.gemini.pyxis.authorization.*;

import net.teamclerks.entities.*;

/**
 * Security provides Pyxis-based security services for the
 * application.
 */
public class Security extends BasicSecurity<User,Group>
{
    public final EntityStore store;

    /**
     * Constructor.
     */
    public Security(Application application)
    {
      super(application, User.class, Group.class, UserToGroup.class,
          UserToLogin.class);

      this.store = application.getStore();
    }

    //
    // Member methods.
    //
    
    @Override
    public JsonWebTokenAuthenticationArbiter constructAuthenticationArbiter()
    {
      return new JsonWebTokenAuthenticationArbiter(this.getApplication());
    }
    
    @Override
    public JsonWebTokenAuthenticationArbiter getAuthenticationArbiter()
    {
      return (JsonWebTokenAuthenticationArbiter) super.getAuthenticationArbiter();
    }

    @Override
    public User constructUser() {
        return new User(this);
    }

    @Override
    public Group constructUserGroup() {
        return new Group();
    }

    @Override
    public Group getUserGroup(String name) {
        // Use the EntityStore's ability to find objects by method call.
        return this.store.get(Group.class, "getName", name);
    }

    @Override
    public Group getUserGroup(long identity) {
        return this.store.get(Group.class, identity);
    }
    
    @Override
    public Rejector getForceLoginRejector()
    {
      return new Rejector()
      {
        @Override
        public void reject(Context context, PyxisUser user)
        {
          context.setStatus(401);
        }
      };
    }

}
