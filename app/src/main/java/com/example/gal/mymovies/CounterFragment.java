package com.example.gal.mymovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CounterFragment extends Fragment {

    private static final String COUNTER_ARGS_MESSAGE = "counter_args_message";

    public static CounterFragment newInstance(String message) {

        CounterFragment counterFragment = new CounterFragment();
        Bundle bundle = new Bundle();
        bundle.putString(COUNTER_ARGS_MESSAGE, message);
        counterFragment.setArguments(bundle);

        return counterFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_counter,
                container, false);

        View layout = view.findViewById(R.id.counter_fl);
        TextView textView = view.findViewById(R.id.counter_tv);

        String message = getArguments().getString(COUNTER_ARGS_MESSAGE);
        textView.setText(message);
        return view;

//        CounterAsyncTask task = new CounterAsyncTask(textView);
//        task.execute(3,2,1);

    }

    private class CounterAsyncTask extends AsyncTask<Integer, Integer, String>{
        private TextView countDownTextView;

        public CounterAsyncTask(TextView countDownTV) {
            countDownTextView = countDownTV;
        }

        @Override
        protected void onPreExecute() {
            countDownTextView.setText("");

        }

        @Override
        protected String doInBackground(Integer... numbers) {
            for (Integer number : numbers) {
                publishProgress(numbers[number]);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "DONE";
        }

        @Override
        protected void onProgressUpdate(Integer... number) {
            countDownTextView.setText(String.valueOf(number[0]));

        }

        @Override
        protected void onPostExecute(String result) {
            countDownTextView.setText(result);
        }
    }
}
