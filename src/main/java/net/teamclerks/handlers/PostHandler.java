package net.teamclerks.handlers;

import java.util.*;
import java.util.stream.*;

import com.techempower.gemini.*;
import com.techempower.gemini.input.*;
import com.techempower.gemini.input.validator.*;
import com.techempower.gemini.path.annotation.*;
import com.techempower.gemini.pyxis.annotation.*;
import com.techempower.gemini.pyxis.handler.*;

import net.teamclerks.entities.*;

public class PostHandler extends SecureMethodUriHandler<Context,User>
{
  private static final ValidatorSet POST_VALIDATOR = new ValidatorSet(
    new RequiredValidator("content"),
    new RequiredValidator("title")
  );

  public PostHandler(GeminiApplication app)
  {
    super(app, "PoHa");
  }
  
  @Path
  @Get
  @PathBypassAuth
  public boolean listPosts()
  {
    return json(store().list(BlogPost.class).stream()
      .filter(p -> !p.isDeleted()).collect(Collectors.toList()));
  }
  
  @Path("{id}")
  @Get
  @PathBypassAuth
  public boolean getPost(long id)
  {
    final BlogPost post = store().get(BlogPost.class, id);
    
    if (post != null && !post.isDeleted())
    {
      return json(post);
    }
    
    return notFound("Post not found");
  }
  
  @Path("latest")
  @Get()
  @PathBypassAuth
  public boolean getLatest()
  {
    return json(store().list(BlogPost.class).stream()
      .filter(p -> !p.isDeleted())
      .sorted((p1,p2) -> p2.getCreated().compareTo(p1.getCreated()))
      .findFirst()
      .get());
  }
  
  @Path("metadata")
  @Get
  @PathBypassAuth
  public boolean listMetadata()
  {
    return json(store().list(BlogPost.class).stream()
      .filter(p -> !p.isDeleted())
      .sorted((p1,p2) -> p2.getCreated().compareTo(p1.getCreated()))
      .map(p -> p.metadata())
      .collect(Collectors.toList()));
  }

  @Path
  @Post
  public boolean createPost()
  {
    final Input input = POST_VALIDATOR.process(context());
    
    if (input.passed())
    {
      final BlogPost post = new BlogPost();
      post.setCreated(new Date());
      post.setContent(query().get("content"));
      post.setTitle(query().get("title"));
      store().put(post);
      
      return json(post.getId());
    }
    
    return json(input);
  }
  
  @Path("{id}")
  @Put
  public boolean editPost(long id)
  {
    final BlogPost post = store().get(BlogPost.class, id);
    
    if (post != null && !post.isDeleted())
    {
      final Input input = POST_VALIDATOR.process(context());
      
      if (input.passed())
      {
        post.setUpdated(new Date());
        post.setContent(query().get("content"));
        post.setTitle(query().get("title"));
        store().put(post);
        
        return json(post.getId());
      }
      
      return json(input);
    }
    
    return notFound("Post not found");
  }
  
  @Path("{id}")
  @Delete
  public boolean deletePost(long id)
  {
    final BlogPost post = store().get(BlogPost.class, id);
    
    if (post != null)
    {
      post.setDeleted(true);
      store().put(post);
      
      return json("Post deleted");
    }
    
    return notFound("Post not found");
  }
}
