package cn.tties.energy.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tties.energy.R;
import cn.tties.energy.application.MyApplication;
import cn.tties.energy.view.fragment.Questions_discussFragment;
import cn.tties.energy.view.fragment.Questions_progressFragment;


/**
 * Created by li on 2018/3/22
 * description：
 * author：guojlli
 */

public class MyQurestionTabAdapter extends FragmentPagerAdapter{
    String[] array;
    List<Fragment> list;
    public MyQurestionTabAdapter(FragmentManager fm,String[] array,List<Fragment> list) {
        super(fm);
        this.array=array;
        this.list=list;
    }

//    @Override
//    public Fragment getItem(int position) {
//        Fragment page = null;
//        if (list.size() > position) {
//            page = list.get(position);
//            if (page != null) {
//                return page;
//            }
//        }
//        while (position>=list.size()) {
//            list.add(null);
//        }
//        return list.get(position);
//    }
   @Override
   public Fragment getItem(int position) {
       Fragment page = null;
       if (list.size() > position) {
           page = list.get(position);
           if (page != null) {
               return page;
           }
       }
       while (position>=list.size()) {
           list.add(null);
       }
       switch (position%2) {
           case 0:
               page = new Questions_progressFragment();
               list.set(position, page);
               break;
           case 1:
               page = new Questions_discussFragment();
               list.set(position, page);
               break;

           default:
               break;
       }
       return page;

   }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return array[position];
    }

}
