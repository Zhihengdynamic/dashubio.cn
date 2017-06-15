// Generated code from Butter Knife. Do not modify!
package com.dashubio.gson.adapter;

import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.dashubio.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class ProvinceAdapter$ViewHolder_ViewBinding<T extends ProvinceAdapter.ViewHolder> implements Unbinder {
  protected T target;

  public ProvinceAdapter$ViewHolder_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.tvPopname = finder.findRequiredViewAsType(source, R.id.tv_popname, "field 'tvPopname'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tvPopname = null;

    this.target = null;
  }
}
