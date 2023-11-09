package com.example.practicadosdsm.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.practicadosdsm.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private EditText nameImput, descriptInput;
    private ListView listConsolas;
    private Button agregarBtn;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> consolas;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        nameImput = binding.nomInput;
        descriptInput = binding.descriptionInput;
        listConsolas = binding.listaConsola;
        agregarBtn = binding.insertBtn;

        consolas = new ArrayList<>();
        adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, consolas);
        listConsolas.setAdapter(adapter);

        binding.insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //insertar la logica de lo que hace el boton
                insertarList();
            }
        });
        // TODO: 09-11-2023 hacer que al seleccionar la lista, muestra la info de cada consola
        listConsolas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String descripcion = descriptInput.getText().toString();
                Toast.makeText(getContext(),descripcion,Toast.LENGTH_SHORT).show();
            }
        });

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void insertarList(){

        String consola = nameImput.getText().toString();
        String descripcion = descriptInput.getText().toString();
        if (!consola.isEmpty() && !descripcion.isEmpty()){
            consolas.add("Consola: " + consola + "\nDescripción no va: "+ descripcion);
            adapter.notifyDataSetChanged();
            nameImput.setText("");
            descriptInput.setText("");

            Toast.makeText(getContext(),"Consola Agregada!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(),"Todos los Campos son Obligatorios!!", Toast.LENGTH_SHORT).show();
        }

    }

}