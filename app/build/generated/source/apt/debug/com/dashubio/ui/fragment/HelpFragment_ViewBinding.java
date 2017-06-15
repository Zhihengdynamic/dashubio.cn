// Generated code from Butter Knife. Do not modify!
package com.dashubio.ui.fragment;

import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.dashubio.R;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class HelpFragment_ViewBinding<T extends HelpFragment> implements Unbinder {
  protected T target;

  public HelpFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.mPullRefreshExpandableListView = finder.findRequiredViewAsType(source, R.id.pull_refresh_expandable_list, "field 'mPullRefreshExpandableListView'", PullToRefreshExpandableListView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mPullRefreshExpandableListView = null;

    this.target = null;
  }
}
