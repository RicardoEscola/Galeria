package rodrigues.leite.galeria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tbMain); //Obtemos o elemento
        setSupportActionBar(toolbar); //indica que tbMain vai ser considerado a Action bar padrão
    }

    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_tb,menu);
        return true;
    }//Cria as opções de menu definidas no arquivo de menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){ //Sempre que a tollbar for clicada
            case R.id.opCamera: //Caso o icone da camera seja clicado
                dispatchTakePictureIntent(); //Dispara a camera
                return true;
            default:
                return super.onOptionsItemSelected(item);
    }
    }
}
