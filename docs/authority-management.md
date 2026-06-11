# Permission Management

Admin3 implements permission control based on the RBAC model. RBAC is Role-Based Access Control. In RBAC, permissions are associated with roles, and users obtain the permissions of these roles by becoming members of appropriate roles. This greatly simplifies permission management. This management is hierarchical and interdependent: permissions are granted to roles, and roles are granted to users, making the permission design clear and easy to manage.

## Configuration

1. Add resources in the `System Management > Permission Resources` page
  
   > Resources are divided into menu resources and button resources. Menu resources need to specify a menu icon and menu address. The menu address needs to be registered in vue-router. Button resources require the frontend to judge the display/hide of buttons based on directive permissions.

    ![](image/resource-list.png)

2. In `System Management > Role Management > Permission Settings`, select the role to configure. In the `Permission Settings` tab, select the corresponding permissions; in the `Role Members` tab, adjust role members.

   ![](image/role-auth2.png)
   ![](image/role-user.png)

3. Refresh the page to see the newly added menu items.

  ![](image/test-menu-resource.png)

## Permission Strings

Permission identifiers support the same wildcard permission string rules as Apache Shiro. The wildcard permission string rules are:
```
Resource Identifier:Action:Object Instance ID
```

That is, what operation can be performed on which instance of which resource. By default, wildcard permission strings are supported.
\":\" indicates the separation of resource/action/instance; \",\" indicates the separation of actions; \"*\" indicates any resource/action/instance.

Handling of missing parts in permission strings:

For example, \"user:view\" is equivalent to \"user:view:\"; while \"organization\" is equivalent to \"organization:\" or \"organization::\". This can be understood as prefix matching.
Also, \"user:\" can match \"user:delete\", \"user:delete\" can match \"user:delete:1\", \"user::1\" can match \"user:view:1\", \"user\" can match \"user:view\" or \"user:view:1\", etc. That is, omitting can match all; omitting can perform prefix matching; but \":view\" cannot match \"system:user:view\", you need to use \"::view\", that is, suffix matching must specify a prefix (multiple colons require multiple to match).

## URL Permission Interception Filtering

Interface permission filtering can be done through the backend @RequiresPermissions annotation, as shown in the following example:
```
@RequiresPermissions("user:update")
@PutMapping("/{userId}")
public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody @Valid UpdateUserRequest request) {
  Organization organization = organizationService.findOrganization(request.organizationId());
  return ResponseEntity.ok(userService.updateUser(userId, request.avatar(), request.gender(), User.State.NORMAL, organization));
}
```

@RequiresPermissions Parameter Description

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermissions {

  /**
  * Can specify one or more permission strings
  * 
  */
  String[] value();

  /**
  * The relationship between multiple permission strings, supports AND (all match) \ OR (any match), default is AND.
  *
  */
  Logical logical() default Logical.AND;
}
```

## Frontend Directive Permissions

Admin3 encapsulates a very convenient custom directive for implementing button-level permissions: v-action.

Usage

```html
<template>
    <!-- Check if user has user:create operation permission -->
    <el-button v-action:user:create>Create User</el-button>
  
    <!-- Check if user has user:delete operation permission -->
    <el-button v-action:user:delete>Delete User</el-button>
  
    <!-- Check if user has user:update operation permission -->
    <el-button v-action:user:update>Update User</el-button>
</template>
```

Implementation Idea:

When Vue is initialized, the action command is registered on the Vue prototype chain (src/main.ts). In the Vue instance, the user's permissions are retrieved from the store for permission judgment.
