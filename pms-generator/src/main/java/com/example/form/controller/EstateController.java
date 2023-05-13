package com.example.form.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.form.model.po.service.EstateService;
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
@RequestMapping("estate")
public class EstateController {

    @Autowired
    private EstateService  estateService;
}
