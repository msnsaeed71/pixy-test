package com.ham.server.rest;

import com.ham.server.config.Constants;
import com.ham.server.rest.dto.Entrance.SessionPixyDTO;
import com.ham.server.rest.dto.response.PixyException;
import com.ham.server.service.SessionPixyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class SessionPixyResource {

    private final Logger log = LoggerFactory.getLogger(SessionPixyResource.class);

    @Autowired
    private SessionPixyService sessionPixyService;

    /*save pixy session json instructions and repeat in database by post method*/
    @RequestMapping(value = {"/session"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> createSessionPixy(@Valid @RequestBody SessionPixyDTO sessionPixyDTO) throws PixyException {
        return new ResponseEntity<>(sessionPixyService.createSessionPixy(sessionPixyDTO), HttpStatus.OK);
    }

    /*get all pixy session ids from db*/
    @RequestMapping(value = {"/sessions"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> getSessionPixys(@PageableDefault(size = Constants.PAGE_SIZE) Pageable page) throws PixyException {
        return new ResponseEntity<>(sessionPixyService.getSessionPixys(page), HttpStatus.OK);
    }

    /*get single pixy session instructions and number of repeat of each instruction for client and server session from db*/
    @RequestMapping(value = {"/session/{sessionId}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    /*if justClient send true -> just session instruction and number of the repeat of it for client type of this session id comes to result*/
    /*else if justServer send true -> just session instruction and number of the repeat of it for server type of this session id comes to result*/
    /*else if justServer send false and justClient false or dont sent ->  session instruction and number of the repeat of it for all type of this session id comes to result*/
    public ResponseEntity<?> getSessionPixys(@PathVariable String sessionId, Boolean justClient, Boolean justServer) throws PixyException {
        return new ResponseEntity<>(sessionPixyService.getSessionPixyById(sessionId, justClient, justServer), HttpStatus.OK);
    }
}
