package ru.alexandrstal.masterslave.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class RestClientService {

    private static Logger logger = LoggerFactory.getLogger(RestClientService.class);

    private final static String MASTER = "/master";
    private final static String MESSAGE = "/message";

    @Value("#{'${app.clients}'.split(',')}")
    private List<String> clients;
    private RestTemplate restTemplate = new RestTemplate();

    public void sendMaster(String masterId) {
        logger.info("Начало рассылки сообщения о том что мастер стал АРМ {}", masterId);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.put("id", Collections.singletonList(masterId));
        clients.forEach(url -> send(url + MASTER, params));
        logger.info("Конец рассылки о том что мастером стал АРМ {}", masterId);
    }

    public void sendMsg(String msg, String id) {
        logger.info("Начало рассылки сообщения {} c АРМ {}",msg, id);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.put("msg", Collections.singletonList(msg));
        params.put("id", Collections.singletonList(id));
        clients.forEach(url -> send(url + MESSAGE, params));
        logger.info("Конец рассылки сообщения {} c АРМ {}",msg, id);
    }

    private String send(String url, MultiValueMap<String, String> map) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParams(map);
        String urlString = builder.toUriString();
        try {

            HttpEntity<String> response = restTemplate.exchange(
                    urlString,
                    HttpMethod.GET,
                    entity,
                    String.class);
            logger.info("Ответ на запрос {} : {}", urlString, response.getBody());
            return response.getBody();
        } catch (Exception e) {
            logger.error("Ошибка отправки запроса {}", urlString);
            return e.getMessage();
        }

    }
}
