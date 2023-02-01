package ddd.caffeine.ratrip.module.place.presentation;

import static java.util.UUID.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import ddd.caffeine.ratrip.module.place.application.PlaceService;
import ddd.caffeine.ratrip.module.place.presentation.dto.request.PlaceSaveByThirdPartyRequestDto;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = PlaceController.class,
	excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class PlaceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	PlaceService placeService;

	@Autowired
	private ObjectMapper mapper;

	@Test
	@DisplayName("custom 어노테이션 Number 정상 동작 테스트")
	void customAnnotationNumberTest() throws Exception {
		//given
		String baseURI = "/v1/places/";
		String thirdPartyId = "12345";
		String placeName = "지원이네 집";
		String address = "서울특별시 서초구 양재동 16-10";

		PlaceSaveByThirdPartyRequestDto request = new PlaceSaveByThirdPartyRequestDto(
			thirdPartyId, placeName, address
		);

		String content = mapper.writeValueAsString(request);

		//when
		ResultActions actions = mockMvc.perform(post(baseURI)
			.content(content)
			.contentType(MediaType.APPLICATION_JSON_VALUE));
		// then
		actions
			.andExpect(status().isOk());
	}

	@ParameterizedTest
	@DisplayName("custom 어노테이션 Number 예외 동작 테스트")
	@ValueSource(strings = {"", "134숫자", "?234"})
	void customAnnotationNumberReturnExceptionTest(String id) throws Exception {
		//given
		String baseURI = "/v1/places/";
		String thirdPartyId = id;
		String placeName = "지원이네 집";
		String address = "서울특별시 서초구 양재동 16-10";

		PlaceSaveByThirdPartyRequestDto request = new PlaceSaveByThirdPartyRequestDto(
			thirdPartyId, placeName, address
		);

		String content = mapper.writeValueAsString(request);

		//when
		ResultActions actions = mockMvc.perform(post(baseURI)
			.content(content)
			.contentType(MediaType.APPLICATION_JSON_VALUE));

		// then
		actions
			.andExpect(status().is4xxClientError());
	}

	@Test
	@DisplayName("custom 어노테이션 UUID 정상 동작 테스트")
	void customAnnotationUUIDTest() throws Exception {
		//given
		String baseURI = "/v1/places/";
		String UUID = randomUUID().toString();
		String URI = baseURI + UUID;

		//when
		ResultActions actions = mockMvc.perform(get(URI)
			.contentType(MediaType.APPLICATION_JSON_VALUE));
		// then
		actions
			.andExpect(status().isOk());
	}

	@ParameterizedTest
	@DisplayName("custom 어노테이션 UUID 예외 동작 테스트")
	@ValueSource(strings = {"", "134숫자", "?2-312-3123-34"})
	void customAnnotationUUIDReturnExceptionTest(String uuid) throws Exception {
		//given
		String baseURI = "/v1/places/";

		String URI = baseURI + uuid;

		//when
		ResultActions actions = mockMvc.perform(get(URI)
			.contentType(MediaType.APPLICATION_JSON_VALUE));
		// then
		actions
			.andExpect(status().is4xxClientError());
	}
}