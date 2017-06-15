// Generated code from Butter Knife. Do not modify!
package com.dashubio.ui.fragment.base;

import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.dashubio.R;
import com.dashubio.ui.view.TitleTextView;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class MenuFragment_ViewBinding<T extends MenuFragment> implements Unbinder {
  protected T target;

  public MenuFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.titv_title = finder.findRequiredViewAsType(source, R.id.titv_title, "field 'titv_title'", TitleTextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.titv_title = null;

    this.target = null;
  }
}
