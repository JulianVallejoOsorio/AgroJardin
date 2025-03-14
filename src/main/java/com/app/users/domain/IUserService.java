package com.app.users.domain;

import java.util.List;

public interface IUserService {
    
    List<User> findAll();

    User findById(Long id);

    User save(User user);

    User update(User user, Long id);

    void deleteById(Long id);
}
