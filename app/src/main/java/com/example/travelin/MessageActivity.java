package com.example.travelin;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.scaledrone.lib.HistoryRoomListener;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;
import com.scaledrone.lib.SubscribeOptions;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MessageActivity extends AppCompatActivity implements RoomListener {

    // replace this with a real channelID from Scaledrone dashboard
    private String channelID = "SvLIfI23JVDW8Jre";
    private String roomName = "observable-room";
    private EditText editText;
    private Scaledrone scaledrone;
    private MessageAdapter messageAdapter;
    private ListView messagesView;
    private ImageButton sendImage;
    private boolean image = false;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Bitmap bmp;
    private byte[] bArray;
    private Button camera;
    private Button gallery;
    private Uri outputFileUri;
    static final int CAMERA_REQUEST_CODE = 2666;
    User u = new User();
    private Button addUserButton;
    private String messageUserEmail = "";
    private FirebaseAuth mAuth;
    //private String newRoomName = "";
    //private String curRoom;
    //private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_message);

        final String roomName = getIntent().getExtras().getString("room");
        final String name = getIntent().getExtras().getString("name");

        createNotificationChannel();

        editText = (EditText) findViewById(R.id.editText);
        //roomName = getIntent().getExtras().getString("room");

        messageAdapter = new MessageAdapter(this);
        messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);

        addUserButton = findViewById(R.id.adduser_button);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : CREATE BOX TO LET THEM SEARCH BY NAME
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference();
                DatabaseReference userRef = ref.child("Users");

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String myEmail = "";
                        User me = null;
                        User them = null;
                        DatabaseReference updateData = null;
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            if (mAuth.getCurrentUser().getUid().equals(snap.getKey())) {
                                myEmail = snap.getValue(User.class).getEmail();
                                me = snap.getValue(User.class);
                            }
                            if (snap.getValue(User.class).getEmail().equals(messageUserEmail)) {
                                them = snap.getValue(User.class);
                                updateData= FirebaseDatabase.getInstance().getReference("Users")
                                        .child(snap.getKey());
                            }
                        }

                        String blocked = them.getBlock();
                        String[] blockedUsers = blocked.split(",");
                        boolean isBlocked = false;

                        for (int i = 0; i < blockedUsers.length; i++) {
                            if (myEmail.equals(blockedUsers[i])) {
                                isBlocked = true;
                                break;
                                // TODO : SOME WAY FOR THEM TO ACTUALLY ENTER ROOMNAME
                            }
                        }
                        if (!isBlocked) {
                            // TODO : TAKE TO SCREEN TO INPUT NEW ROOMNAME OR CHOOSE EXISTING
                            them.addInvite(roomName);
                            updateData.child("roomInvites").setValue(them.getRoomInvites());
                            // TODO : CONFIRM INVITE HAS BEEN SENT
                        } else {
                            // TODO : NOTIFY THAT CANNOT INVITE BECAUSE BLOCKED
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        sendImage = findViewById(R.id.sendimage_button);
        sendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = true;

                // Determine Uri of camera image to save.
                final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
                root.mkdirs();
                final String fname = "img_"+ System.currentTimeMillis() + ".jpg";
                final File sdImageMainDirectory = new File(root, fname);
                outputFileUri = Uri.fromFile(sdImageMainDirectory);

                // Camera.
                final List<Intent> cameraIntents = new ArrayList<Intent>();
                final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                final PackageManager packageManager = getPackageManager();
                final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
                for(ResolveInfo res : listCam) {
                    final String packageName = res.activityInfo.packageName;
                    final Intent intent = new Intent(captureIntent);
                    intent.setComponent(new ComponentName(packageName, res.activityInfo.name));
                    intent.setPackage(packageName);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    cameraIntents.add(intent);
                }

                // Filesystem.
                final Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                // Chooser of filesystem options.
                final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

                // Add the camera options.
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

                startActivityForResult(chooserIntent, 0);
            }
        });

        MemberData data = new MemberData(name, getRandomColor());

        scaledrone = new Scaledrone(channelID, data);
        scaledrone.connect(new Listener() {
            @Override
            public void onOpen() {
                System.out.println("Scaledrone connection open");
                scaledrone.subscribe(roomName, MessageActivity.this);
            }

            @Override
            public void onOpenFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onClosed(String reason) {
                System.err.println(reason);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImageUri = null;

        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                //Uri selectedImageUri;
                if (isCamera) {
                    selectedImageUri = outputFileUri;
                } else {
                    selectedImageUri = data == null ? null : data.getData();
                }
            }
            Uri targetUri = selectedImageUri;
            try {
                bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                bArray = stream.toByteArray();
                editText.setBackground(new BitmapDrawable(getResources(), bmp));
                //editText.setBackgroundResource(d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Travelin";
            String description = "Travelin notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Travelin", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }
    }

    public void sendMessage(View view) {
        if (image == true) {
            image = false;
            editText.setBackgroundResource(0);
            String path = "fuckthisbullshit/" + UUID.randomUUID() + ".png";
            final StorageReference fuckReg = storage.getReference(path);

            StorageMetadata meta = new StorageMetadata.Builder()
                    .setCustomMetadata("fuck", "fuckthis").build();
            UploadTask up = fuckReg.putBytes(bArray, meta);

            Task<Uri> urlTask = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fuckReg.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri downloadUri = null;
                    if (task.isSuccessful()) {
                        downloadUri = task.getResult();
                        System.out.println(downloadUri);
                        String message = downloadUri.toString() + "|";
                        if (message.length() > 0) {
                            scaledrone.publish(roomName, message);
                            editText.getText().clear();
                        }
                    } else {

                    }
                }
            });

        } else {
            String message = editText.getText().toString();
            if (message.length() > 0) {
                scaledrone.publish(roomName, message);
                editText.getText().clear();
            }
        }

    }

    @Override
    public void onOpen(Room room) {
        System.out.println("Connected to room");
    }

    @Override
    public void onOpenFailure(Room room, Exception ex) {
        System.err.println(ex);
    }

    @Override
    public void onMessage(Room room, com.scaledrone.lib.Message receivedMessage) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final MemberData data = mapper.treeToValue(receivedMessage.getMember().getClientData(), MemberData.class);
            final boolean belongsToCurrentUser = receivedMessage.getClientID().equals(scaledrone.getClientID());
            if (receivedMessage.getData().asText().charAt(receivedMessage.getData().asText().length() - 1) == '|') {
                String str = receivedMessage.getData().asText().substring(0, receivedMessage.getData().asText().length() - 1);
                StorageReference fuckthis = storage.getReferenceFromUrl(str);
                final long OM = 5000*500000000;
                fuckthis.getBytes(OM).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        final Message message1 = new Message(BitmapFactory.decodeByteArray(bytes, 0, bytes.length), data, belongsToCurrentUser);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                messageAdapter.add(message1);
                                messagesView.setSelection(messagesView.getCount() - 1);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("fuckthisnoise");
                    }
                });

                    Intent resultIntent = new Intent(this, MessageActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                    stackBuilder.addNextIntentWithParentStack(resultIntent);
                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Travelin")
                            .setSmallIcon(R.drawable.circle)
                            .setContentTitle(data.getName())
                            .setContentText(str)
                            .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(str))
                            .setPriority(NotificationCompat.PRIORITY_HIGH);
                    builder.setContentIntent(resultPendingIntent);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    notificationManager.notify(10, builder.build());
            } else {
                final Message message = new Message(receivedMessage.getData().asText(), data, belongsToCurrentUser);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageAdapter.add(message);
                        messagesView.setSelection(messagesView.getCount() - 1);
                    }
                });
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String getRandomName() {
        String[] adjs = {"autumn", "hidden", "bitter", "misty", "silent", "empty", "dry", "dark", "summer", "icy", "delicate", "quiet", "white", "cool", "spring", "winter", "patient", "twilight", "dawn", "crimson", "wispy", "weathered", "blue", "billowing", "broken", "cold", "damp", "falling", "frosty", "green", "long", "late", "lingering", "bold", "little", "morning", "muddy", "old", "red", "rough", "still", "small", "sparkling", "throbbing", "shy", "wandering", "withered", "wild", "black", "young", "holy", "solitary", "fragrant", "aged", "snowy", "proud", "floral", "restless", "divine", "polished", "ancient", "purple", "lively", "nameless"};
        String[] nouns = {"waterfall", "river", "breeze", "moon", "rain", "wind", "sea", "morning", "snow", "lake", "sunset", "pine", "shadow", "leaf", "dawn", "glitter", "forest", "hill", "cloud", "meadow", "sun", "glade", "bird", "brook", "butterfly", "bush", "dew", "dust", "field", "fire", "flower", "firefly", "feather", "grass", "haze", "mountain", "night", "pond", "darkness", "snowflake", "silence", "sound", "sky", "shape", "surf", "thunder", "violet", "water", "wildflower", "wave", "water", "resonance", "sun", "wood", "dream", "cherry", "tree", "fog", "frost", "voice", "paper", "frog", "smoke", "star"};
        return (
                adjs[(int) Math.floor(Math.random() * adjs.length)] +
                        "_" +
                        nouns[(int) Math.floor(Math.random() * nouns.length)]
        );
    }

    private String getRandomColor() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer("#");
        while(sb.length() < 7){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }
}

class MemberData {
    private String name;
    private String color;

    public MemberData(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public MemberData() {
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "MemberData{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
