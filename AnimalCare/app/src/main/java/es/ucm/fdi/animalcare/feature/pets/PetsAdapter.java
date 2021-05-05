package es.ucm.fdi.animalcare.feature.pets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import es.ucm.fdi.animalcare.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.ucm.fdi.animalcare.data.Pets;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.ViewHolder> {
    private List<Pets> mData;
    private LayoutInflater mInflater;
    private Context ctext;

    public PetsAdapter(List<Pets> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.ctext = context;
        this.mData = itemList;
    }

    @Override
    public PetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.pet_list_item, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name, type;

        ViewHolder(View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.imgFoto);
            name = itemView.findViewById(R.id.NamePetView);
            type = itemView.findViewById(R.id.TypePetView);
        }

        void bindData(final Pets pet){
           image.setImageResource(R.drawable.pet_dog);
           name.setText(pet.getName());
           type.setText(pet.getType());
        }
    }
}
