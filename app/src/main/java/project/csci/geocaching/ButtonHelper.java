package project.csci.geocaching;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

public class ButtonHelper {

    public void buttonClickSetter(final Activity activity, final View button) {
       button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    button.setBackground(activity.getResources().getDrawable(R.drawable.round_button_down,null));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button.setBackground(activity.getResources().getDrawable(R.drawable.round_button_up,null));
                }
                return false;
            }
        });
    }
}
