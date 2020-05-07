package com.example.inventoria.ui.supplier.editor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventoria.R;
import com.example.inventoria.tools.SessionManager;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SupplierActivity extends AppCompatActivity implements SupplierView {

    SupplierPresenter presenter;
    ProgressDialog progressDialog;
    SessionManager session;

    String id_supplier, nama_supplier, no_telp, alamat;

    @BindView(R.id.nama_supplier)
    EditText et_nama_supplier;

    @BindView(R.id.no_telp)
    EditText et_no_telp;

    @BindView(R.id.alamat)
    EditText et_alamat;

    @BindView(R.id.content_simpan)
    LinearLayout content_simpan;

    @BindView(R.id.content_update)
    LinearLayout content_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);

        ButterKnife.bind(this);

        session = new SessionManager(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        presenter = new SupplierPresenter(this);

        initDataIntent();
        setTextEditor();
    }

    @OnClick(R.id.simpan) void simpan(){
        presenter.saveSupplier(

                et_nama_supplier.getText().toString(),
                et_no_telp.getText().toString(),
                et_alamat.getText().toString()
        );
    }

    @OnClick(R.id.update) void update() {
        presenter.updateSupplier(

                id_supplier,
                et_nama_supplier.getText().toString(),
                et_no_telp.getText().toString(),
                et_alamat.getText().toString()
        );
    }


    @OnClick(R.id.hapus) void hapus() {
        presenter.deleteSupplier(

                id_supplier
        );
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void statusSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void statusError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initDataIntent() {
        Intent intent= getIntent();
        id_supplier = intent.getStringExtra("id_supplier");
        nama_supplier = intent.getStringExtra("nama_supplier");
        no_telp = intent.getStringExtra("no_telp");
        alamat = intent.getStringExtra("alamat");
    }

    private void setTextEditor() {
        if (id_supplier != null) {
            getSupportActionBar().setTitle("Update data");
            et_nama_supplier.setText(nama_supplier);
            et_alamat.setText(alamat);
            et_no_telp.setText(no_telp);
            content_update.setVisibility(View.VISIBLE);
            content_simpan.setVisibility(View.GONE);
        } else {
            getSupportActionBar().setTitle("Simpan data");
        }
    }
}
