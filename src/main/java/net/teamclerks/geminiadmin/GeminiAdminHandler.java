package net.teamclerks.geminiadmin;

import com.techempower.gemini.*;
import com.techempower.gemini.admin.*;
import com.techempower.gemini.admin.standard.cache.*;
import com.techempower.gemini.admin.standard.config.*;
import com.techempower.gemini.admin.standard.email.*;
import com.techempower.gemini.admin.standard.monitor.*;
import com.techempower.gemini.admin.standard.system.*;

import net.teamclerks.entities.*;

/**
 * GeminiAdminHandler is the implementation of Gemini's basic admin
 * handler.
 */
public class GeminiAdminHandler
    extends BasicAdminHandler<Context>
{

  // An example of custom user list columns:
  /*
   * ReflectiveListColumn[] CUSTOM_USER_LIST_COLUMNS =
   * ReflectiveListColumn.constructArray( new String[] { "getFavoriteColor",
   * "Favorite Color", "--" } );
   */

  //
  // Constructor
  //
  
  public GeminiAdminHandler(GeminiApplication application)
  {
    super(application, "GeminiAdmin.", User.class, Group.class);

    // User management.
    addFunctionCategory(new UserCategory());

    // Email.
    addFunctionCategory(new EmailCategory());

    // Configuration.
    addFunctionCategory(new ConfigurationCategory());

    // Monitoring.
    addFunctionCategory(new MonitorCategory());

    // Cache relations.
    addFunctionCategory(new CacheCategory());

    // System management.
    addFunctionCategory(new SystemCategory());

    application.getConfigurator().addConfigurable(this);
  }

}
