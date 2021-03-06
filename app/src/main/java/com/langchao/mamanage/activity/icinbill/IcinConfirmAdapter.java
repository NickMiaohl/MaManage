package com.langchao.mamanage.activity.icinbill;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.langchao.mamanage.R;
import com.langchao.mamanage.common.MaConstants;
import com.langchao.mamanage.db.order.Pu_order_b;
import com.langchao.mamanage.dialog.AlertForResult;
import com.langchao.mamanage.dialog.MessageDialog;
import com.langchao.mamanage.dialog.PopCallBack;
import com.langchao.mamanage.utils.MathUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wongsuechang on 2016/6/26.
 */
public class IcinConfirmAdapter extends BaseAdapter {

    private IcinOrderConfirmActivity context;
    List<Pu_order_b> blist = new ArrayList<>();



    public IcinConfirmAdapter(IcinOrderConfirmActivity diroutOrderActivity, List<Pu_order_b> list) {
        context = diroutOrderActivity;
        blist = list;
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
        @ViewInject(R.id.tv_dir_out_order_left)
        TextView tvleft;



        public Pu_order_b pu_order_b;

        public BaseAdapter baseAdapter;

        @Event(value = {R.id.et_dir_out_order_m_num }, type = View.OnClickListener.class)
        private void numClick(View v){
            AlertForResult.popUp( pu_order_b.getCurQty(),context,new PopCallBack() {
                @Override
                public void setNum(double num) {
                    if(num > (pu_order_b.getLimitQty()- pu_order_b.getRkQty())){
                        num = pu_order_b.getLimitQty()- pu_order_b.getRkQty();
                        MessageDialog.show(context,"已经达到上限");
                    }
                    if(num > 0) {
                        pu_order_b.setCurQty(num);

                        notifyDataSetChanged();
                    }
                }
            });
        }


        @Event(value = {R.id.tv_dir_out_order_m_add }, type = View.OnClickListener.class)
        private void add(View v){
            if(pu_order_b.getCurQty() < (pu_order_b.getLimitQty()- pu_order_b.getRkQty())) {
                pu_order_b.setCurQty(pu_order_b.getCurQty() + 1);
                checkCurMax();
                baseAdapter.notifyDataSetChanged();

            }else{
                pu_order_b.setCurQty(pu_order_b.getLimitQty()- pu_order_b.getRkQty());
                baseAdapter.notifyDataSetChanged();
                MessageDialog.show(context,"已经达到上限");
            }
        }


        void checkCurMax(){
            if(pu_order_b.getCurQty() > (pu_order_b.getLimitQty()- pu_order_b.getRkQty())){
                pu_order_b.setCurQty( pu_order_b.getLimitQty()- pu_order_b.getRkQty());
                MessageDialog.show(context,"已经达到上限");
            }
        }

        @Event(value = {R.id.tv_dir_out_order_m_del }, type = View.OnClickListener.class)
        private void sub(View v){
            if(pu_order_b.getCurQty() > 1) {
                pu_order_b.setCurQty(pu_order_b.getCurQty() - 1);
                baseAdapter.notifyDataSetChanged();

            }
        }

        @Event(value = {R.id.img_m_add }, type = View.OnClickListener.class)
        private void del(View v){

               blist.remove(pu_order_b);
                baseAdapter.notifyDataSetChanged();
                notice(pu_order_b);

            context.updateChoosdSize(blist.size());
        }

        private void notice(Pu_order_b pu_order_b){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("order", pu_order_b);

            intent.putExtras(bundle);
            intent.setAction(MaConstants.FRESH); // 说明动作
            context.sendBroadcast(intent);// 该函数用于发送广播
        }
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

        Pu_order_b order_b = blist.get(position);
        _Holder.tvBrand.setText(order_b.getBrand());
        _Holder.imgAdd.setImageResource(R.mipmap.del);
        _Holder.leftQty.setText(MathUtils.scaleDouble4(order_b.getSourceQty()-order_b.getRkQty()-order_b.getCurQty() ) );
        _Holder.tvModel.setText(order_b.getModel());
        _Holder.tvName.setText(order_b.getName());
        _Holder.tvNum.setText(MathUtils.scaleDouble(order_b.getCurQty()));
        _Holder.tvNote.setText(order_b.getNote());
        _Holder.tvUnit.setText(order_b.getUnit());
        //_Holder.tvleft.setVisibility(View.INVISIBLE);
        //_Holder.leftQty.setVisibility(View.INVISIBLE);
        _Holder.pu_order_b = order_b;
        _Holder.baseAdapter = this;
        return convertView;
    }



}
