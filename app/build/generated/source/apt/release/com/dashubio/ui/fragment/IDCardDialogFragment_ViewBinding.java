// Generated code from Butter Knife. Do not modify!
package com.dashubio.ui.fragment;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.dashubio.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class IDCardDialogFragment_ViewBinding<T extends IDCardDialogFragment> implements Unbinder {
  protected T target;

  public IDCardDialogFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.saveBtn = finder.findRequiredViewAsType(source, R.id.saveBtn, "field 'saveBtn'", Button.class);
    target.cancelBtn = finder.findRequiredViewAsType(source, R.id.cancelBtn, "field 'cancelBtn'", Button.class);
    target.bottomContainer = finder.findRequiredViewAsType(source, R.id.bottomContainer, "field 'bottomContainer'", LinearLayout.class);
    target.mNameEt = finder.findRequiredViewAsType(source, R.id.name_et, "field 'mNameEt'", EditText.class);
    target.mGenderEt = finder.findRequiredViewAsType(source, R.id.gender_et, "field 'mGenderEt'", TextView.class);
    target.mFolkEt = finder.findRequiredViewAsType(source, R.id.folk_et, "field 'mFolkEt'", EditText.class);
    target.mBirthEt = finder.findRequiredViewAsType(source, R.id.birth_et, "field 'mBirthEt'", EditText.class);
    target.mAddressEt = finder.findRequiredViewAsType(source, R.id.address_et, "field 'mAddressEt'", EditText.class);
    target.mIDNumberEt = finder.findRequiredViewAsType(source, R.id.id_number_et, "field 'mIDNumberEt'", EditText.class);
    target.mPhoneNumberEt = finder.findRequiredViewAsType(source, R.id.phone_number_et, "field 'mPhoneNumberEt'", EditText.class);
    target.mContactsNumber = finder.findRequiredViewAsType(source, R.id.edt_contacts_number, "field 'mContactsNumber'", EditText.class);
    target.layXingbie = finder.findRequiredViewAsType(source, R.id.lay_xingbie, "field 'layXingbie'", LinearLayout.class);
    target.layMingzu = finder.findRequiredViewAsType(source, R.id.lay_mingzu, "field 'layMingzu'", LinearLayout.class);
    target.layChusheng = finder.findRequiredViewAsType(source, R.id.lay_chusheng, "field 'layChusheng'", LinearLayout.class);
    target.layDiqu = finder.findRequiredViewAsType(source, R.id.lay_diqu, "field 'layDiqu'", LinearLayout.class);
    target.layDizhi = finder.findRequiredViewAsType(source, R.id.lay_dizhi, "field 'layDizhi'", LinearLayout.class);
    target.layMphone = finder.findRequiredViewAsType(source, R.id.lay_mphone, "field 'layMphone'", LinearLayout.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.saveBtn = null;
    target.cancelBtn = null;
    target.bottomContainer = null;
    target.mNameEt = null;
    target.mGenderEt = null;
    target.mFolkEt = null;
    target.mBirthEt = null;
    target.mAddressEt = null;
    target.mIDNumberEt = null;
    target.mPhoneNumberEt = null;
    target.mContactsNumber = null;
    target.layXingbie = null;
    target.layMingzu = null;
    target.layChusheng = null;
    target.layDiqu = null;
    target.layDizhi = null;
    target.layMphone = null;

    this.target = null;
  }
}
