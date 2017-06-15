// Generated code from Butter Knife. Do not modify!
package com.dashubio.ui.fragment;

import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.dashubio.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class HelpContextFragment_ViewBinding<T extends HelpContextFragment> implements Unbinder {
  protected T target;

  public HelpContextFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.tvHelptitle = finder.findRequiredViewAsType(source, R.id.tv_helptitle, "field 'tvHelptitle'", TextView.class);
    target.tvHelpcontext = finder.findRequiredViewAsType(source, R.id.tv_helpcontext, "field 'tvHelpcontext'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tvHelptitle = null;
    target.tvHelpcontext = null;

    this.target = null;
  }
}
