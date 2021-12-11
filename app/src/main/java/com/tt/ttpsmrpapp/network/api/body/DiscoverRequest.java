package com.tt.ttpsmrpapp.network.api.body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscoverRequest {
    @SerializedName("parentId")
    @Expose
    private String parentId;
    @SerializedName("childrenId")
    @Expose
    private String childrenId;
    @SerializedName("instanceId")
    @Expose
    private String instanceId;

    public DiscoverRequest(String parentId, String childrenId, String instanceId) {
        this.parentId = parentId;
        this.childrenId = childrenId;
        this.instanceId = instanceId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(String childrenId) {
        this.childrenId = childrenId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}
