package ru.myastrebov.view.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.myastrebov.model.Dish;
import ru.myastrebov.model.DishTag;
import ru.myastrebov.services.DishService;
import ru.myastrebov.services.exception.DishDoesNotExistException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void testFindAllDishes() throws Exception {

        Dish tee = createDish(1L, "green tee", "green tee with lemon", Long.MAX_VALUE);
        Dish pork = createDish(2L, "pork", "pork with vegetables", 50L);
        when(dishService.getMenu()).thenReturn(Lists.newArrayList(tee, pork));

        mockMvc.perform(get("/dish/all"))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", equalTo("green tee")))
                .andExpect(jsonPath("$[0].cost", equalTo(Long.MAX_VALUE)))
                .andExpect(jsonPath("$[0].description", equalTo("green tee with lemon")))
                .andExpect(jsonPath("$[0].links[*].href", hasItem(endsWith("/dish/1"))))

                .andExpect(jsonPath("$[1].name", equalTo("pork")))
                .andExpect(jsonPath("$[1].cost", equalTo(50)))
                .andExpect(jsonPath("$[1].description", equalTo("pork with vegetables")))
                .andExpect(jsonPath("$[1].links[*].href", hasItem(endsWith("/dish/2"))))
        ;
        verify(dishService, times(1)).getMenu();
    }

    @Test
    public void testFindDishById() throws Exception {
        Dish dish = createDish(1L, "name", "description", 15L);
        dish.setTags(Lists.newArrayList(new DishTag("tee", 100L), new DishTag("drinks", 101L)));
        when(dishService.getDishById(eq(1L))).thenReturn(dish);

        mockMvc.perform(get("/dish/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("name")))
                .andExpect(jsonPath("$.cost", equalTo(15)))
                .andExpect(jsonPath("$.description", equalTo("description")))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/dish/1"))))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/dish/tag/100"))))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/dish/tag/101"))))
        ;
        verify(dishService, times(1)).getDishById(eq(1L));
    }

    @Test
    public void testNotFoundDishById() throws Exception {
        when(dishService.getDishById(any(Long.class))).thenThrow(new DishDoesNotExistException());
        mockMvc.perform(get("/dish/1025"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateDish() throws Exception {
        Dish dish = createDish(null, "name", "desc", 14L);
        when(dishService.addNewDish(any(Dish.class))).thenReturn(createDish(1L, "name", "desc", 14L));

        String content = new ObjectMapper().writeValueAsString(dish);
        mockMvc.perform(
                post("/dish/add").contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("name")))
                .andExpect(jsonPath("$.cost", equalTo(14)))
                .andExpect(jsonPath("$.description", equalTo("desc")))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/dish/1"))));

        ArgumentCaptor<Dish> argumentCaptor = ArgumentCaptor.forClass(Dish.class);
        verify(dishService, times(1)).addNewDish(argumentCaptor.capture());
        Dish argumentCaptorValue = argumentCaptor.getValue();
        assertThat(argumentCaptorValue.getCost(), equalTo(dish.getCost()));
        assertThat(argumentCaptorValue.getName(), equalTo(dish.getName()));
        assertThat(argumentCaptorValue.getDescription(), equalTo(dish.getDescription()));
        assertThat(argumentCaptorValue.getId(), nullValue());
    }

    @Test
    public void testGetAllDishTags() throws Exception {
        when(dishService.getAllTags()).thenReturn(Lists.newArrayList(new DishTag("hot", 1L), new DishTag("tee", 2L)));

        mockMvc.perform(get("/dish/tag/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].tagName", equalTo("hot")))
                .andExpect(jsonPath("$[0].links[*].href", hasItem(endsWith("/dish/tag/1"))))
                .andExpect(jsonPath("$[1].tagName", equalTo("tee")))
                .andExpect(jsonPath("$[1].links[*].href", hasItem(endsWith("/dish/tag/2"))))
        ;
        verify(dishService).getAllTags();
    }

    @Test
    public void testFindDishByTag() throws Exception {
        when(dishService.findByTag(any(Long.class))).thenReturn(Lists.newArrayList(createDish(2L, "tee", "desc", 15L)));
        mockMvc.perform(get("/dish/tag/487"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", equalTo("tee")))
                .andExpect(jsonPath("$[0].description", equalTo("desc")))
                .andExpect(jsonPath("$[0].cost", equalTo(15)))
                .andExpect(jsonPath("$[0].links[*].href", hasItem(endsWith("/dish/2"))));

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