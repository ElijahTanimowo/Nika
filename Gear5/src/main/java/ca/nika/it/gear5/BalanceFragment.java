// CENG-322-0NC Francisco Santos n01423860, Pradeep Singh n00975892
// CENG-322-0NB Ralph Robes n01410324, Elijah Tanimowo n01433560
package ca.nika.it.gear5;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class BalanceFragment extends Fragment{

    PreferenceManager preferenceManager;
    EditText editTextNumber,editTextEXP,editTextCVV;
    AlertDialog dialog;

    public BalanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void loadImage() {
        preferenceManager = PreferenceManager.getInstance(getActivity());


        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(getString(R.string.SettingsPref), Context.MODE_PRIVATE);

        if (sharedPreferences != null) {

            String getUserId = sharedPreferences.getString(getString(R.string.userProfile), getString(R.string.blank));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);

        ImageButton gear500=(ImageButton) view.findViewById(R.id.gear5_coin_500);
        ImageButton gear1000=(ImageButton) view.findViewById(R.id.gear5_coin_1000);
        ImageButton gear2200=(ImageButton) view.findViewById(R.id.gear5_coin_2200);
        ImageButton gear5700=(ImageButton) view.findViewById(R.id.gear5_coin_5700);
        ImageButton gear11600=(ImageButton) view.findViewById(R.id.gear5_coin_11600);

        //check for which was pressed
        gear500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.gear500title)
                        .setMessage(R.string.gear500msg)
                        .setPositiveButton(R.string.Continue,new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                openDialog();
                            }
                        })
                        .setNegativeButton(R.string.cancel,null)
                        .show();
            }
        });

        gear1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.gear1000title)
                        .setMessage(R.string.gear1100msg)
                        .setPositiveButton(R.string.Continue,new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                openDialog();
                            }
                        })
                        .setNegativeButton(R.string.cancel,null)
                        .show();
            }
        });

        gear2200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.gear2200title)
                        .setMessage(R.string.gear2200msg)
                        .setPositiveButton(R.string.Continue,new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                openDialog();
                            }
                        })
                        .setNegativeButton(R.string.cancel,null)
                        .show();
            }
        });

        gear5700.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.gear5700title)
                        .setMessage(R.string.gear5700msg)
                        .setPositiveButton(R.string.Continue,new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                openDialog();
                            }
                        })
                        .setNegativeButton(R.string.cancel,null)
                        .show();
            }
        });

        gear11600.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.gear11600title)
                        .setMessage(R.string.gear11600msg)
                        .setPositiveButton(R.string.Continue,new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                openDialog();
                            }
                        })
                        .setNegativeButton(R.string.cancel,null)
                        .show();
            }
        });

        return view;
    }

    public void openDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.payment);

        View view = getLayoutInflater().inflate(R.layout.layout_dialog,null);

        editTextNumber = view.findViewById(R.id.edit_card_num);
        editTextEXP = view.findViewById(R.id.edit_card_exp);
        editTextCVV = view.findViewById(R.id.edit_card_cvv);

        builder.setView(view);

        dialog = builder.create();

        dialog.show();
    }

    /*(num.isEmpty()){
            editTextNumber.setError("Card Number is Required");
            editTextNumber.requestFocus();
            button.setEnabled(false);
        }

        if(num.length()!=16){
            editTextNumber.setError("Card Number length should be 16");
            editTextNumber.requestFocus();
            button.setEnabled(false);
        }

        if(exp.isEmpty()){
            editTextEXP.setError("Expiration Date is Required");
            editTextEXP.requestFocus();
            button.setEnabled(false);
        }

        if(exp.length()!=4){
            editTextNumber.setError("Expiration Date length should be 4");
            editTextNumber.requestFocus();
            button.setEnabled(false);
        }

        if(cvv.isEmpty()){
            editTextEXP.setError("CVV is Required");
            editTextEXP.requestFocus();
            button.setEnabled(false);
        }

        if(cvv.length()!=3){
            editTextCVV.setError("CVV length should be 3");
            editTextCVV.requestFocus();
            button.setEnabled(false);
        }*/

}