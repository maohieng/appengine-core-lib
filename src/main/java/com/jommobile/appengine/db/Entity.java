package com.jommobile.appengine.db;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;

import java.util.Date;

public interface Entity {

    public void initForDB();

    public Date getCreatedDate();

    public Date getModifiedDate();

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public String getCreatedBy();

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public String getModifiedBy();

    public boolean isActive();

    public boolean isDeleted();

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Flavor getEntityFlavor();

}
