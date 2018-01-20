package rsam.utn2017.dam.agenda;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rsam.utn2017.dam.agenda.GuardiaTabFragment.OnListFragmentInteractionListener;
import rsam.utn2017.dam.agenda.model.Guardia;

import java.util.List;

/**
 //
 *
 * TODO: Replace the implementation with code for your data type.
 */
public class MyGuardiaRecyclerViewAdapter extends RecyclerView.Adapter<MyGuardiaRecyclerViewAdapter.ViewHolder> {

    private final List<Guardia> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyGuardiaRecyclerViewAdapter(List<Guardia> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_guardia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).getId().toString());
        holder.txtEquipo.setText(mValues.get(position).getTextoEquipo());
        holder.txtFecha.setText(mValues.get(position).getFechaString());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    public void clear(){
        mValues.clear();
    }

    public void addAll(List<Guardia> items){
        mValues.addAll(items);
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView txtEquipo;
        public final TextView txtFecha;
        public Guardia mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            txtEquipo = (TextView) view.findViewById(R.id.content);
            txtFecha = (TextView) view.findViewById(R.id.fechaGuardia);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtEquipo.getText() + "'";
        }
    }
}
