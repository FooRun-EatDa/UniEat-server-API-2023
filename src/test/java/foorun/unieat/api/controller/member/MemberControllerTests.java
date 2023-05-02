package foorun.unieat.api.controller.member;

import foorun.unieat.api.controller.CommonControllerTests;
import foorun.unieat.api.controller.MemberController;
import foorun.unieat.api.model.domain.member.request.MemberSignIn;
import foorun.unieat.api.service.member.MemberSignInService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(controllers = MemberController.class)
@MockBeans({
        @MockBean(JpaMetamodelMappingContext.class),
        @MockBean(MemberSignInService.class)
})
@ExtendWith(MockitoExtension.class)
@DisplayName("사용자 로그인 처리")
public class MemberControllerTests extends CommonControllerTests {
    /*
    @Test
    void memberSignInOAuth() throws Exception {
        mockMvc.perform(request(HttpMethod.POST, "/test"))
                .andExpect(status().isOk());
    }
    */
    //@InjectMocks
    //private MemberSignInService memberSignInService;

    /*@DisplayName("확인 - 성공 사례")
    @ParameterizedTest
    @ValueSource(strings = "kakao_1234")
    void signInSuccess(String memberId) throws Exception {
        MemberSignIn model = new MemberSignIn();
        model.setPrimaryId(memberId);

        String contents = objectMapper.writeValueAsString(model);

        mockMvc.perform(MockMvcRequestBuilders.post("/member/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(HttpHeaders.EMPTY)
                .content(contents))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        ;
    }*/
}