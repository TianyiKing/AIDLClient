// IDeviceInterface.aidl
package cn.itoo.aidlserver;

import cn.itoo.aidlserver.Student;

interface IDeviceInterface {

    // 传递基础类型
    void setHelloString(String string);  // 传值给服务端
    String getHelloString();             // 从服务端取值

    // 传递对象类型
    void addStudent(in Student student); // 传值给服务端
    Student getStudent(int id);          // 从服务端取值

}