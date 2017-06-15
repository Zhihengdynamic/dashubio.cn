// Generated code from Butter Knife. Do not modify!
package com.dashubio.ui.fragment.second;

import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.dashubio.R;
import com.todddavies.components.progressbar.ProgressWheel;
import com.zhy.view.flowlayout.TagFlowLayout;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class SecondMeasureFragment_ViewBinding<T extends SecondMeasureFragment> implements Unbinder {
  protected T target;

  public SecondMeasureFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.mDeviceStatusTv = finder.findRequiredViewAsType(source, R.id.device_status_tv, "field 'mDeviceStatusTv'", TextView.class);
    target.mLinkHealthInstrumentTv = finder.findRequiredViewAsType(source, R.id.link_health_instrument_tv, "field 'mLinkHealthInstrumentTv'", TextView.class);
    target.mMeasureHealthInstrumentTv = finder.findRequiredViewAsType(source, R.id.measure_health_instrument_tv, "field 'mMeasureHealthInstrumentTv'", TextView.class);
    target.mHealthDetectingTagflowlayout = finder.findRequiredViewAsType(source, R.id.health_detecting_instrument_tagflowlayout, "field 'mHealthDetectingTagflowlayout'", TagFlowLayout.class);
    target.mLinkDryBiochemicalAnalyzerTv = finder.findRequiredViewAsType(source, R.id.link_dry_biochemical_analyzer_tv, "field 'mLinkDryBiochemicalAnalyzerTv'", TextView.class);
    target.mMeasureDryBiochemicalAnalyzerTv = finder.findRequiredViewAsType(source, R.id.measure_dry_biochemical_analyzer_tv, "field 'mMeasureDryBiochemicalAnalyzerTv'", TextView.class);
    target.mLinkUrineAnalyzerTv = finder.findRequiredViewAsType(source, R.id.link_urine_analyzer_tv, "field 'mLinkUrineAnalyzerTv'", TextView.class);
    target.mMeasureUrineAnalyzerTv = finder.findRequiredViewAsType(source, R.id.measure_urine_analyzer_tv, "field 'mMeasureUrineAnalyzerTv'", TextView.class);
    target.mProgressWheel = finder.findRequiredViewAsType(source, R.id.pw_spinner, "field 'mProgressWheel'", ProgressWheel.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mDeviceStatusTv = null;
    target.mLinkHealthInstrumentTv = null;
    target.mMeasureHealthInstrumentTv = null;
    target.mHealthDetectingTagflowlayout = null;
    target.mLinkDryBiochemicalAnalyzerTv = null;
    target.mMeasureDryBiochemicalAnalyzerTv = null;
    target.mLinkUrineAnalyzerTv = null;
    target.mMeasureUrineAnalyzerTv = null;
    target.mProgressWheel = null;

    this.target = null;
  }
}
