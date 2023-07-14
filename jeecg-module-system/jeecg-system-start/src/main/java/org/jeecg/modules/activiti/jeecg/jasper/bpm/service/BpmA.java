package org.jeecg.modules.activiti.jeecg.jasper.bpm.service;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.jeecg.modules.activiti.jeecg.jasper.extbpm.ExtbpmA;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController("stencilsetRestResource")
@RequestMapping({"service"})
public class BpmA {
    private final String a = ExtbpmA.f("stencilset.json");

    public BpmA() {
    }

    @RequestMapping(
            value = {"/editor/stencilset"},
            method = {RequestMethod.GET},
            produces = {"application/json;charset=utf-8"}
    )
    @ResponseBody
    public String getStencilset() {
        InputStream var1 = this.getClass().getClassLoader().getResourceAsStream(this.a);

        try {
            return IOUtils.toString(var1, "utf-8");
        } catch (Exception var3) {
            throw new ActivitiException("Error while loading stencil set", var3);
        }
    }
}
