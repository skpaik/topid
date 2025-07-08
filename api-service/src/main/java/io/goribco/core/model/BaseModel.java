package io.goribco.core.model;

import io.goribco.apis.helper.DateTimeHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseModel {
    @Id
    private String id;

    //    @Null
//    private UserData createdBy;
    private String ownerId;
    private String createdById;

    private Date createdAt;
    private long createdAtMoment;
    private String createdAtStr;


    //    @Null
//    private UserData updatedBy;
    private String updatedById;


    private Date updatedAt;
    private long updatedAtMoment;
    private String updatedAtStr;


    private boolean isPublished;

    //    @Null
//    private UserData publishedBy;
    private String publishedById;

    private Date publishedAt;
    private long publishedAtMoment;
    private String publishedAtStr;

    private boolean enabled;
    private boolean isLocked;//By Super Admin, User cannot change it;
    private String disabledReason;

    public Date getCreatedAt() {
        if (createdAtMoment == 0) return null;
        return DateTimeHelper.convertToDate(createdAtMoment);
    }

    public String getCreatedAtStr() {
        if (createdAtMoment == 0) return null;
        return DateTimeHelper.convertToDateStr(createdAtMoment);
    }

    public Date getUpdatedAt() {
        if (updatedAtMoment == 0) return null;
        return DateTimeHelper.convertToDate(updatedAtMoment);
    }

    public String getUpdatedAtStr() {
        if (updatedAtMoment == 0) return null;
        return DateTimeHelper.convertToDateStr(updatedAtMoment);
    }

    public Date getPublishedAt() {
        if (publishedAtMoment == 0) return null;
        return DateTimeHelper.convertToDate(publishedAtMoment);
    }

    public String getPublishedAtStr() {
        if (publishedAtMoment == 0) return null;
        return DateTimeHelper.convertToDateStr(publishedAtMoment);
    }
}
