package com.github.friday.sys.domain.dto;

import com.github.friday.sys.domain.entity.Permission;

import java.util.List;

public class PermissionDTO extends Permission {
    List<PermissionDTO> childrenList;

    public List<PermissionDTO> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<PermissionDTO> childrenList) {
        this.childrenList = childrenList;
    }
}
