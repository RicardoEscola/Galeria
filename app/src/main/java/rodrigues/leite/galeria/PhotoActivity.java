package rodrigues.leite.galeria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Toolbar toolbar = findViewById(R.id.tbPhoto); //Obtemos o elemento
        setSupportActionBar(toolbar); //indica que tbPhoto vai ser considerado a Action bar padrão

        ActionBar actionBar = getSupportActionBar(); //Pega a ActionBar que definimos acima
        actionBar.setDisplayHomeAsUpEnabled(true); //Habilita o botão de voltar
    }

    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.photo_activity_tb,menu);
        return true;
    } //Cria as opções de menu definidas no arquivo de menu

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opShare://quando o icone for clicado
                sharePhoto();//excuta o codigo
                return true;
            default:
                return super.onOptionsItemSelected(item);
            }
        }
}