package com.university.erp.service;

import com.university.erp.model.Admin;
import com.university.erp.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserService userService;

    public void save(Admin admin){
        userService.save(admin.getUser());
        adminRepository.save(admin);
    }
}
