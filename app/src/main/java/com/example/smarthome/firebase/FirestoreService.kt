package com.example.smarthome.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreService {

    companion object {
        private const val TAG = "FirestoreService"
        private const val COLLECTION = "Devices"
        private const val DOCUMENT = "rPZCGX1DhxBMFmmk8cOH"
    }

    private val db = FirebaseFirestore.getInstance()

    fun listenToDevices(
        onDataChanged: (Map<String, Any>) -> Unit
    ) {

        db.collection(COLLECTION)
            .document(DOCUMENT)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    Log.e(TAG, "Firestore Error", error)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {

                    val data = snapshot.data

                    if (data != null) {
                        onDataChanged(data)
                    }

                    Log.d(TAG, "Current Data : $data")
                }
            }
    }

    fun updateBooleanField(
        field: String,
        value: Boolean
    ) {

        db.collection(COLLECTION)
            .document(DOCUMENT)
            .update(field, value)
            .addOnSuccessListener {
                Log.d(TAG, "$field updated to $value")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Failed to update $field", e)
            }
    }
}