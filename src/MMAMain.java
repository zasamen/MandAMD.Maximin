import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import minimax.MinimaxFactory;
import mmAndkmeans.MultiPrFactory;

public class MMAMain extends Main {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            FXMLLoader loader = setLoader();
            initStage(loader,primaryStage);
//            initController(loader,primaryStage,new MinimaxFactory());
            initController(loader,primaryStage,new MultiPrFactory());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
