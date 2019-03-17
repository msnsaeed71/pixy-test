package com.ham.server.service;

import com.ham.server.persistence.entity.SessionPixy;
import com.ham.server.persistence.entity.SessionPixyInstructions;
import com.ham.server.persistence.repository.SessionPixyInstructionsRepository;
import com.ham.server.persistence.repository.SessionPixyRepository;
import com.ham.server.rest.dto.Entrance.SessionPixyDTO;
import com.ham.server.rest.dto.response.OperationResponse;
import com.ham.server.rest.dto.response.PixyException;
import com.ham.server.rest.dto.result.SessionPixyInstructionRepeatDTO;
import com.ham.server.rest.dto.result.SessionPixyResultDto;
import com.ham.server.rest.dto.result.SinglePixyInstructionsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SessionPixyService {

    @Autowired
    SessionPixyRepository sessionPixyRepository;

    @Autowired
    SessionPixyInstructionsRepository sessionPixyInstructionsRepository;

    /*save pixy session json instructions and repeat in database by post method*/
    @Transactional
    public OperationResponse createSessionPixy(SessionPixyDTO sessionPixyDTO) throws PixyException {
        /* iterate instructions and count each instruction and save in map*/
        Map<String , Integer> countKeyMap = new HashMap<>();
        //Set<String> keys = sessionPixyDTO.getSessionKeyValueList().stream().map(SessionKeyValueDTO::getKey).collect(Collectors.toSet());
        sessionPixyDTO.getSessionKeyValueList().forEach(sessionKeyValueDTO -> {
            Integer count = countKeyMap.get(sessionKeyValueDTO.getKey());
            if(count == null)
                countKeyMap.put(sessionKeyValueDTO.getKey(),1);
            else
                countKeyMap.put(sessionKeyValueDTO.getKey(),++count);
        });
        /*get the type of session id -> because sessionId of server and client is same they identified by type in data base*/
        String type;
        if (sessionPixyDTO.getIsClient() != null && sessionPixyDTO.getIsClient())
            type = SessionPixy.TYPE_CLIENT;
        else
            type = SessionPixy.TYPE_SERVER;
        //UUID.randomUUID().toString()
        /*save session id and type in db if not exist (we can throw exception if key is duplicate or we
        can consider update if needed or can creat put method separately for update method)*/
        final SessionPixy sessionPixy;
        if (sessionPixyRepository.findBySessionIdAndType(sessionPixyDTO.getSessionId(),type) == null)
            sessionPixy = sessionPixyRepository.saveAndFlush(new SessionPixy(sessionPixyDTO.getSessionId(),type));
        else
            throw new PixyException(OperationResponse.ResponseStatusEnum.BAD_REQUEST, "شناسه تکراری. از طریق put اقدام شود. البته این روش هنوز پیاده سازی نشده است");
        /* save the count of repeat for each instruction session pixy*/
        sessionPixyInstructionsRepository.save(countKeyMap.entrySet().stream().map(stringIntegerEntry -> new SessionPixyInstructions(stringIntegerEntry.getKey(),stringIntegerEntry.getValue(),sessionPixy)).collect(Collectors.toList()));
        /*return message that saved successfully*/
        return new OperationResponse(OperationResponse.ResponseStatusEnum.SUCCESS, "داده ها با موفقیت ذخیره گردید");
    }

    /*get all pixy session ids from db*/
    @Transactional(readOnly = true)
    public Page<SessionPixyResultDto> getSessionPixys(Pageable pageable) {
        /*find all session pixy in pagination format*/
        Page<SessionPixy> sessionPixyPage = sessionPixyRepository.findAll(pageable);
        /*return all needed response of session pixy ids in pagination format*/
        return new PageImpl(sessionPixyPage.getContent().stream().map(SessionPixyResultDto::new).collect(Collectors.toList()), pageable, sessionPixyPage.getTotalElements());
    }
    /*get single pixy session instructions and number of repeat of each instruction for client and server session from db*/
    @Transactional(readOnly = true)
    public SinglePixyInstructionsDTO getSessionPixyById(String sessionId, Boolean justClient, Boolean justServer) throws PixyException{
        if (justClient != null && justClient && (justServer == null || !justServer)) {  /*if justClient send true -> just session instruction and number of the repeat of it for client type of this session id comes to result*/
            SinglePixyInstructionsDTO singlePixyInstructionsDTO = new SinglePixyInstructionsDTO();
            singlePixyInstructionsDTO.setPixyInstructionRepeatDTOSClient(getPixyRepeatDtoByIDAndType(sessionId, SessionPixy.TYPE_CLIENT));
        } else if (justServer != null && justServer && (justClient == null || !justClient)) { /*else if justServer send true -> just session instruction and number of the repeat of it for server type of this session id comes to result*/
            SinglePixyInstructionsDTO singlePixyInstructionsDTO = new SinglePixyInstructionsDTO();
            singlePixyInstructionsDTO.setPixyInstructionRepeatDTOSClient(getPixyRepeatDtoByIDAndType(sessionId, SessionPixy.TYPE_SERVER));
            return singlePixyInstructionsDTO;
        }
        /*if session pixy by this session id not found throw exception*/

        /*else if justServer send false and justClient false or dont sent ->  session instruction and number of the repeat of it for all type of this session id comes to result*/
        SinglePixyInstructionsDTO singlePixyInstructionsDTO = new SinglePixyInstructionsDTO(new ArrayList<>(), new ArrayList<>());
        List<SessionPixyInstructions> sessionPixyInstructionRepeatDTOS = sessionPixyInstructionsRepository.findAllBySessionPixyNotNullAndSessionPixy_SessionId(sessionId);

        /*all session proxy instruction add in server and client list instructionRepeat separately for response*/
        sessionPixyInstructionRepeatDTOS.forEach(sessionPixyInstructions -> {
            if(sessionPixyInstructions.getSessionPixy().getType().equals(SessionPixy.TYPE_CLIENT))
                singlePixyInstructionsDTO.addToPixyInstructionRepeatDTOSClient(new SessionPixyInstructionRepeatDTO(sessionPixyInstructions));
            else
                singlePixyInstructionsDTO.addToPixyInstructionRepeatDTOSServer(new SessionPixyInstructionRepeatDTO(sessionPixyInstructions));

        });
        return singlePixyInstructionsDTO;
    }

    private List<SessionPixyInstructionRepeatDTO> getPixyRepeatDtoByIDAndType(String sessionId, String type) throws PixyException {
        /*if session pixy by this session id and type not found throw exception*/
        return sessionPixyInstructionsRepository.findAllBySessionPixyNotNullAndSessionPixy_SessionIdAndSessionPixy_Type(sessionId, type).stream().map(SessionPixyInstructionRepeatDTO::new).collect(Collectors.toList());
    }
}
