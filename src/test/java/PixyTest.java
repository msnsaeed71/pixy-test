import com.google.gson.Gson;
import com.ham.server.PixyApplication;
import com.ham.server.rest.dto.Entrance.SessionKeyValueDTO;
import com.ham.server.rest.dto.Entrance.SessionPixyDTO;
import com.ham.server.rest.dto.response.OperationResponse;
import com.ham.server.rest.dto.response.PixyException;
import com.ham.server.rest.dto.result.SessionPixyResultDto;
import com.ham.server.rest.dto.result.SinglePixyInstructionsDTO;
import com.ham.server.service.SessionPixyService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PixyApplication.class)
@WebAppConfiguration
@Transactional
@ActiveProfiles("test")
public class PixyTest {
    @Autowired
    private SessionPixyService sessionPixyService;

    public static final String SESSION_ID_SAMPLE = "7a41b2bc-ad53-4a92-aa78-31371699e9f8";
    @Test
    @Transactional
    public void createSessionPixy() throws PixyException {
        SessionPixyDTO sessionPixyDTO = new SessionPixyDTO();
        sessionPixyDTO.setSessionId(SESSION_ID_SAMPLE);
        sessionPixyDTO.setIsClient(false);
        List<SessionKeyValueDTO> sessionKeyValueDTOs = new ArrayList<>();

        sessionKeyValueDTOs.add(new SessionKeyValueDTO("sync","10.4318019221"));
        sessionKeyValueDTOs.add(new SessionKeyValueDTO("mouse","4.1738,3.792,1.0"));
        sessionKeyValueDTOs.add(new SessionKeyValueDTO("mouse","4.1733,3.795,1.0"));
        sessionKeyValueDTOs.add(new SessionKeyValueDTO("get","1.0,1./"));
        sessionPixyDTO.setSessionKeyValueList(sessionKeyValueDTOs);
        System.out.println(new Gson().toJson(sessionPixyDTO));
        OperationResponse operationResponse = sessionPixyService.createSessionPixy(sessionPixyDTO);
        assertSame("status should ok", HttpStatus.OK,operationResponse.getStatus().getHttpStatus());
        System.out.println(operationResponse.getMessage());

    }

    @After
    public void getSessionPixys() throws PixyException {
        Pageable page = new PageRequest(0,25,new Sort(new Sort.Order(Sort.Direction.ASC, "sessionId")));
        Page<SessionPixyResultDto> sessionPixyResultDtoPage=sessionPixyService.getSessionPixys(page);
        //sessionPixyResultDtoPage.getContent().forEach(sessionPixyResultDto -> System.out.println(sessionPixyResultDto.toString()));
        assertTrue(sessionPixyResultDtoPage.getContent().stream()
                .anyMatch(sessionPixyResultDto -> sessionPixyResultDto.getSessionId().equals(SESSION_ID_SAMPLE)));
        System.out.println("*********getSessionPixys()");
        System.out.println(new Gson().toJson(sessionPixyResultDtoPage.getContent()));
    }

    @After
    public void getSessionPixyById() throws PixyException {
        SinglePixyInstructionsDTO singlePixyInstructionsDTO = sessionPixyService.getSessionPixyById(SESSION_ID_SAMPLE, null, null);
        assertTrue( singlePixyInstructionsDTO.getPixyInstructionRepeatDTOSClient() == null || singlePixyInstructionsDTO.getPixyInstructionRepeatDTOSClient().size() == 0);
        assertTrue( singlePixyInstructionsDTO.getPixyInstructionRepeatDTOSServer().stream()
                .anyMatch(obj-> obj.getInstruction().equals("mouse") && obj.getRepeat().equals(2)));
        System.out.println("*********getSessionPixyById()");
        System.out.println(new Gson().toJson(singlePixyInstructionsDTO));

    }
}
