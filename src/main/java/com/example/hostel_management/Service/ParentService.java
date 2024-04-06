//package com.example.hostel_management.Service;
//
//import com.example.hostel_management.Model.Parent;
//import com.example.hostel_management.Repository.ParentRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ParentService {
//    private final ParentRepository parentRepository;
//
//    public ParentService(ParentRepository parentRepository) {
//        this.parentRepository = parentRepository;
//    }
//
//    public Parent saveParent(Parent parent) {
//        return parentRepository.save(parent);
//    }
//
//    public Parent getParentById(Long id) {
//        return parentRepository.findById(id).orElse(null);
//    }
//
//    public List<Parent> getAllParents() {
//        return parentRepository.findAll();
//    }
//}
