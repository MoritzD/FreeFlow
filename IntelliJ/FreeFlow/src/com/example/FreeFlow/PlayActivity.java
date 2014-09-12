/**
 * Created by Sami on 08.09.14.
 */
public class PlayActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.play);


        SharedPreferences settings = getSharedPreferences("ColorPref", MODE_PRIVATE);

        int color = settings.getInt("pathColor", Color.CYAN);

        //Board board = (Board) findViewById(R.id.board);
        //board.setColor(color);
>>>>>>> origin/master

    }

}
