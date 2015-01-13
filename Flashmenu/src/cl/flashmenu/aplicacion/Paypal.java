package cl.flashmenu.aplicacion;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalOAuthScopes;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.ShippingAddress;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.System;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import carta.listaPlatos2;
import cliente.perfilCliente;

/**
 * Basic sample using the SDK to make a payment or consent to future payments.
 * 
 * For sample mobile backend interactions, see
 * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
 */
public class Paypal extends Activity {
	private static final String TAG = "paymentExample";
	/**
	 * - Set to PaymentActivity.ENVIRONMENT_PRODUCTION to move real money.
	 * 
	 * - Set to PaymentActivity.ENVIRONMENT_SANDBOX to use your test credentials
	 * from https://developer.paypal.com
	 * 
	 * - Set to PayPalConfiguration.ENVIRONMENT_NO_NETWORK to kick the tires
	 * without communicating to PayPal's servers.
	 */
	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;

	// note that these credentials will differ between live & sandbox environments.
	private static final String CONFIG_CLIENT_ID = "credential from developer.paypal.com";

	private static final int REQUEST_CODE_PAYMENT = 1;
	private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
	private static final int REQUEST_CODE_PROFILE_SHARING = 3;

	//desde intent
	//String idRest, usuario, mailRest, direccionRest, hora, fecha, idCliente, Cliente_email, mesa;
	TextView perfil, cerrar , perfilUsuario;

	TextView f, h, d, detallePro, totalPro;

	String productos = "", to;
	DecimalFormat fmt;

	int LastId;

	JSONParser jsonParser = new JSONParser();


	private static String url_create_Cliente = servidor.ip() + servidor.ruta2()+"nuevaReserva.php";
	private static String url_borrar_horario_mesa = servidor.ip() + servidor.ruta2()+"borrarHorarioMesa.php";


	int amount = 0;

	JSONParser jParser = new JSONParser();


	JSONParser jsonParser2 = new JSONParser();

	private static String url_email = servidor.ip() + servidor.ruta2()+"email.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_IDRESERVA = "LastId";

	/////para mail

	private static PayPalConfiguration config = new PayPalConfiguration()
	.environment(CONFIG_ENVIRONMENT)
	.clientId(CONFIG_CLIENT_ID)
	// The following are only used in PayPalFuturePaymentActivity.
	.merchantName("Hipster Store")
	.merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
	.merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paypal);


		perfilUsuario = (TextView) findViewById(R.id.nombreClientePAYPAL);
		perfilUsuario.setText(UserData.Cliente_email);		
		perfil = (TextView) findViewById(R.id.perfilPayPal);
		perfil.setOnClickListener(new View.OnClickListener() {


			@Override
			public void onClick(View v) {


				Intent i = new Intent(getApplicationContext(), perfilCliente.class);
				startActivity(i);

				//finish();
			}
		});

		fmt = new DecimalFormat();
		DecimalFormatSymbols fmts = new DecimalFormatSymbols();

		fmts.setGroupingSeparator('.');

		fmt.setGroupingSize(3);
		fmt.setGroupingUsed(true);
		fmt.setDecimalFormatSymbols(fmts);


		//

		

		f = ((TextView) findViewById(R.id.detallefecha));
		h = ((TextView) findViewById(R.id.detallehora));
		d = ((TextView) findViewById(R.id.detalledireccion));
		detallePro = (TextView) findViewById(R.id.detalleproductosComprados);
		totalPro = (TextView) findViewById(R.id.totalcompra);
		f.setText(UserData.Horarios_mesa_fecha);
		h.setText(UserData.Horarios_mesa_hora);
		d.setText(UserData.Rest_direccion);
		totalPro.setText("$" + fmt.format((UserData.PrecioPlatos()+UserData.PrecioBebidas()+UserData.PrecioPostres()+UserData.PrecioMenu())));
		amount = (UserData.PrecioPlatos()+UserData.PrecioBebidas()+UserData.PrecioPostres()+UserData.PrecioMenu());

		to = String.valueOf((UserData.PrecioPlatos()+UserData.PrecioBebidas()+UserData.PrecioPostres()+UserData.PrecioMenu()));




		HashMap<String, Object> extract = (HashMap<String, Object>)UserData.lista.get(0);
		/*UserData.lista.remove(0);
		//(String)extract.get(listaPlatos2.TAG_NOMBRE);


		while(!extract.isEmpty()){
			productos += (String)extract.get(listaPlatos2.TAG_NOMBRE) + ": " + (String)extract.get(listaPlatos2.TAG_PRECIO) + "\n";
			extract = (HashMap<String, Object>)UserData.lista.get(0);
			UserData.lista.remove(0);
		}*/
		for(int i=0;i<UserData.lista.size();i++){
			extract = (HashMap<String, Object>)UserData.lista.get(i);
			productos += (String)extract.get(UserData.TAG_NOMBRE_PRODUCTO) + ": " + "$" + fmt.format(Integer.parseInt((String)extract.get(UserData.TAG_PRECIO_PRODUCTO))) + "\n";
		}
		UserData.lista.clear();
		detallePro.setText(productos);




		//

		//		new CrearReserva().execute();
		//

		Intent intent = new Intent(this, PayPalService.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
		startService(intent);


	}

	

	class CrearReserva extends AsyncTask<String, String, String> {
		
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("Reserva_fecha", UserData.Horarios_mesa_fecha));
			params.add(new BasicNameValuePair("Reserva_hora", UserData.Horarios_mesa_hora));
			params.add(new BasicNameValuePair("Reserva_total", to));
			params.add(new BasicNameValuePair("Reserva_direccion", UserData.Rest_direccion));
			params.add(new BasicNameValuePair("Reserva_detalleProductos", productos));
			params.add(new BasicNameValuePair("Reserva_email", UserData.Rest_email));
			params.add(new BasicNameValuePair("Cliente_idCliente", UserData.idCliente));
			params.add(new BasicNameValuePair("Mesa_Nro_mesa", UserData.Mesa_nro));


			JSONObject json = jsonParser.makeHttpRequest(url_create_Cliente,"POST", params);
			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);
				LastId  = json.getInt(TAG_IDRESERVA);

				if (success == 1) {

					new enviarMail().execute();

				} else {
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	//



	////para email

	class enviarMail extends AsyncTask<String, String, String> {


		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();			
			params.add(new BasicNameValuePair("Cliente_email", UserData.Cliente_email));
			params.add(new BasicNameValuePair("Rest_email", UserData.Rest_email));
			params.add(new BasicNameValuePair("Nro_reserva", String.valueOf(LastId)));
			params.add(new BasicNameValuePair("Hora", UserData.Horarios_mesa_hora));
			params.add(new BasicNameValuePair("Fecha", UserData.Horarios_mesa_fecha));
			params.add(new BasicNameValuePair("Nro_mesa", UserData.Mesa_nro));
			params.add(new BasicNameValuePair("Rest_direccion", UserData.Rest_direccion));
			params.add(new BasicNameValuePair("idreserva", String.valueOf(LastId)));

			params.add(new BasicNameValuePair("productos", productos));



			JSONObject json = jsonParser2.makeHttpRequest(url_email,"POST", params);

			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

				} else {
					// fallo
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}


	}

	////para email



	//ELIMINAR HORARIO DE MESA
	class eliminarHorario extends AsyncTask<String, String, String> {
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("buscar1", UserData.Horarios_mesa_fecha));
			params.add(new BasicNameValuePair("buscar2", UserData.Horarios_mesa_hora));
			params.add(new BasicNameValuePair("buscar3", UserData.Mesa_nro));


			JSONObject json = jsonParser.makeHttpRequest(url_borrar_horario_mesa,"POST", params);
			Log.d("Create Response", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {


				} else {}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}
	}
	///ELIMINAR HORARIO DE MESA


	public void onBuyPressed(View pressed) {
		/* 
		 * PAYMENT_INTENT_SALE will cause the payment to complete immediately.
		 * Change PAYMENT_INTENT_SALE to 
		 *   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
		 *   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
		 *     later via calls from your server.
		 * 
		 * Also, to include additional payment details and an item list, see getStuffToBuy() below.
		 */

		PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

		/*
		 * See getStuffToBuy(..) for examples of some available payment options.
		 */

		Intent intent = new Intent(Paypal.this, PaymentActivity.class);

		intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

		startActivityForResult(intent, REQUEST_CODE_PAYMENT);
	}

	private PayPalPayment getThingToBuy(String paymentIntent) {
		return new PayPalPayment(new BigDecimal(amount), "USD", "Total del pedido: ",paymentIntent);
	}

	/* 
	 * This method shows use of optional payment details and item list.
	 */
	private PayPalPayment getStuffToBuy(String paymentIntent) {
		//--- include an item list, payment amount details
		PayPalItem[] items =
			{
				new PayPalItem("old jeans with holes", 2, new BigDecimal("87.50"), "USD",
						"sku-12345678"),
						new PayPalItem("free rainbow patch", 1, new BigDecimal("0.00"),
								"USD", "sku-zero-price"),
								new PayPalItem("long sleeve plaid shirt (no mustache included)", 6, new BigDecimal("37.99"),
										"USD", "sku-33333") 
			};
		BigDecimal subtotal = PayPalItem.getItemTotal(items);
		BigDecimal shipping = new BigDecimal("7.21");
		BigDecimal tax = new BigDecimal("4.67");
		PayPalPaymentDetails paymentDetails = new PayPalPaymentDetails(shipping, subtotal, tax);
		BigDecimal amount = subtotal.add(shipping).add(tax);
		PayPalPayment payment = new PayPalPayment(amount, "USD", "hipster jeans", paymentIntent);
		payment.items(items).paymentDetails(paymentDetails);

		//--- set other optional fields like invoice_number, custom field, and soft_descriptor
		payment.custom("This is text that will be associated with the payment that the app can use.");

		return payment;
	}

	/*
	 * Add app-provided shipping address to payment
	 */
	private void addAppProvidedShippingAddress(PayPalPayment paypalPayment) {
		ShippingAddress shippingAddress =
				new ShippingAddress().recipientName("Mom Parker").line1("52 North Main St.")
				.city("Austin").state("TX").postalCode("78729").countryCode("US");
		paypalPayment.providedShippingAddress(shippingAddress);
	}

	/*
	 * Enable retrieval of shipping addresses from buyer's PayPal account
	 */
	private void enableShippingAddressRetrieval(PayPalPayment paypalPayment, boolean enable) {
		paypalPayment.enablePayPalShippingAddressesRetrieval(enable);
	}

	public void onFuturePaymentPressed(View pressed) {
		Intent intent = new Intent(Paypal.this, PayPalFuturePaymentActivity.class);


		startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);
	}

	public void onProfileSharingPressed(View pressed) {
		Intent intent = new Intent(Paypal.this, PayPalProfileSharingActivity.class);
		intent.putExtra(PayPalProfileSharingActivity.EXTRA_REQUESTED_SCOPES, getOauthScopes());

		startActivityForResult(intent, REQUEST_CODE_PROFILE_SHARING);
	}

	private PayPalOAuthScopes getOauthScopes() {
		/* create the set of required scopes
		 * Note: see https://developer.paypal.com/docs/integration/direct/identity/attributes/ for mapping between the
		 * attributes you select for this app in the PayPal developer portal and the scopes required here.
		 */
		Set<String> scopes = new HashSet<String>(
				Arrays.asList(PayPalOAuthScopes.PAYPAL_SCOPE_EMAIL, PayPalOAuthScopes.PAYPAL_SCOPE_ADDRESS) );

		return new PayPalOAuthScopes(scopes);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_PAYMENT) {
			if (resultCode == Activity.RESULT_OK) {
				PaymentConfirmation confirm =
						data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
				if (confirm != null) {
					try {
						Log.i(TAG, confirm.toJSONObject().toString(4));
						Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));
						/**
						 *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
						 * or consent completion.
						 * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
						 * for more details.
						 *
						 * For sample mobile backend interactions, see
						 * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
						 */
						Toast.makeText(
								getApplicationContext(),
								"PaymentConfirmation info received from PayPal", Toast.LENGTH_LONG)
								.show();
						new CrearReserva().execute();
						new eliminarHorario().execute();
						//						System.out.println("reservandoooooooooooooooooooooooooooooo");


					} catch (JSONException e) {
						Log.e(TAG, "an extremely unlikely failure occurred: ", e);
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Log.i(TAG, "The user canceled.");
			} else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
				Log.i(
						TAG,
						"An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
			}
		} else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
			if (resultCode == Activity.RESULT_OK) {
				PayPalAuthorization auth =
						data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
				if (auth != null) {
					try {
						Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));

						String authorization_code = auth.getAuthorizationCode();
						Log.i("FuturePaymentExample", authorization_code);

						sendAuthorizationToServer(auth);
						Toast.makeText(
								getApplicationContext(),
								"Future Payment code received from PayPal", Toast.LENGTH_LONG)
								.show();

					} catch (JSONException e) {
						Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Log.i("FuturePaymentExample", "The user canceled.");
			} else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
				Log.i(
						"FuturePaymentExample",
						"Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
			} 
		} else if (requestCode == REQUEST_CODE_PROFILE_SHARING) {
			if (resultCode == Activity.RESULT_OK) {
				PayPalAuthorization auth =
						data.getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
				if (auth != null) {
					try {
						Log.i("ProfileSharingExample", auth.toJSONObject().toString(4));

						String authorization_code = auth.getAuthorizationCode();
						Log.i("ProfileSharingExample", authorization_code);

						sendAuthorizationToServer(auth);
						Toast.makeText(
								getApplicationContext(),
								"Profile Sharing code received from PayPal", Toast.LENGTH_LONG)
								.show();

					} catch (JSONException e) {
						Log.e("ProfileSharingExample", "an extremely unlikely failure occurred: ", e);
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Log.i("ProfileSharingExample", "The user canceled.");
			} else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
				Log.i(
						"ProfileSharingExample",
						"Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
			}
		}
	}

	private void sendAuthorizationToServer(PayPalAuthorization authorization) {

		/**
		 * TODO: Send the authorization response to your server, where it can
		 * exchange the authorization code for OAuth access and refresh tokens.
		 * 
		 * Your server must then store these tokens, so that your server code
		 * can execute payments for this user in the future.
		 * 
		 * A more complete example that includes the required app-server to
		 * PayPal-server integration is available from
		 * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
		 */

	}

	public void onFuturePaymentPurchasePressed(View pressed) {
		// Get the Application Correlation ID from the SDK
		String correlationId = PayPalConfiguration.getApplicationCorrelationId(this);

		Log.i("FuturePaymentExample", "Application Correlation ID: " + correlationId);

		// TODO: Send correlationId and transaction details to your server for processing with
		// PayPal...
		Toast.makeText(
				getApplicationContext(), "App Correlation ID received from SDK", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onDestroy() {
		// Stop service when done
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}
}
