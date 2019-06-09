package cv.toni.pathos.repository;


import cv.toni.pathos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByName(String name);
    User findUserByEmail(String email);
    User findUserById(int id);
    List<User> findUsersByRole_Role(String role);

    User findUserByColaboradorsContains(User u);
}