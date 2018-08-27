package ru.alexandrstal.masterslave.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.alexandrstal.masterslave.model.MasterSlaveModel;
import ru.alexandrstal.masterslave.view.AppView;

@RestController
public class AppRestController {

    private static Logger logger = LoggerFactory.getLogger(AppRestController.class);

    @Autowired
    MasterSlaveModel model;

    @Autowired
    AppView view;

    @RequestMapping("/message")
    public String getMessage(@RequestParam(value = "msg") String msg, @RequestParam(value = "id", defaultValue = "") String id) {

        logger.info("Получено сообщение {} от АРМ {}", msg, id);
        if (id.equals(model.getMasterId())) {
            model.setMessage(msg);
            view.render();
        }
        return "ok";
    }

    @RequestMapping("/master")
    public String getMaster(@RequestParam(value = "id") String masterId) {
        logger.info("Мастер установлен на АРМ {}", masterId);
        if (!masterId.equals(model.getId())) {
            model.setMaster(false);
        }
        model.setMasterId(masterId);
        view.render();
        return "ok";
    }

}
