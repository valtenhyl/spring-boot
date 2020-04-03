package com.valten.service;

import org.springframework.stereotype.Service;

public interface DynamicDataSourceService {

    void updateUser(Long id);

    void updateUserBySlave1(Long id);

    void updateUserBySlave2(Long id);
}
