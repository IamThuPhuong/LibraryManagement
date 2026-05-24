package main.service;

import main.enums.Permission;
import main.enums.UserRole;
import main.entity.User;

import java.util.HashSet;
import java.util.Set;


/**
 * 1.6 Phân quyền người dùng (quản lý hoặc nhân viên)
 *
 * @author Thu Phương
 * @version 1.0
 * @since 2026-04-23
 */
public class AuthorService {

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

        permission.add(Permission.COMMON);
        permission.add(Permission.MANAGE_BOOK);
        permission.add(Permission.MANAGE_USER);
        permission.add(Permission.DELETE_READER);
        permission.add(Permission.AUTHORIZE_USER);
        permission.add(Permission.STATISTIC);

        //1.1, 1.2, 1.3, 1.4
        //2.xxx
        //3.xxx
        //4.xxx
        //5.xxx
        //6.xxx
        if (userRole == UserRole.MANAGER) {
            System.out.println("Manager có tất cả quyền.");
            // 1.6 Phân quyền người dùng (quản lý hoặc nhân viên)
            permission.remove(Permission.AUTHORIZE_USER);
        }

        //1.1, 1.2, 1.3, 1.4
        //2.1, 2.2, 2.3, 2.5, 2.6
        //3.5, 3.6
        //4.xxx
        //5.xxx
        //6.5, 6.6
        if (userRole == UserRole.OFFICER) {
            System.out.println("Officer có quyền thêm và xóa sách");
            // 2.4 Xóa thông tin độc giả
            permission.remove(Permission.DELETE_READER);
            // Các chức năng quản lý sách (thêm, sửa, xóa sách)
            permission.remove(Permission.MANAGE_BOOK);
            // Các chức năng thống kê (6.1, 6.2, 6.3, 6.4)
            permission.remove(Permission.STATISTIC);
        }
        return permission;
    }
}



