package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.Parent;
import com.example.hostel_management.Service.ParentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parent")
public class ParentController {
    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }


    public Parent saveParent(@RequestBody Parent parent){
        return parentService.saveParent(parent);
    }

    @GetMapping(value = "/{id}")
    public Parent getParentById(@PathVariable Long id){
        return parentService.getParentById(id);
    }

    @GetMapping(value = "")
    public List<Parent> getAllParents(){
        return parentService.getAllParents();
    }

}
