package model;

/**
 * Defines operations related to group management.
 * Classes implementing this interface can perform operations on group members.
 */
public interface IGroupAdminActions {

    /**
     * Adds the specified user to the specified group.
     *
     * @param user  User to be added
     * @param group Group reference
     */
    void addUserToGroup(AbstractUser user, GroupChat group);

    /**
     * Removes the specified user from the specified group.
     *
     * @param user  User to be removed
     * @param group Group reference
     */
    void removeUserFromGroup(AbstractUser user, GroupChat group);

    /**
     * Updates the group name.
     *
     * @param group   Group reference
     * @param newName New group name
     */
    void renameGroup(GroupChat group, String newName);
}
