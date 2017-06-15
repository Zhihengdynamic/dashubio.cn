// Generated code from Butter Knife. Do not modify!
package com.dashubio.ui.fragment.second;

import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.dashubio.R;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.zhy.view.flowlayout.TagFlowLayout;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class SecondHistoryFragment_ViewBinding<T extends SecondHistoryFragment> implements Unbinder {
  protected T target;

  public SecondHistoryFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.mCategoryContainer = finder.findRequiredViewAsType(source, R.id.category_container, "field 'mCategoryContainer'", ScrollView.class);
    target.mDetectInstrument1Title = finder.findRequiredViewAsType(source, R.id.detect_instrument_1_title, "field 'mDetectInstrument1Title'", LinearLayout.class);
    target.mDetectInstrument1Tv = finder.findRequiredViewAsType(source, R.id.detect_instrument_1_tv, "field 'mDetectInstrument1Tv'", TextView.class);
    target.mDetectInstrument1Tagflowlayout = finder.findRequiredViewAsType(source, R.id.detect_instrument_1_tagflowlayout, "field 'mDetectInstrument1Tagflowlayout'", TagFlowLayout.class);
    target.mDetectInstrument2Title = finder.findRequiredViewAsType(source, R.id.detect_instrument_2_title, "field 'mDetectInstrument2Title'", LinearLayout.class);
    target.mDetectInstrument2Tv = finder.findRequiredViewAsType(source, R.id.detect_instrument_2_tv, "field 'mDetectInstrument2Tv'", TextView.class);
    target.mDetectInstrument2Tagflowlayout = finder.findRequiredViewAsType(source, R.id.detect_instrument_2_tagflowlayout, "field 'mDetectInstrument2Tagflowlayout'", TagFlowLayout.class);
    target.mDetectInstrument3Title = finder.findRequiredViewAsType(source, R.id.detect_instrument_3_title, "field 'mDetectInstrument3Title'", LinearLayout.class);
    target.mDetectInstrument3Tv = finder.findRequiredViewAsType(source, R.id.detect_instrument_3_tv, "field 'mDetectInstrument3Tv'", TextView.class);
    target.mDetectInstrument3Tagflowlayout = finder.findRequiredViewAsType(source, R.id.detect_instrument_3_tagflowlayout, "field 'mDetectInstrument3Tagflowlayout'", TagFlowLayout.class);
    target.mDataShowContainer = finder.findRequiredView(source, R.id.data_show_container, "field 'mDataShowContainer'");
    target.mPullToRefreshExpandableListView = finder.findRequiredViewAsType(source, R.id.historical_data_lv, "field 'mPullToRefreshExpandableListView'", PullToRefreshExpandableListView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mCategoryContainer = null;
    target.mDetectInstrument1Title = null;
    target.mDetectInstrument1Tv = null;
    target.mDetectInstrument1Tagflowlayout = null;
    target.mDetectInstrument2Title = null;
    target.mDetectInstrument2Tv = null;
    target.mDetectInstrument2Tagflowlayout = null;
    target.mDetectInstrument3Title = null;
    target.mDetectInstrument3Tv = null;
    target.mDetectInstrument3Tagflowlayout = null;
    target.mDataShowContainer = null;
    target.mPullToRefreshExpandableListView = null;

    this.target = null;
  }
}
