package connectfour.gui;

import connectfour.model.ConnectFourBoard;
import connectfour.model.Observer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.*;


public class ConnectFourGUI implements Observer<ConnectFourBoard> {

    private ConnectFourBoard board;

    public ConnectFourGUI(){
        this.board = new ConnectFourBoard();
        initializeView();
    }

    /*
    ************************************ VIEW ******************************************
     */

    private void initializeView(){this.board.addObserver(this);}

    public GridPane makeGridPane() {
        GridPane grid = new GridPane();
        for (int row = 0; row<ConnectFourBoard.ROWS; row++) {
            for (int col = 0; col<ConnectFourBoard.COLS; col++) {
                Button button = new Button("test");
                grid.add(button, col, row);
            }
        }
        return grid;
    }

    public void start(Stage stage){
        GridPane grid = makeGridPane();

        Scene scene = new Scene(grid);
        stage.setTitle("ConnectFourGUI");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    @Override
    public void update(ConnectFourBoard board) {

    }

    /*
    ************************************ Control ********************************************
     */

    public static void main(String[] args) {
        ConnectFourGUI gui = new ConnectFourGUI();
        gui.initializeView();
    }
}
