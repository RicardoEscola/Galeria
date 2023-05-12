package rodrigues.leite.galeria;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.PackageManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    static int RESULT_TAKE_PICTURE = 1;
    static  int RESULT_REQUEST_PERMISSION = 2;
    String currnentPhotoPath;

    List<String> photos = new ArrayList<>();
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.CAMERA);

        checkForPermissions(permissions);

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

    private void dispatchTakePictureIntent(){ //Quando a camera for acionada
        File f = null; //cria um arquivo vazio para adicionar a foto a seguir
        try {
            f = createImageFile():
        } catch (IOException e ){
            Toast.makeText(MainActivity.this, "Não foi possivel criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }

        currnentPhotoPath = f.getAbsolutePath();

        if(f != null){
            Uri fUri = FileProvider.getUriForFile(MainActivity.this, "rodrigues.leite.galeria.fileprovider",f); //gera o endereço da img
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //passa a uri para a intent
            i.putExtra(MediaStore.EXTRA_OUTPUT,fUri); //URI é passado para o app camera
            startActivityForResult(i, RESULT_TAKE_PICTURE); //abre a camera e espera o resutado que seria a foto
        }
    }

    private File createImageFile() throws IOException{ //criar a imagem
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); //guarda a date e hora, para adicionar as fotos
        String imageFileName = "JPEG_" + timeStamp; //define o nome do arquivo
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File f = File.createTempFile(imageFileName, ".jpg",storageDir);
        return f;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_TAKE_PICTURE){
            if(resultCode == Activity.RESULT_OK){ //caso a foto tenha sido tirada
                photos.add(currnentPhotoPath); //coloca ela na lista de fotos
                mainAdapter.notifyItemInserted(photos.size()-1); //avisa o mainAdapter que tem uma nova foto na area
            } else {
                File f = new File(currnentPhotoPath); //caso a foto n for criada o arquivo é deletado
                f.delete();
            }
        }
    }
    // Permissões

    private void checkForPermissions(List<String> permissions){
        List<String> permissionsNotGranted = new ArrayList<>();

        for(String permission : permissions){
            if(!hasPermissions(permission)){
                permissionsNotGranted.add(permission);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(permissionsNotGranted.size() > 0){
                requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]),RESULT_REQUEST_PERMISSION);
            }
        }
    }
    private boolean hasPermissions(String permission){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return ActivityCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        final List<String> permissionsRejected = new ArrayList<>();
        if(requestCode == RESULT_REQUEST_PERMISSION){
            for(String permision : permissions){
                if(!hasPermissions(permision)){
                    permissionsRejected.add(permision);
                }
            }
        }
        if(permissionsRejected.size() > 0){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(shouldShowRequestPermissionRationale(permissionsRejected.get(0))){
                    new AlertDialog.Builder(MainActivity.this).setMessage("Para usar a app é preciso conceder essas permissões").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), RESULT_REQUEST_PERMISSION);
                        }
                    }).create().show();
                }
            }
        }
    }

}
