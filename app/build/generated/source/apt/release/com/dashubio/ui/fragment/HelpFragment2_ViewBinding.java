// Generated code from Butter Knife. Do not modify!
package com.dashubio.ui.fragment;

import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.dashubio.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class HelpFragment2_ViewBinding<T extends HelpFragment2> implements Unbinder {
  protected T target;

  public HelpFragment2_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.lvHelp = finder.findRequiredViewAsType(source, R.id.lv_help, "field 'lvHelp'", ListView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.lvHelp = null;

    this.target = null;
  }
}
