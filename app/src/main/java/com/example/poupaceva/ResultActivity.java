package com.example.poupaceva;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    TextView txtPrecoLitroSeiscentos;
    TextView txtPrecoLitroQuatrocentos;
    TextView txtPrecoLitroTrezentos;
    TextView txtComprarEste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        txtPrecoLitroSeiscentos = findViewById(R.id.txtPrecoLitroSeiscentos);
        txtPrecoLitroQuatrocentos = findViewById(R.id.txtPrecoLitroQuatrocentos);
        txtPrecoLitroTrezentos = findViewById(R.id.txtPrecoLitroTrezentos);
        txtComprarEste = findViewById(R.id.txtComprarEste);

        Resultado result = (Resultado) getIntent().getSerializableExtra("value");

        if(result != null) {
            // Seta o melhor cuso/benefício na área de resultado
            txtComprarEste.setText(checkCheapestBeer(result.seiscentos, result.quatrocentos, result.trezentos));

            // Set valor do litro para a área de resultados
            if (result.seiscentos == 0F) {
                txtPrecoLitroSeiscentos.setText(getResources().getString(R.string.app_nao_inserido));
                txtPrecoLitroQuatrocentos.setText(String.format(Locale.ENGLISH,"%.02f",result.quatrocentos * 1000));
                txtPrecoLitroTrezentos.setText(String.format(Locale.ENGLISH,"%.02f",result.trezentos * 1000));
            } else if (result.quatrocentos == 0F) {
                txtPrecoLitroSeiscentos.setText(String.format(Locale.ENGLISH,"%.02f",result.seiscentos * 1000));
                txtPrecoLitroQuatrocentos.setText(getResources().getString(R.string.app_nao_inserido));
                txtPrecoLitroTrezentos.setText(String.format(Locale.ENGLISH,"%.02f",result.trezentos * 1000));
            } else if (result.trezentos == 0F) {
                txtPrecoLitroSeiscentos.setText(String.format(Locale.ENGLISH,"%.02f",result.seiscentos * 1000));
                txtPrecoLitroQuatrocentos.setText(String.format(Locale.ENGLISH,"%.02f",result.quatrocentos * 1000));
                txtPrecoLitroTrezentos.setText(getResources().getString(R.string.app_nao_inserido));
            } else {
                txtPrecoLitroSeiscentos.setText(String.format(Locale.ENGLISH,"%.02f",result.seiscentos * 1000));
                txtPrecoLitroQuatrocentos.setText(String.format(Locale.ENGLISH, "%.02f", result.quatrocentos * 1000));
                txtPrecoLitroTrezentos.setText(String.format(Locale.ENGLISH,"%.02f",result.trezentos * 1000));
            }
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.app_erro_ao_carregar_valores), Toast.LENGTH_LONG).show();
        }
    }

    public String checkCheapestBeer(float valor_ml_seiscentos, float valor_ml_quatrocentos, float valor_ml_trezentos) {

        String compreTrezentos = getResources().getString(R.string.app_compre_trezentos);
        String compreQuatrocentos = getResources().getString(R.string.app_compre_quatrocentos);
        String compreSeiscentos = getResources().getString(R.string.app_compre_seiscentos);

        if (valor_ml_seiscentos == 0F) {
            if (valor_ml_quatrocentos <= valor_ml_trezentos) {
                return compreQuatrocentos;
            } else {
                return compreTrezentos;
            }
        }

        if (valor_ml_quatrocentos == 0F) {
            if (valor_ml_seiscentos <= valor_ml_trezentos) {
                return compreSeiscentos;
            } else {
                return compreTrezentos;
            }
        }

        if (valor_ml_trezentos == 0F) {
            if (valor_ml_seiscentos <= valor_ml_quatrocentos) {
                return compreSeiscentos;
            } else {
                return compreQuatrocentos;
            }
        }

        if (valor_ml_seiscentos <= valor_ml_quatrocentos && valor_ml_seiscentos <= valor_ml_trezentos) {
            return compreSeiscentos;
        } else if (valor_ml_quatrocentos < valor_ml_seiscentos && valor_ml_quatrocentos <= valor_ml_trezentos) {
            return compreQuatrocentos;
        } else {
            return compreTrezentos;
        }

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
