package idv.leo.oneatwob;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class MainActivity extends Activity
{
    int Count = 0,AA,BB,k,base,NUM;
    int [] Tag = new int[11];
    char [] ss = new char[5];
    String Answer;
    boolean flag = false, Double = false, Not4Digit = false, NotDigit = false, haveZero = false, haveNonDigit = false;
    String GenerateRandomNumber()
    {
        for(int t=1;t<=9;t++) Tag[t]=0;
        Tag[0]=20;
        int [] Rn = new int[5];
        Rn[0] = 1 + ((int)(Math.random()*8))%10;
        while(Tag[Rn[0]]>0)     Rn[0]= 1 + ((int)(Math.random()*8))%10;
        Tag[Rn[0]]++;
        Rn[1] = 1 + ((int)(Math.random()*8))%10;

        while(Tag[Rn[1]]>0)     Rn[1]= 1 + ((int)(Math.random()*8))%10;
        Tag[Rn[1]]++;
        Rn[2] = 1 + ((int)(Math.random()*8))%10;

        while(Tag[Rn[2]]>0)     Rn[2]= 1 + ((int)(Math.random()*8))%10;
        Tag[Rn[2]]++;
        Rn[3] = 1 + ((int)(Math.random()*8))%10;

        while(Tag[Rn[3]]>0)     Rn[3]= 1 + ((int)(Math.random()*8))%10;
        Tag[Rn[3]]++;

        base = 1;
        NUM = 0;
        for(int i=3;i>=0;i--)
        {
            NUM += base*Rn[i];
            base *= 10;
        }
        return String.valueOf(NUM);
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Answer = GenerateRandomNumber();
        final Button enter = (Button) findViewById(R.id.enter);
        final Button quit = (Button) findViewById(R.id.quitbut);
        final Button cont = (Button) findViewById(R.id.continuebut);
        quit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        flag = false;
        Count = 0;
        final TextView []show = new TextView [7];
        final TextView ANS = (TextView) findViewById(R.id.ANSTEXT);
        final EditText pass = (EditText) findViewById(R.id.guessed);
        pass.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        show[1] = (TextView) findViewById(R.id.TextView01);
        show[2] = (TextView) findViewById(R.id.TextView02);
        show[3] = (TextView) findViewById(R.id.TextView03);
        show[4] = (TextView) findViewById(R.id.TextView04);
        show[5] = (TextView) findViewById(R.id.TextView05);
        cont.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Answer = GenerateRandomNumber();
                for(int i=1;i<=5;i++) show[i].setText("");
                ANS.setText("");
                pass.setText("");
                flag = false;
                Count = 0;
                enter.setClickable(true);
                Toast.makeText(MainActivity.this, "請加油", Toast.LENGTH_SHORT).show();
            }
        });
        enter.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String guessed = pass.getText().toString();

                Not4Digit = Double = haveZero = haveNonDigit = false;
                if(guessed.length() != 4) {
                    Not4Digit = true;
                }
                else {
                    for(int i=0;i<4;i++)
                        for(int j=i+1;j<4;j++)
                            if(guessed.charAt(i) == guessed.charAt(j))
                                Double = true;
                    for(int i=0;i<4;i++)
                        if(guessed.charAt(i) == '0')
                            haveZero = true;
                    for(int i=0;i<4;i++)
                        if(guessed.charAt(i) < '0' || guessed.charAt(i) > '9')
                            haveNonDigit = true;
                }

                if(Not4Digit) {
                    Toast.makeText(MainActivity.this, "請輸入4個數字", Toast.LENGTH_LONG).show();
                    pass.setText("");
                }
                else if(Double) {
                    Toast.makeText(MainActivity.this, "4個數字都不能一樣", Toast.LENGTH_LONG).show();
                    pass.setText("");
                }
                else if(haveNonDigit) {
                    Toast.makeText(MainActivity.this, "只限定數字", Toast.LENGTH_LONG).show();
                    pass.setText("");
                }
                else if(haveZero) {
                    Toast.makeText(MainActivity.this, "數字為0~9", Toast.LENGTH_LONG).show();
                    pass.setText("");
                }
                else {
                    Count++;
                    pass.setText("");
                    if(guessed.equals(Answer))
                    {
                        flag = true;
                        if(Count <= 2)
                            Toast.makeText(MainActivity.this, "答對了", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "答對了!", Toast.LENGTH_LONG).show();
                        show[Count].setText(guessed+"    "+4+'A'+0+'B');
                        ANS.setText("答案:"+ Answer);
                        enter.setClickable(false);
                    }
                    else
                    {
                        k = 0;
                        AA = BB = 0;
                        for(int i=0;i<4;i++)
                        {
                            if(guessed.charAt(i) == Answer.charAt(i))
                                AA++;
                            else
                                ss[k++]=guessed.charAt(i);
                        }
                        for(int j=0;j<k;j++)
                        {
                            for(int ka=0;ka<4;ka++)
                            {
                                if(ss[j] == Answer.charAt(ka))
                                    BB++;
                            }
                        }
                        show[Count].setText(guessed+"     "+AA+'A'+BB+'B');
                    }
                    if(!flag && Count == 5)
                    {
                        ANS.setText("答案:" + Answer);
                        Toast.makeText(MainActivity.this, "只有5次機會，你輸囉~", Toast.LENGTH_LONG).show();
                        enter.setClickable(false);
                    }
                }
            }
        });
    }
}