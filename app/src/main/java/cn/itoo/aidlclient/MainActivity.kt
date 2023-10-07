package cn.itoo.aidlclient

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import cn.itoo.aidlserver.IDeviceInterface
import cn.itoo.aidlserver.Student

class MainActivity : ComponentActivity() {
    private val TAG = "AidlTestService"

    private var myAidlInterface: IDeviceInterface? = null

    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            myAidlInterface = IDeviceInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            myAidlInterface = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity);

        findViewById<View>(R.id.btn_set).setOnClickListener {
            if (myAidlInterface != null) {
                try {
                    myAidlInterface!!.addStudent(Student(1, "student_test", 26, 0))
                    myAidlInterface!!.helloString = "hello aidl!"
                    Toast.makeText(this, "set success", Toast.LENGTH_SHORT).show()
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "set fail", Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<View>(R.id.btn_get).setOnClickListener {
            if (myAidlInterface != null) {
                try {
                    Log.i(TAG, "getStudent: $" + myAidlInterface!!.getStudent(1));
                    Toast.makeText(this, myAidlInterface!!.helloString, Toast.LENGTH_SHORT).show()
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "get fail", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val intent = Intent()
        intent.component =
            ComponentName("cn.itoo.aidlserver", "cn.itoo.aidlserver.AidlTestService")
        val re = bindService(intent, connection, BIND_AUTO_CREATE)
        Log.i(TAG, "bindService: $re")
    }

    override fun onPause() {
        super.onPause()
        unbindService(connection)
    }
}
