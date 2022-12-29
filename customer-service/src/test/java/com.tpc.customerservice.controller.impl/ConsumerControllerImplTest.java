package com.tpc.customerservice.controller.impl;

import com.tpc.customerservice.controller.impl.ConsumerControllerImpl;
import com.tpc.customerservice.dto.ConsumerDTO;
import com.tpc.customerservice.entities.Customer;
import com.tpc.customerservice.mapper.ConsumerMapper;
import com.tpc.customerservice.mapper.ReferenceMapper;
import com.tpc.customerservice.service.ConsumerService;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ConsumerControllerImplTest {
    //TODO: create the data Test generator class ConsumerBuilder
    private static final String ENDPOINT_URL = "/consumers";
    @MockBean
    private ReferenceMapper referenceMapper;
    @InjectMocks
    private ConsumerControllerImpl consumerController;
    @MockBean
    private ConsumerService consumerService;
    @MockBean
    private ConsumerMapper consumerMapper;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.consumerController).build();
    }

    @Test
    public void getAll() throws Exception {
        Mockito.when(consumerMapper.asDTOList(ArgumentMatchers.any())).thenReturn(ConsumerBuilder.getListDTO());

        Mockito.when(consumerService.findAll()).thenReturn(ConsumerBuilder.getListEntities());
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(consumerMapper.asDTO(ArgumentMatchers.any())).thenReturn(ConsumerBuilder.getDTO());

        Mockito.when(consumerService.findById(ArgumentMatchers.anyLong())).thenReturn(java.util.Optional.of(ConsumerBuilder.getEntity()));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(consumerService, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(consumerService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(consumerMapper.asEntity(ArgumentMatchers.any())).thenReturn(ConsumerBuilder.getEntity());
        Mockito.when(consumerService.save(ArgumentMatchers.any(Customer.class))).thenReturn(ConsumerBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(CustomUtils.asJsonString(ConsumerBuilder.getDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(consumerService, Mockito.times(1)).save(ArgumentMatchers.any(Customer.class));
        Mockito.verifyNoMoreInteractions(consumerService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(consumerMapper.asEntity(ArgumentMatchers.any())).thenReturn(ConsumerBuilder.getEntity());
        Mockito.when(consumerService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(ConsumerBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(CustomUtils.asJsonString(ConsumerBuilder.getDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(consumerService, Mockito.times(1)).update(ArgumentMatchers.any(Customer.class), ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(consumerService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(consumerService).deleteById(ArgumentMatchers.anyLong());
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(consumerService, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(consumerService);
    }