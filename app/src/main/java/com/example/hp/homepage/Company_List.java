package com.example.hp.homepage;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Company_List extends AppCompatActivity implements companyAdapter.OnItemClickListener {

    Dialog myDialog;
    Button refreshButton;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference companyRef = db.collection("Companies");
    private companyAdapter adapter;

    private Button  btn, btnscroll;

    Spinner companylist_branches;
    String branch_search;

    TextView textView, dispplayid;
    TextView display;
    FirebaseAuth firebaseAuth;
    String cgpa, userId,data, mycg;
    private LinearLayout llPdf;
    private Bitmap bitmap;

    /*

           <Button
            android:id="@+id/exportpdf"
            android:layout_margin="10dp"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:background="@drawable/buttonstylepink"
            android:text="Export To PDF"
            android:textColor="#fff" />


    */

    private RelativeLayout rlContainer;

    private int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company__list);

        GlobalClass globalClass= (GlobalClass) getApplicationContext();
        myDialog= new Dialog(this);

        //btn= findViewById(R.id.exportpdf);
        rlContainer = findViewById(R.id.rlContainer);

        setUpRecyclerView();
/*
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("size"," "+llPdf.getWidth() +"  "+llPdf.getWidth());
                bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
                createPdf();
            }
        });

        */

    }
/*
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        width = rlContainer.getWidth();
        height = rlContainer.getHeight();

        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c1 = new Canvas(b);
        rlContainer.draw(c1);

        PdfDocument pd = new PdfDocument();

        PdfDocument.PageInfo pi = new PdfDocument.PageInfo.Builder(width, height, 1).create();
        PdfDocument.Page p = pd.startPage(pi);
        Canvas c = p.getCanvas();
        c.drawBitmap(b, 0, 0, new Paint());
        pd.finishPage(p);

        try {
            //make sure you have asked for storage permission before this
            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "a-computer-engineer-pdf-test.pdf");
            pd.writeTo(new FileOutputStream(f));
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        pd.close();
    }

    */

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        // write the document content
        String targetPdf = "/storage/emulated/0/pdffromlayout.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF();

    }

    private void openGeneratedPDF(){
        File file = new File("/storage/emulated/0/pdffromlayout.pdf");
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(Company_List.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void setUpRecyclerView(){

        data="10";

        Float result= Float.valueOf(data);
        Query query = companyRef.whereLessThanOrEqualTo("mincg", result);
        FirestoreRecyclerOptions<Company> options = new FirestoreRecyclerOptions.Builder<com.example.hp.homepage.Company>()
                .setQuery(query, com.example.hp.homepage.Company.class)
                .build();

        adapter = new com.example.hp.homepage.companyAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.comppanylist_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }


    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected  void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void OnItemCLick(final DocumentSnapshot documentSnapshot, int position) {
      TextView txtclose;
        TextView PopupName;
        TextView cgdisp;
        TextView description;
        Button btnapply;
        myDialog.setContentView(R.layout.custompupup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        PopupName= (TextView) myDialog.findViewById(R.id.popupnm);
        cgdisp= (TextView) myDialog.findViewById(R.id.popupcg);
        description= (TextView) myDialog.findViewById(R.id.popupdesc);

        Company company= documentSnapshot.toObject(Company.class);
        String nm= documentSnapshot.getId();
        final String descpop= documentSnapshot.getString("description");


        double cg= documentSnapshot.getDouble("mincg");
        final String dispcg= String.valueOf(cg);

        PopupName.setText(nm);
        cgdisp.setText(dispcg);
        description.setText(descpop);

        btnapply = (Button) myDialog.findViewById(R.id.btnapply);

        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Company_List.this, "Showing Company Details...", Toast.LENGTH_SHORT).show();
                GlobalClass globalClass= (GlobalClass) getApplicationContext();
                globalClass.setCompanyid(documentSnapshot.getId());
                globalClass.setCompany_cg_criteria(dispcg);
                globalClass.setCompany_description(descpop);

                Intent i;
                i= new Intent(Company_List.this, CompanyDetailsAdmin.class); startActivity(i);

            }
        });


        //
        txtclose.setText("X");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }


}
