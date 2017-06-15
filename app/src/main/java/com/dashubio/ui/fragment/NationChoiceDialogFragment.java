
package com.dashubio.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;

import com.dashubio.R;

/**
 * 民族选择
 */
public class NationChoiceDialogFragment extends DialogFragment {

    private static final String NATION_DATA_EXTRA = "extra_nation_data";
    private String mNation;

    /**
     * Factory method to generate a new instance of the fragment given an image number.
     *
     * @param nation The image url to load
     * @return A new instance of ImageDetailFragment with imageNum extras
     */
    public static NationChoiceDialogFragment newInstance(String nation) {
        final NationChoiceDialogFragment f = new NationChoiceDialogFragment();

        final Bundle args = new Bundle();
        args.putString(NATION_DATA_EXTRA, nation);
        f.setArguments(args);

        return f;
    }

    /**
     * Empty constructor as per the Fragment documentation
     */
    public NationChoiceDialogFragment() {
    }


    // Use this instance of the interface to deliver action events
    private NationChoiceListener mNationChoiceListener;

    public void setNationChoiceListener(NationChoiceListener nationChoiceListener) {
        this.mNationChoiceListener = nationChoiceListener;
    }

    int itemSelectedNum = 0;


    /**
     * Populate image using a url from extras, use the convenience factory method
     * {@link NationChoiceDialogFragment#newInstance(String)} to create this fragment.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNation = getArguments() != null ? getArguments().getString(NATION_DATA_EXTRA) : null;
        if (!TextUtils.isEmpty(mNation)) {
            String[] nationList = getResources().getStringArray(R.array.nationResArray);
            if(nationList != null){
                for(int i = 0; i < nationList.length; i++){
                    String curNationStr = nationList[i];
                    if(!TextUtils.isEmpty(curNationStr) && curNationStr.contains(mNation)){
                        itemSelectedNum = i;
                        break;
                    }
                }
            }
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Activity activity = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Set the dialog title
        builder.setTitle(R.string.choice_nation)
                // Specify the list array, the items to be selected by default
                // (null for none),
                // and the listener through which to receive callbacks when
                // items are selected
                .setSingleChoiceItems(R.array.nationResArray, itemSelectedNum,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                itemSelectedNum = which;
                            }
                        })
                // Set the action buttons
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                String[] nationList = getResources().getStringArray(R.array.nationResArray);
                                mNationChoiceListener.onNationChoiceSingleChoiceComplete(nationList[itemSelectedNum]);
                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
        return builder.create();
    }

    public interface NationChoiceListener {
        void onNationChoiceSingleChoiceComplete(String nation);
    }

}
