package cn.edu.gdmec.w07150837.myguard.m3communicationguard.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.w07150837.myguard.R;
import cn.edu.gdmec.w07150837.myguard.m2theftguard.entity.ContactInfo;

/**
 * Created by weiruibo on 12/20/16.
 */

public class ContactAdapter extends BaseAdapter{
    private List<ContactInfo>contactInfos;
    private Context context;

    public ContactAdapter(List<ContactInfo> systemContacts,Context context){
        super();
        this.contactInfos = systemContacts;
        this.context = context;
    }
    @Override
    public int getCount() {
        return contactInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return contactInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
         //这里报错---   convertView = View.inflate(context, android.R.layout.item_list_contact_select,null);
            holder = new ViewHolder();
          //这里报错----  holder.mNameTV = (TextView) convertView.findViewById(R.id.tv_name);
           // holder.mphone = (TextView)convertView.findViewById(R.id.tv_phone);
            holder.mContactImgv = convertView.findViewById(R.id.view1);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
//  这里报错---      holder.mNameTV.setText(contactInfos.get(position).name);
//        holder.mPhoneTV.setTextColor(context.getResources().getColor(R.color.bright_purple));
//        holder.mPhoneTV.setTextColor(context.getResources().getColor(R.color.bright_purple));
//        holder.mContactImgv.setBackgrondResource(R.drawable.brightpurple_contact_icon);
        return convertView;
    }
    static class ViewHolder{
        TextView mName;
        TextView mPhone;
        View mContactImgv;
    }
}
