package connectfour.gui;

import connectfour.model.ConnectFourBoard;
import connectfour.model.Observer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.util.ArrayList;

/**
 * The graphical user interface for ConnectFourBoard. This class encapsulates the view and the
 * controller portions of the MVC architecture by giving buttons functionality and updating the
 * view of the board and its statistics every move.
 * @author Nick Tibbels nst2038@rit.edu
 * @author Samuel Whitney shw9601@rit.edu
 * */
public class ConnectFourGUI extends Application implements Observer<ConnectFourBoard> {

    private ConnectFourBoard board;
    private GridPane grid;
    private ArrayList<ArrayList<Button>> buttons = new ArrayList<ArrayList<Button>>();
    private Text infoText;
    private final Image EMPTY = new Image(getClass().getResourceAsStream("empty.png"));
    private final Image P1BLACK = new Image(getClass().getResourceAsStream("p1black.png"));
    private final Image P2RED = new Image(getClass().getResourceAsStream("p2red.png"));

    public ConnectFourGUI(){
        this.board = new ConnectFourBoard();
        initializeView();
    }

    /**
     * Method for adding an observer for the board in the graphical interface. Is called
     * when initializing the constructor for the GUI.
     * */
    private void initializeView(){this.board.addObserver(this);}


    /**
     * Helper method for creating the GridPane that stores all the buttons for running
     * the ConnectFourBoard Model.
     * */
    public GridPane makeGridPane() {
        GridPane grid = new GridPane();
        for (int row = 0; row<ConnectFourBoard.ROWS; row++) {
            ArrayList<Button> rowButtons = new ArrayList<>();
            for (int col = 0; col<ConnectFourBoard.COLS; col++) {
                Button button = new Button();
                button.setGraphic(new ImageView(EMPTY));
                int buttonCol = col;
                button.setOnAction((event) -> {
                    this.board.makeMove(buttonCol);
                });
                rowButtons.add(button);
                grid.add(button, col, row);
            }
            buttons.add(rowButtons);
        }
        return grid;
    }

    /**
     * The method called on by Application.launch(args) from main. Makes a BorderPane that
     * stores the GridPane for the board in the center and displays a string of information
     * about the game's statistics at the bottom.
     * */
    public void start(Stage stage){
        BorderPane pane = new BorderPane();
        this.grid = makeGridPane();
        this.infoText = new Text("Player Turn: " + this.board.getCurrentPlayer() +
                "\nMoves Made: " + this.board.getMovesMade() +
                "\nStatus: " + this.board.getGameStatus());

        pane.setCenter(grid);
        pane.setBottom(infoText);

        Scene scene = new Scene(pane);
        stage.setTitle("ConnectFourGUI");

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * The overridden update method that informs observers that something has changed and updates
     * the state of the board. This is called everytime a button is action event-ed.*/
    @Override
    public void update(ConnectFourBoard board) {
        for (int row = 0; row<ConnectFourBoard.ROWS; row++) {
            for (int col = 0; col<ConnectFourBoard.COLS; col++) {
                ConnectFourBoard.Player check = this.board.getContents(row, col);
                if (check == ConnectFourBoard.Player.P1){
                    buttons.get(row).get(col).setGraphic(new ImageView(P1BLACK));
                } else if (check == ConnectFourBoard.Player.P2) {
                    buttons.get(row).get(col).setGraphic(new ImageView(P2RED));
                }
                if (this.board.getGameStatus() != ConnectFourBoard.Status.NOT_OVER){
                    buttons.get(row).get(col).setDisable(true);
                }
            }
        }
        infoText.setText("Player Turn: " + this.board.getCurrentPlayer() +
                "\nMoves Made: " + this.board.getMovesMade() +
                "\nStatus: " + this.board.getGameStatus());
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
