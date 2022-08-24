package com.joseph.fortress.security;

import com.google.common.collect.Sets;

import java.util.Set;

public enum ApplicationUserRole {

    STUDENT(Sets.newHashSet()), // Student has NO permissions, hence the EMPTY set

    ADMIN(Sets.newHashSet(
            ApplicationUserPermission.COURSE_READ,
            ApplicationUserPermission.COURSE_WRITE,
            ApplicationUserPermission.STUDENT_READ,
            ApplicationUserPermission.STUDENT_WRITE));

    // Using a Set for UNIQUE values to define permissions
    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }
}
