package com.baidu.excel;

/**
 * @author mazhen <mazhen@kuaishou.com>
 * Created on 2019-04-14
 */
public class CategorySQLBO {

    private long id;
    private String name;
    private long pid;
    private int hierarchy;

    public CategorySQLBO(long id, String name, long pid, int hierarchy) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.hierarchy = hierarchy;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPid() {
        return pid;
    }

    public int getHierarchy() {
        return hierarchy;
    }
}
