package com.jommobile.appengine.db;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;

import java.util.Date;

/**
 * Created by MAO Hieng on 10/18/18.
 * <p>
 *
 * </p>
 * @deprecated use {@link Entity} and combine with {@link StringId} or {@link LongId}.
 */
@Deprecated
public interface EntityLongId extends LongId {

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
