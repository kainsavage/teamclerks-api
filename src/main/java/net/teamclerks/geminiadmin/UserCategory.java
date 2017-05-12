package net.teamclerks.geminiadmin;

import com.techempower.gemini.*;
import com.techempower.gemini.form.*;
import com.techempower.gemini.pyxis.*;

import net.teamclerks.entities.*;

/**
 * Implementation of the UserCategory of administrative functionality.
 */
public class UserCategory
        extends com.techempower.gemini.admin.standard.users.UserCategory
{
  
  //
  // Member methods
  //

  /**
   * Apply a submitted and validated form's edits to a User object.
   */
  @Override
  public boolean updateUser(BasicWebUser bWuser, Form form, Context context)
  {
    boolean success = super.updateUser(bWuser, form, context);
    if (success) {
      // Set custom fields of the user from the provided Form object.
      // Note that the Form object is already assumed to be validated.

      //User user = (User)bWuser;
      //user.setFavoriteColor(form.getStringValue("favoritecolor"));
    }

    return success;
  }

  /**
   * Save a just-edited User object.
   */
  @Override
  public boolean saveUser(GeminiApplication app, BasicWebUser user, 
      Form form, Context context)
  {
    boolean success = super.saveUser(app, user, form, context);
    if (success) {
      // Do any custom processing after the update of the data entity.

      // Example: Save the public key to a separate table.
      //String publicKey = form.getStringValue("public-key");
        //((User)user).setPublicKey(publicKey);
    }

    return success;
  }

  /**
   * Creates the Form for adding and editing users.
   */
  @Override
  protected Form buildAddEditUserForm(GeminiApplication app, 
      Context context, BasicWebUser bWuser)
  {
    User user = (User) bWuser;

    Form form = super.buildAddEditUserForm(app, context, user);

    return form;
  }

}
