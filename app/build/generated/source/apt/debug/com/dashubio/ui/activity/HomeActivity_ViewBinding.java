// Generated code from Butter Knife. Do not modify!
package com.dashubio.ui.activity;

import butterknife.internal.Finder;
import com.dashubio.R;
import com.dashubio.ui.view.TitleTextView;
import java.lang.Object;
import java.lang.Override;

public class HomeActivity_ViewBinding<T extends HomeActivity> extends BaseActivity_ViewBinding<T> {
  public HomeActivity_ViewBinding(T target, Finder finder, Object source) {
    super(target, finder, source);

    target.titv_title = finder.findRequiredViewAsType(source, R.id.titv_title, "field 'titv_title'", TitleTextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.titv_title = null;
  }
}
