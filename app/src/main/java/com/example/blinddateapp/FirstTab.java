package com.example.blinddateapp;

import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstTab extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstTab() {
        // Required empty public constructor
    }
    public static FirstTab getInstance(){
        FirstTab d = new FirstTab();
        return d;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstTab.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstTab newInstance(String param1, String param2) {
        FirstTab fragment = new FirstTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView recyclerView;
    public static FirstTabAdapter adapter;
    private ArrayList<ContactsVO> list = new ArrayList<>();
    private ArrayList<ContactsVO> arraylist = new ArrayList<>();
    private EditText searchName;
    private TextView textView2;
    private FloatingActionButton dialogOpenButton;

    Gson gson = new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_first_tab, container, false);
        // Inflate the layout for this fragment
        recyclerView = rootView.findViewById(R.id.recyclerview);
        searchName = rootView.findViewById(R.id.searchName);
        textView2 =rootView.findViewById(R.id .textView2);
        dialogOpenButton = rootView.findViewById(R.id.dialogOpenButton);

        list = getContactList();
        adapter = new FirstTabAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        arraylist = list;

        //EditText ?????? ?????????
        searchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input?????? ????????? ?????????????????? ????????????.
                // search ???????????? ????????????.
                String text = searchName.getText().toString();
                //textView2.setText("sf");
                search(text);
            }
        });


        //onClick ?????????
        dialogOpenButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FirstTabDialog d = FirstTabDialog.getInstance();
                d.show(getActivity().getSupportFragmentManager(), FirstTabDialog.TAG_EVENT_DIALOG);
            }
        });


        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<ContactsVO> getContactList() {
        ArrayList<ContactsVO> list_contacts = new ArrayList<>();
        Gson gson = new Gson();
        AssetManager assetManager = getResources().getAssets();

        try {
            FileInputStream fis = new FileInputStream("/data/data/com.example.blinddateapp/test.json");
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            String json = new String(buffer, "UTF-8");
            //textView2.setText(json);
            JSONObject jsonObject = new JSONObject(json);

            JSONArray jsonArray = jsonObject.getJSONArray("contacts");

            int index = 0;
            while (index < jsonArray.length()) {

                ContactsVO contactsVO = gson.fromJson(jsonArray.get(index).toString(), ContactsVO.class);

                /*while (index == 0) {
                    list_contacts.add(contactsVO);
                    index++;
                }*/

                int sortIndex = 0;
                while (sortIndex < list_contacts.size()) {
                    int compareResult = list_contacts.get(sortIndex).name.compareTo(contactsVO.name);
                    if (compareResult > 0) break;
                    else sortIndex++;
                }
                list_contacts.add(sortIndex, contactsVO);

                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list_contacts;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void search(String charText) {

        arraylist = getContactList();
        // ?????? ??????????????? ???????????? ????????? ?????? ????????????.
        list.clear();
        // ?????? ????????? ???????????? ?????? ???????????? ????????????.
        if (charText.length() == 0) {
            list.addAll(arraylist);
            //textView2.setText("sf");
        }
        // ?????? ????????? ??????..
        else {
            // ???????????? ?????? ???????????? ????????????.
            for (int i = 0; i < arraylist.size(); i++) {
                // arraylist??? ?????? ???????????? ???????????? ??????(charText)??? ???????????? ????????? true??? ????????????.
                if (arraylist.get(i).name.contains(charText)) {
                    // ????????? ???????????? ???????????? ????????????.
                    list.add(arraylist.get(i));
                    //textView2.setText("sf");
                }
            }
        }
        // ????????? ???????????? ????????????????????? ???????????? ???????????? ????????? ???????????? ????????? ????????????.
        adapter.notifyDataSetChanged();
    }
}