package rodrigues.leite.galeria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rodrigues.leite.lista.R;
import rodrigues.leite.lista.activity.MainActivity;
import rodrigues.leite.lista.model.MyItem;

public class MyAdapter extends RecyclerView.Adapter {
    MainActivity mainActivity;
    List<MyItem> itens;

    public MyAdapter(MainActivity mainActivity, List<MyItem> itens) {
        this.mainActivity = mainActivity;
        this.itens = itens;
    }

    @NonNull
    @Override
    //Passo 12 ---------------------------------------------------------------------------------------------

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mainActivity); // Lê o arquivo xml então cria os itens
        View v = inflater.inflate(R.layout.item_list,parent, false); //Cria os elementos pegando eles da lista
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) { //Parametros: 1° os intens criados na onCreateViewHolder e 2° qual o item deve ser usado para preencher o item
        // onBindViewHolder preenche a UI com os dados de item

        MyItem myItem = itens.get(position); //pegamos o item
        View v = holder.itemView; //pegamos o view que está dento de viwholder

        //Preenchendo os dados da UI com os dados do item
        ImageView imvfoto = v.findViewById(R.id.imvPhoto);
        imvfoto.setImageBitmap(myItem.photo);

        TextView tvTitle = v.findViewById(R.id.tvTitle);
        tvTitle.setText(myItem.title);

        TextView tvdesc = v.findViewById(R.id.tvDesc);
        tvdesc.setText(myItem.description);
    }

    @Override
    public int getItemCount() {
        return itens.size(); // retorna a quantidade de itens na itens
    }
}