package pxgd.hyena.com.mydemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 自定义数组适配器
 */
public class FlavorAdapter extends ArrayAdapter<Flavor> {

    /**
     * 构造
     * @param context
     * @param androidFlavors
     */
    public FlavorAdapter(Activity context, List<Flavor> androidFlavors) {
        super(context, 0, androidFlavors);
    }

    /**
     * 针对每一行数据映射到项布局时使用
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            //加载项布局
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.flavor_list_item, parent, false
            );
        }

        //获取当前行的实体类对象设置此行视图的值
        Flavor flavor = getItem(position);
        ImageView iconImg = convertView.findViewById(R.id.img_icon);
        TextView txtName = convertView.findViewById(R.id.txt_item_name);
        TextView txtNumber =convertView.findViewById(R.id.txt_item_number);

        iconImg.setImageResource(flavor.image);
        txtName.setText(flavor.name);
        txtNumber.setText(flavor.number);
        return convertView;
    }
}
