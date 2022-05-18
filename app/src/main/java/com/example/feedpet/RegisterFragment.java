package com.example.feedpet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import java.security.SecureRandom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText user,dni,codigo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View vista;
    CheckBox recordar;
    Button ingresar;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button inicioSesion;
    private FirebaseFirestore db;
    private PresenterUsers presenterUsers;


    private FirebaseAuth mAuth;
    private PresenterRegister registerAdapter;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        mAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        registerAdapter = new PresenterRegister(this.getContext(), mAuth, databaseReference);
        db=FirebaseFirestore.getInstance();
        presenterUsers= new PresenterUsers(this.getContext(),db);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_register, container, false);
        inicializar();
        if(revisarSesion())
        {
            user.setText(this.preferences.getString("user",""));
            dni.setText(this.preferences.getString("dni",""));
        }else
        {
            //vista.Toast.makeText(this, "Iniciar Sesion", Toast.LENGTH_SHORT).show();
        }
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarSesion(recordar.isChecked());

                String UsNom = user .getText().toString().trim();
                String UsDni = dni .getText().toString().trim();
                String UsCod = codigo.getText().toString();
                if(UsCod.length()==0)
                {
                    UsCod=generateRandomString(10);
                }

                registerAdapter.signUpUser(UsNom,UsDni);
                presenterUsers.saveUser(UsNom,UsDni,UsCod);



            }
        });

        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(vista.getContext(), MainActivity2.class);
                intent1.putExtra("fragment",1);
                startActivity(intent1);
            }
        });


        return vista;
    }
    private boolean revisarSesion()
    {
        return this.preferences.getBoolean("sesion", false);
    }

    private void guardarSesion(boolean check)
    {
        editor.putBoolean("sesion", check);
        editor.putString("user",user.getText().toString());
        editor.putString("dni",dni.getText().toString());
        editor.apply();


    }
    private void inicializar(){
        preferences=getActivity().getPreferences(Context.MODE_PRIVATE);
        editor=preferences.edit();
        recordar=vista.findViewById(R.id.recordar);
        user=vista.findViewById(R.id.nombre);
        dni=vista.findViewById(R.id.dni);
        codigo=vista.findViewById(R.id.codigo);
        ingresar=vista.findViewById(R.id.registrar);
        inicioSesion=vista.findViewById(R.id.inicioSesion);
    }
    public static String generateRandomString(int length) {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";

        String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        SecureRandom random = new SecureRandom();

        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // 0-62 (exclusivo), retorno aleatorio 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }
}