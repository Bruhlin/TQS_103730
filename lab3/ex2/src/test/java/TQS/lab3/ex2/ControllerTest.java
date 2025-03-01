package TQS.lab3.ex2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(CarController.class)
class ControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockitoBean
	private CarService carService;
	
	@Test
	void whenPostCar_thenCreateCar() throws Exception {
		Car roma = new Car("Ferrari", "Roma");

		when(carService.save(any())).thenReturn(roma);

		mvc.perform(post("/api/newcar").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(roma)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.maker", is("Ferrari")));

		verify(carService, times(1)).save(any());
				
	}

	@Test
	void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
		Car roma = new Car("Ferrari", "Roma");
		Car urus = new Car("Lamborghini", "Urus");
		Car evoque = new Car("Range Rover", "Evoque");

		List<Car> allCars = Arrays.asList(roma, urus, evoque);

		when(carService.getAllCars()).thenReturn(allCars);

		mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].maker", is(roma.getMaker())))
				.andExpect(jsonPath("$[1].maker", is(urus.getMaker())))
				.andExpect(jsonPath("$[2].maker", is(evoque.getMaker())));

		verify(carService, times(1)).getAllCars();
	} 

	@Test
	void whenGetCarById_thenReturnCar() throws Exception {
		Car roma = new Car("Ferrari", "Roma");
		
		when(carService.getCarDetails(anyLong())).thenReturn(roma);

		mvc.perform(get("/api/cars/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.maker", is("Ferrari")));

		verify(carService, times(1)).getCarDetails(anyLong());
	}

	@Test
	void whenInvalidCarId_thenReturnNull() throws Exception {
		when(carService.getCarDetails(anyLong())).thenReturn(null);

		mvc.perform(get("/api/cars/2").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(carService, times(1)).getCarDetails(anyLong());
	}

	@Test
	void whenReplacementsExists_thenReturnReplacementCar() throws Exception {
		Car replacement = new Car("Range Rover", "Evoque", "SUV", "Diesel");
		when(carService.findReplacementCar(222L)).thenReturn(Optional.of(replacement));

		mvc.perform(get("/api/cars/222/replacement")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.maker", is("Range Rover")));

		verify(carService, times(1)).findReplacementCar(222L);
	}

	@Test
	void whenNoReplacementExists_thenReturnNotFound() throws Exception {
		when(carService.findReplacementCar(111L)).thenReturn(Optional.empty());

		mvc.perform(get("/api/cars/111/replacement"))
				.andExpect(status().isNotFound());
	}

}
