package rodrigues.leite.galeria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.security.identity.CipherSuiteNotSupportedException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    static int RESULT_TAKE_PICTURE = 1;
    String currnentPhotoPath;

    List<String> photos = new ArrayList<>();
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tbMain); //Obtemos o elemento
        setSupportActionBar(toolbar); //indica que tbMain vai ser considerado a Action bar padrão

        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); //Acessa o diretório Pictures
        File[] files = dir.listFiles();
        for(int i = 0; i < files[i].length; i++){ //Lê as fotos salvas
            photos.add(files[i].getAbsolutedPath()); //adiicona na lista de fotos
        }
        mainAdapter = new MainAdapter(MainActivity. this,photos);
        RecyclerView rvGallery = findViewById(R.id.rvGallery);
        rvGallery.setAdapter(mainAdapter);

        float w = getResources().getDimension(R.dimen.itemWidth);
        int numberOfColumns = Utils.calculateNoOfCollumns(MainActivity.this,w);//calcula o tanto de colunas de fotos cabem na tela
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, numberOfColumns);//configura o recicleview para exibir as fotos na grid respeitanto o espaço calculado acima
        rvGallery.setLayoutManager(gridLayoutManager);
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

    //Criando oa navegação das telas
    public void startPhotoActivity(String photoPath){
        Intent i = new Intent(MainActivity.this, PhotoActivity.class);
        i.putExtra("photo_path",photoPath);
        startActivity(i);
    }

    private void dispatchTakePictureIntent(){
        File f = null;
        try {
            f = createImageFile():
        } catch (IOException e ){
            Toast.makeText(MainActivity.this, "Não foi possivel criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }

        currentPhotoPath = f.getAbsolutePath();

        if(f != null){
            Uri fUri = FileProvider.getUriForFile(MainActivity.this, "rodrigues.leite.galeria.fileprovider",f);
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT,fUri);
            startActivityForResult(i, RESULT_TAKE_PICTURE);
        }
    }
}
