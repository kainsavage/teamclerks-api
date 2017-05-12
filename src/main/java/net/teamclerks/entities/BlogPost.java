package net.teamclerks.entities;

import java.util.*;

import com.fasterxml.jackson.annotation.*;
import com.techempower.data.annotation.*;

@CachedEntity
public class BlogPost extends BlogDataEntity
{
  private Date    created;
  private Date    updated;
  private String  content;
  private String  title;
  private boolean deleted;
  
  @JsonProperty("created")
  public Date getCreated()
  {
    return created;
  }
  public void setCreated(Date created)
  {
    this.created = created;
  }
  @JsonProperty("updated")
  public Date getUpdated()
  {
    return updated;
  }
  public void setUpdated(Date updated)
  {
    this.updated = updated;
  }
  @JsonProperty("content")
  public String getContent()
  {
    return content;
  }
  public void setContent(String content)
  {
    this.content = content;
  }
  @JsonProperty("title")
  public String getTitle()
  {
    return title;
  }
  public void setTitle(String title)
  {
    this.title = title;
  }
  public boolean isDeleted()
  {
    return deleted;
  }
  public void setDeleted(boolean deleted)
  {
    this.deleted = deleted;
  }
  
  public final Map<String,Object> metadata()
  {
    final Map<String,Object> data = new HashMap<>();
    data.put("id", this.getId());
    data.put("created", this.getCreated());
    data.put("updated", this.getUpdated());
    data.put("title", this.getTitle());
    
    return data;
  }
}
