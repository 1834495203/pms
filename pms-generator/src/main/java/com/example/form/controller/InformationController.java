package com.example.form.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.form.model.po.service.InformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GLaDOS
 */
@Slf4j
@RestController
@RequestMapping("information")
public class InformationController {

    @Autowired
    private InformationService  informationService;
}
