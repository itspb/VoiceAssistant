package com.example.android.voiceassistant;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.voiceassistant.pojo.Message;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button sendButton;
    private EditText editTextQuestion;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private TextToSpeech textToSpeech;
    private AIViewModel aiViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = findViewById(R.id.sendButton);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        recyclerView = findViewById(R.id.recyclerView);
        messageAdapter = new MessageAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        aiViewModel = ViewModelProviders.of(this).get(AIViewModel.class);
        aiViewModel.getResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String answer) {
                if (answer != null) {
                    messageAdapter.messageList.add(new Message(answer, false));
                    messageAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(messageAdapter.messageList.size() - 1);
                    textToSpeech.speak(answer, TextToSpeech.QUEUE_ADD, null, null);
                }
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = editTextQuestion.getText().toString();
                if (!question.isEmpty()) {
                    messageAdapter.messageList.add(new Message(question, true));
                    aiViewModel.getAnswer(question);
                    editTextQuestion.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Сначала введите что-нибудь", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("ru"));
                }
            }
        });




    }
}
