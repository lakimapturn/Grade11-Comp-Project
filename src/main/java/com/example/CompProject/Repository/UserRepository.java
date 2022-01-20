package com.example.CompProject.Repository;

import com.example.CompProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User getByFullname(String Fullname);
//    @Query("select u from User u " +
//            "where lower(u.getTrain().getStringId()) like lower(concat('%', :trainId, '%')) ")
//    List<User> search(@Param("trainId") String trainId);
}
