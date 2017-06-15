// Generated code from Butter Knife. Do not modify!
package com.dashubio.ui.fragment.second;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import com.dashubio.R;
import com.dashubio.ui.view.NoScrollGridView;
import com.dashubio.ui.view.NoScrollListView;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class SecondHealthFilesFragment_ViewBinding<T extends SecondHealthFilesFragment> implements Unbinder {
  protected T target;

  private View view2131558734;

  private View view2131558737;

  private View view2131558740;

  public SecondHealthFilesFragment_ViewBinding(final T target, Finder finder, Object source) {
    this.target = target;

    View view;
    target.fathergridView = finder.findRequiredViewAsType(source, R.id.gv_father, "field 'fathergridView'", NoScrollGridView.class);
    target.songridView = finder.findRequiredViewAsType(source, R.id.gv_son, "field 'songridView'", NoScrollGridView.class);
    target.mothergridView = finder.findRequiredViewAsType(source, R.id.gv_mother, "field 'mothergridView'", NoScrollGridView.class);
    target.mLayoutFather = finder.findRequiredViewAsType(source, R.id.layout_father, "field 'mLayoutFather'", LinearLayout.class);
    target.mLayoutMother = finder.findRequiredViewAsType(source, R.id.layout_mother, "field 'mLayoutMother'", LinearLayout.class);
    target.mLayoutSon = finder.findRequiredViewAsType(source, R.id.layout_son, "field 'mLayoutSon'", LinearLayout.class);
    target.mRegisterBtn = finder.findRequiredViewAsType(source, R.id.register_btn, "field 'mRegisterBtn'", Button.class);
    target.mManualRegisterContainer = finder.findRequiredViewAsType(source, R.id.manual_register_container, "field 'mManualRegisterContainer'", ScrollView.class);
    target.mDiseaseContainer = finder.findRequiredViewAsType(source, R.id.disease_container, "field 'mDiseaseContainer'", LinearLayout.class);
    target.mDiseaseGridView = finder.findRequiredViewAsType(source, R.id.disease_gridView, "field 'mDiseaseGridView'", NoScrollGridView.class);
    target.mSelectDiseaseIv = finder.findRequiredViewAsType(source, R.id.select_disease_iv, "field 'mSelectDiseaseIv'", ImageView.class);
    target.mOperationContainer = finder.findRequiredViewAsType(source, R.id.operation_container, "field 'mOperationContainer'", LinearLayout.class);
    target.mOperationLv = finder.findRequiredViewAsType(source, R.id.operation_lv, "field 'mOperationLv'", NoScrollListView.class);
    target.mTraumaContainer = finder.findRequiredViewAsType(source, R.id.trauma_container, "field 'mTraumaContainer'", LinearLayout.class);
    target.mTraumaLv = finder.findRequiredViewAsType(source, R.id.trauma_lv, "field 'mTraumaLv'", NoScrollListView.class);
    target.mBloodTransfusionContainer = finder.findRequiredViewAsType(source, R.id.bloodTransfusion_container, "field 'mBloodTransfusionContainer'", LinearLayout.class);
    target.mBloodTransfusionLv = finder.findRequiredViewAsType(source, R.id.bloodTransfusion_lv, "field 'mBloodTransfusionLv'", NoScrollListView.class);
    view = finder.findRequiredView(source, R.id.select_img_father, "method 'OnGrListener'");
    view2131558734 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnGrListener(p0);
      }
    });
    view = finder.findRequiredView(source, R.id.select_img_mother, "method 'OnGrListener'");
    view2131558737 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnGrListener(p0);
      }
    });
    view = finder.findRequiredView(source, R.id.select_img_son, "method 'OnGrListener'");
    view2131558740 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnGrListener(p0);
      }
    });
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.fathergridView = null;
    target.songridView = null;
    target.mothergridView = null;
    target.mLayoutFather = null;
    target.mLayoutMother = null;
    target.mLayoutSon = null;
    target.mRegisterBtn = null;
    target.mManualRegisterContainer = null;
    target.mDiseaseContainer = null;
    target.mDiseaseGridView = null;
    target.mSelectDiseaseIv = null;
    target.mOperationContainer = null;
    target.mOperationLv = null;
    target.mTraumaContainer = null;
    target.mTraumaLv = null;
    target.mBloodTransfusionContainer = null;
    target.mBloodTransfusionLv = null;

    view2131558734.setOnClickListener(null);
    view2131558734 = null;
    view2131558737.setOnClickListener(null);
    view2131558737 = null;
    view2131558740.setOnClickListener(null);
    view2131558740 = null;

    this.target = null;
  }
}
