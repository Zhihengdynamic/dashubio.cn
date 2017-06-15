package com.dashubio.ui.fragment.second;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.ui.fragment.base.BaseFragment;
import com.dashubio.utils.ViewHolder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

// 第二登陆人首页--检查单数据
public class SecondIndexFragment extends BaseFragment {

	View view = null;
	TextView tv_time;
	PullToRefreshListView lv_record;
	private boolean isRefreshing = false;
	private RecordAdapter adapter;
	private int num = 0;
	private int currentPosition = -1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_index, container,false);
		initView();
		return view;
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		tv_time = (TextView) view.findViewById(R.id.tv_time);
		lv_record = (PullToRefreshListView) view.findViewById(R.id.lv_record);
		lv_record.setMode(Mode.BOTH);  
		lv_record.getLoadingLayoutProxy(false, true).
		setPullLabel(getString(R.string.pull_to_load));  
		lv_record.getLoadingLayoutProxy(false, true).
		setRefreshingLabel(getString(R.string.loading));  
		lv_record.getLoadingLayoutProxy(false, true).
		setReleaseLabel(getString(R.string.release_to_load)); 
		lv_record.setOnRefreshListener(new OnRefreshListener<ListView>(){  

				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//					// TODO Auto-generated method stub
//					 if (!isRefreshing ) {
//						 isRefreshing = true;
//
//					        if (lv_record.isHeaderShown()) {
//					        	num = 0;
//					        	if (adapter != null) {
//							    	adapter.clear();
//								}
//					            // 下拉刷新
//					            refreshDown();
//					        } else if (lv_record.isFooterShown()) {
//					        	num += 1;
//					            // 上拉加载
//					        	loadNextPageLv();
//					        }
//					    } else {
//					    	lv_record.onRefreshComplete();
//					    }
				}
	        });
		
		adapter = new RecordAdapter();
		lv_record.setAdapter(adapter);
	}
	

	// 下拉刷新 
	private void refreshDown() {
		// TODO Auto-generated method stub
		
	}
	
	// 上拉加载  
	private void loadNextPageLv() {
		// TODO Auto-generated method stub
		
	}
	
	class RecordAdapter extends BaseAdapter {
		
		private LayoutInflater mInflater = null;
		private ViewHolder holder;
		public RecordAdapter () {
			this.mInflater = LayoutInflater.from(getActivity().getApplicationContext());
		}
		
		public void addData() {
//			this.infos.addAll(list);
			notifyDataSetChanged();
		}
		
		public void clear() {
//			if (infos != null) {
//				infos.clear();
//			}
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 10;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup parent) {
			// TODO Auto-generated method stub
			holder = new ViewHolder();
			if (arg1 == null) {
				arg1 = mInflater.inflate(
						R.layout.list_item_index,
						null);
				holder.tv_item_index_name = (TextView) arg1
						.findViewById(R.id.tv_item_index_name);
				holder.tv_item_index_age = (TextView) arg1
						.findViewById(R.id.tv_item_index_age);
				holder.tv_item_index_subject = (TextView) arg1
						.findViewById(R.id.tv_item_index_subject);
				holder.tv_item_index_time = (TextView) arg1
						.findViewById(R.id.tv_item_index_time);
//				holder.tv_item_index_result = (TextView) arg1
//						.findViewById(R.id.tv_item_index_result);
//				holder.rl_item_index_result = (RelativeLayout) arg1
//						.findViewById(R.id.rl_item_index_result);
//				holder.tv_item_index_result_status = (TextView) arg1
//						.findViewById(R.id.tv_item_index_result_status);
//				holder.img_item_index_warning = (ImageView) arg1
//						.findViewById(R.id.img_item_index_warning);
//				holder.tv_item_index_result_detail = (TextView) arg1
//						.findViewById(R.id.tv_item_index_result_detail);
				
				arg1.setTag(holder);
			} else {
				holder = (ViewHolder) arg1.getTag();
			}	
//			ItemListener itemListener = new ItemListener(position);
//			holder.tv_item_index_result.setOnClickListener(itemListener);
			holder.rl_item_index_result.setVisibility(View.GONE);
			if (position == currentPosition) {
				holder.rl_item_index_result.setVisibility(View.VISIBLE);
        		if (position % 2 == 0) {
        			holder.tv_item_index_result_status.setText("血压水平正常");
        			holder.img_item_index_warning.setVisibility(View.GONE);
    			} else {
    				holder.tv_item_index_result_status.setText("血压水平偏高");
        			holder.img_item_index_warning.setVisibility(View.VISIBLE);
    			}
			}
			return arg1;
		}
	
//		class ItemListener implements OnClickListener {
//	        private int m_position;
//
//	        ItemListener(int pos) {
//	            m_position = pos;
//	        }
//
//	        @Override
//	        public void onClick(View v) {
//	        	switch (v.getId()) {
//	        	case R.id.tv_item_index_result: // 查看按钮
//	        		if (currentPosition == m_position) {
//	        			currentPosition = -1;
//					} else {
//						currentPosition = m_position;
//					}
//	        		adapter.notifyDataSetChanged();
//	        		break;
//				default:
//					break;
//				}
//
//	        }
//		}
		
	}
}
