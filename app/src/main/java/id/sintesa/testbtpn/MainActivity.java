package id.sintesa.testbtpn;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hilman.tauba.uimodule_utils.widgets.base_display_recycle_swipe_refresh.BaseDisplayDataAdapter;
import com.hilman.tauba.uimodule_utils.widgets.base_display_recycle_swipe_refresh.BaseDisplayDataManager;
import com.hilman.tauba.uimodule_utils.widgets.base_display_recycle_swipe_refresh.BaseDisplayDataScrollListener;
import com.hilman.tauba.uimodule_utils.widgets.base_display_recycle_swipe_refresh.BaseDisplayViewHolder;

import java.util.List;

import javax.inject.Inject;

import id.sintesa.testbtpn.databinding.ActivityMainBinding;
import id.sintesa.testbtpn.databinding.MainCardBinding;
import id.sintesa.testbtpn.modules.DaggerContactComponent;
import testbtpnmodule.entity.Contact;
import testbtpnmodule.service.ContactService;

public class MainActivity extends AppCompatActivity implements BaseDisplayDataScrollListener.BaseDisplayDataScrollInterface, BaseDisplayDataAdapter.IBaseDisplayInterface<Contact, BaseDisplayViewHolder>, ContactService.getContactCallback {

    public static final int ERROR_APP = 1;
    public static final int NO_ERROR = 0;

    @Inject
    ContactService mContactService;

    private BaseDisplayDataManager<Contact> manager;
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        DaggerContactComponent.builder().build().inject(this);
        manager = new BaseDisplayDataManager<>(this);
        manager.apply(mainBinding.recycler, mainBinding.swipeRefresh, this, this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        manager.getmScrollListener().loadFirstItem();
    }

    @Override
    public void onLoadMoreItems(String s, int i) {
        mContactService.getContacts(this);


    }

    @Override
    public BaseDisplayViewHolder onCreateViewItem(List<Contact> list, ViewGroup viewGroup, int i) {
        View v = getLayoutInflater().inflate(R.layout.main_card, viewGroup, false);
        return new BaseDisplayViewHolder(v);
    }

    @Override
    public BaseDisplayViewHolder onCreateLoadingItem(List<Contact> list, ViewGroup viewGroup, int i) {
        return new BaseDisplayViewHolder(getLayoutInflater().inflate(R.layout.loading, viewGroup, false));
    }

    @Override
    public void onBindLoadinng(BaseDisplayViewHolder baseDisplayViewHolder, int i, List<Contact> list) {
        baseDisplayViewHolder.getItemView().setVisibility(View.GONE);
    }

    @Override
    public void onBindItem(BaseDisplayViewHolder baseDisplayViewHolder, int i, List<Contact> list) {
        MainCardBinding mainCardBinding = DataBindingUtil.bind(baseDisplayViewHolder.getItemView());
        mainCardBinding.setAct(this);
        mainCardBinding.setPlaceHolderPhoto(R.drawable.ic_tag_faces_black_24dp);
        mainCardBinding.setContact(list.get(i));
        Glide.with(this).load(list.get(i).getPhoto()).placeholder(R.drawable.ic_tag_faces_black_24dp).circleCrop().into(mainCardBinding.image);
    }

    @Override
    public void onGetContacts(final List<Contact> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                manager.addData(list);
                manager.setLastPage();
            }
        });


    }

    @Override
    public void onErrorGetContacts(Exception e) {
        mainBinding.setError(ERROR_APP);
        e.printStackTrace();

    }
}
