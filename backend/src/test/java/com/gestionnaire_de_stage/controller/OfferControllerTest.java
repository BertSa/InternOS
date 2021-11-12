package com.gestionnaire_de_stage.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionnaire_de_stage.dto.OfferDTO;
import com.gestionnaire_de_stage.dto.ValidationOffer;
import com.gestionnaire_de_stage.exception.EmailDoesNotExistException;
import com.gestionnaire_de_stage.exception.IdDoesNotExistException;
import com.gestionnaire_de_stage.exception.OfferAlreadyExistsException;
import com.gestionnaire_de_stage.exception.OfferAlreadyTreatedException;
import com.gestionnaire_de_stage.model.Offer;
import com.gestionnaire_de_stage.service.OfferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(OfferController.class)
public class OfferControllerTest {

    private final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OfferService offerService;
    private Offer dummyOffer;

    @Test
    public void testOfferCreate_withValidEntry() throws Exception {
        OfferDTO dummyOfferDTO = getDummyOfferDTO();
        dummyOffer = getDummyOffer();
        dummyOffer.setId(null);
        when(offerService.create(any())).thenReturn(getDummyOffer());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        Offer returnedOffer = MAPPER.readValue(response.getContentAsString(), Offer.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(returnedOffer.getId())
                .isNotNull()
                .isGreaterThan(0);
    }

    @Test
    public void testOfferCreate_withNullEntry() throws Exception {
        when(offerService.create(any())).thenThrow(new IllegalArgumentException("Offre est null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new OfferDTO())))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Offre est null");
    }

    @Test
    public void testOfferCreate_withNullEmail() throws Exception {
        OfferDTO dummyOfferDTO = getDummyOfferDTO();
        dummyOffer = getDummyOffer();
        dummyOffer.setId(null);
        when(offerService.create(any())).thenThrow(new IllegalArgumentException("Le courriel de l'utilisateur ne peut être null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).containsIgnoringCase("Le courriel de l'utilisateur ne peut être null");
    }

    @Test
    public void testOfferCreate_withAlreadyExistingOffer() throws Exception {
        OfferDTO dummyOfferDTO = getDummyOfferDTO();
        dummyOffer = getDummyOffer();
        dummyOffer.setId(null);
        when(offerService.create(any())).thenThrow(new OfferAlreadyExistsException());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Offre existe déjà");
    }

    @Test
    public void testOfferCreate_withInvalidEmail() throws Exception {
        OfferDTO dummyOfferDTO = getDummyOfferDTO();
        dummyOffer = getDummyOffer();
        dummyOffer.setId(null);
        when(offerService.create(any())).thenThrow(EmailDoesNotExistException.class);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(dummyOfferDTO)))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Le courriel n'existe pas");
    }

    @Test
    public void testUpdateOffer_withNullId() throws Exception {
        dummyOffer = getDummyOffer();
        dummyOffer.setId(null);
        when(offerService.validation(any())).thenThrow(new IllegalArgumentException("L'id est null"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new ValidationOffer(dummyOffer.getId(), true))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("L'id est null");
    }

    @Test
    public void testUpdateOffer_withOfferAlreadyTreated() throws Exception {
        dummyOffer = new Offer();
        when(offerService.validation(any())).thenThrow(new OfferAlreadyTreatedException());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new ValidationOffer(dummyOffer.getId(), true))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Offre déjà traité!");
    }

    @Test
    public void testUpdateOffer_withValidOffer() throws Exception {
        dummyOffer = getDummyOffer();
        when(offerService.validation(any())).thenReturn(dummyOffer);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new ValidationOffer(dummyOffer.getId(), true))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        Offer returnedOffer = MAPPER.readValue(response.getContentAsString(), Offer.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(returnedOffer).isEqualTo(dummyOffer);
    }

    @Test
    public void testUpdateOffer_withInvalidId() throws Exception {
        dummyOffer = getDummyOffer();
        when(offerService.validation(any())).thenThrow(new IdDoesNotExistException());

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/offers/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(new ValidationOffer(dummyOffer.getId(), true))))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Offre non existante!");
    }


    @Test
    public void testGetOffersByDepartment() throws Exception {
        String department = "myDepartment";
        List<Offer> dummyArrayOffer = getDummyArrayOffer();
        when(offerService.getOffersByDepartment(any())).thenReturn(dummyArrayOffer);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get(String.format("/offers/%s", department))
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Offer> returnedOffers = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(returnedOffers).containsAll(dummyArrayOffer);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testGetOffersByDepartment_withNoOffer() throws Exception {
        String department = "myDepartmentWithNoOffer";

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/offers/{0}", department)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Offer> returnedOffers = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(returnedOffers).isEmpty();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testGetOffersByDepartment_withNoDepartment() throws Exception {
        when(offerService.getOffersByDepartment(any())).thenThrow(new IllegalArgumentException("Le département n'est pas précisé"));

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/offers/")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        assertThat(response.getContentAsString()).contains("Le département n'est pas précisé");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private List<Offer> getDummyArrayOffer() {
        List<Offer> dummyArrayOffer = new ArrayList<>();
        for (long i = 0; i < 3; i++) {
            Offer dummyOffer = getDummyOffer();
            dummyOffer.setId(i);
            dummyArrayOffer.add(dummyOffer);
        }
        return dummyArrayOffer;
    }


    private Offer getDummyOffer() {
        Offer offer = new Offer();
        offer.setDepartment("Un departement");
        offer.setAddress("ajsaodas");
        offer.setId(1L);
        offer.setDescription("oeinoiendw");
        offer.setSalary(10);
        offer.setTitle("oeinoiendw");
        return offer;
    }

    private OfferDTO getDummyOfferDTO() {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setCreator_email("donewith@email.com");
        offerDTO.setSalary(18.0d);
        offerDTO.setDescription("Une description");
        offerDTO.setAddress("Addresse du cégep");
        offerDTO.setTitle("Offer title");
        offerDTO.setDepartment("Department name");
        return offerDTO;
    }

    @Test
    void getValidOffers() throws Exception {
        List<Offer> dummyArrayOffer = getDummyArrayOffer();
        when(offerService.getValidOffers()).thenReturn(dummyArrayOffer);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/offers/valid")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Offer> returnedOffers = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(returnedOffers).isEqualTo(dummyArrayOffer);
    }

    @Test
    void getNotValidatedOffers() throws Exception {
        List<Offer> dummyArrayOffer = getDummyArrayOffer();
        when(offerService.getNotValidatedOffers()).thenReturn(dummyArrayOffer);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/offers/not_validated")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        List<Offer> returnedOffers = MAPPER.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(returnedOffers).isEqualTo(dummyArrayOffer);
    }
}
