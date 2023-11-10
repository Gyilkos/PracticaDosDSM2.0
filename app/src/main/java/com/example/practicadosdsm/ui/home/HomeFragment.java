package com.example.practicadosdsm.ui.home;

import android.content.Intent;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.practicadosdsm.databinding.FragmentHomeBinding;
import com.example.practicadosdsm.ui.dashboard.DashboardFragment;

import com.example.practicadosdsm.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private EditText nameImput, descriptInput;
    private ListView listConsolas;
    private Button agregarBtn;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> consolas;
    private ArrayList<String> consolasS;
    private TextView infConsola;

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
        consolasS = new ArrayList<>();
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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_navigation_home_to_navigation_dashboard);
            }
        });

        infConsola = (TextView) binding.infoConsola;
        listConsolas = (ListView) binding.listaConsola;

        listConsolas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                infConsola.setText(listConsolas.getItemAtPosition(i) + ".");
            }
        });
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
            consolas.add("Consola: " + consola + "\nDescripción: "+ descripcion);
            consolasS.add(consola + ", " + descripcion);
            adapter.notifyDataSetChanged();
            nameImput.setText("");
            descriptInput.setText("");

            Toast.makeText(getContext(),"Consola Agregada!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(),"Todos los Campos son Obligatorios!!", Toast.LENGTH_SHORT).show();
        }

    }


}