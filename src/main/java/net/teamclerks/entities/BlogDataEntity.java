/**
 * Copyright (c) 2016
 * Teamclerks
 * All Rights Reserved.
 *
 * Blog
 */

package net.teamclerks.entities;

import com.fasterxml.jackson.annotation.*;
import com.techempower.util.*;

/**
 * The base class for all data entities in Blog.  This class provides
 * the implementation of the Identifiable interface expected of all entities,
 * cached or otherwise.
 *
 * @author kain
 */
public abstract class BlogDataEntity
        implements Identifiable {
    /**
     * The identity for this object.
     */
    private long id;

    @JsonProperty("id")
    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long newIdentity) {
        this.id = newIdentity;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + getId() + "]";
    }

}   // End BlogDataEntity.
