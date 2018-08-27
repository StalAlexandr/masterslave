package ru.alexandrstal.masterslave.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alexandrstal.masterslave.model.MasterSlaveModel;
import ru.alexandrstal.masterslave.service.RestClientService;
import ru.alexandrstal.masterslave.view.AppView;

@Component
public class AppViewController {

    @Autowired
    MasterSlaveModel model;

    @Autowired
    AppView view;

    @Autowired
    RestClientService clientService;

    public void setMaster() {
        model.setMaster(true);
        model.setMasterId(model.getId());
        clientService.sendMaster(model.getId());
        view.render();
    }

    public void sendMessage(String msg) {
        model.setMessage(msg);
        clientService.sendMsg(msg, model.getId());
        view.render();
    }

}
