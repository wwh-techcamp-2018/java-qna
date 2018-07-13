package codesquad.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import sun.jvm.hotspot.HelloWorld;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloWorld.class)
public class TestResult {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void success() {

//        this.mockMvc.perform(get("/").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"));
//        String result = Result.ok();



    }

//    @Test
//    public void addAnswer() {
//
//        MockMvc.
//
//
//        Answer a = new Answer();
//        Answer result = Result.ok(a);
//        assertEquals(a, result);
//    }
}
