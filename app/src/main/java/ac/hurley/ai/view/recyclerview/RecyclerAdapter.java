package ac.hurley.ai.view.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ac.hurley.ai.R;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/15 20:48
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    List<String> mDataSet = new ArrayList<>();

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main, parent, false);
        // 实例化ViewHolder
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        // 绑定数据
        viewHolder.tvName.setText(mDataSet.get(position));
    }

    /**
     * 自定义的ViewHolder
     */
    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            // nameTv = (TextView) itemView.findViewById(R.id.);
        }
    }
}
