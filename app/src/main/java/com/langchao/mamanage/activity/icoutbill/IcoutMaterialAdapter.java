package com.langchao.mamanage.activity.icoutbill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.langchao.mamanage.R;
import com.langchao.mamanage.db.icin.Ic_inbill_b;
import com.langchao.mamanage.dialog.AlertForResult;
import com.langchao.mamanage.dialog.PopCallBack;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wongsuechang on 2016/6/26.
 */
public class IcoutMaterialAdapter extends BaseAdapter {

    private IcoutInbillActivity context;
    List<Ic_inbill_b> blist = new ArrayList<>();

    List<Ic_inbill_b> choosedList = new ArrayList<>();

    public IcoutMaterialAdapter(IcoutInbillActivity diroutOrderActivity, List<Ic_inbill_b> list) {
        context = diroutOrderActivity;
        blist = list;
    }

    public void update(Ic_inbill_b in_b) {
        int posion = in_b.getPosition();
        blist.get(posion).setCurQty(in_b.getCurQty());
    }

    class ViewHolder {
        @ViewInject(R.id.img_m_add)
        ImageView imgAdd;
        @ViewInject(R.id.tv_dir_out_order_m_name)
        TextView tvName;
        @ViewInject(R.id.tv_dir_out_order_m_add)
        TextView tvAdd;
        @ViewInject(R.id.et_dir_out_order_m_num)
        EditText tvNum;
        @ViewInject(R.id.tv_dir_out_order_m_del)
        TextView tvDel;
        @ViewInject(R.id.tv_dir_out_order_m_model)
        TextView tvModel;
        @ViewInject(R.id.tv_dir_out_order_m_unit)
        TextView tvUnit;
        @ViewInject(R.id.tv_dir_out_order_m_brand)
        TextView tvBrand;
        @ViewInject(R.id.tv_dir_out_order_m_note)
        TextView tvNote;
        @ViewInject(R.id.tv_dir_out_order_m_qty)
        TextView leftQty;
        @ViewInject(R.id.tv_dir_out_order_1)
        TextView tv1;
        @ViewInject(R.id.tv_dir_out_order_left)
        TextView tv_dir_out_order_left;


        public Ic_inbill_b pu_order_b;

        public BaseAdapter baseAdapter;


        @Event(value = {R.id.et_dir_out_order_m_num }, type = View.OnClickListener.class)
        private void numClick(View v){
            AlertForResult.popUp( pu_order_b.getCurQty(),context,new PopCallBack() {
                @Override
                public void setNum(double num) {
                    if(num > pu_order_b.getSourceQty()){
                        num = pu_order_b.getSourceQty();
                    }
                    if(num > 0) {
                        if(num > (pu_order_b.getSourceQty()-pu_order_b.getCkQty())){
                            num = pu_order_b.getSourceQty()-pu_order_b.getCkQty();
                        }
                        pu_order_b.setCurQty(num);

                        notifyDataSetChanged();
                    }
                }
            });
        }

        @Event(value = {R.id.tv_dir_out_order_m_add }, type = View.OnClickListener.class)
        private void add(View v){
            if(pu_order_b.getCurQty() < (pu_order_b.getSourceQty()-pu_order_b.getCkQty())) {
                pu_order_b.setCurQty(pu_order_b.getCurQty() + 1);
                baseAdapter.notifyDataSetChanged();
            }
        }
        @Event(value = {R.id.img_m_add }, type = View.OnClickListener.class)
        private void choose(View v){



            if(!choosedList.contains(pu_order_b)) {

                AlertForResult.popUp( pu_order_b.getCurQty(),context,new PopCallBack() {
                    @Override
                    public void setNum(double num) {
                        if(num > pu_order_b.getSourceQty()){
                            num = pu_order_b.getSourceQty();
                        }
                        if(num > 0) {
                            if(num > (pu_order_b.getSourceQty()-pu_order_b.getCkQty())){
                                num = pu_order_b.getSourceQty()-pu_order_b.getCkQty();
                            }
                            pu_order_b.setCurQty(num);
                            choosedList.add(pu_order_b);
                            context.updateTotalNum(choosedList.size());
                            notifyDataSetChanged();
                        }
                    }
                });

            }
        }

        @Event(value = {R.id.tv_dir_out_order_m_del }, type = View.OnClickListener.class)
        private void sub(View v){
            if(pu_order_b.getCurQty() > 1) {
                pu_order_b.setCurQty(pu_order_b.getCurQty() - 1);
                baseAdapter.notifyDataSetChanged();
            }
        }
    }

    public void chooseAll(){
        choosedList.clear();
        for(Ic_inbill_b b : blist){
            b.setCurQty(b.getSourceQty()-b.getCkQty());
            choosedList.add(b);
        }
        context.updateTotalNum(choosedList.size());
        notifyDataSetChanged();
    }

    public void unChooseAll(){
        for(Ic_inbill_b b : choosedList){
            b.setCurQty(0);

        }
        choosedList.clear();
        context.updateTotalNum(choosedList.size());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(null == blist)
            return 0;
        return blist.size();
    }

    @Override
    public Object getItem(int position) {
        return blist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder _Holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(x.app());
            convertView = layoutInflater.inflate(R.layout.item_dir_out_order_material, null);
            x.view().inject(_Holder, convertView);
            convertView.setTag(_Holder);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        } else {
            _Holder = (ViewHolder) convertView.getTag();
        }
        _Holder.tv_dir_out_order_left.setText("入库剩余数");
        Ic_inbill_b order_b = blist.get(position);
        _Holder.tvBrand.setText(order_b.getBrand());
        _Holder.leftQty.setText((order_b.getSourceQty()-order_b.getCkQty()-order_b.getCurQty() )+"" );
        _Holder.tvModel.setText(order_b.getModel());
        _Holder.tvName.setText(order_b.getName());
        _Holder.tvNum.setText(order_b.getCurQty()+"");
        _Holder.tvNote.setText(order_b.getNote());
        _Holder.tvUnit.setText(order_b.getUnit());

        _Holder.pu_order_b = order_b;
        _Holder.baseAdapter = this;
        return convertView;
    }



}