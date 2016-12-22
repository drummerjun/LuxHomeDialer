package com.jun.luxhomedialer.views;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jun.luxhomedialer.Constants;
import com.jun.luxhomedialer.R;
import com.jun.luxhomedialer.components.CircularDrawable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class RfidSubActivity extends AppCompatActivity{
    private static final String TAG = RfidSubActivity.class.getSimpleName();
    private static final String KEY_TEMP = "TEMPKEY";
    private static final String KEY_IMG = "TEMPIMG";
    private static final int CODE_CONTACT = 101;
    private static final int CODE_CAMERA = 201;
    private static final int CODE_GALLERY = 202;
    private int TAG_ID = -1;
    private String contactID, imageUriString = "";
    private EditText tagEditor;
    private ImageView contactImage;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
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

        TAG_ID = getIntent().getIntExtra(Constants.RFIDTAG, TAG_ID);
        if(TAG_ID == -1) {
            finish();
        } else {
            Resources res = getResources();
            String title = String.format(res.getString(R.string.default_tag), TAG_ID);
            getSupportActionBar().setTitle(title);
        }

        tagEditor = (EditText)findViewById(R.id.edittext_tag);
        SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, MODE_PRIVATE);
        String label = pref.getString(Constants.RFIDTAG + String.valueOf(TAG_ID), "");
        if(label.equals(Constants.RESET)) {
            tagEditor.setText("");
        } else {
            tagEditor.setText(label);
        }

        Button resetButton = (Button)findViewById(R.id.btn_delete);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(new ContextThemeWrapper(RfidSubActivity.this, R.style.AlertDialogTheme))
                        .setMessage(R.string.msg_reset_tag)
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
                                //String defaultTag = getResources().getString(R.string.default_empty);
                                editor.putString(KEY_TEMP + TAG_ID, Constants.RESET);
                                editor.putString(KEY_IMG + TAG_ID, "");
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
                if(newTag.isEmpty() && imageUriString.isEmpty()) {
                    finish();
                } else if(newTag.isEmpty()) {
                    new AlertDialog.Builder(new ContextThemeWrapper(RfidSubActivity.this, R.style.AlertDialogTheme))
                            .setMessage(R.string.empty_name)
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
                    editor.putString(KEY_IMG + TAG_ID, imageUriString);
                    editor.putString(KEY_TEMP + TAG_ID, newTag);
                    editor.apply();
                    finish();
                }
            }
        });

        contactImage = (ImageView)findViewById(R.id.img_thumbnail);
        String imageUriString = pref.getString(Constants.URIRFID + String.valueOf(TAG_ID), "");
        parseConactImage(imageUriString);
        contactImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                //startActivityForResult(intent, CODE_CONTACT);

                final CharSequence[] items = {getResources().getString(R.string.dialog_camera),
                    getResources().getString(R.string.dialog_gallery)};

                AlertDialog.Builder builder = new AlertDialog.Builder(RfidSubActivity.this);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, MODE_PRIVATE);
        String tempTag = pref.getString(KEY_TEMP + String.valueOf(TAG_ID), "");
        if(tempTag.equals(Constants.RESET)) {
            tagEditor.setText("");
        } else if(!tempTag.isEmpty()) {
            tagEditor.setText(tempTag);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case CODE_CONTACT:
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    tagEditor.setText(retrieveContactName(contactData));
                    retrieveContactNumber(contactData);
                    retrieveContactPhoto();
                }
                break;
            case CODE_CAMERA:
            case CODE_GALLERY:
                if(resultCode == RESULT_OK){
                    Uri uriImage = data.getData();
                    setContactImage(uriImage);
                    imageUriString = uriImage.toString();
                }
                break;
            default:
        }
    }

    private String retrieveContactName(Uri uriContact) {
        String contactName = null;
        // querying contact data store
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);
        if (cursor.moveToFirst()) {
            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }
        cursor.close();
        Log.d(TAG, "Contact Name: " + contactName);
        return contactName;
    }

    private String retrieveContactNumber(Uri uriContact) {
        String contactNumber = null;
        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {
            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }
        cursorID.close();
        Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
                new String[]{contactID},
                null);
        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(
                    cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }
        cursorPhone.close();
        Log.d(TAG, "Contact Phone Number: " + contactNumber);
        return contactNumber;
    }

    private void retrieveContactPhoto() {
        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactID)));

            if (inputStream != null) {
                Bitmap photo = BitmapFactory.decodeStream(inputStream);
                CircularDrawable circularDrawable = new CircularDrawable(photo);
                contactImage.setImageDrawable(circularDrawable);
            }

            if(inputStream != null) {
                //assert inputStream != null;
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setContactImage(final Uri imageUri) {
        final ContentResolver contentResolver = this.getContentResolver();
        new AsyncTask<Void, Void, CircularDrawable>() {
            @Override
            protected CircularDrawable doInBackground(Void... params) {
                Bitmap srcBitmap;
                try {
                    //srcBitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
                    srcBitmap = getThumbnail(contentResolver, imageUri, 120);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

                Bitmap bitmap;
                if (srcBitmap.getWidth() >= srcBitmap.getHeight()){
                    bitmap = Bitmap.createBitmap(
                            srcBitmap,
                            srcBitmap.getWidth() / 2 - srcBitmap.getHeight() / 2,
                            0,
                            srcBitmap.getHeight(),
                            srcBitmap.getHeight()
                    );
                }else{
                    bitmap = Bitmap.createBitmap(
                            srcBitmap,
                            0,
                            srcBitmap.getHeight()/2 - srcBitmap.getWidth()/2,
                            srcBitmap.getWidth(),
                            srcBitmap.getWidth()
                    );
                }
                srcBitmap.recycle();
                srcBitmap = null;
                CircularDrawable circularDrawable = new CircularDrawable(bitmap);
                //bitmap.recycle();
                return circularDrawable;
            }

            @Override
            protected void onPostExecute(CircularDrawable circularDrawable) {
                super.onPostExecute(circularDrawable);
                if(circularDrawable != null) {
                    contactImage.setImageDrawable(circularDrawable);
                }
            }
        }.execute();
    }

    private void parseConactImage(String uriString) {
        if(!uriString.isEmpty()) {
            Uri imageUri = Uri.parse(uriString);
            setContactImage(imageUri);
        }
    }

    private static Bitmap getThumbnail(ContentResolver resolver, Uri uri, int thumbnailSize) throws FileNotFoundException, IOException{
        InputStream input = resolver.openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
        double ratio = (originalSize > thumbnailSize) ? (originalSize / thumbnailSize) : 1.0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither=true;//optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        input = resolver.openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }
}
