package dai.luis.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dai.luis.controllers.poker.PokerController;
import dai.luis.models.Naipe;
import dai.luis.service.PokerService;

public class PokerControllerTest extends AbstractControllerTest{

	@InjectMocks
	PokerController pokerController;
	
	@Mock
	PokerService pokerService;
	
	@Mock
	List<String> mockedList;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		super.setUp(pokerController);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testIrPoker() throws Exception {
		
		List<Naipe> naipes = listNaipesMock();
		
		when(pokerService.sacaDiezCartasAzar()).thenReturn(someListMock());
		when(pokerService.getCartasFinalesJugador(Mockito.anyList(), Mockito.anyBoolean())).thenReturn(naipes);
		List<String> someList = new ArrayList<String>();
		when(pokerService.comprobaciones(Mockito.anyList())).thenReturn(someList);
				
		/*Es una prueba*/
		mockedList.add("one");
		mockedList.clear();
		/*Es una prueba*/
		
		getMockMvc().perform(get("/poker"));
		
		verify(pokerService).sacaDiezCartasAzar();
		verify(pokerService).getCartasFinalesJugador(someListMock(), false);
		verify(pokerService, times(2)).comprobaciones(naipes);
		
	/*Es una prueba 
	 * Recuerda las interacciones y comprueba que son iguales*/
		verify(mockedList).add("one");
		verify(mockedList).clear();
	/*FIN Es una prueba*/
	}
	
	private List<Integer> someListMock(){
		List<Integer> someList = new ArrayList<Integer>();
		someList.add(1);
		someList.add(2);
		someList.add(3);
		someList.add(4);
		someList.add(5);
		return someList;
	}
	
	private List<Naipe> listNaipesMock(){
		List<Naipe> naipes = new ArrayList<Naipe>();
		Naipe naipe = new Naipe();
		naipe.setIdNaipe(1);
		naipe.setNumero(1);
		naipe.setPalo("c");
		naipes.add(naipe);
		
		naipe = new Naipe();
		naipe.setIdNaipe(7);
		naipe.setNumero(7);
		naipe.setPalo("c");
		naipes.add(naipe);
		
		naipe = new Naipe();
		naipe.setIdNaipe(12);
		naipe.setNumero(12);
		naipe.setPalo("c");
		naipes.add(naipe);

		naipe = new Naipe();
		naipe.setIdNaipe(13);
		naipe.setNumero(13);
		naipe.setPalo("c");
		naipes.add(naipe);

		naipe = new Naipe();
		naipe.setIdNaipe(44);
		naipe.setNumero(5);
		naipe.setPalo("p");
		naipes.add(naipe);
		
		return naipes;		
	}

}
