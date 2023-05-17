package rodrigues.leite.galeria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;

public class PhotoActivity extends AppCompatActivity {
    String photoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Toolbar toolbar = findViewById(R.id.tbPhoto); //Obtemos o elemento
        setSupportActionBar(toolbar); //indica que tbPhoto vai ser considerado a Action bar padrão

        ActionBar actionBar = getSupportActionBar(); //Pega a ActionBar que definimos acima
        actionBar.setDisplayHomeAsUpEnabled(true); //Habilita o botão de voltar

        Intent i = getIntent();
        photoPath = i.getStringExtra("photo_path"); //Pega o caminho da foto que veio da Main

        Bitmap bitmap = Util.getBitmap(photoPath);
        ImageView imPhoto = findViewById(R.id.imPhoto);
        imPhoto.setImageBitmap(bitmap);
    }

    void sharePhoto(){
        Uri photoUri = FileProvider.getUriForFile(PhotoActivity.this,"rodrigues.leite.galeria.fileprovider",new File(photoPath));
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_STREAM, photoUri);
        i.setType("image/jpeg");
        startActivity(i);
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