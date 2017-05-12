package net.teamclerks;

import com.techempower.*;
import com.techempower.gemini.*;
import com.techempower.gemini.exceptionhandler.*;
import com.techempower.gemini.path.*;
import com.techempower.gemini.pyxis.*;
import com.techempower.gemini.pyxis.handler.*;
import com.techempower.js.*;

import net.teamclerks.handlers.*;

/**
 * Application. As a subclass of GeminiApplication, this class acts as
 * the central "hub" of references to components such as the Dispatcher,
 * Security, and EntityStore.
 */
public class Application
    extends ResinGeminiApplication
{

  //
  // Static variables.
  //

  private static final Application INSTANCE = new Application();

  //
  // Constructor
  //

  /**
   * Constructor. This method can be extended to construct references to
   * custom components for the application.
   */
  public Application()
  {
    super();
  }
  
  //
  // Static methods
  //

  /**
   * Get the single running instance of this application.
   */
  public static Application getInstance()
  {
    return Application.INSTANCE;
  }

  //
  // Public methods
  //
  
  /**
   * Return and application-specific reference to the Security.
   */
  @Override
  public Security getSecurity()
  {
    return (Security)super.getSecurity();
  }
  
  @Override
  public JavaScriptWriter constructJavaScriptWriter()
  {
    return new JacksonJavaScriptWriter();
  }

  //
  // Protected methods.
  //
  
  @Override
  protected Version constructVersion()
  {
    return new AppVersion();
  }

  /**
   * Constructs a Security reference. This is overloaded to return a custom
   * Security.
   */
  @Override
  protected PyxisSecurity constructSecurity()
  {
    return new Security(this);
  }

  /**
   * Constructs a Dispatcher.
   */
  @Override
  protected Dispatcher constructDispatcher()
  {
    final PathDispatcher.Configuration<Context> config =
        new PathDispatcher.Configuration<Context>();

    // Handlers
    config
      .add("login", new LoginHandler<Context>(this))
      .add("logout", new LogoutHandler<Context>(this))
      .add("posts", new PostHandler(this));
    
    // Add ExceptionHandlers
    config
      .add(new LoggingExceptionHandler(this))
      .add(new NotificationExceptionHandler(this));

    return new PathDispatcher<>(this, config);
  }

  /**
   * Constructs an ApplicationEmailTemplater reference.
   */
  @Override
  protected AppEmailTemplater constructEmailTemplater()
  {
    return new AppEmailTemplater(this);
  }

}

