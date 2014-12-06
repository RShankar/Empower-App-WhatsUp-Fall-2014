package com.group2.whatsup.Helpers;

import java.util.HashMap;

public class LookupTable<A,B> {
    private HashMap<A, B> _aToBLookup;
    private HashMap<B, A> _bToALookup;

    public LookupTable(){
        _aToBLookup = new HashMap<A, B>();
        _bToALookup = new HashMap<B, A>();
    }

    public A GetKey(B val){
        if(_bToALookup.containsKey(val)){
            return _bToALookup.get(val);
        }
        return null;
    }

    public B GetVal(A key){
        if(_aToBLookup.containsKey(key)){
            return _aToBLookup.get(key);
        }

        return null;
    }

    public void Add(A key, B val){
        _aToBLookup.put(key, val);
        _bToALookup.put(val, key);
    }

    public void RemoveKey(A key){
        if(_aToBLookup.containsKey(key)){
            B val = _aToBLookup.get(key);
            _bToALookup.remove(val);
            _aToBLookup.remove(key);
        }
    }

    public void RemoveVal(B val){
        if(_bToALookup.containsKey(val)){
            A key = _bToALookup.get(val);
            _aToBLookup.remove(key);
            _bToALookup.remove(val);
        }

    }

}