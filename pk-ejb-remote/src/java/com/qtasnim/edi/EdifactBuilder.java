// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) definits fieldsfirst nonlb space lnc safe 
// Source File Name:   EdifactBuilder.java

package com.qtasnim.edi;


public interface EdifactBuilder {

	public abstract String buildAsString();

	public abstract String buildAsXML();
}
