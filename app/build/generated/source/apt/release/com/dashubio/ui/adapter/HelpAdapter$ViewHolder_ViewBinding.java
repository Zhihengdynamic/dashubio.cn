// Generated code from Butter Knife. Do not modify!
package com.dashubio.ui.adapter;

import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.dashubio.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class HelpAdapter$ViewHolder_ViewBinding<T extends HelpAdapter.ViewHolder> implements Unbinder {
  protected T target;

  public HelpAdapter$ViewHolder_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.tvItemHelpContent = finder.findRequiredViewAsType(source, R.id.tv_item_help_content, "field 'tvItemHelpContent'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tvItemHelpContent = null;

    this.target = null;
  }
}
