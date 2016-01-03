//package se.cygni.game.controller;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import se.cygni.game.Tile;
//import se.cygni.game.World;
//import se.cygni.snake.controller.GameController;
//import se.cygni.snake.suscriber.NewGameSubscriber;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class GameControllerTest {
//    @Mock
//    private NewGameSubscriber subscriber;
//
//    @InjectMocks
//    private GameController approvalController = new GameController();
//
//    private MockMvc mockMvc ;
//
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//
//        mockMvc = MockMvcBuilders.standaloneSetup(approvalController).build();
//
//    }
//    @Test
//    public void testCreateGame() throws Exception {
//        when(subscriber.createNewGame()).thenReturn(new World(new Tile[5][5]));
//
//        mockMvc.perform(get("/newGame"))
//                .andExpect(status().isOk());
//    }
//}
