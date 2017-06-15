// Generated code from Butter Knife. Do not modify!
package com.dashubio.ui.fragment;

import android.widget.Button;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.dashubio.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class RegisterFragment_ViewBinding<T extends RegisterFragment> implements Unbinder {
  protected T target;

  public RegisterFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.mIdCardRecognitionContainer = finder.findRequiredViewAsType(source, R.id.id_card_recognition_container, "field 'mIdCardRecognitionContainer'", LinearLayout.class);
    target.mManualRegisterBtn = finder.findRequiredViewAsType(source, R.id.manual_register_btn, "field 'mManualRegisterBtn'", Button.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mIdCardRecognitionContainer = null;
    target.mManualRegisterBtn = null;

    this.target = null;
  }
}
