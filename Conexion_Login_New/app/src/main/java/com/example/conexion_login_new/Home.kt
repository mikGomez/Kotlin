package com.example.conexion_login_new

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.conexion_login_new.databinding.ActivityHomeBinding
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.Random

class Home : AppCompatActivity() {
    /**************************DATOS DE PRUEBA ******************************************************************/
    var miArray:ArrayList<User> = ArrayList()  //Este será el arrayList que se usará para el adapter del RecyclerView o de la ListView.
    //Valores fake.
    val nombres = listOf("Ragnar","Ivar","Lagertha","Floki")
    val apellidos = listOf("Lothbrok","Sin huesos","Piel de Hierro","Semi diosa")
    val edades = listOf(18, 23, 45, 67, 34, 47, 41)

    lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseauth : FirebaseAuth
    val TAG = "MAGG"
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Para la autenticación, de cualquier tipo.
        firebaseauth = FirebaseAuth.getInstance()

        //Recuperamos los datos del login.
        binding.txtEmail.text = intent.getStringExtra("email").toString()
        binding.txtProveedor.text = intent.getStringExtra("provider").toString()
        binding.txtNombre.text = intent.getStringExtra("nombre").toString()

        binding.btCerrarSesion.setOnClickListener {
            Log.e(TAG, firebaseauth.currentUser.toString())
            // Olvidar al usuario, limpiando cualquier referencia persistente
            //comprobadlo en Firebase, como ha desaparecido.
//            firebaseauth.currentUser?.delete()?.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    firebaseauth.signOut()
//                    Log.e(TAG,"Cerrada sesión completamente")
//                } else {
//                    Log.e(TAG,"Hubo algún error al cerrar la sesión")
//                }
//            }
            firebaseauth.signOut()

            val signInClient = Identity.getSignInClient(this)
            signInClient.signOut()
            Log.e(TAG,"Cerrada sesión completamente")
            finish()

        }
        binding.btVolver.setOnClickListener {
            // Log.e(TAG, firebaseauth.currentUser.toString())
            firebaseauth.signOut()
            finish()
        }
        binding.btGuardar.setOnClickListener {
            //Se guardarán en modo HashMap (clave, valor).
            var user = hashMapOf(
                "provider" to binding.txtProveedor.text,
                "email" to binding.txtEmail.text.toString(),
                "name" to binding.edNombre.text.toString(),
                "age" to binding.edEdad.text.toString(),
                "roles" to arrayListOf(1, 2, 3),
                "timestamp" to FieldValue.serverTimestamp()
            )

            // Si no existe el documento lo crea, si existe lo remplaza.
            db.collection("users")
                .document(user.get("email").toString()) //Será la clave del documento.
                .set(user).addOnSuccessListener {
                    Toast.makeText(this, "Almacenado",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this, "Ha ocurrido un error",Toast.LENGTH_SHORT).show()
                }



            //Otra forma
            //Si no existe el documento lo crea, si existe añade otro. Las id serán asignadas automáticamente.
//            db.collection("users")
//                .add(user)
//                .addOnSuccessListener {
//                    Toast.makeText(this, "Almacenado", Toast.LENGTH_SHORT).show()
//                    Log.e(TAG, "Documento añadido con ID: ${it.id}")
//                }
//                .addOnFailureListener { e ->
//                    Log.w(TAG, "Error añadiendo documento", e.cause)
//                }

//            this.crearUnoAutomaticoConIndiceAleatorio()
//            this.crearUnoAutomaticoConIndiceNoAleatorio()
//            this.crearCamposArray()
        }
        //-------------- Eliminar ---------------------------------------------------------------
        binding.btEliminar.setOnClickListener {
            //Buscamos antes si existe un campo con ese email en un documento.
            val id = db.collection("users")
                .whereEqualTo("email",binding.txtEmail.text.toString())
                .get()
                .addOnSuccessListener {result ->
                    //En result, vienen los que cumplen la condición (si no pongo nada es it)
                    //Con esto borramos el primero.
                    //db.collection("users").document(result.elementAt(0).id).delete().toString()
                    //Con esto borramos todos. No olvidar que id aquí no es una Primarykey, puede repetirse.
                    for (document in result) {
                        db.collection("users")
                            .document(document.id)
                            .delete().toString() //lo importante aquí es el delete. el toString es pq además devuelve un mensaje con lo sucedido.
                    }

                    Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this, "No se ha encontrado el documento a eliminar", Toast.LENGTH_SHORT).show()
                }

        }
        //----------------------------- Boton recuperar -----------------------
        binding.btRecuperar.setOnClickListener {
            var roles : ArrayList<Int>
            //Búsqueda por id del documento.
            db.collection("users")
                .document(binding.txtEmail.text.toString())
                .get()
                .addOnSuccessListener {
                    //Si encuentra el documento será satisfactorio este listener y entraremos en él.
                    binding.edNombre.setText(it.get("name") as String?)
                    binding.edEdad.setText(it.get("age") as String?)
                    if (it.get("roles")!=null) {
                        roles = it.get("roles") as ArrayList<Int>
                        Log.e(TAG,roles.toString())
                        binding.txtRoles.text=roles.toString()
                    }
                    else {
                        Log.e(TAG, "Sin roles")
                        binding.txtRoles.text="SIN ROLES"
                    }

                    Toast.makeText(this, "Recuperado", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
                }
            //Búsqueda por un campo del documento, con whereEqualTo, en vez de por id.
            // Habría que asegurar que es un campo no repetido y la búsqueda sólo trae un documento, si no, se lee el primero
//            db.collection("users")
//                //.document(binding.txtEmail.text.toString())
//                .whereEqualTo("age", binding.edEdad.text.toString())
//                .get()
//                .addOnSuccessListener {
//                    //Si encuentra el documento será satisfactorio este listener y entraremos en él.
//
//                    Log.d(TAG, "${it.documents[0].id} => ${it.documents[0].data}")
//                    binding.edNombre.setText(it.documents[0].get("name") as String?)
//                    binding.edEdad.setText(it.documents[0].get("age") as String?)
//                    if (it.documents[0].get("roles")!=null) {
//                        roles = it.documents[0].get("roles") as ArrayList<Int>
//                        Log.e(TAG,roles.toString())
//                        binding.txtRoles.text=roles.toString()
//                    }
//                    else {
//                        Log.e(TAG, "Sin roles")
//                        binding.txtRoles.text="SIN ROLES"
//                    }
//
//                    Toast.makeText(this, "Recuperado", Toast.LENGTH_SHORT).show()
//                }.addOnFailureListener{
//                    Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
//                }
        }

        //----------------------- Boton recuperar Todo
        //https://cloud.google.com/firestore/docs/query-data/queries?hl=es-419#kotlin+ktxandroid_3
        binding.btRecuperarTodos.setOnClickListener {
            //Sin Corrutinas
//            var al = ArrayList<String>()
//            db.collection("users")
//                //.whereEqualTo("name",binding.edNombre.text)
//                .get()
//                .addOnSuccessListener {
//                    for (document in it) {
//                        al.add(document.data.toString())
//                        Log.d(TAG, "${document.id} => ${document.data}")
////                        var us = User(document.get("age") as String,document.get("first") as String,document.get("last") as String,document.get("roles") as ArrayList<Int>)
////                        this.miArray.add(us)
//                        Log.d(TAG, al.toString())//Se va construyendo el array, y lo mostramos
//                    }
//
//                }
//                .addOnFailureListener { exception ->
//                    Log.w(TAG, "Error getting documents.", exception)
//                }
//            Log.e(TAG, "Aunque el código va después de la llamada el ArrayList está vacío: ${al.toString()}")

            //Observamos que esto nos da un AL vacío porque la consulta es asíncrona y necesitaremos hacerla con una corrutina.


            //Corrutinas 1.
//            var al = ArrayList<String>()
//            GlobalScope.launch(Dispatchers.IO) {
//                try {
//                    val querySnapshot = db.collection("users")
//                        .get()
//                        .await()
//
//                    val results = mutableListOf<String>()
//
//                    for (document in querySnapshot.documents) {
//                        Log.d(TAG, "${document.id} => ${document.data}")
//                        al.add(document.data.toString())
//                    }
//
//                    // Realiza acciones en el hilo principal
//                    launch(Dispatchers.Main) {
//                        // Procesa los resultados aquí
//                        Log.e(TAG, "Esto está en el hilo principal rellenado después de la corrutina: ${al.toString()}")
//                    }
//                } catch (e: Exception) {
//                    // Maneja errores aquí
//                    e.printStackTrace()
//                }
//            }
//
//            //Corrutinas 2.
            runBlocking {
                val job : Job = launch(context = Dispatchers.Default) {
                    val datos : QuerySnapshot = getDataFromFireStore() as QuerySnapshot //Obtenermos la colección
                    obtenerDatos(datos as QuerySnapshot?)  //'Destripamos' la colección y la metemos en nuestro ArrayList

                }
                //Con este método el hilo principal de onCreate se espera a que la función acabe y devuelva la colección con los datos.
                job.join() //Esperamos a que el método acabe: https://dzone.com/articles/waiting-for-coroutines
            }
            Log.e(TAG,"----------------")
            for(e in miArray){//mostramos el array para demostrar que trae datos.
                Log.d(TAG,e.toString())
            }
            Log.e(TAG,"----------------")
            //Aquí se pondría en el setAdapter del RecyclerView.
            Toast.makeText(this, "Datos cargados, consulta el log con la etiqueta ACSCO",Toast.LENGTH_SHORT).show()

        }//Fin del btnRecuperar.Onclick

    }//------------------------ Fin OnCreate ---------------------------------------------------

    //*****************************************************************************************************************
    //********************** Métodos de creación/modificación/borrado de datos ****************************************
    //*****************************************************************************************************************
    /**
     * Este método crea datos al azar con índice aleatorio y usando la opción add.
     */
    fun crearUnoAutomaticoConIndiceAleatorio(){
        // Create a new user with a first and last name
        val randomNombre = nombres.random()
        val randomApellido = apellidos.random()
        val edadRandom = edades.random()

        var user = hashMapOf(
            "first" to randomNombre,
            "last" to randomApellido,
            "age" to edadRandom
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.e(TAG, "Documento añadido con ID: ${it.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e.cause)
            }
    }

    //----------------------------------------------------------------------------------------
    /**
     * Este método crea datos al azar usando set y poniendo como índice del documento el DNI.
     */
    fun crearUnoAutomaticoConIndiceNoAleatorio(){
        // Create a new user with a first and last name
        val randomNombre = nombres.random()
        val randomApellido = apellidos.random()
        val edadRandom = edades.random()
        val random = Random()
        val DNI = random.nextInt(100)

        var user = hashMapOf(
            "first" to randomNombre,
            "last" to randomApellido,
            "age" to edadRandom
        )

        // Add a new document with a generated ID
        db.collection("users")
            .document(DNI.toString())  //Si hubiera un campo duplicado, lo reemplaza.
            .set(user)
            .addOnSuccessListener {
                Log.e(TAG, "Documento añadido.")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e.cause)
            }
    }


    //----------------------------------------------------------------------------------------
    /**
     * Este método crea un documento que dentro tiene un campo de tipo array (para los roles).
     */
    fun crearCamposArray(){
        // Create a new user with a first and last name
        val randomNombre = nombres.random()
        val randomApellido = apellidos.random()
        val edadRandom = edades.random()
        val random = Random()
        val DNI = random.nextInt(100)

        var user = hashMapOf(
            "first" to randomNombre,
            "last" to randomApellido,
            "age" to edadRandom,
            "roles" to arrayListOf(1, 2, 3)
        )

        // Add a new document with a generated ID
        db.collection("users")
            .document(DNI.toString())  //Si hubiera un campo duplicado, lo remplaza.
            .set(user)
            .addOnSuccessListener {
                Log.e(TAG, "Documento añadido.")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e.cause)
            }
    }
    suspend fun getDataFromFireStore()  : QuerySnapshot? {
        return try{
            val data = db.collection("users")
                //.whereEqualTo("name", "Lagertha")
                .whereGreaterThanOrEqualTo("age",18)  //https://firebase.google.com/docs/firestore/query-data/order-limit-data?hl=es-419
                .orderBy("age", Query.Direction.DESCENDING)
                //.limit(4) //Limita la cantidad de elementos mostrados.
                .get()
                .await()
            data
        }catch (e : Exception){
            null
        }
    }

    /**
     * Función que recuperará los datos obtenidos del método: getDataFromFireStore().
     * Llamada también desde el entorno de corrutinas: (ver onCreate, runBlocking).
     */
    private fun obtenerDatos(datos: QuerySnapshot?) {
        for(dc: DocumentChange in datos?.documentChanges!!){
            if (dc.type == DocumentChange.Type.ADDED){
                //miAr.add(dc.document.toObject(User::class.java))
                var roles : ArrayList<Int>

                if (dc.document.get("roles") != null){
                    roles = dc.document.get("roles") as ArrayList<Int>
                }
                else {
                    roles = arrayListOf()
                }
                var al = User(
                    dc.document.get("age").toString(),
                    dc.document.get("first").toString(),
                    dc.document.get("last").toString(),
                    roles
                )
                //Log.e(TAG, al.toString())
                miArray.add(al)
            }
        }
    }
}