package es.ucm.fdi.animalcare.feature.pets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.ucm.fdi.animalcare.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.ucm.fdi.animalcare.data.Pets;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.ViewHolder> implements View.OnClickListener {
    private List<Pets> mData;
    private LayoutInflater mInflater;
    private Context ctext;

    private View.OnClickListener listener;

    public PetsAdapter(List<Pets> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.ctext = context;
        this.mData = itemList;
    }

    @Override
    public PetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.pet_list_item, parent, false);

        view.setOnClickListener(this);

        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PetsAdapter.ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Pets> data){
        mData = data;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView name, type;

        ViewHolder(View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.imgFoto);
            name = itemView.findViewById(R.id.NamePetView);
            type = itemView.findViewById(R.id.TypePetView);
        }

        void bindData(final Pets pet){
            //image.setImageResource(R.drawable.pet_dog);
            name.setText(pet.getName());
            type.setText(pet.getType());
            switch (pet.getType()) {
                case "Perro": image.setImageResource(R.drawable.dog_green); break;
                case "Gato": image.setImageResource(R.drawable.cat_green); break;
                case "Pajaro": image.setImageResource(R.drawable.bird_green); break;
                case "Caballo": image.setImageResource(R.drawable.horse_green); break;
                case "Pez": image.setImageResource(R.drawable.fish_green); break;
                case "Tortuga": image.setImageResource(R.drawable.turtle_green); break;
                default: image.setImageResource(R.drawable.dog_green); break;
            }
        }
    }
}
