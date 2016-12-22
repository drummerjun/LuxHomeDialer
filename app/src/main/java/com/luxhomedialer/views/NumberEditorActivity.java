package com.jun.luxhomedialer.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jun.luxhomedialer.Constants;
import com.jun.luxhomedialer.LoadBitmapTask;
import com.jun.luxhomedialer.R;

public class NumberEditorActivity extends AppCompatActivity{
    private static final String TAG = NumberEditorActivity.class.getSimpleName();
    private static final String KEY_TEMP = "TEMPKEY";
    private static final String KEY_URI = "TEMPURI";
    private static final String NUMBER_ID = "NUMBERID";
    private static final int CODE_CAMERA = 201;
    private static final int CODE_GALLERY = 202;
    private String function = Constants.CALL;
    private String tempUriString = "";
    private int TAG_ID = -1;
    private ImageView contactImage;
    private EditText tagEditor;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        function = getIntent().getStringExtra(Constants.FUNCTION);
        TAG_ID = getIntent().getIntExtra(NUMBER_ID, TAG_ID);
        if(TAG_ID == -1) {
            Log.e(TAG, "exiting with invalid tag ID=" + TAG_ID);
            finish();
        }

        SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, MODE_PRIVATE);
        String imageUriString = "";
        if (function.equals(Constants.CALL)) {
            imageUriString = pref.getString(Constants.URICALL + String.valueOf(TAG_ID), "");
        } else if (function.equals(Constants.SMS)) {
            imageUriString = pref.getString(Constants.URISMS + String.valueOf(TAG_ID), "");
        } else if (function.equals(Constants.SPEEDDIAL)) {
            imageUriString = pref.getString(Constants.URISPEED + String.valueOf(TAG_ID), "");
        }

        contactImage = (ImageView)findViewById(R.id.img_thumbnail);
        Log.w(TAG, "imageUriString=" + imageUriString);
        if(!imageUriString.isEmpty()) {
            new LoadBitmapTask().execute(this.getContentResolver(), imageUriString, contactImage);
        }
        contactImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {getResources().getString(R.string.dialog_camera),
                        getResources().getString(R.string.dialog_gallery)};

                AlertDialog.Builder builder = new AlertDialog.Builder(NumberEditorActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item) {
                            case 0:
                                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePicture, CODE_CAMERA);
                                break;
                            case 1:
                                Intent pickPhoto  = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto, CODE_GALLERY);
                                break;
                            default:
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        tagEditor = (EditText)findViewById(R.id.edittext_tag);
        tagEditor.setInputType(InputType.TYPE_CLASS_PHONE);
        tagEditor.setHint(getResources().getString(R.string.hint_phone));
        tagEditor.setText(pref.getString(function + String.valueOf(TAG_ID), ""));

        Button resetButton = (Button)findViewById(R.id.btn_delete);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(new ContextThemeWrapper(NumberEditorActivity.this, R.style.AlertDialogTheme))
                        .setMessage(R.string.msg_reset_number)
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing
                            }
                        })
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString(KEY_TEMP + TAG_ID, Constants.RESET);
                                editor.putString(KEY_URI + TAG_ID, "");
                                editor.apply();
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        Button okButton = (Button)findViewById(R.id.btn_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTag = tagEditor.getText().toString();
                if(newTag.isEmpty() && tempUriString.isEmpty()) {
                    finish();
                } else if(newTag.isEmpty()) {
                    new AlertDialog.Builder(new ContextThemeWrapper(NumberEditorActivity.this, R.style.AlertDialogTheme))
                            .setMessage(R.string.empty_num)
                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(KEY_URI + TAG_ID, tempUriString);
                    if(newTag.equals(getResources().getString(R.string.hint_number))) {
                        newTag = "";
                    }
                    editor.putString(KEY_TEMP + TAG_ID, newTag);
                    editor.apply();
                    finish();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "onResume");
        setTitle();
        SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, MODE_PRIVATE);
        String tempTag = pref.getString(KEY_TEMP + String.valueOf(TAG_ID), "");
        if(tempTag.equals(Constants.RESET)) {
            tagEditor.setText("");
        } else if(!tempTag.isEmpty()) {
            tagEditor.setText(tempTag);
        }

        String tempUri = pref.getString(KEY_URI + String.valueOf(TAG_ID), "");
        if(!tempUri.isEmpty()) {
            new LoadBitmapTask().execute(this.getContentResolver(), tempUri, contactImage);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case CODE_CAMERA:
            case CODE_GALLERY:
                if(resultCode == RESULT_OK){
                    Uri uriImage = data.getData();
                    new LoadBitmapTask().execute(this.getContentResolver(), uriImage, contactImage);
                    tempUriString = uriImage.toString();
                    //SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, MODE_PRIVATE);
                    //pref.edit().putString(KEY_URI, uriImage.toString()).apply();
                }
                break;
            default:
        }
    }

    private void setTitle() {
        String title = "";
        Resources res = getResources();
        if (function.equals(Constants.CALL)) {
            title = String.format(res.getString(R.string.default_call), TAG_ID);
        } else if (function.equals(Constants.SMS)) {
            title = String.format(res.getString(R.string.default_sms), TAG_ID);
        } else if (function.equals(Constants.SPEEDDIAL)) {
            title = String.format(res.getString(R.string.default_speed), TAG_ID);
        }
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onDestory");
    }
}
