package com.example.travelin;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.DialogInterface;
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
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private String defaultRoomName = "observable-room";
    private EditText editText;
    private Scaledrone scaledrone;
    private MessageAdapter messageAdapter;
    private ListView messagesView;
    private ImageButton sendImage;
    private boolean image = false;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://fir-learn-f2515.appspot.com");
    private Bitmap bmp;
    private byte[] bArray;
    private Button camera;
    private Button gallery;
    private Uri outputFileUri;
    static final int CAMERA_REQUEST_CODE = 2666;
    User u = new User();
    String uStr = "";
    private Button addUserButton;
    private FirebaseAuth mAuth;
    private boolean sent = false;
    private FloatingActionButton viewUsers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_message);
        mAuth = FirebaseAuth.getInstance();

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference ref=database.getReference();
        final DatabaseReference usesRef=ref.child("Users");

        final String roomName = getIntent().getExtras().getString("room");
        final String name = getIntent().getExtras().getString("name");
        defaultRoomName = roomName;

        viewUsers = findViewById(R.id.viewUsersInRoom);
        viewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageActivity.this, ViewUsersInRoomActivity.class);
                //intent.putExtra("name", u.getName());
                intent.putExtra("room",roomName);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        createNotificationChannel();

        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        usesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    if (firebaseUser.getUid().equals(postSnapshot.getKey())) {
                        uStr = user.getName();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editText = (EditText) findViewById(R.id.editText);

        messageAdapter = new MessageAdapter(this);
        messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);

        addUserButton = findViewById(R.id.adduser_button);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MessageActivity.this);
                alertDialog.setTitle("Invite New User");
                alertDialog.setMessage("Enter User email");

                final EditText editText = new EditText(getApplicationContext());
                alertDialog.setView(editText);

                alertDialog.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String checkEmpty = editText.getText().toString();
                        if (checkEmpty.equals("")) {
                            // TODO : MAKE TOAST
                        } else {
                            final String userEmail = checkEmpty;
                            usesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User u = null;
                                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                        u = snap.getValue(User.class);
                                        if (userEmail.equals(u.getEmail())) {
                                            u.addInvite(roomName);
                                            DatabaseReference updateData = null;
                                            updateData = FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(snap.getKey());

                                            updateData.child("roomInvites").setValue(u.getRoomInvites());

                                            return;
                                        }
                                    }

                                    Toast toast = Toast.makeText(getApplicationContext(), "INVALID EMAIL ADDRESS", Toast.LENGTH_LONG);
                                    toast.show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }
                });

                alertDialog.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO : MAKE TOAST
                    }
                });

                alertDialog.show();
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

        MemberData data = new MemberData(uStr, getRandomColor());

        scaledrone = new Scaledrone(channelID, data);
        scaledrone.connect(new Listener() {
            @Override
            public void onOpen() {
                System.out.println("Scaledrone connection open");
                //scaledrone.subscribe(roomName, MessageActivity.this);
                Room room = scaledrone.subscribe(roomName, new RoomListener() {
                    // implement the default RoomListener methods here

                    @Override
                    public void onOpen(Room room) {

                    }

                    @Override
                    public void onOpenFailure(Room room, Exception ex) {

                    }

                    @Override
                    public void onMessage(Room room, com.scaledrone.lib.Message receivedMessage) {
                        System.out.println("pls");
                        final ObjectMapper mapper = new ObjectMapper();
                        try {
                            System.out.println("what about here");
                            final MemberData data = mapper.treeToValue(receivedMessage.getMember().getClientData(), MemberData.class);
                            final boolean belongsToCurrentUser = receivedMessage.getClientID().equals(scaledrone.getClientID());
                            System.out.println("did we even get to the data lol");
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
                                sendNotification(data.getName(),"Uploaded image");

                            } else {
                                final Message message = new Message(receivedMessage.getData().asText(), data, belongsToCurrentUser);
                                sendNotification(data.getName(),message.getText());
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
                }, new SubscribeOptions(50));

                room.listenToHistoryEvents(new HistoryRoomListener() {
                    @Override
                    public void onHistoryMessage(Room room, com.scaledrone.lib.Message receivedMessage) {
                        final ObjectMapper mapper = new ObjectMapper();
                        final MemberData data = new MemberData(" ", "#000000");
                        final boolean belongsToCurrentUser = receivedMessage.getClientID().equals(scaledrone.getClientID());

                        if (receivedMessage.getData().asText().charAt(receivedMessage.getData().asText().length() - 1) == '|') {
                            try {
                                String str = receivedMessage.getData().asText().substring(0, receivedMessage.getData().asText().length() - 1);
                                StorageReference fuckthis = storage.getReferenceFromUrl(str);
                                final long OM = 5000 * 500000000;
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
                            } catch (Exception e) {
                                System.out.println("ripmessagelol");
                            }

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
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {

                        }
                    }
                });
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

    public void sendNotification(String title, String text) {
        if (!sent) {
            Intent resultIntent = new Intent(this, MessageActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntentWithParentStack(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Travelin")
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(text))
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setContentIntent(resultPendingIntent);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(10, builder.build());
        } else {
            sent = false;
        }
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
                            scaledrone.publish(defaultRoomName, message);
                            editText.getText().clear();
                        }
                    } else {

                    }
                }
            });
            sent = true;
        } else {
            String message = editText.getText().toString();
            if (message.length() > 0) {
                scaledrone.publish(defaultRoomName, message);
                editText.getText().clear();
                sent = true;
            }
        }

    }

    @Override
    public void onOpen(Room room) {
        System.out.println("Conneted to room");
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
                if (!sent) {
                    final Message message = new Message("Uploaded image", data, belongsToCurrentUser);
                    Intent resultIntent = new Intent(this, MessageActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                    stackBuilder.addNextIntentWithParentStack(resultIntent);
                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Travelin")
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle(data.getName())
                            .setContentText(receivedMessage.getData().asText())
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(receivedMessage.getData().asText()))
                            .setPriority(NotificationCompat.PRIORITY_HIGH);
                    builder.setContentIntent(resultPendingIntent);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    notificationManager.notify(10, builder.build());
                }
                else {
                    sent = false;
                }

            } else {
                final Message message = new Message(receivedMessage.getData().asText(), data, belongsToCurrentUser);
                if (!sent) {
                    System.out.println("check 1");
                    Intent resultIntent = new Intent(this, MessageActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                    stackBuilder.addNextIntentWithParentStack(resultIntent);
                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    System.out.println("intents OK");
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Travelin")
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle(data.getName())
                            .setContentText(receivedMessage.getData().asText())
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(receivedMessage.getData().asText()))
                            .setPriority(NotificationCompat.PRIORITY_HIGH);
                    System.out.println("notification OK");
                    builder.setContentIntent(resultPendingIntent);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    notificationManager.notify(10, builder.build());
                    System.out.println("notification sent");
                } else {
                    sent = false;
                }
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
