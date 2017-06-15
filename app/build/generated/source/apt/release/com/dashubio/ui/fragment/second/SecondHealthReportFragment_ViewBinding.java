// Generated code from Butter Knife. Do not modify!
package com.dashubio.ui.fragment.second;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import com.dashubio.R;
import com.dashubio.ui.view.ProgressWebView;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class SecondHealthReportFragment_ViewBinding<T extends SecondHealthReportFragment> implements Unbinder {
  protected T target;

  private View view2131558689;

  private View view2131558690;

  public SecondHealthReportFragment_ViewBinding(final T target, Finder finder, Object source) {
    this.target = target;

    View view;
    target.mPullRefreshExpandableListView = finder.findRequiredViewAsType(source, R.id.pull_refresh_expandable_list, "field 'mPullRefreshExpandableListView'", PullToRefreshExpandableListView.class);
    view = finder.findRequiredView(source, R.id.btn_details, "field 'btnDetails' and method 'onClick'");
    target.btnDetails = finder.castView(view, R.id.btn_details, "field 'btnDetails'", Button.class);
    view2131558689 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = finder.findRequiredView(source, R.id.btn_comprehensive, "field 'btnComprehensive' and method 'onClick'");
    target.btnComprehensive = finder.castView(view, R.id.btn_comprehensive, "field 'btnComprehensive'", Button.class);
    view2131558690 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.layoutBtn = finder.findRequiredViewAsType(source, R.id.layout_btn, "field 'layoutBtn'", LinearLayout.class);
    target.mWebContainer = finder.findRequiredView(source, R.id.web_container, "field 'mWebContainer'");
    target.mReportTitleTv = finder.findRequiredViewAsType(source, R.id.report_title_tv, "field 'mReportTitleTv'", TextView.class);
    target.mWebView = finder.findRequiredViewAsType(source, R.id.webview, "field 'mWebView'", ProgressWebView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mPullRefreshExpandableListView = null;
    target.btnDetails = null;
    target.btnComprehensive = null;
    target.layoutBtn = null;
    target.mWebContainer = null;
    target.mReportTitleTv = null;
    target.mWebView = null;

    view2131558689.setOnClickListener(null);
    view2131558689 = null;
    view2131558690.setOnClickListener(null);
    view2131558690 = null;

    this.target = null;
  }
}
