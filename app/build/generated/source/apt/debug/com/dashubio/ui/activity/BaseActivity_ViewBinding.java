// Generated code from Butter Knife. Do not modify!
package com.dashubio.ui.activity;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import com.dashubio.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class BaseActivity_ViewBinding<T extends BaseActivity> implements Unbinder {
  protected T target;

  private View view2131558553;

  public BaseActivity_ViewBinding(final T target, Finder finder, Object source) {
    this.target = target;

    View view;
    view = finder.findOptionalView(source, R.id.header_left_container);
    target.mHeaderLeft = view;
    if (view != null) {
      view2131558553 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.goBack();
        }
      });
    }
    target.mHeaderTitle = finder.findOptionalViewAsType(source, R.id.header_title, "field 'mHeaderTitle'", TextView.class);
    target.mHeaderRight = finder.findOptionalView(source, R.id.header_right_container);
    target.mHeaderRightText = finder.findOptionalViewAsType(source, R.id.header_right_text, "field 'mHeaderRightText'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mHeaderLeft = null;
    target.mHeaderTitle = null;
    target.mHeaderRight = null;
    target.mHeaderRightText = null;

    if (view2131558553 != null) {
      view2131558553.setOnClickListener(null);
      view2131558553 = null;
    }

    this.target = null;
  }
}
