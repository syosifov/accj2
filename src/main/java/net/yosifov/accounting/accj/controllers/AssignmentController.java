package net.yosifov.accounting.accj.controllers;

import net.yosifov.accounting.accj.utils.AssignmentData;
import net.yosifov.accounting.accj.utils.Business;
import net.yosifov.accounting.accj.utils.ReverseAssignRec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AssignmentController {

    static final Logger logger = LoggerFactory.getLogger(AssignmentController.class);

    @Autowired
    private Business business;

    @PostMapping("/jpa/v1/assign")
    public void assign(@RequestBody AssignmentData assignmentData) throws Exception {
        System.out.println(assignmentData);
        business.multyAssign(assignmentData, null);
    }

    @PostMapping("/jpa/v1/reverse")
    public void reverseAssign(@RequestBody ReverseAssignRec reverseAssignRec) {
        System.out.println(reverseAssignRec);
        business.reverseAssign(reverseAssignRec);
    }
}

