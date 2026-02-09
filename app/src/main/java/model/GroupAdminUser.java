package model;

/**
 * User type representing the group administrator role.
 * Implements IGroupAdminActions interface for group management operations.
 */
public class GroupAdminUser extends User implements IGroupAdminActions, INotifiable {

    public GroupAdminUser(int userId, String username, String email, String password) {
        super(userId, username, email, password);
    }

    public GroupAdminUser(int userId, String username, String email, String password, String bio, boolean isActive) {
        super(userId, username, email, password, bio, isActive);
    }

    @Override
    public void addUserToGroup(AbstractUser user, GroupChat group) {
        group.addMember(user);
        System.out.println(user.getUsername() + " added to group: " + group.getGroupName());
    }

    @Override
    public void removeUserFromGroup(AbstractUser user, GroupChat group) {
        group.removeMember(user);
        System.out.println(user.getUsername() + " removed from group: " + group.getGroupName());
    }

    @Override
    public void renameGroup(GroupChat group, String newName) {
        group.setGroupName(newName);
        System.out.println("Group name updated to '" + newName + "'.");
    }

    @Override
    public void notifyUser(String title, String body) {
        System.out.println("Notification - " + title + ": " + body + " (user: " + getUsername() + ")");
    }

}
