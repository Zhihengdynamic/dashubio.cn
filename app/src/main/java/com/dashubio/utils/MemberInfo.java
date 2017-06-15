package com.dashubio.utils;

/**
 * 会员信息的javabean
 */
public class MemberInfo {

    public int    _id;
    public String name;
    public String pwds;

    public MemberInfo(){}
    public MemberInfo(int _id,String name,String pwds){
        this._id = _id;
        this.name = name;
        this.pwds = pwds;
    }

    public int get_id() {
        return _id;
    }
}
