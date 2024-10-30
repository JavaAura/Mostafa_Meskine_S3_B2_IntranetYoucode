package com.example.IntranetYoucode.Repositories;

import com.example.IntranetYoucode.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
