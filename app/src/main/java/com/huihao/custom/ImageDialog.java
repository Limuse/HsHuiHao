package com.huihao.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huihao.R;

/**
 * Created by admin on 2015/8/5.
 */
public class ImageDialog extends Dialog {

    public ImageDialog(Context context) {
        super(context);
    }
    public ImageDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String info;
        private int imageRes;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = "邀请新伙伴 立得<font color='#ff4400'>"+message+"</font>元";
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
        public Builder setInfo(String num1,String num2) {
            this.info = "邀请新伙伴 他得<font color='#ff4400'>"+num1+"</font>元 你得<font color='#ff4400'>"+num2+"</font>元";
            return this;
        }
        public Builder setImage(int res) {
            this.imageRes = res;
            return this;
        }


        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public ImageDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ImageDialog dialog = new ImageDialog(context,R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_image, null);

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

            if (info != null) {
                ((TextView) layout.findViewById(R.id.dialog_info)).setText(Html.fromHtml(info));
            }
            if (imageRes != 0) {
                ((ImageView) layout.findViewById(R.id.dialog_logo)).setImageResource(imageRes);
            }

            if (message != null) {
                ((TextView) layout.findViewById(R.id.dialog_title)).setText(Html.fromHtml(message));
            } else if (contentView != null) {
                ((LinearLayout) layout.findViewById(R.id.dialog_content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.dialog_content))
                        .addView(contentView, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
            }
            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }
    }
}
