package com.vieboo.vbankapp.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.data.NoticeListVO;

/**
 * 🙏 GOD BLESS NO BUG !!! 🙏
 * Author： vieboo
 * Date： 2020/5/5 19:15
 * Description：
 */
public class NoticeListAdapter extends BaseQuickAdapter<NoticeListVO, BaseViewHolder> implements LoadMoreModule {

    private INoticeList iNoticeList;

    public NoticeListAdapter() {
        super(R.layout.item_notice_list, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeListVO item) {
        TextView tvSerialNum = helper.getView(R.id.tvSerialNum);
        TextView tvFilename = helper.getView(R.id.tvFilename);
        ImageView ivIsRead = helper.getView(R.id.ivIsRead);

        int adapterPosition = helper.getAdapterPosition() + 1;
        tvSerialNum.setText(String.valueOf(adapterPosition));

        tvFilename.setText(item.getTitle());

        int noticeStatus = item.getNoticeStatus();
        if (noticeStatus == 0) {
            //未读
            ivIsRead.setImageResource(R.drawable.ic_unread);
            iNoticeList.clickRead(item.getId() , item.getContent());
        } else {
            ivIsRead.setImageResource(R.drawable.ic_read);
        }
    }

    public void setINoticeList(INoticeList iNoticeList) {
        this.iNoticeList = iNoticeList;
    }

    public interface INoticeList {
        void clickRead(int id , String pdfUrl);
    }

}
