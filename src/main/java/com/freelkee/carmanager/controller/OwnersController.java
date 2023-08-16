package com.freelkee.carmanager.controller;
import com.freelkee.carmanager.entity.Owner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/owners")
public class OwnersController {

    public List<Owner> getOwners(){
        return new ArrayList<>();
    }
}
