import com.openwaygroup.ipsgateway.controller.CommunicationController;
import com.openwaygroup.ipsgateway.entities.Connection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class CommunicationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testConnectionInitializeClient() throws IOException {
        boolean expectedResult = true;
        CommunicationController communicationController = new CommunicationController();
        Connection connection = new Connection(communicationController.getLocalHostAddress(), 9999,"4.2.2.2", 53,false);
        boolean actualResult = communicationController.initializeClient(connection);
        assertEquals(expectedResult,actualResult);
        assertEquals(expectedResult,actualResult);
    }
    @Test
    void testConnectionInitializeServer() throws IOException{
        boolean expectedResult = true;
        CommunicationController communicationController = new CommunicationController();
        Connection connection = new Connection(communicationController.getLocalHostAddress(),9999,true);
        boolean actualResult = communicationController.initializeServer(connection);
        assertEquals(expectedResult,actualResult);
        assertEquals(expectedResult,actualResult);
    }
    @Test
    void testCommunicationIndex() throws Exception{
        this.mockMvc
                .perform(get("/communication"))
                .andExpect(status().isOk())
                .andExpect(view().name("communication"))
                .andExpect(model().attributeExists("edit"));
    }

    @Test
    void connect() throws Exception{
        this.mockMvc
                .perform(post("/connect"));
    }

}
