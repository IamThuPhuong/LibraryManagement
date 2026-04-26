package main.service;

import main.enums.Permission;
import main.enums.UserRole;
import main.info.user.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AuthorService {

    public Map<UserRole, Set<Permission>> mapRolePermission(UserRole role, Set<Permission> permission) {
        Map<UserRole, Set<Permission>> mapRolePermission = new HashMap<>();
        mapRolePermission.put(role, permission);
        return mapRolePermission;
    }

    public boolean checkPermission(User user, Permission permission) throws NullPointerException {
        if (user == null)     {
            throw new NullPointerException("Bạn chưa đăng nhập!");
        }
        if (user.getUserRole() == UserRole.ADMIN) return true;

        Set<Permission> permissions = getPermissionsByRole(user.getUserRole());

        return permissions.contains(permission);
    }

    private Set<Permission> getPermissionsByRole(UserRole userRole) {
        Set<Permission> permission = new HashSet<>();

        permission.add(Permission.DELETE_BOOK);
        permission.add(Permission.ADD_BOOK);
        permission.add(Permission.MANAGE_USER);
        permission.add(Permission.READ_BOOK);

        if (userRole == UserRole.MANAGER) {
            System.out.println("Manager có tất cả quyền");
        }

        if (userRole == UserRole.OFFICER) {
            System.out.println("Officer có quyền thêm và xóa sách");
            permission.remove(Permission.MANAGE_USER);
        }
        if (userRole == UserRole.READER) {
            permission.remove(Permission.DELETE_BOOK);
            permission.remove(Permission.ADD_BOOK);
            permission.remove(Permission.MANAGE_USER);
            System.out.println("Reader chỉ có quyền đọc sách");
        }

        return permission;
    }
}



