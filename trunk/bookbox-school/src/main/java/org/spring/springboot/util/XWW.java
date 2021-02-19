package org.spring.springboot.util;

import java.io.IOException;
import java.io.RandomAccessFile;

public class XWW {
	public String name;
    public String id;
    public String text;

    public XWW() {
    }

    public XWW(String name, String id, String text) {
        this.name = name;
        this.id = id;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    //将三个数据进行写入，同时换行
    public void write(RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.writeUTF(name);
        randomAccessFile.writeUTF(id);
        randomAccessFile.writeUTF(text);
        randomAccessFile.writeUTF("\n");
    }

    //将三个数据读取文件
    public void read(RandomAccessFile randomAccessFile) throws IOException {
        this.name=randomAccessFile.readUTF();
        this.id=randomAccessFile.readUTF();
        this.text=randomAccessFile.readUTF();
    }
    //修改名字
    public void write2(RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.writeUTF(name);
    }
}
