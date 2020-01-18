package com.icosom.social;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.atom.mobilepaymentsdk.PayActivity;
import com.icosom.social.Interface.GetLastIdCallback;
import com.icosom.social.activity.DashboardRecharge;
import com.icosom.social.activity.MainActivity;
import com.icosom.social.activity.PassbookRecharge;
import com.icosom.social.utility.*;
import com.icosom.social.utility.RequestBuilder;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Net extends Activity implements View.OnClickListener {
    Button payMerchantNB;
    Button payMerchantDC;
    Spinner Bank;
    Spinner NetType;
    Spinner PaymentType;
    Spinner PaymentOption;
    String mprod;
    String amt = null;
    String strPayment = "";
    EditText et_nb_amt, et_Net_amt;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    AppController appController;
    String dates,date,statusss,AMOUNT;
    private String user_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        user_id = sp.getString("userId", "");

    }

    private String createXmlForProducts() throws Exception {
        // TODO Auto-generated method stub

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Here instead of parsing an existing document we want to
        // create a new one.
        Document testDoc = builder.newDocument();

        // This creates a new tag named 'testElem' inside
        // the document and sets its data to 'TestContent'
        ArrayList<String> lst = new ArrayList<String>();
        lst.add("1,One,250,1,2");
        lst.add("2,Two,250,1,2,3,4,5");

//		lst.add("3,Three,500");

//		String[] input = {"1,One,250,1,2,3,4,5", "2,Two,250,1,2,3,4,5", "3,Three,250,1,2,3,4,5"};
//		String[] line = new String[8];
        int doubleAmt = 0;
        Element products = testDoc.createElement("products");
        testDoc.appendChild(products);

        for (String s : lst) {
            String line[] = s.split(",");

//		for(int i = 0; i < lst.size(); i++){
//			
//			line = lst.get(i).split(",");
            Element product = testDoc.createElement("product");

            products.appendChild(product);

            Element id = testDoc.createElement("id");
            id.appendChild(testDoc.createTextNode(line[0]));
            product.appendChild(id);

            Element name = testDoc.createElement("name");
            name.appendChild(testDoc.createTextNode(line[1]));
            product.appendChild(name);

            Element amount = testDoc.createElement("amount");
            amount.appendChild(testDoc.createTextNode(line[2]));
            product.appendChild(amount);

            doubleAmt = doubleAmt + Integer.parseInt(line[2]);
//			amt = amt + line[2];
            amt = Integer.toString(doubleAmt);



            if (line.length > 3) {
                Element param1 = testDoc.createElement("param1");
                param1.appendChild(testDoc.createTextNode(line[3]));
                product.appendChild(param1);
            }

            if (line.length > 4) {
                Element param2 = testDoc.createElement("param2");
                param2.appendChild(testDoc.createTextNode(line[4]));
                product.appendChild(param2);
            }

            if (line.length > 5) {
                Element param3 = testDoc.createElement("param3");
                param3.appendChild(testDoc.createTextNode(line[5]));
                product.appendChild(param3);
            }

            if (line.length > 6) {
                Element param4 = testDoc.createElement("param4");
                param4.appendChild(testDoc.createTextNode(line[6]));
                product.appendChild(param4);
            }

            if (line.length > 7) {
                Element param5 = testDoc.createElement("param5");
                param5.appendChild(testDoc.createTextNode(line[7]));
                product.appendChild(param5);
            }
        }

        System.out.println("Total Amount :::" + amt);


        try {
            DOMSource source = new DOMSource(testDoc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(source, result);
            writer.flush();
            //		        	System.out.println( writer.toString()); 
            String s = writer.toString().split("\\?")[2].substring(1, writer.toString().split("\\?")[2].length());
            //		        	wslog.writelog(Priority.INFO,"passDetailsXmlRequest", s);

            System.out.println("Product XML : " + s);
            return s;
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("In On Resume");
        setContentView(R.layout.activity_net);


        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        edt = sp.edit();
        appController = (AppController) getApplicationContext();

        try {
            mprod = createXmlForProducts();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        et_nb_amt = (EditText) findViewById(R.id.et_nb_amt);
      //  et_nb_amt.setText(amt);




        Bank = (Spinner) findViewById(R.id.sp_bank);
        PaymentOption = (Spinner) findViewById(R.id.sp_payment_option);
        payMerchantNB = (Button) findViewById(R.id.btn_payMerchantNB);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
     date = sdf.format(new Date());


        SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMddHHmmss");
        dates = sdfs.format(new Date());


        PaymentOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                switch (position) {

                    case 1:

                        Bank.setVisibility(View.VISIBLE);
                        break;
                    case 2:

                        Bank.setVisibility(View.GONE);
                        break;

                    case 3:

                        Bank.setVisibility(View.GONE);
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


        payMerchantNB.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                amt = et_nb_amt.getText().toString();
                int amounts= Integer.parseInt(amt);
            //    updateBalance(amt);


                int payOption = PaymentOption.getSelectedItemPosition();


                switch (payOption) {
                    case 1:
                        strPayment = "NB";
                        break;
                    case 2:
                        strPayment = "IMPS";
                        break;
//				case 3:
//					strPayment = "WALLET";
//					break;
                }

                if (amt.equalsIgnoreCase("")) {
                    Toast.makeText(Net.this, "Please enter valid amount", Toast.LENGTH_LONG).show();
                }
			    else if(amounts<=10)
				{
					Toast.makeText(Net.this, "Amount must be greater than Rs 10", Toast.LENGTH_LONG).show();
				}
                else if (PaymentOption.getSelectedItemPosition() == 0) {
                    Toast.makeText(Net.this, "Please select Payment option", Toast.LENGTH_LONG).show();
                } else {

                    Double doubleAmt = Double.valueOf(amt);
                    amt = doubleAmt.toString();

                    String bankId = "2001";


//
                 Intent newPayIntent = new Intent(Net.this, PayActivity.class);

                    newPayIntent.putExtra("merchantId", "59906");
                    newPayIntent.putExtra("txnscamt", "0"); //Fixed. Must be �0�
                    newPayIntent.putExtra("loginid", "59906");

// 				    newPayIntent.putExtra("loginid", "2"); //for Multi product
                    newPayIntent.putExtra("password", "ISAMA@123");
                    newPayIntent.putExtra("prodid", "ISAMA");
//			        newPayIntent.putExtra("prodid", "Multi");
                    newPayIntent.putExtra("txncurr", "INR"); //Fixed. Must be �INR�
                    newPayIntent.putExtra("clientcode", encodeBase64("001"));
                    newPayIntent.putExtra("custacc", "100000036600");
                    newPayIntent.putExtra("amt", amt);//Should be 3 decimal number i.e 1.000
                    newPayIntent.putExtra("txnid", dates);
                    newPayIntent.putExtra("date", date);//Should be in same format
//			        newPayIntent.putExtra("bankid", bankId); //Should be valid bank id
                    newPayIntent.putExtra("discriminator", strPayment); //NB/ IMPS/ All
                    newPayIntent.putExtra("signature_request", "2d97535974393e18c1");
                    newPayIntent.putExtra("signature_response", "5f55b96189003c623b");

//			        //use below Production url only with Production "Library-MobilePaymentSDK", Located inside PROD folder
                    newPayIntent.putExtra("ru", "https://payment.atomtech.in/mobilesdk/param"); //ru FOR Production
                    //use below UAT url only with UAT "Library-MobilePaymentSDK", Located inside UAT folder
                    // newPayIntent.putExtra("ru", "https://paynetzuat.atomtech.in/mobilesdk/param"); // FOR UAT (Testing)

                    //Optinal Parameters
                    newPayIntent.putExtra("customerName", sp.getString("firstName", "")); //Only for Name
                    newPayIntent.putExtra("customerEmailID", "isamatechnolgy@gmail.com");//Only for Email ID
                    newPayIntent.putExtra("customerMobileNo", sp.getString("phone", ""));//Only for Mobile Number
                    newPayIntent.putExtra("billingAddress", "saharanpur");//Only for Address
                    newPayIntent.putExtra("optionalUdf9", "OPTIONAL DATA 1");// Can pass any data
                    newPayIntent.putExtra("mprod", mprod); // Pass data in XML format, only for Multi product

                    startActivityForResult(newPayIntent, 1);

//
                }
            }
        });


    }

    public String encodeBase64(String encode) {
        System.out.println("[encodeBase64] Base64 encode : " + encode);
        String decode = null;

        try {


//			decode= new sun.misc.BASE64Encoder().encode(encode.getBytes());
            decode = Base64.encode(encode.getBytes());
        } catch (Exception e) {
            System.out.println("Unable to decode : " + e);
        }
        return decode;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed here it is 2
        System.out.println("RESULTCODE--->" + resultCode);
        System.out.println("REQUESTCODE--->" + requestCode);
        System.out.println("RESULT_OK--->" + RESULT_OK);


        if (requestCode == 1) {
            System.out.println("---------INSIDE-------");

            if (data != null) {
                String message = data.getStringExtra("status");
                String[] resKey = data.getStringArrayExtra("responseKeyArray");
                String[] resValue = data.getStringArrayExtra("responseValueArray");

                if (resKey != null && resValue != null) {
                    for (int i = 0; i < resKey.length; i++)
                        System.out.println("  " + i + " resKey : " + resKey[i] + " resValue : " + resValue[i]);
                }
                if(message.equalsIgnoreCase("Transaction Successful!")){
                    System.out.println("RECEIVED BACK--->" + message);
                    // Toast.makeText(Net.this, ""+doubleAmt.intValue(), Toast.LENGTH_SHORT).show();

                    //  startActivity(new Intent(Net.this,DashboardRecharge.class));

                    updateBalance(amt);

                }
                else {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }
            }

        }

    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    private void updateBalance(final String balance){
        System.out.println("PayUMain: Update Balance");
        String key_a = "userId";
        //set user id here
        String value_a = sp.getString("userId", "1");
        String key_b = "balance";
        String value_b = balance;
        String key_c = "transactionId";
        //set user email here
        String value_c = dates;
        String key_d = "status";
        String value_d = "Add Money";
        String url = "https://icosom.com/wallet/main/androidProcess.php?action=updateWalletBalance";

        RequestBody requestBody;
        requestBody = com.icosom.social.utility.RequestBuilder.fourParameter(key_a, value_a, key_b, value_b, key_c, value_c, key_d, value_d);

        try {
            appController.PostTest(url, requestBody, new GetLastIdCallback() {
                @Override
                public void lastId(String id) {
                    try {
                        JSONObject jsonObject = new JSONObject(id);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                sendNotification_To_User(balance);

                              updateBalances(balance);
                               // startActivity(new Intent(Net.this, PassbookRecharge.class));
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }






    public void sendNotification_To_User(String amount)

    {

        String user_name=sp.getString("firstName", "")
                + " " + sp.getString("lastName", "");

        String device_token = sp.getString("device_token", "");//firebase token id


        String message="";
        String title="Icosom";


        message =user_name+" has "+" added"+" "+amount+" "+"Rs";

        System.out.println("kaif_SendNotificationMethod");
        System.out.println("message"+message);
        System.out.println("title"+title);
        System.out.println("device_token"+device_token);


        RequestBody body = new FormBody.Builder().
                add("send_to", "single").
                add("firebase_token", device_token).
                add("message", message).
                add("title", "icosom").
                add("image_url", "").
                add("action", "").
                add("user_id", user_id).

                build();

        Request request = new Request.Builder().
                url("https://icosom.com/kaif_notification/newindex.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                Net.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();

                System.out.println("notification_send_Success");
                /*try {
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("success").equals("1")) {
                        Log.e("notification_success","");
                        System.out.println("notification_success"+jo);
                    }
                    else
                    {
                        Log.e("notification__failed","");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        });
    }






    private void updateBalances(String balances){
        System.out.println("PayUMain: Update Balance");
        String key_a = "userId";
        //set user id here
        String value_a = sp.getString("userId", "1");
        String key_b = "amount";
        String value_b = balances;
        String key_c = "transactionId";
        String value_c = dates;
        String key_d = "number";
        String value_d = "1234567890";
        String key_e = "accNumb";
        String value_e =  date;
        String url = "https://icosom.com/wallet/main/dmrAndroidProcess.php?action=insertInfo";

        RequestBody requestBody;
        requestBody = RequestBuilder.fiveParameter( key_a,value_a,key_b,value_b,key_c,value_c,key_d,value_d,key_e,value_e);
      //  requestBody = com.icosom.social.utility.RequestBuilder.fiveParameter(key_a, value_a, key_b, value_b, key_c, value_c, key_d, value_d, key_e, value_e);

        try {
            appController.PostTest(url, requestBody, new GetLastIdCallback() {
                @Override
                public void lastId(String id) {
                    try {
                        Log.e("id", "lastId: +, "+id);
                        JSONObject jsonObject = new JSONObject(id);
                        statusss = jsonObject.getString("status");
                        final String message = jsonObject.getString("message");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(statusss.equalsIgnoreCase("success")) {
                                    Toast.makeText(Net.this, "Suceess", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Net.this, PassbookRecharge.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(Net.this, "Failure"+message, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Net.this, PassbookRecharge.class));
                                    finish();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Net.this, DashboardRecharge.class));

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(Net.this, MainActivity.class));
    }
}
