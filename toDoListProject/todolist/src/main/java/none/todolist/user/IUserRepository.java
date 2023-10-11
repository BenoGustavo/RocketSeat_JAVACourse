package none.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

// This interface will be used to create a repository for the user model
public interface IUserRepository extends JpaRepository<UserModel, UUID> {

    // This method will be used to find a user by username
    UserModel findByUsername(String username);
}
