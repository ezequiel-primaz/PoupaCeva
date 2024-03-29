package com.example.poupaceva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText edt_seiscentos;
    EditText edt_quatro_sete_tres;
    EditText edt_tres_cinco_zero;
    Button btn_calcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_seiscentos = findViewById(R.id.edt_seiscentos);
        edt_quatro_sete_tres = findViewById(R.id.edt_quatro_sete_tres);
        edt_tres_cinco_zero = findViewById(R.id.edt_tres_cinco_zero);
        btn_calcular = findViewById(R.id.btn_calcular);

        btn_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float valor_ml_seiscentos;
                float valor_ml_quatrocentos;
                float valor_ml_trezentos;

                // Verifica quais valores foram inseridos e prepara o sistema para tratar os não inseridos
                if (TextUtils.isEmpty(edt_seiscentos.getText().toString())) {
                    valor_ml_seiscentos = 0F;
                } else {
                    valor_ml_seiscentos = Float.parseFloat(edt_seiscentos.getText().toString().trim())/600;
                }

                if (TextUtils.isEmpty(edt_quatro_sete_tres.getText().toString())) {
                    valor_ml_quatrocentos = 0F;
                } else {
                    valor_ml_quatrocentos = Float.parseFloat(edt_quatro_sete_tres.getText().toString().trim())/473;
                }

                if (TextUtils.isEmpty(edt_tres_cinco_zero.getText().toString())) {
                    valor_ml_trezentos = 0F;
                } else {
                    valor_ml_trezentos = Float.parseFloat(edt_tres_cinco_zero.getText().toString().trim()) / 350;
                }

                // Verifica se há ao menos 2 valores inseridos
                if(!hasMinQuantValues(valor_ml_seiscentos, valor_ml_quatrocentos, valor_ml_trezentos)){
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.app_insira_dois_valores), Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                Resultado r = new Resultado(valor_ml_seiscentos, valor_ml_quatrocentos, valor_ml_trezentos);
                intent.putExtra("value", r);
                startActivity(intent);
            }
        });
    }



    private boolean hasMinQuantValues(float valor_ml_seiscentos, float valor_ml_quatrocentos, float valor_ml_trezentos) {
        int count = 0;

        if (valor_ml_seiscentos != 0F) {
            count++;
        }

        if (valor_ml_quatrocentos != 0F) {
            count++;
        }

        if (valor_ml_trezentos != 0F) {
            count++;
        }

        return count > 1;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_GetData) {
            String url = "https://poupa-ceva.herokuapp.com/getPrice";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                edt_seiscentos.setText(response.getString("seiscentos"));
                                edt_quatro_sete_tres.setText(response.getString("quatrocentos"));
                                edt_tres_cinco_zero.setText(response.getString("trezentos"));
                            } catch(JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.app_falha_ao_obter_dados), Toast.LENGTH_LONG).show();
                        }
                    });

            // Add a request (in this example, called stringRequest) to your RequestQueue.
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
