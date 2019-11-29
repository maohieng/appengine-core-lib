package com.jommobile.appengine.db;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;

import java.util.Date;

/**
 * Created by MAO Hieng on 10/18/18.
 * <p>
 *
 * </p>
 */
public interface EntityStringId extends StringId {

    public void initForDB();

    public Date getCreatedDate();

    public Date getModifiedDate();

    public String getCreatedBy();

    public String getModifiedBy();

    public boolean isActive();

    public boolean isDeleted();

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Flavor getEntityFlavor();

}
