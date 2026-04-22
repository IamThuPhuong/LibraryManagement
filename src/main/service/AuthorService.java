package main.service;

import main.enums.UserPermission;
import main.enums.UserRole;
import main.info.user.User;

import javax.management.relation.Role;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AuthorService {
    public void determineRolesAndPermission(){

        // Set quyen cho admin
        Set<UserPermission> adminPermission = new HashSet<>();
        adminPermission.add(UserPermission.DELETEBOOK);
        adminPermission.add(UserPermission.ADDBOOK);
        adminPermission.add(UserPermission.MANAGEUSER);
        adminPermission.add(UserPermission.READBOOK);

        mapRolePermission(UserRole.ADMIN,adminPermission);

        // Set quyen cho Manager
        Set<UserPermission> managerPermission = new HashSet<>();
        managerPermission.add(UserPermission.MANAGEUSER);
        managerPermission.add(UserPermission.READBOOK);
        managerPermission.add(UserPermission.DELETEBOOK);
        managerPermission.add(UserPermission.ADDBOOK);

        mapRolePermission(UserRole.MANAGER, managerPermission);

        // Set quyen cho Officer
        Set<UserPermission> officerPermission = new HashSet<>();
        officerPermission.add(UserPermission.READBOOK);
        officerPermission.add(UserPermission.ADDBOOK);
        officerPermission.add(UserPermission.DELETEBOOK);

        mapRolePermission(UserRole.OFFICER, officerPermission);

        // Set quyen cho Reader
        Set<UserPermission> readerPermission = new HashSet<>();
        readerPermission.add(UserPermission.READBOOK);

        mapRolePermission(UserRole.READER, readerPermission);

    }

    public Map<UserRole, Set<UserPermission>> mapRolePermission(UserRole role, Set<UserPermission> permission){
        Map<UserRole, Set<UserPermission>> mapRolePermission = new HashMap<>();
        mapRolePermission.put(role, permission);
        return mapRolePermission;
    }
}

