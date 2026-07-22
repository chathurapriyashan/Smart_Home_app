package com.example.smarthome

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.smarthome.navigation.NavigationGraph
import com.example.smarthome.ui.theme.SmartHomeTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

val DocumentId = "rPZCGX1DhxBMFmmk8cOH"
val CollectionName = "Devices"
val db = Firebase.firestore



fun getRealTimeData(){
    val TAG = "MainActivity-DATABASE_REALTIME"
    val docRef = db.collection(CollectionName).document(DocumentId)
    docRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w(TAG, "Listen failed.", e)
            return@addSnapshotListener
        }

        if (snapshot != null && snapshot.exists()) {
//            val houseDevices = snapshot.toObject(HouseDevices::class.java)
//            Log.d(TAG, "Current data: $houseDevices")
        } else {
            Log.d(TAG, "Current data: null")
        }
    }
}


fun getData(){
    val TAG = "MainActivity-DATABASE"
    val docRef = db.collection(CollectionName).document(DocumentId)
    docRef.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
            } else {
                Log.d(TAG, "No such document")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
        }
}


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        getRealTimeData();

        setContent {

            SmartHomeTheme {


                NavigationGraph()


            }

        }

    }

}