// Generated code from Butter Knife. Do not modify!
package com.dashubio.ui.fragment.second;

import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.dashubio.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class SecondHealthWarningFragment_ViewBinding<T extends SecondHealthWarningFragment> implements Unbinder {
  protected T target;

  public SecondHealthWarningFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.mHealthWarningSpinner = finder.findRequiredViewAsType(source, R.id.health_warning_spinner, "field 'mHealthWarningSpinner'", Spinner.class);
    target.mPullRefreshListView = finder.findRequiredViewAsType(source, R.id.pullToRefreshListView, "field 'mPullRefreshListView'", PullToRefreshListView.class);
    target.tvNonedata = finder.findRequiredViewAsType(source, R.id.tv_nonedata, "field 'tvNonedata'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mHealthWarningSpinner = null;
    target.mPullRefreshListView = null;
    target.tvNonedata = null;

    this.target = null;
  }
}
