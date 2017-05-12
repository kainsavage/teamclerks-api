---------------------------------------------------------------------------

Contents

  1. What's provided.
  2. Source directory structure.
  3. Building the application.

---------------------------------------------------------------------------
1. WHAT'S PROVIDED

In this automatically generated package, you will find the following
directories:

 - src, which houses the application in its entirety, containing the
   source code to use as a foundation for building your Gemini application.
 - configuration, which contains the Gemini ".conf" files to use as a starting 
   point for your application's configuration. 
 - database, which contains any scripts that may be useful for
   automatically creating tables used by the application.
 - WEB-INF/emails, the folder containing the emails used by the system.
 - WEB-INF/mustache, the folder containing the mustache templates used by
   the system for server-side composition (optional).
 
---------------------------------------------------------------------------
2. SOURCE DIRECTORY STRUCTURE.

Within the src directory (with your package folders to follow), 
you will find the following source files:

 - Application.java

   This is a subclass of GeminiApplication that has been configured for
   your needs. The code is automatically configrued to use the following 
   components: Pyxis-based security, an EntityStore, and a 
   BasicConnectorFactory for database access.
   
 - AppEmailTemplater.java

   This is a subclass of EmailTemplater that has been preconfigured to
   provide the "Forgotten password" e-mail used by the LoginHandler as well
   as the "Password reset" e-mail used by the PasswordResetHandler.
   
 - Server.java

   This is a main class of the application and simply wraps the instantiation
   of the Application.

 - AppVersion.java

   This is a subclass of Version that is preconfigured for the
   application.

 - Security.java

   This is a subclass of PyxisSecurity which provides User
   authentication services (which are used by the LoginHandler).

 - entities/User.java

   This is a simple subclass of Pyxis' BasicWebUser.  This is a data
   entity class in that each instance represents a "row" in a database.

 - entities/Group.java

   This is a simple implementation of Pyxis' PyxisGroup or a user group.
   Like User.java, each instance of this object represents a row in the
   database.
   
 - entities/UserToGroup.java

   This is a the relation of User to Group. 

 - entities/DataEntity.java
   This is an implementation of Identifiable that can be used to 
   provide custom data entity functionality.

 - geminiadmin/GeminiAdminHandler.java

   Starter gemini Administration functionality.

 - geminiadmin/UserCategory.java
 
   Category for the application specific User class.

---------------------------------------------------------------------------
3. BUILDING THE APPLICATION

build.xml, ivy.xml, and ivysettings.xml files are provided by default. You
will need to run the following Ant command:
  - ant resolve
  
This command will

 - Download the dependencies and source jars (if available) for the project
   (if you did not have them in before) into a lib folder

You should then add these jars to the class path in your IDE of choice

---------------------------------------------------------------------------
4. BUILDING THE DATABASE SCHEMA

It is required that you enter values for:

 - Database host: (dbhost)
 - Database username: (dblogin)
 - Database name: (dbname)
 - Database password: (dbpass)

Ensure that these values are correct and that your database and user exist
and that the password for said user is correct.

Currently mysql is the only supported database engine. 

Using your database username and password, connect to your database server
and select your database. The application is expecting that certain tables
exist before it can properly be start up.

It is recommended to use dbmigrate, and if doing so, execute the script
found in changelog.sql.

Next, ensure app.properties has the correct values entered:
 
 Lastly, issue the dbmigrate command:
 - ant dbmigrate

---------------------------------------------------------------------------
5. ENABLE TOKEN AUTHENTICATION, IF DESIRED
  - Set the Security.Cryptograph.AesGcmNoPadding.Enabled variable to true in the
  configuration file for your machine.
  - Download the JCE Unlimited Strength Jurisdiction Policy Files from
  http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
  and copy the files `local_policy.jar` and `US_export_policy.jar` into
  $JDK_HOME/jre/lib/security
  - Add the following Overloaded methods in Security.java:
      @Override
      public TokenAuthenticationArbiter constructAuthenticationArbiter()
      {
        return new TokenAuthenticationArbiter(this.getApplication());
      }

      @Override
      public TokenAuthenticationArbiter getAuthenticationArbiter()
      {
        return (TokenAuthenticationArbiter) super.getAuthenticationArbiter();
      }

---------------------------------------------------------------------------
6. FIRE IT UP

You are free to start developing your webapp, now. Try hitting your default welcome page:
 
 - ant run
 - http://localhost/
 
If this works, then you are good to go. If you get an error, you may need to
update your build.properties file to specify a higher port (like 8080).

---------------------------------------------------------------------------
7. DEPLOYMENT
To generate a .jar file
   - Copy build.properties.template to build.properties and ensure that the physical
     location of your lessc is correct in build.properties
   - Update configuration variable DeploymentDescription to an appropriate value (ie, production or staging)
   - Update the configuration variable LogDirectory to the location you would like log files to be written out to
   - run ant from the directory containing build.xml
   - .jar file will be generated in the /dist folder
   - you can java -jar the file to confirm it's working

Recommended: delete this file at this point.