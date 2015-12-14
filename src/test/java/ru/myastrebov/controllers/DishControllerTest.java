package ru.myastrebov.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.myastrebov.model.Dish;
import ru.myastrebov.services.DishService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DishControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DishService dishService;

    @InjectMocks
    private DishController uut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(uut).build();
    }

    @Test
    public void testGetDishes() throws Exception {

        Dish tee = createDish(1L, "green tee", "green tee with lemon", Long.MAX_VALUE);
        Dish pork = createDish(2L, "pork", "pork with vegetables", 50L);
        when(dishService.getMenu()).thenReturn(Lists.newArrayList(tee, pork));

        mockMvc.perform(get("/dish"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", equalTo("green tee")))
                .andExpect(jsonPath("$[0].cost", equalTo(Long.MAX_VALUE)))
                .andExpect(jsonPath("$[0].description", equalTo("green tee with lemon")))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[1].name", equalTo("pork")))
                .andExpect(jsonPath("$[1].cost", equalTo(50)))
                .andExpect(jsonPath("$[1].description", equalTo("pork with vegetables")))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
        ;
        verify(dishService, times(1)).getMenu();
    }

    @Test
    public void testDishById() throws Exception {
        when(dishService.getDishById(eq(1L))).thenReturn(createDish(1L, "name", "description", 15L));

        mockMvc.perform(get("/dish/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("name")))
                .andExpect(jsonPath("$.cost", equalTo(15)))
                .andExpect(jsonPath("$.description", equalTo("description")))
                .andExpect(jsonPath("$.id", equalTo(1)))
        ;
        verify(dishService, times(1)).getDishById(eq(1L));
    }

    @Test
    public void testCreateDish() throws Exception {
        Dish dish = createDish(null, "name", "desc", 14L);
        when(dishService.addNewDish(any(Dish.class))).thenAnswer(invocation -> {
            Dish dishArg = (Dish) invocation.getArguments()[0];
            if (dishArg.getName().equals("name") && dishArg.getCost().equals(14L) && dishArg.getDescription().equals("desc")) {
                return createDish(1L, "name", "desc", 14L);
            } else {
                fail();
                return null;
            }
        });

        String content = new ObjectMapper().writeValueAsString(dish);
        mockMvc.perform(
                post("/dish/add").contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("name")))
                .andExpect(jsonPath("$.cost", equalTo(14)))
                .andExpect(jsonPath("$.description", equalTo("desc")))
                .andExpect(jsonPath("$.id", equalTo(1)))
        ;
        verify(dishService, times(1)).addNewDish(any(Dish.class));
    }

    private Dish createDish(Long id, String name, String description, long cost) {
        Dish dish = new Dish();
        dish.setId(id);
        dish.setName(name);
        dish.setDescription(description);
        dish.setCost(cost);
        return dish;
    }
}