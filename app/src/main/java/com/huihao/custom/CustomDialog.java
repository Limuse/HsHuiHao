package com.huihao.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huihao.R;

/**
 * Created by admin on 2015/8/5.
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(context,R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_choose, null);

            DisplayMetrics dm = new DisplayMetrics();

            dialog.addContentView(layout, new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
            ((TextView) layout.findViewById(R.id.dialog_title)).setText(title);
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.dialog_right))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.dialog_right))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                layout.findViewById(R.id.dialog_right).setVisibility(
                        View.GONE);
            }
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.dialog_left))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.dialog_left))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                layout.findViewById(R.id.dialog_left).setVisibility(
                        View.GONE);
            }
            if (message != null) {
                ((TextView) layout.findViewById(R.id.dialog_title)).setText(message);
            } else if (contentView != null) {
                ((LinearLayout) layout.findViewById(R.id.dialog_content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.dialog_content))
                        .addView(contentView, new WindowManager.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }
}
