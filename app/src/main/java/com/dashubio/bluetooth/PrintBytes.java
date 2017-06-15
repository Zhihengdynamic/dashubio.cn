package com.dashubio.bluetooth;

import android.util.Log;

import com.dashubio.utils.log.Logger;

public class PrintBytes
{
  public static void printData(byte[] paramArrayOfByte)
  {
    Logger.i("************************");
    String str = "";
    int i = 0;
    while (true)
    {
      if (i >= paramArrayOfByte.length)
      {
        Logger.i("Data------>" + str);
        Logger.i("************************");
        return;
      }
      if ((i >= 3) && ((i - 2) % 7 == 1))
      {
        Log.i("Data", str);
        str = "";
      }
      str = str + " " + Integer.toHexString(paramArrayOfByte[i]);
      ++i;
    }
  }

  public static void printData(byte[] paramArrayOfByte, int paramInt)
  {
    Logger.i("************************");
    String str = "";
    int i = 0;
    while (true)
    {
      if (i >= paramInt)
      {
        Logger.i("Data------>" + str);
        Logger.i("************************");
        return;
      }
      str = str + " " + Integer.toHexString(paramArrayOfByte[i]);
      ++i;
    }
  }
}